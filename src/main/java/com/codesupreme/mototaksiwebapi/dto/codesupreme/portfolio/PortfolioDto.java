package com.codesupreme.mototaksiwebapi.dto.codesupreme.portfolio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioDto {

    private Long id;

    private String title;
    private String description;
    private String imageUrl;
    private String category;
    private String client;
    private String projectUrl;
    private String slug;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

