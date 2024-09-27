package com.example.apipeticos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "User_")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser;
    Long idAddress;
    @NotNull
    String fullName;
    @NotNull
    String username;
    @NotNull
    String email;
    String gender;
    int idPlan;
    String cnpj;
    @Transient
    private String Plan;
    @Transient
    private String bairro;
    @Transient
    private String phone;




    @Override
    public String toString() {
        return "Users { " +
                "idUser= " + idUser +
                ", idAddress= " + idAddress +
                ", fullName= '" + fullName + '\'' +
                ", username= '" + username + '\'' +
                ", email= '" + email + '\'' +
                ", gender= '" + gender + '\'' +
                ", idPlan= " + idPlan +
                ", cnpj= " + cnpj +
                " }";
    }
}
