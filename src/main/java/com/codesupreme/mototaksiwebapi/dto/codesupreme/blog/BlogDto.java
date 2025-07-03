package com.codesupreme.mototaksiwebapi.dto.codesupreme.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDto {

    private Long id;

    private String title;
    private String content;

    private String category;
    private String thumbnailUrl;
    private String slug;

    private Date createdAt;
    private Date updatedAt;

}

