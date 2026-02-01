package com.codesupreme.mototaksiwebapi.menyupro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MpProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private Double price;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private MpCategory category;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private MpBusiness business;
}
