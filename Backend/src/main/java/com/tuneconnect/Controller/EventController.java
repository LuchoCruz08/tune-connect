package com.tuneconnect.Controller;
import com.tuneconnect.DTO.Request.EventRequestDTO;
import com.tuneconnect.DTO.Response.EventResponseDTO;
import com.tuneconnect.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<EventResponseDTO> eventResponseDTOList = eventService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(eventResponseDTOList);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error searching for events");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.getById(id));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The event with that ID was not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> create(EventRequestDTO eventRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.create(eventRequestDTO));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating event");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EventRequestDTO eventRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eventService.update(id, eventRequestDTO));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating event");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        eventService.delete(id);
    }

}
