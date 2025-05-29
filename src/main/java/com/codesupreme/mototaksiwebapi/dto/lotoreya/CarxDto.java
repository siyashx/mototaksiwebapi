package com.codesupreme.mototaksiwebapi.dto.lotoreya;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CarxDto {
    private Long id;

    private Long winnerUserId;
    private String winnerBiletCode;
    private Date createdAt;
}
