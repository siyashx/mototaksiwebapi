package com.codesupreme.mototaksiwebapi.menyupro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MpBusinessDto {
    public Long id;
    public String businessName;
    public String phone;
    public String profileImage;
    public String bio;
    public String slug;
}

