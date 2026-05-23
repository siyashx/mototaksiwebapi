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
import java.util.List;

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
        return blogRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<BlogDto> getActiveBlogs() {
        return blogRepository.findByActiveTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public BlogDto getBlogById(Long id) {
        return blogRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public BlogDto getBlogBySlug(String slug) {
        return blogRepository.findBySlug(slug)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public ResponseEntity<BlogDto> createBlog(BlogDto blogDto) {
        Blog blog = modelMapper.map(blogDto, Blog.class);

        String title = blogDto.getTitle();
        String content = blogDto.getContent();
        String category = blogDto.getCategory();

        blog.setSlug(
                blogDto.getSlug() != null && !blogDto.getSlug().isBlank()
                        ? generateUniqueSlug(blogDto.getSlug())
                        : generateUniqueSlug(title)
        );

        blog.setCategoryKey(
                blogDto.getCategoryKey() != null && !blogDto.getCategoryKey().isBlank()
                        ? blogDto.getCategoryKey()
                        : generateCategoryKey(category)
        );

        blog.setExcerpt(
                blogDto.getExcerpt() != null && !blogDto.getExcerpt().isBlank()
                        ? blogDto.getExcerpt()
                        : generateExcerpt(content)
        );

        blog.setThumbnailAlt(
                blogDto.getThumbnailAlt() != null && !blogDto.getThumbnailAlt().isBlank()
                        ? blogDto.getThumbnailAlt()
                        : title
        );

        blog.setIconClass(
                blogDto.getIconClass() != null && !blogDto.getIconClass().isBlank()
                        ? blogDto.getIconClass()
                        : generateIconClass(category)
        );

        blog.setReadTimeMinutes(
                blogDto.getReadTimeMinutes() != null
                        ? blogDto.getReadTimeMinutes()
                        : calculateReadTime(content)
        );

        blog.setMetaTitle(
                blogDto.getMetaTitle() != null && !blogDto.getMetaTitle().isBlank()
                        ? blogDto.getMetaTitle()
                        : title + " | Code Supreme Bloq"
        );

        blog.setMetaDescription(
                blogDto.getMetaDescription() != null && !blogDto.getMetaDescription().isBlank()
                        ? blogDto.getMetaDescription()
                        : generateExcerpt(content)
        );

        blog.setMetaKeywords(
                blogDto.getMetaKeywords() != null && !blogDto.getMetaKeywords().isBlank()
                        ? blogDto.getMetaKeywords()
                        : generateMetaKeywords(title, category)
        );

        blog.setActive(blogDto.getActive() != null ? blogDto.getActive() : true);

        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());

        Blog savedBlog = blogRepository.save(blog);

        return ResponseEntity.ok(mapToDto(savedBlog));
    }

    @Override
    public BlogDto updateBlog(Long id, BlogDto blogDto) {
        return blogRepository.findById(id)
                .map(blog -> {
                    if (blogDto.getTitle() != null) {
                        blog.setTitle(blogDto.getTitle());
                    }

                    if (blogDto.getContent() != null) {
                        blog.setContent(blogDto.getContent());
                    }

                    if (blogDto.getCategory() != null) {
                        blog.setCategory(blogDto.getCategory());
                    }

                    if (blogDto.getCategoryKey() != null) {
                        blog.setCategoryKey(blogDto.getCategoryKey());
                    } else if (blogDto.getCategory() != null) {
                        blog.setCategoryKey(generateCategoryKey(blogDto.getCategory()));
                    }

                    if (blogDto.getThumbnailUrl() != null) {
                        blog.setThumbnailUrl(blogDto.getThumbnailUrl());
                    }

                    if (blogDto.getThumbnailAlt() != null) {
                        blog.setThumbnailAlt(blogDto.getThumbnailAlt());
                    }

                    if (blogDto.getIconClass() != null) {
                        blog.setIconClass(blogDto.getIconClass());
                    }

                    if (blogDto.getExcerpt() != null) {
                        blog.setExcerpt(blogDto.getExcerpt());
                    }

                    if (blogDto.getReadTimeMinutes() != null) {
                        blog.setReadTimeMinutes(blogDto.getReadTimeMinutes());
                    }

                    if (blogDto.getMetaTitle() != null) {
                        blog.setMetaTitle(blogDto.getMetaTitle());
                    }

                    if (blogDto.getMetaDescription() != null) {
                        blog.setMetaDescription(blogDto.getMetaDescription());
                    }

                    if (blogDto.getMetaKeywords() != null) {
                        blog.setMetaKeywords(blogDto.getMetaKeywords());
                    }

                    if (blogDto.getActive() != null) {
                        blog.setActive(blogDto.getActive());
                    }

                    if (blogDto.getSlug() != null && !blogDto.getSlug().isBlank()) {
                        String newSlug = SlugUtil.toSlug(blogDto.getSlug());

                        if (!newSlug.equals(blog.getSlug())) {
                            blog.setSlug(generateUniqueSlug(newSlug));
                        }
                    } else if (blogDto.getTitle() != null && !blogDto.getTitle().isBlank()) {
                        String newSlug = SlugUtil.toSlug(blogDto.getTitle());

                        if (!newSlug.equals(blog.getSlug())) {
                            blog.setSlug(generateUniqueSlug(newSlug));
                        }
                    }

                    if ((blog.getExcerpt() == null || blog.getExcerpt().isBlank()) && blog.getContent() != null) {
                        blog.setExcerpt(generateExcerpt(blog.getContent()));
                    }

                    if ((blog.getThumbnailAlt() == null || blog.getThumbnailAlt().isBlank()) && blog.getTitle() != null) {
                        blog.setThumbnailAlt(blog.getTitle());
                    }

                    if ((blog.getIconClass() == null || blog.getIconClass().isBlank()) && blog.getCategory() != null) {
                        blog.setIconClass(generateIconClass(blog.getCategory()));
                    }

                    if (blog.getReadTimeMinutes() == null && blog.getContent() != null) {
                        blog.setReadTimeMinutes(calculateReadTime(blog.getContent()));
                    }

                    blog.setUpdatedAt(LocalDateTime.now());

                    Blog updatedBlog = blogRepository.save(blog);

                    return mapToDto(updatedBlog);
                })
                .orElse(null);
    }

    @Override
    public List<BlogDto> searchBlogsByTitle(String keyword) {
        return blogRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<BlogDto> getBlogsByCategory(String category) {
        return blogRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<BlogDto> getBlogsByCategoryKey(String categoryKey) {
        return blogRepository.findByCategoryKeyIgnoreCase(categoryKey)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    private BlogDto mapToDto(Blog blog) {
        return modelMapper.map(blog, BlogDto.class);
    }

    private String generateUniqueSlug(String value) {
        String baseSlug = SlugUtil.toSlug(value);

        if (baseSlug == null || baseSlug.isBlank()) {
            baseSlug = "blog";
        }

        String slug = baseSlug;
        int counter = 1;

        while (blogRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }

        return slug;
    }

    private String generateCategoryKey(String category) {
        if (category == null || category.isBlank()) {
            return "blog";
        }

        String normalized = category.trim().toLowerCase();

        return switch (normalized) {
            case "seo" -> "seo";
            case "veb sayt", "vebsayt", "web sayt", "websayt" -> "veb-sayt";
            case "mobil", "mobil uyğunluq", "mobil uygunluq", "mobil tətbiq", "mobil tetbiq" -> "mobil";
            case "dizayn", "design" -> "dizayn";
            case "texniki dəstək", "texniki destek", "texniki-destek" -> "texniki-destek";
            default -> SlugUtil.toSlug(category);
        };
    }

    private String generateIconClass(String category) {
        String categoryKey = generateCategoryKey(category);

        return switch (categoryKey) {
            case "seo" -> "fa-solid fa-chart-line";
            case "veb-sayt" -> "fa-solid fa-laptop-code";
            case "mobil" -> "fa-solid fa-mobile-screen-button";
            case "dizayn" -> "fa-solid fa-palette";
            case "texniki-destek" -> "fa-solid fa-shield-heart";
            default -> "fa-solid fa-newspaper";
        };
    }

    private String generateExcerpt(String content) {
        if (content == null || content.isBlank()) {
            return "";
        }

        String plainText = content
                .replaceAll("<[^>]*>", "")
                .replaceAll("\\s+", " ")
                .trim();

        if (plainText.length() <= 150) {
            return plainText;
        }

        return plainText.substring(0, 150).trim() + "...";
    }

    private Integer calculateReadTime(String content) {
        if (content == null || content.isBlank()) {
            return 1;
        }

        String plainText = content
                .replaceAll("<[^>]*>", "")
                .replaceAll("\\s+", " ")
                .trim();

        if (plainText.isBlank()) {
            return 1;
        }

        int words = plainText.split("\\s+").length;

        return Math.max(1, (int) Math.ceil(words / 180.0));
    }

    private String generateMetaKeywords(String title, String category) {
        String safeTitle = title != null ? title : "";
        String safeCategory = category != null ? category : "";

        return safeTitle + ", " + safeCategory + ", Code Supreme bloq, veb sayt, SEO, mobil uyğunluq, dizayn, texniki dəstək";
    }
}