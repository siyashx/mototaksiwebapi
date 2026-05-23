package com.codesupreme.mototaksiwebapi.model.codesupreme.portfolio;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "portfolio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 180)
    private String title;

    @Column(length = 5000)
    private String description;

    @Column(length = 500)
    private String imageUrl;

    @Column(length = 255)
    private String imageAlt;

    @Column(length = 80)
    private String category;

    @Column(length = 80)
    private String categoryKey;

    @Column(length = 180)
    private String client;

    @Column(length = 500)
    private String projectUrl;

    @Column(unique = true, length = 180)
    private String slug;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Portfolio(Long id) {
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