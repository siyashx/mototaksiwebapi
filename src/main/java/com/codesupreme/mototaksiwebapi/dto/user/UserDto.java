package com.codesupreme.mototaksiwebapi.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String createdDate;
    @JsonProperty("isDisable")
    private Boolean isDisable;

}
