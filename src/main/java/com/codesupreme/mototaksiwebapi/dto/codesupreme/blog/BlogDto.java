package com.codesupreme.mototaksiwebapi.dto.codesupreme.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDto {

    private Long id;

    private String title;

    private String content;

    // Blog kartında görünəcək qısa mətn
    private String excerpt;

    // Məsələn: SEO, Veb sayt, Mobil uyğunluq, Dizayn, Texniki dəstək
    private String category;

    // Filter üçün: seo, veb-sayt, mobil, dizayn, texniki-destek
    private String categoryKey;

    // Şəkil varsa göstəriləcək
    private String thumbnailUrl;

    // Şəkil alt text üçün SEO baxımından faydalıdır
    private String thumbnailAlt;

    // Şəkil yoxdursa FontAwesome icon göstərmək üçün
    private String iconClass;

    // Məsələn: sayt-google-da-niye-gorunmur
    private String slug;

    // Məsələn: 4 yazılır, frontenddə "4 dəq" göstərilir
    private Integer readTimeMinutes;

    // SEO üçün detail səhifədə istifadə olunacaq
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;

    // Blog aktiv/passiv idarəsi üçün
    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}