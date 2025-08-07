package com.sante.reventionplatform.dto;

import com.sante.reventionplatform.entity.Hospital;

import java.util.List;
import java.util.stream.Collectors;

public class HospitalDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String description;
    private List<ServiceDTO> services;

    public HospitalDTO() {}

    public HospitalDTO(com.sante.reventionplatform.entity.Hospital hospital) {
        this.id = hospital.getId();
        this.name = hospital.getName();
        this.address = hospital.getAddress();
        this.phone = hospital.getPhone();
        this.email = hospital.getEmail();
        this.description = hospital.getDescription();
        if (hospital.getServices() != null) {
            this.services = hospital.getServices().stream()
                    .map(ServiceDTO::new)
                    .collect(Collectors.toList());
        }
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<ServiceDTO> getServices() { return services; }
    public void setServices(List<ServiceDTO> services) { this.services = services; }
}