package com.codesupreme.mototaksiwebapi.model.user;

import com.codesupreme.mototaksiwebapi.model.lotoreya.Bilet;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private Double balance;
    @OneToMany(mappedBy = "user")
    private List<Bilet> biletList;
    private String createdDate;
    @JsonProperty("isDisable")
    private Boolean isDisable;

    public User(Long id) {
        this.id = id;
    }

}
