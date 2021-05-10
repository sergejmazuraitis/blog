package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.exception.CommentFoundException;
import lt.codeacademy.project.blog.model.Comment;
import lt.codeacademy.project.blog.model.User;
import lt.codeacademy.project.blog.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private Pageable pageable;

    @Mock
    private Comment comment;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void testAddCommentWhenCommentIsNull() {
        doThrow(IllegalArgumentException.class).when(commentRepository).save(eq(null));

        commentService.addComment(null);

        verify(commentRepository, times(1)).save(eq(null));
    }

    @Test
    void testAddCommentWhenCommentExist() {
        when(commentRepository.save(comment)).thenReturn(comment);

        commentService.addComment(comment);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testGetCommentByIdWhenCommentNotExist() {
        when(commentRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(CommentFoundException.class, () -> commentService.getCommentById(UUID.randomUUID()));

    }

    @Test
    void testGetCommentByIdWhenCommentExist() {
        UUID id = UUID.randomUUID();
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        Comment commentTest = commentService.getCommentById(id);

        assertEquals(comment, commentTest);
    }

    @Test
    void testUpdateCommentWhenCommentNotExist() {
        doThrow(IllegalArgumentException.class).when(commentRepository).save(eq(null));

        commentService.updateComment(null);

        verify(commentRepository, times(1)).save(eq(null));
    }

    @Test
    void testUpdateCommentWhenCommentExist() {
        when(commentRepository.save(comment)).thenReturn(comment);

        commentService.updateComment(comment);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testDeleteCommentWhenCommentNotExist() {
        doThrow(IllegalArgumentException.class).when(commentRepository).deleteById(any(UUID.class));

        assertThrows(IllegalArgumentException.class, () -> commentService.deleteComment(UUID.randomUUID()));
    }

    @Test
    void testDeleteCommentWhenCommentExist() {
        UUID uuid = UUID.randomUUID();
        doNothing().when(commentRepository).deleteById(uuid);

        commentService.deleteComment(uuid);

        verify(commentRepository, times(1)).deleteById(uuid);
    }

    @Test
    void testGetCommentsByBlogPostIdWhenCommentsNotExists() {
        when(commentRepository.getCommentsByBlogPostId(any(UUID.class), any(Pageable.class))).thenReturn(Page.empty());

        Page<Comment> comments = commentService.getCommentsByBlogPostId(UUID.randomUUID(), pageable);

        assertNotNull(comments);
        assertTrue(comments.isEmpty());

    }

    @Test
    void testGetCommentsByBlogPostIdWhenCommentExists() {
        UUID uuid = UUID.randomUUID();
        Page<Comment> comments = new PageImpl<>(List.of(new Comment()));
        when(commentRepository.getCommentsByBlogPostId(uuid, pageable)).thenReturn(comments);

        Page<Comment> commentPage = commentService.getCommentsByBlogPostId(uuid, pageable);

        assertNotNull(commentPage);
        assertFalse(commentPage.isEmpty());
        assertEquals(1, commentPage.getTotalElements());
    }

    @Test
    void testGetCommentsByBlogPostIdWhenBlogPostNotExist() {
        when(commentRepository.getCommentsByBlogPostId(any(UUID.class), any(Pageable.class))).thenReturn(Page.empty());

        Page<Comment> comments = commentService.getCommentsByBlogPostId(UUID.randomUUID(), pageable);

        assertNotNull(comments);
        assertTrue(comments.isEmpty());
    }

    @Test
    void testValidateIsUserCommentWhenCommentNotExist() {
        when(commentRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(CommentFoundException.class, () -> commentService.getCommentById(UUID.randomUUID()));
    }

    @Test
    void testValidateIsUserCommentWhenUserIdNotExist() {
        UUID uuid = UUID.randomUUID();

        when(commentRepository.findById(uuid)).thenReturn(Optional.of(comment));

        commentService.validateIsUserComment(uuid, null, null);

        assertFalse(commentService.validateIsUserComment(uuid, null, null));
    }
}