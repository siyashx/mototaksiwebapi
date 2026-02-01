package com.codesupreme.mototaksiwebapi.menyupro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MpAuthDto {
    public String businessName;
    public String phone;
    public String password;
}

