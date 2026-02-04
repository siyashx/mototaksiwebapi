package com.codesupreme.mototaksiwebapi.menyupro.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MpPublicProfileDto {
    public Long id;
    public String businessName;
    public String bio;
    public String profileImage;
    public String slug;
    public String phone;
}
