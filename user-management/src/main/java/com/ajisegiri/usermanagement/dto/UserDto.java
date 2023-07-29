package com.ajisegiri.usermanagement.dto;

import com.ajisegiri.usermanagement.customConstraints.UniqueEmail;
import com.ajisegiri.usermanagement.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable{
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String fireBaseUid;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String imageUrl;
    @Email
    @NotNull
    @UniqueEmail
    private String email;
    @NotNull
    private String contactNumber;

    private Role role;
    @NotNull
    private AddressDto address;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date creationDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Time time;



}