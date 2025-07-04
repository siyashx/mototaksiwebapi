package com.codesupreme.mototaksiwebapi.service.inter.codesupreme.blog;

import com.codesupreme.mototaksiwebapi.dto.codesupreme.blog.BlogDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogServiceInter {

    BlogDto getBlogById(Long id);

    List<BlogDto> getAllBlogs();

    ResponseEntity<BlogDto> createBlog(BlogDto blogDto);

    List<BlogDto> searchBlogsByTitle(String keyword);

    List<BlogDto> getBlogsByCategory(String category);



    BlogDto updateBlog(Long id, BlogDto blogDto);

    void deleteBlog(Long id);
}

