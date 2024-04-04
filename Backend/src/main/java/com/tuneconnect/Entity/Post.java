package com.tuneconnect.Entity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false, unique = true)
    private long postId;

    @Column(name = "post_content")
    private String postContent;

    @OneToOne
    @JoinColumn(name = "image_id")
    @Column(name = "post_image")
    private Image postImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Column(name = "postUser")
    private User user;

}
