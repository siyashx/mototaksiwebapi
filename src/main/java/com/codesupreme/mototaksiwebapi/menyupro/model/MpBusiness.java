package com.codesupreme.mototaksiwebapi.menyupro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MpBusiness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;
    private String phone;

    @Column(nullable = false)
    private Boolean phoneVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MpApprovalStatus approvalStatus = MpApprovalStatus.PENDING;

    @Column(nullable = false)
    private Boolean isActive = false;

    // (opsional) admin reject reason
    private String rejectReason;

    private String password;

    private String profileImage;

    @Column(length = 1000)
    private String bio;

    @Column(unique = true)
    private String slug;
}
