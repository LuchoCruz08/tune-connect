package com.tuneconnect.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false, unique = true)
    private Long eventId;

    @Column(name = "event_title", nullable = false)
    private String eventTitle;

    @Column(name = "event_location", nullable = false)
    private String eventLocation;

    @Column(name = "event_description")
    private String eventDescription;

    @Column(name = "event_price")
    private Double eventPrice;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "event_hour", nullable = false)
    private String eventHour;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @Column(name = "event_artist")
    private Artist eventArtist;

}
