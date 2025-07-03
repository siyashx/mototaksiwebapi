package com.codesupreme.mototaksiwebapi.service.impl.codesupreme.blog;

import com.codesupreme.mototaksiwebapi.dao.codesupreme.blog.BlogRepository;
import com.codesupreme.mototaksiwebapi.dto.codesupreme.blog.BlogDto;
import com.codesupreme.mototaksiwebapi.model.codesupreme.blog.Blog;
import com.codesupreme.mototaksiwebapi.service.inter.codesupreme.blog.BlogServiceInter;
import com.codesupreme.mototaksiwebapi.util.SlugUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogServiceInter {

    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    public BlogServiceImpl(BlogRepository blogRepository, ModelMapper modelMapper) {
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BlogDto> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        List<BlogDto> blogDtos = new ArrayList<>();

        for (Blog blog : blogs) {
            blogDtos.add(modelMapper.map(blog, BlogDto.class));
        }

        return blogDtos;
    }

    @Override
    public BlogDto getBlogById(Long id) {
        Optional<Blog> blogOpt = blogRepository.findById(id);
        return blogOpt.map(blog -> modelMapper.map(blog, BlogDto.class)).orElse(null);
    }

    @Override
    public ResponseEntity<BlogDto> createBlog(BlogDto blogDto) {
        Blog blog = modelMapper.map(blogDto, Blog.class);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        blog.setSlug(SlugUtil.toSlug(blogDto.getTitle()));

        Blog savedBlog = blogRepository.save(blog);
        return ResponseEntity.ok(modelMapper.map(savedBlog, BlogDto.class));
    }

    @Override
    public BlogDto updateBlog(Long id, BlogDto blogDto) {
        Optional<Blog> blogOpt = blogRepository.findById(id);
        if (blogOpt.isPresent()) {
            Blog blog = blogOpt.get();

            if (blogDto.getTitle() != null) {
                blog.setTitle(blogDto.getTitle());
                blog.setSlug(SlugUtil.toSlug(blogDto.getTitle()));
            }
            if (blogDto.getContent() != null) blog.setContent(blogDto.getContent());
            if (blogDto.getCategory() != null) blog.setCategory(blogDto.getCategory());
            if (blogDto.getThumbnailUrl() != null) blog.setThumbnailUrl(blogDto.getThumbnailUrl());
            blog.setUpdatedAt(LocalDateTime.now());

            Blog updated = blogRepository.save(blog);
            return modelMapper.map(updated, BlogDto.class);
        }

        return null;
    }

    @Override
    public List<BlogDto> searchBlogsByTitle(String keyword) {
        List<Blog> blogs = blogRepository.findByTitleContainingIgnoreCase(keyword);
        List<BlogDto> blogDtos = new ArrayList<>();

        for (Blog blog : blogs) {
            blogDtos.add(modelMapper.map(blog, BlogDto.class));
        }

        return blogDtos;
    }

    @Override
    public List<BlogDto> getBlogsByCategory(String category) {
        List<Blog> blogs = blogRepository.findByCategoryIgnoreCase(category);
        List<BlogDto> blogDtos = new ArrayList<>();

        for (Blog blog : blogs) {
            blogDtos.add(modelMapper.map(blog, BlogDto.class));
        }

        return blogDtos;
    }


    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}