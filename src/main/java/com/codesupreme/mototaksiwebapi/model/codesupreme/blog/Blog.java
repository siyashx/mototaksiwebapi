package com.codesupreme.mototaksiwebapi.model.codesupreme.blog;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "blogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000, nullable = false)
    private String content;

    private String category;

    private String thumbnailUrl;

    private String slug;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ID-only constructor (opsional olaraq əlavə)
    public Blog(Long id) {
        this.id = id;
    }
}
