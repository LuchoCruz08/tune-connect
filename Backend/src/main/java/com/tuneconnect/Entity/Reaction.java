package com.tuneconnect.Entity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reaction")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id", nullable = false, unique = true)
    private Long reactionId;

    @Column(name = "reaction_comment")
    private String reactionComment;

    @Column(name = "reaction_like")
    private Integer reactionLike;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Column(name = "reaction_post")
    private Post reactionPost;

}
