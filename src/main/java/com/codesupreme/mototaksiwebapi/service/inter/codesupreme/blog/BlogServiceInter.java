package com.codesupreme.mototaksiwebapi.service.inter.codesupreme.blog;

import com.codesupreme.mototaksiwebapi.dto.codesupreme.blog.BlogDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogServiceInter {

    List<BlogDto> getAllBlogs();

    List<BlogDto> getActiveBlogs();

    BlogDto getBlogById(Long id);

    BlogDto getBlogBySlug(String slug);

    ResponseEntity<BlogDto> createBlog(BlogDto blogDto);

    BlogDto updateBlog(Long id, BlogDto blogDto);

    List<BlogDto> searchBlogsByTitle(String keyword);

    List<BlogDto> getBlogsByCategory(String category);

    List<BlogDto> getBlogsByCategoryKey(String categoryKey);

    void deleteBlog(Long id);
}