package com.codesupreme.mototaksiwebapi.api.codesupreme.blog;

import com.codesupreme.mototaksiwebapi.dto.codesupreme.blog.BlogDto;
import com.codesupreme.mototaksiwebapi.model.codesupreme.blog.Blog;
import com.codesupreme.mototaksiwebapi.service.impl.codesupreme.blog.BlogServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/blog")
public class BlogController {

    private final BlogServiceImpl blogService;

    public BlogController(BlogServiceImpl blogService) {
        this.blogService = blogService;
    }

    // List all blogs
    @GetMapping
    public List<BlogDto> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    // Get blog by ID
    @GetMapping("/{blogId}")
    public ResponseEntity<?> getBlogById(@PathVariable("blogId") Long blogId) {
        BlogDto blogDto = blogService.getBlogById(blogId);
        if (blogDto != null) {
            return ResponseEntity.ok(blogDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Blog doesn't exist with given ID.");
    }

    // Create blog
    @PostMapping
    public ResponseEntity<BlogDto> createBlog(@RequestBody BlogDto blogDto) {
        return blogService.createBlog(blogDto);
    }

    // Update blog
    @PutMapping("/{blogId}")
    public ResponseEntity<?> updateBlog(
            @PathVariable("blogId") Long blogId,
            @RequestBody BlogDto blogDto
    ) {
        BlogDto updatedBlog = blogService.updateBlog(blogId, blogDto);
        if (updatedBlog != null) {
            return ResponseEntity.ok(updatedBlog);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Blog not found to update.");
    }

    // Search by title keyword
    @GetMapping("/search")
    public ResponseEntity<List<BlogDto>> searchBlogs(@RequestParam("keyword") String keyword) {
        List<BlogDto> result = blogService.searchBlogsByTitle(keyword);
        return ResponseEntity.ok(result);
    }

    // Filter by category
    @GetMapping("/category")
    public ResponseEntity<List<BlogDto>> getBlogsByCategory(@RequestParam("category") String category) {
        List<BlogDto> result = blogService.getBlogsByCategory(category);
        return ResponseEntity.ok(result);
    }

    // Blogu slug ilə tapmaq üçün yeni endpoint
    @GetMapping("/slug/{slug}")
    public Blog getBlogBySlug(@PathVariable String slug) {
        return blogService.getBlogBySlug(slug);
    }


    // Delete blog
    @DeleteMapping("/{blogId}")
    public ResponseEntity<?> deleteBlog(@PathVariable("blogId") Long blogId) {
        blogService.deleteBlog(blogId);
        return ResponseEntity.ok().build();
    }
}
