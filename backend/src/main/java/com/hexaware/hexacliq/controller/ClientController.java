package com.hexaware.hexacliq.controller;

import com.hexaware.hexacliq.dto.Client;
import com.hexaware.hexacliq.dto.User;
import com.hexaware.hexacliq.exception.DataNotFoundException;
import com.hexaware.hexacliq.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/clients")
@CrossOrigin("*")
@Slf4j
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public ResponseEntity<Object> createClient(@RequestBody Client client, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        client.setCreatedBy(user.getUsername());
        client.setCreatedTime(LocalDate.now());
        client.setModifiedBy(user.getUsername());
        client.setModifiedTime(LocalDate.now());
        return ResponseEntity.ok(clientService.save(client));
    }

    @PutMapping
    public ResponseEntity<Object> updateClient(@RequestBody Client client, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        client.setModifiedBy(user.getUsername());
        client.setModifiedTime(LocalDate.now());
        return ResponseEntity.ok(clientService.save(client));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Object> findClient(@PathVariable Integer clientId) {
        Client client = clientService.findById(clientId)
                .orElseThrow(() -> new DataNotFoundException("Client not found"));
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

}
