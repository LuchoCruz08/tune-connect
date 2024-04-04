package com.tuneconnect.Entity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false, unique = true)
    private Long imageId;

    @Column(name = "image_alt", nullable = false)
    private String imageAlt;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "cloudinary_id", nullable = false)
    private String cloudinaryId;

    @OneToOne
    @JoinColumn(name = "album_id")
    @Column(name = "image_album")
    private Album imageAlbum;
}
