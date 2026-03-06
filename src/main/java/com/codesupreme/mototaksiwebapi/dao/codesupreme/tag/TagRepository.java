package com.codesupreme.mototaksiwebapi.dao.codesupreme.tag;

import com.codesupreme.mototaksiwebapi.model.codesupreme.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByLabelContainingIgnoreCase(String keyword);

    Optional<Tag> findBySlug(String slug);
}