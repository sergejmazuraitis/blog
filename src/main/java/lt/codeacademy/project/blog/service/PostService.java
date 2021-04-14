package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    void addPost(Post post);

    Post getPostById(UUID id);

    Post getPostByName(String name);

    List<Post> getPosts();

    void updatePost(Post post);

    void deletePost(UUID id);


}
