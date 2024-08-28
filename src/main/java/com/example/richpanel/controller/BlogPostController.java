package com.example.richpanel.controller;

import com.example.richpanel.exception.ResourceNotFoundException;
import com.example.richpanel.model.BlogPost;
import com.example.richpanel.repository.InMemoryBlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/posts")
public class BlogPostController {

    @Autowired
    private InMemoryBlogPostRepository blogPostRepository;

    @PostMapping
    public ResponseEntity<BlogPost> createPost(@RequestBody BlogPost blogPost) {
        BlogPost savedPost = blogPostRepository.save(blogPost);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable Long id) {
        BlogPost post = blogPostRepository.findById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
    }


    @Cacheable("posts")
    @GetMapping
    public List<BlogPost> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ConcurrentHashMap<Long, BlogPost> posts = blogPostRepository.findAll();
        return new ArrayList<>(posts.values())
                .subList(page * size, Math.min((page + 1) * size, posts.size()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updatePost(@PathVariable Long id, @RequestBody BlogPost blogPostDetails) {
        BlogPost post = blogPostRepository.findById(id);
        if (post != null) {
            post.setTitle(blogPostDetails.getTitle());
            post.setContent(blogPostDetails.getContent());
            blogPostRepository.save(post);
            return ResponseEntity.ok(post);
        } else {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
    }


    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        BlogPost post = blogPostRepository.findById(id);
        if (post != null) {
            blogPostRepository.deleteById(id);
            return "Post Deleted Successfully id: "+id;
        } else {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
    }
}

