package com.codesupreme.mototaksiwebapi.dao.codesupreme.blog;

import com.codesupreme.mototaksiwebapi.model.codesupreme.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findByTitleContainingIgnoreCase(String keyword);

    List<Blog> findByCategoryIgnoreCase(String category);

    List<Blog> findByCategoryKeyIgnoreCase(String categoryKey);

    List<Blog> findByActiveTrueOrderByCreatedAtDesc();

    Optional<Blog> findBySlug(String slug);

    boolean existsBySlug(String slug);
}