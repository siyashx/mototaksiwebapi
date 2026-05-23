package com.codesupreme.mototaksiwebapi.api.codesupreme.blog;

import com.codesupreme.mototaksiwebapi.dto.codesupreme.blog.BlogDto;
import com.codesupreme.mototaksiwebapi.service.inter.codesupreme.blog.BlogServiceInter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/blog")
@CrossOrigin(origins = "*")
public class BlogController {

    private final BlogServiceInter blogService;

    public BlogController(BlogServiceInter blogService) {
        this.blogService = blogService;
    }

    // Saytda görünəcək aktiv bloglar
    @GetMapping
    public ResponseEntity<List<BlogDto>> getActiveBlogs() {
        return ResponseEntity.ok(blogService.getActiveBlogs());
    }

    // Admin panel üçün bütün bloglar
    @GetMapping("/all")
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    // Blogu ID ilə tapmaq
    @GetMapping("/{blogId}")
    public ResponseEntity<?> getBlogById(@PathVariable Long blogId) {
        BlogDto blogDto = blogService.getBlogById(blogId);

        if (blogDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Blog bu ID ilə tapılmadı.");
        }

        return ResponseEntity.ok(blogDto);
    }

    // Blogu slug ilə tapmaq
    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getBlogBySlug(@PathVariable String slug) {
        BlogDto blogDto = blogService.getBlogBySlug(slug);

        if (blogDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Blog bu slug ilə tapılmadı.");
        }

        return ResponseEntity.ok(blogDto);
    }

    // Yeni blog yaratmaq
    @PostMapping
    public ResponseEntity<BlogDto> createBlog(@RequestBody BlogDto blogDto) {
        return blogService.createBlog(blogDto);
    }

    // Blog yeniləmək
    @PutMapping("/{blogId}")
    public ResponseEntity<?> updateBlog(
            @PathVariable Long blogId,
            @RequestBody BlogDto blogDto
    ) {
        BlogDto updatedBlog = blogService.updateBlog(blogId, blogDto);

        if (updatedBlog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Yenilənəcək blog tapılmadı.");
        }

        return ResponseEntity.ok(updatedBlog);
    }

    // Başlığa görə axtarış
    @GetMapping("/search")
    public ResponseEntity<List<BlogDto>> searchBlogsByTitle(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(blogService.searchBlogsByTitle(keyword));
    }

    // Kateqoriya adına görə filter
    // Məsələn: /api/v6/blog/category?category=SEO
    @GetMapping("/category")
    public ResponseEntity<List<BlogDto>> getBlogsByCategory(
            @RequestParam String category
    ) {
        return ResponseEntity.ok(blogService.getBlogsByCategory(category));
    }

    // Kateqoriya key-ə görə filter
    // Məsələn: /api/v6/blog/category-key?categoryKey=seo
    @GetMapping("/category-key")
    public ResponseEntity<List<BlogDto>> getBlogsByCategoryKey(
            @RequestParam String categoryKey
    ) {
        return ResponseEntity.ok(blogService.getBlogsByCategoryKey(categoryKey));
    }

    // Blog silmək
    @DeleteMapping("/{blogId}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long blogId) {
        BlogDto blogDto = blogService.getBlogById(blogId);

        if (blogDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Silinəcək blog tapılmadı.");
        }

        blogService.deleteBlog(blogId);

        return ResponseEntity.ok("Blog uğurla silindi.");
    }
}