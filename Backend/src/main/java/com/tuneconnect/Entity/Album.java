package com.tuneconnect.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", nullable = false, unique = true)
    private Long albumId;

    @Column(name = "album_title", nullable = false)
    private String albumTitle;

    @Column(name = "album_date")
    private LocalDate albumDate;

    @OneToMany(mappedBy = "song_album")
    @Column(name = "album_songs")
    private List<Song> albumSongs;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @Column(name = "album_artist", nullable = false)
    private Artist albumArtist;

    @OneToOne
    @Column(name = "album_image")
    private Image albumImage;
}
