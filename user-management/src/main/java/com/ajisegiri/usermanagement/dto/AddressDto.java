package com.ajisegiri.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;

}
