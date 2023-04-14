package com.hexaware.hexacliq.service;

import com.hexaware.hexacliq.dao.IClientRepository;
import com.hexaware.hexacliq.dto.Client;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService  {
    @Autowired
    IClientRepository repository;

    @Transactional
    public Client save(Client client) {
        return repository.save(client);
    }

    public Optional<Client> findById(Integer id) {
        return repository.findById(id);
    }
}
