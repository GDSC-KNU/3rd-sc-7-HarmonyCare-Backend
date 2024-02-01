package com.harmonycare.google.dto;

public record UserResourceDto(
        String id, String email, Boolean verified_email,
        String name, String given_name, String family_name,
        String picture, String locale
) {
}
