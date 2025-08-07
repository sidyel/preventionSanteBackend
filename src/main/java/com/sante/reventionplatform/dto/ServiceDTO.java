package com.sante.reventionplatform.dto;



import com.sante.reventionplatform.entity.Service;
import com.sante.reventionplatform.entity.Tarif;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Long hospitalId;
    private String hospitalName;
    private List<TarifDTO> tarifs;

    public ServiceDTO() {}

    public ServiceDTO(Service service) {
        this.id = service.getId();
        this.name = service.getName();
        this.description = service.getDescription();
        this.category = service.getCategory();
        this.hospitalId = service.getHospital().getId();
        this.hospitalName = service.getHospital().getName();
        if (service.getTarifs() != null) {
            this.tarifs = service.getTarifs().stream()
                    .map(TarifDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public static class TarifDTO {
        private Long id;
        private String procedureName;
        private Double price;
        private String description;
        private Integer duration;

        public TarifDTO() {}

        public TarifDTO(Tarif tarif) {
            this.id = tarif.getId();
            this.procedureName = tarif.getProcedureName();
            this.price = tarif.getPrice().doubleValue();
            this.description = tarif.getDescription();
            this.duration = tarif.getDuration();
        }

        // Getters et Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getProcedureName() { return procedureName; }
        public void setProcedureName(String procedureName) { this.procedureName = procedureName; }

        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Integer getDuration() { return duration; }
        public void setDuration(Integer duration) { this.duration = duration; }
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Long getHospitalId() { return hospitalId; }
    public void setHospitalId(Long hospitalId) { this.hospitalId = hospitalId; }

    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

    public List<TarifDTO> getTarifs() { return tarifs; }
    public void setTarifs(List<TarifDTO> tarifs) { this.tarifs = tarifs; }
}