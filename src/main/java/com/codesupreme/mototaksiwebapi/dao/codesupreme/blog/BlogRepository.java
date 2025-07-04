package com.codesupreme.mototaksiwebapi.dao.codesupreme.blog;

import com.codesupreme.mototaksiwebapi.model.codesupreme.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByTitleContainingIgnoreCase(String keyword);

    List<Blog> findByCategoryIgnoreCase(String category);
    Optional<Blog> findBySlug(String slug);
}

