package com.example.apipeticos.repositories;


import com.example.apipeticos.models.PetRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRegisterRepository extends JpaRepository<PetRegister, Integer> {

    PetRegister findByIdPet(Integer idPet);

    @Procedure(procedureName = "delete_pet")
    void deletePet(@Param("pet_id") Integer idPet);

    @Query(value = "SELECT pr.* FROM pet_register pr JOIN user_ u ON pr.id_user = u.id_user WHERE u.username = :username", nativeQuery = true)
    List<PetRegister> findPetsByUsername(@Param("username") String username);

    @Procedure(procedureName = "insert_pet")
    void insertPet(
            @Param("id_user") int idUser,
            @Param("nickname") String nickname,
            @Param("age") int age,
            @Param("sex") String sex,
            @Param("specie") String specie,
            @Param("race") String race,
            @Param("size") String size,
            @Param("color") String color,
            @Param("description") String description
    );

}
