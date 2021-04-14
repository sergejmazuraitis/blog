package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.Post;
import lt.codeacademy.project.blog.repository.PostRepository;

import java.util.List;
import java.util.UUID;

public class PostServiceImpl implements PostService {
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
    public List<Post> getPosts() {
        return postRepository.findAll();
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
