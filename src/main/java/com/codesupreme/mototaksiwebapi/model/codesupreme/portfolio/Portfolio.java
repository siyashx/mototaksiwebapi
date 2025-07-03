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

    private String title;

    @Column(length = 5000)
    private String description;

    private String imageUrl;

    private String category;

    private String client;

    private String projectUrl;

    @Column(unique = true)
    private String slug;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // id-only constructor
    public Portfolio(Long id) {
        this.id = id;
    }
}


