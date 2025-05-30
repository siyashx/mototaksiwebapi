package com.codesupreme.mototaksiwebapi.dto.lotoreya;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BiletDto {
    private Long id;
    private String code;
    private Long userId;
}
