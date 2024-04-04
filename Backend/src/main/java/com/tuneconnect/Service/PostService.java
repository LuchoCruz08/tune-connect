package com.tuneconnect.Service;
import com.tuneconnect.DTO.Request.PostRequestDTO;
import com.tuneconnect.DTO.Response.PostResponseDTO;
import com.tuneconnect.Entity.Image;
import com.tuneconnect.Entity.Post;
import com.tuneconnect.Entity.User;
import com.tuneconnect.Handler.UnauthorizedAccessException;
import com.tuneconnect.Handler.UserNotExistException;
import com.tuneconnect.Repository.PostRepository;
import com.tuneconnect.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private ImageService imageService;

    public List<PostResponseDTO> getAllPost() {
        List<Post> postList = postRepository.findAll();
        List<PostResponseDTO> postResponseDTOList = new ArrayList<>();

        for(Post post : postList) {
            PostResponseDTO postResponseDTO = new PostResponseDTO(post);
            postResponseDTOList.add(postResponseDTO);
        }

        return postResponseDTOList;
    }

    @Transactional
    public List<PostResponseDTO> getPostByUsername(String username) {
        Optional<List<Post>> optionalPostList = postRepository.findByUsername(username);

        if(optionalPostList.isPresent()) {
            List<Post> postList = optionalPostList.get();

            if(!postList.isEmpty()) {
                return postList.stream()
                        .map(PostResponseDTO::new)
                        .collect(Collectors.toList());
            } else {
                throw new EntityNotFoundException("The user has no posts");
            }

        } else {
            throw new UserNotExistException();
        }
    }

    @Transactional
    public List<PostResponseDTO> getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID not found: " + id));

        return Collections.singletonList(new PostResponseDTO(post));
    }

    @Transactional
    public PostResponseDTO create(PostRequestDTO postRequestDTO, String username) throws IOException {
        Optional<User> user = userRepository.findByUsername(username);
        Post post = new Post();

        if(user.isPresent()) {
            post.setUser(user.get());
            post.setPostContent(postRequestDTO.message());

            if(postRequestDTO.image() != null) {
                Image image = imageService.save(postRequestDTO.image());
                post.setPostImage(image);
            }

            postRepository.save(post);
        } else {
            throw new UserNotExistException();
        }

        return new PostResponseDTO(post);
    }

    @Transactional
    public PostResponseDTO update(Long id, PostRequestDTO postRequestDTO, String username) throws IOException {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Post> optionalPost = postRepository.findById(id);

        if(user.isPresent() && optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if(post.getUser().equals(user.get())) {
                post.setPostContent(postRequestDTO.message());

                if(postRequestDTO.image() != null) {
                    Image image = imageService.save(postRequestDTO.image());
                    post.setPostImage(image);
                }

                postRepository.save(post);
                return new PostResponseDTO(post);
            } else {
                throw new UnauthorizedAccessException();
            }

        } else {
            throw new EntityNotFoundException("Post with ID not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Post> optionalPost = postRepository.findById(id);

        if(user.isPresent() && optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if(post.getUser().equals(user.get())) {
                postRepository.deleteById(id);
            } else {
                throw new UnauthorizedAccessException();
            }

        } else {
            throw new EntityNotFoundException("Post with ID not found: " + id);
        }

    }

}
