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
    private String password;

    private String profileImage;

    @Column(length = 1000)
    private String bio;

    @Column(unique = true)
    private String slug;
}
