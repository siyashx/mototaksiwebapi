package com.codesupreme.mototaksiwebapi.model.elan;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Elan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String image;
    private Integer price;
    private String title;
    private String description;
    private String category;
    private Boolean premium;
    private Date expireDate;
    private Boolean isDeleted;
    private String createdAt;
    private String updatedAt;

    // id-only constructor
    public Elan(Long id) {
        this.id = id;
    }
}


