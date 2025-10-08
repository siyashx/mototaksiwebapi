package com.codesupreme.mototaksiwebapi.model.qrcode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mainLink;
    private Boolean isAppLink;
    private String androidLink;
    private String iosLink;

    private String createdAt;
    private String updatedAt;
}
