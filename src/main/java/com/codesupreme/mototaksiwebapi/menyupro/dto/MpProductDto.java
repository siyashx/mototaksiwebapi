package com.codesupreme.mototaksiwebapi.menyupro.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MpProductDto {
    public Long id;
    public String title;
    public String description;
    public Double price;
    public String image;
    public Long categoryId;
}

