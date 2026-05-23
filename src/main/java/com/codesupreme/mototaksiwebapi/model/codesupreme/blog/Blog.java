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

    // Blog başlığı
    @Column(nullable = false, length = 180)
    private String title;

    // Əsas blog mətni
    @Column(length = 5000, nullable = false)
    private String content;

    // Kartlarda görünəcək qısa açıqlama
    @Column(length = 500)
    private String excerpt;

    // Məsələn: SEO, Veb sayt, Mobil uyğunluq
    @Column(length = 80)
    private String category;

    // Filter üçün açar: seo, veb-sayt, mobil, dizayn, texniki-destek
    @Column(length = 80)
    private String categoryKey;

    // Blog şəkli
    @Column(length = 500)
    private String thumbnailUrl;

    // Şəkil üçün alt text
    @Column(length = 255)
    private String thumbnailAlt;

    // Şəkil yoxdursa icon göstərmək üçün
    @Column(length = 120)
    private String iconClass;

    // /blog/{slug} üçün
    @Column(nullable = false, unique = true, length = 180)
    private String slug;

    // Kartda "4 dəq", "5 dəq" kimi göstərmək üçün
    private Integer readTimeMinutes;

    // SEO title
    @Column(length = 180)
    private String metaTitle;

    // SEO description
    @Column(length = 300)
    private String metaDescription;

    // SEO keywords
    @Column(length = 500)
    private String metaKeywords;

    // Blog yayımdadır ya yox
    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Blog(Long id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        if (this.active == null) {
            this.active = true;
        }

        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }

        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}