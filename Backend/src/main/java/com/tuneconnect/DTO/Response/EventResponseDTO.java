package com.tuneconnect.DTO.Response;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tuneconnect.Entity.Event;
import java.time.LocalDate;

public record EventResponseDTO(
        Long eventId,
        String eventTitle,
        String eventLocation,
        String eventDescription,
        Double eventPrice,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate eventDate,
        String eventHour
) {

    public EventResponseDTO(Event event) {
        this(event.getEventId(), event.getEventTitle(), event.getEventLocation(), event.getEventDescription(),
                event.getEventPrice(), event.getEventDate(), event.getEventHour());
    }

}
