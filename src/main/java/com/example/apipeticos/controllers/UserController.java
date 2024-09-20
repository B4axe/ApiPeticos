package com.example.apipeticos.controllers;

import com.example.apipeticos.models.Users;
import com.example.apipeticos.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UsersService usersService;

    @Autowired
    public UserController(  UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping("/getall")
    public List<Users> getAll(){
        return usersService.findAll();
    }


    @PostMapping("/insert")
    public ResponseEntity<String> insertUser(@Valid @RequestBody Users users, BindingResult resultado){
        if (resultado.hasErrors()){
            Map<String, String> erros = validateUser(resultado);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.toString());
        }else {
            usersService.saveUser(users);
            return ResponseEntity.ok("User inserted with success");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        if (usersService.findbyId(id) != null) {
            Users deleted = usersService.deleteUser(id);
            return ResponseEntity.ok("User deleted with sucess : "+deleted);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id,
                                                   @Valid @RequestBody Users userUpdated, BindingResult result){

        if (result.hasErrors()){
            Map<String, String> erros = validateUser(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.toString());
        }else {
            Users user = usersService.findbyId(id);
            user.setFullName(userUpdated.getFullName());
            user.setEmail(userUpdated.getEmail());
            user.setPassword(userUpdated.getPassword());
            user.setUsername(userUpdated.getUsername());
            user.setGender(userUpdated.getGender());
            user.setIdPlan(userUpdated.getIdPlan());
            user.setIdAddress(userUpdated.getIdAddress());
            user.setCnpj(userUpdated.getCnpj());
            usersService.saveUser(user);
            return ResponseEntity.ok("User updated with success");
        }
    }

    @PatchMapping ("/partialupdate/{id}")
    public ResponseEntity<String> atualizarParcial(@PathVariable Long id,
                                                   @RequestBody Map<String, Object> changes){

        Users usersExistente = usersService.findbyId(id);
        if (usersExistente != null){
            Users user = usersExistente;

            if (changes.containsKey("fullName")){
                user.setFullName(String.valueOf(changes.get("fullName")));
            }
            if (changes.containsKey("email")){
                user.setEmail(String.valueOf(changes.get("email")));
            }
            if (changes.containsKey("password")){
                user.setPassword(String.valueOf(changes.get("password")));
            }
            if (changes.containsKey("username")){
                user.setUsername(String.valueOf(changes.get("username")));
            }
            if (changes.containsKey("gender")){
                user.setGender(String.valueOf(changes.get("gender")));
            }
            if (changes.containsKey("idPlan")){
                user.setIdPlan((Integer) changes.get("idPlan"));
            }
            if (changes.containsKey("cnpj")){
                user.setCnpj((Long) changes.get("cnpj"));
            }
            if (changes.containsKey("idAddress")){
                user.setIdAddress((Long) changes.get("idAddress"));
            }




            usersService.saveUser(user);

            return ResponseEntity.ok("Alterado com sucesso");
        }
        return ResponseEntity.notFound().build();
    }



    public Map<String, String> validateUser(BindingResult result){
        Map<String, String> erros = new HashMap<>();

        for (FieldError error : result.getFieldErrors()){
            erros.put(error.getField(), error.getDefaultMessage());
        }

        return erros;
    }
}
