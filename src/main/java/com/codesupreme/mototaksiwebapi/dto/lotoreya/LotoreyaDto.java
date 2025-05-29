package com.codesupreme.mototaksiwebapi.dto.lotoreya;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LotoreyaDto {
    private Long id;
    private Long userId;
    private Long elanId;

    private CarxDto carx;
    private List<BiletDto> bilets;

    private Double biletPrice;
    private Date lotoreyaDate;
    private Boolean isAccept;
    private Date createdAt;
}
