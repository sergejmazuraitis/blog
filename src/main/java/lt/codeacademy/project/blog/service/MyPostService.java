package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MyPostService {
    void addPost(Post post);

    Post getPostById(UUID id);

    Post getPostByName(String name);

    Page<Post> getAllPostsWithPages(Pageable pageable);

    void updatePost(Post post);

    void deletePost(UUID id);


}
