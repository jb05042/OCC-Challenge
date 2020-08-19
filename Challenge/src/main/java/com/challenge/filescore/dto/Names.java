package com.challenge.filescore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Names {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
