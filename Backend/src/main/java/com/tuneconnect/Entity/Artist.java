package com.tuneconnect.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "artist")
public class Artist extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id", nullable = false, unique = true)
    private Long artistId;

    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Column(name = "artist_description")
    private String artistDescription;

    @OneToMany(mappedBy = "artist")
    @Column(name = "artist_albums")
    private List<Album> artistAlbums;

    @OneToMany(mappedBy = "artist")
    @Column(name = "artist_events")
    private List<Event> artistEvents;

    @Column(name = "musical_genre", nullable = false)
    private String musicalGenre;

}
