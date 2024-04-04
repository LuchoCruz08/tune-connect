package com.tuneconnect.DTO.Request;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record EventRequestDTO(
        Long eventId,
        String eventTitle,
        String eventLocation,
        String eventDescription,
        Double eventPrice,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate eventDate,
        String eventHour
) {
}
