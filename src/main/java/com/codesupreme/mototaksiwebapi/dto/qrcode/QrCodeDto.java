package com.codesupreme.mototaksiwebapi.dto.qrcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QrCodeDto {

    private Long id;

    private String mainLink;
    private Boolean isAppLink;
    private String androidLink;
    private String iosLink;

    private String createdAt;
    private String updatedAt;
}
