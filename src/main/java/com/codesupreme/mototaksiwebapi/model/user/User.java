package com.codesupreme.mototaksiwebapi.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private Double balance;
    private String createdDate;
    @JsonProperty("isDisable")
    private Boolean isDisable;

    public User(Long id) {
        this.id = id;
    }

}
