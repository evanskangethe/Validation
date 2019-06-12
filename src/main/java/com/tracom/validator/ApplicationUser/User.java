package com.tracom.validator.ApplicationUser;

;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Setter @Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "username cannot be null")
    @Size(min = 4, max = 30,message = "Username must between 4 to 30 characters")
    private String username;

    @Size(min = 8,message = "password length should be greater than 8")
    private String password;

    public User(@NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String email, @NotNull(message = "username cannot be null") @Size(min = 4, max = 30, message = "Username must between 4 to 30 characters") String username, @Min(value = 8, message = "password length should be greater than 8") String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
