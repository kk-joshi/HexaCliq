package com.hexaware.hexacliq.controller;

import com.hexaware.hexacliq.dto.Client;
import com.hexaware.hexacliq.exception.DataNotFoundException;
import com.hexaware.hexacliq.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@CrossOrigin("*")
@Slf4j
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping("/")
    public ResponseEntity<Object> createClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.save(client));
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.save(client));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Object> findClient(@PathVariable Integer clientId) {
        Client client = clientService.findById(clientId)
                .orElseThrow(() -> new DataNotFoundException("Client not found"));
        return ResponseEntity.ok(client);
    }

}
