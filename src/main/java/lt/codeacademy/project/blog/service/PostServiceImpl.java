package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.exception.PostFoundException;
import lt.codeacademy.project.blog.model.Post;
import lt.codeacademy.project.blog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostServiceImpl implements MyPostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void addPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Post getPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(PostFoundException::new);
    }

    @Override
    public Post getPostByName(String name) {
        return postRepository.findByName(name).get(0);
    }

    @Override
    public Page<Post> getAllPostsWithPages(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }
}
