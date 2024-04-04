package com.tuneconnect.Service;
import com.tuneconnect.DTO.Request.EventRequestDTO;
import com.tuneconnect.DTO.Response.EventResponseDTO;
import com.tuneconnect.Entity.Event;
import com.tuneconnect.Handler.AlbumException;
import com.tuneconnect.Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<EventResponseDTO> getAll() throws Exception {
        try {

            List<Event> eventList = eventRepository.findAll();
            List<EventResponseDTO> eventResponseDTOList = new ArrayList<EventResponseDTO>();
            for(Event events : eventList) {
                EventResponseDTO eventResponseDTO = new EventResponseDTO(events);
                eventResponseDTOList.add(eventResponseDTO);
            }
            return eventResponseDTOList;

        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public EventResponseDTO getById(Long id) throws Exception {
        try {
            Event event = eventRepository.findById(id).get();
            return new EventResponseDTO(event);
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean existsById(Long id) {
        return eventRepository.existsById(id);
    }

    @Transactional
    public EventResponseDTO create(EventRequestDTO eventRequestDTO) throws Exception {
        Event newEvent = new Event();

        try {
            LocalDate eventDate = validateDate(String.valueOf(eventRequestDTO.eventDate()));
            newEvent.setEventDate(eventDate);
            newEvent.setEventTitle(eventRequestDTO.eventTitle());
            newEvent.setEventLocation(eventRequestDTO.eventLocation());
            newEvent.setEventDescription(eventRequestDTO.eventDescription());
            newEvent.setEventPrice(eventRequestDTO.eventPrice());
            newEvent.setEventHour(eventRequestDTO.eventHour());

            eventRepository.save(newEvent);
            EventResponseDTO eventResponseDTO = new EventResponseDTO(newEvent);
            newEvent.setEventId(eventResponseDTO.eventId());
            return eventResponseDTO;
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public EventResponseDTO update(Long id, EventRequestDTO eventRequestDTO) throws Exception {
        Event eventUpdate = new Event();

        try {
            if(eventRepository.existsById(id)) {
                LocalDate eventDate = validateDate(String.valueOf(eventRequestDTO.eventDate()));
                eventUpdate.setEventDate(eventDate);
                eventUpdate.setEventTitle(eventRequestDTO.eventTitle());
                eventUpdate.setEventLocation(eventRequestDTO.eventLocation());
                eventUpdate.setEventDescription(eventRequestDTO.eventDescription());
                eventUpdate.setEventPrice(eventRequestDTO.eventPrice());
                eventUpdate.setEventHour(eventRequestDTO.eventHour());
                eventUpdate.setEventId(id);

                Event event = eventRepository.save(eventUpdate);
                return new EventResponseDTO(event);
            }
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Transactional
    public void delete(Long id) throws Exception {
        try {
            if(eventRepository.existsById(id)) {
                eventRepository.deleteById(id);
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

   public LocalDate validateDate(String date) throws AlbumException {
        try {
            return LocalDate.parse(date);
        } catch(Exception e) {
            throw new AlbumException("The date format must be dd-MM-yyyy", HttpStatus.BAD_REQUEST);
        }
   }

   public String getArtistFromToken() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if(!(authentication instanceof AnonymousAuthenticationToken)) {
           return authentication.getName();
       }
       return null;
   }

}
