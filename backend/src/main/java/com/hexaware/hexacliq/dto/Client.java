package com.hexaware.hexacliq.dto;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "client_master")
public class Client {
    @Id
    @GeneratedValue
    private Integer clientId;

    @Column(name = "client_name")
    private String clientName;

    @OneToMany(mappedBy = "client")
    private Set<Project> projects;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "primary_contact")
    private String primaryContact;

    @Column(name = "secondary_contact")
    private String secondaryContact;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "modified_time")
    private Date modifiedTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;
}
