package com.codesupreme.mototaksiwebapi.menyupro.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MpCategoryDto {
    public Long id;
    public String name;
    public Long businessId;
}


