package com.example.demo.payloads;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;

    @NotEmpty
    @Size(min = 4, message = "Username must be a minimum of 4 characters!!")
    private String name;

    @Email(message = "Email address is not valid!!")
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be a minimum of 3 characters and a maximum of 10 characters!!")
    private String password;

    @NotEmpty
    private String about;

    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
