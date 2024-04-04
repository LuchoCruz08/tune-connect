package com.tuneconnect.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id", nullable = false, unique = true)
    private Long songId;

    @Column(name = "song_title", nullable = false)
    private String songTitle;

    @Column(name = "song_genre")
    private String songGenre;

    @Column(name = "song_link")
    private String songLink;

    @Column(name = "song_images")
    private List<String> songImages;

    @ManyToOne
    @JoinColumn(name = "album_id")
    @Column(name = "song_album")
    private Album songAlbum;
}
