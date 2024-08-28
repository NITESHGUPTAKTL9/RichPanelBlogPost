package com.example.richpanel.repository;

import com.example.richpanel.model.BlogPost;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryBlogPostRepository {

    private ConcurrentHashMap<Long, BlogPost> posts = new ConcurrentHashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);

    public BlogPost save(BlogPost blogPost) {
        if (blogPost.getId() == null) {
            blogPost.setId(idCounter.getAndIncrement());
        }
        posts.put(blogPost.getId(), blogPost);
        return blogPost;
    }

    public BlogPost findById(Long id) {
        return posts.get(id);
    }

    public void deleteById(Long id) {
        posts.remove(id);
    }

    public ConcurrentHashMap<Long, BlogPost> findAll() {
        return posts;
    }
}
