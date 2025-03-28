package com.codesupreme.mototaksiwebapi.dto.elan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ElanDto {

    private Long id;

    private Long userId;
    private String image;
    private Integer price;
    private String title;
    private String description;
    private String category;
    private Boolean premium;
    private Date expireDate;
    private Boolean isDeleted;
    private String createdAt;
    private String updatedAt;

}
