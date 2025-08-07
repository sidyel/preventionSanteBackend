package com.sante.reventionplatform;


import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.repository.ServiceRepository;
import com.sante.reventionplatform.repository.TarifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
public class HptDataInitializer implements CommandLineRunner {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TarifRepository
            tarifRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository1 userRepository1;

    @Override
    public void run(String... args) throws Exception {
        if (hospitalRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        System.out.println("🏥 Initialisation des données par défaut...");

        // Créer les hôpitaux
        List<Hospital> hospitals = createHospitals();

        // Créer les services pour chaque hôpital
        for (Hospital hospital : hospitals) {
            createServicesForHospital(hospital);
            createSchedulesForHospital(hospital);
        }

        // Créer quelques utilisateurs de test
        createTestUsers();

        System.out.println("✅ Données initialisées avec succès !");
    }

    private List<Hospital> createHospitals() {
        List<Hospital> hospitals = Arrays.asList(
                new Hospital(
                        "Hôpital Principal de Dakar",
                        "Avenue Cheikh Anta Diop, Dakar, Sénégal",
                        "+221 33 823 42 37",
                        "contact@hopital-principal-dakar.sn"
                ),
                new Hospital(
                        "Clinique Pasteur",
                        "Rue Pasteur, Plateau, Dakar, Sénégal",
                        "+221 33 823 71 81",
                        "info@clinique-pasteur.sn"
                ),
                new Hospital(
                        "Hôpital Aristide Le Dantec",
                        "Avenue Pasteur, Dakar, Sénégal",
                        "+221 33 821 21 21",
                        "contact@ledantec.sn"
                ),
                new Hospital(
                        "Clinique de la Madeleine",
                        "Boulevard de la République, Dakar, Sénégal",
                        "+221 33 822 35 67",
                        "info@madeleine-clinic.sn"
                ),
                new Hospital(
                        "Centre Hospitalier Abass Ndao",
                        "Route de Ouakam, Dakar, Sénégal",
                        "+221 33 869 03 00",
                        "contact@chu-abassndao.sn"
                ),
                new Hospital(
                        "Hôpital Général Idrissa Pouye",
                        "Grand Yoff, Dakar, Sénégal",
                        "+221 33 859 84 84",
                        "info@idrissa-pouye.sn"
                )
        );

        // Ajouter des descriptions
        hospitals.get(0).setDescription("Hôpital public de référence offrant tous les services spécialisés");
        hospitals.get(1).setDescription("Clinique privée moderne avec équipements de pointe");
        hospitals.get(2).setDescription("Centre hospitalier universitaire spécialisé en médecine générale");
        hospitals.get(3).setDescription("Clinique privée de standing avec service VIP");
        hospitals.get(4).setDescription("Centre hospitalier spécialisé en chirurgie et urgences");
        hospitals.get(5).setDescription("Hôpital général offrant soins primaires et spécialisés");

        return hospitalRepository.saveAll(hospitals);
    }

    private void createServicesForHospital(Hospital hospital) {
        List<Service> services = Arrays.asList(
                // Service d'Imagerie
                createService("Service d'Imagerie Médicale", "Scanner, IRM, Radiologie", "Imagerie", hospital),

                // Service de Consultation
                createService("Consultations Spécialisées", "Cardiologie, Neurologie, Dermatologie", "Consultation", hospital),

                // Service d'Urgence
                createService("Service des Urgences", "Urgences 24h/24, Soins d'urgence", "Urgence", hospital),

                // Service de Laboratoire
                createService("Laboratoire d'Analyses", "Analyses sanguines, Biochimie", "Laboratoire", hospital),

                // Service de Chirurgie
                createService("Service de Chirurgie", "Chirurgie générale et spécialisée", "Chirurgie", hospital)
        );

        List<Service> savedServices = serviceRepository.saveAll(services);

        // Créer les tarifs pour chaque service
        for (Service service : savedServices) {
            createTarifsForService(service);
        }
    }

    private Service createService(String name, String description, String category, Hospital hospital) {
        return new Service(name, description, category, hospital);
    }

    private void createTarifsForService(Service service) {
        switch (service.getCategory()) {
            case "Imagerie":
                createImagerieTarifs(service);
                break;
            case "Consultation":
                createConsultationTarifs(service);
                break;
            case "Urgence":
                createUrgenceTarifs(service);
                break;
            case "Laboratoire":
                createLaboratoireTarifs(service);
                break;
            case "Chirurgie":
                createChirurgieTarifs(service);
                break;
        }
    }

    private void createImagerieTarifs(Service service) {
        List<Tarif> tarifs = Arrays.asList(
                new Tarif("Scanner cérébral", new BigDecimal("75000"), service),
                new Tarif("Scanner thoracique", new BigDecimal("80000"), service),
                new Tarif("Scanner abdominal", new BigDecimal("85000"), service),
                new Tarif("IRM cérébrale", new BigDecimal("120000"), service),
                new Tarif("IRM lombaire", new BigDecimal("110000"), service),
                new Tarif("Radiographie thorax", new BigDecimal("15000"), service),
                new Tarif("Radiographie membre", new BigDecimal("12000"), service),
                new Tarif("Échographie abdominale", new BigDecimal("25000"), service),
                new Tarif("Échographie pelvienne", new BigDecimal("25000"), service),
                new Tarif("Échographie cardiaque", new BigDecimal("35000"), service)
        );

        // Ajouter descriptions et durées
        tarifs.get(0).setDescription("Examen du cerveau par scanner");
        tarifs.get(0).setDuration(30);

        tarifs.get(1).setDescription("Imagerie des poumons et du thorax");
        tarifs.get(1).setDuration(25);

        tarifs.get(2).setDescription("Scanner de l'abdomen complet");
        tarifs.get(2).setDuration(35);

        tarifs.get(3).setDescription("IRM haute résolution du cerveau");
        tarifs.get(3).setDuration(45);

        tarifs.get(4).setDescription("IRM de la colonne lombaire");
        tarifs.get(4).setDuration(40);

        tarifs.get(5).setDescription("Radio des poumons standard");
        tarifs.get(5).setDuration(10);

        tarifs.get(6).setDescription("Radio bras, jambe ou articulation");
        tarifs.get(6).setDuration(10);

        tarifs.get(7).setDescription("Échographie abdomen complet");
        tarifs.get(7).setDuration(20);

        tarifs.get(8).setDescription("Échographie gynécologique");
        tarifs.get(8).setDuration(20);

        tarifs.get(9).setDescription("Échocardiographie complète");
        tarifs.get(9).setDuration(30);

        tarifRepository.saveAll(tarifs);
    }

    private void createConsultationTarifs(Service service) {
        List<Tarif> tarifs = Arrays.asList(
                new Tarif("Consultation Cardiologie", new BigDecimal("35000"), service),
                new Tarif("Consultation Neurologie", new BigDecimal("40000"), service),
                new Tarif("Consultation Dermatologie", new BigDecimal("30000"), service),
                new Tarif("Consultation Gynécologie", new BigDecimal("35000"), service),
                new Tarif("Consultation Pédiatrie", new BigDecimal("25000"), service),
                new Tarif("Consultation Ophtalmologie", new BigDecimal("30000"), service),
                new Tarif("Consultation ORL", new BigDecimal("30000"), service),
                new Tarif("Consultation Médecine générale", new BigDecimal("20000"), service),
                new Tarif("Consultation Orthopédie", new BigDecimal("35000"), service),
                new Tarif("Consultation Psychiatrie", new BigDecimal("40000"), service)
        );

        // Ajouter descriptions et durées
        for (Tarif tarif : tarifs) {
            tarif.setDescription("Consultation spécialisée avec examen clinique");
            tarif.setDuration(30);
        }

        tarifs.get(7).setDescription("Consultation généraliste pour tous types de maux");
        tarifs.get(7).setDuration(20);

        tarifRepository.saveAll(tarifs);
    }

    private void createUrgenceTarifs(Service service) {
        List<Tarif> tarifs = Arrays.asList(
                new Tarif("Consultation d'urgence", new BigDecimal("15000"), service),
                new Tarif("Urgence chirurgicale", new BigDecimal("50000"), service),
                new Tarif("Urgence cardiaque", new BigDecimal("45000"), service),
                new Tarif("Sutures simples", new BigDecimal("20000"), service),
                new Tarif("Pansement complexe", new BigDecimal("12000"), service),
                new Tarif("Perfusion", new BigDecimal("8000"), service),
                new Tarif("ECG d'urgence", new BigDecimal("10000"), service),
                new Tarif("Oxygénothérapie", new BigDecimal("15000"), service)
        );

        // Descriptions pour urgences
        tarifs.get(0).setDescription("Prise en charge rapide en urgence");
        tarifs.get(0).setDuration(15);

        tarifs.get(1).setDescription("Urgence nécessitant intervention chirurgicale");
        tarifs.get(1).setDuration(60);

        tarifs.get(2).setDescription("Urgence cardiologique");
        tarifs.get(2).setDuration(45);

        tarifs.get(3).setDescription("Suture de plaie simple");
        tarifs.get(3).setDuration(20);

        tarifRepository.saveAll(tarifs);
    }

    private void createLaboratoireTarifs(Service service) {
        List<Tarif> tarifs = Arrays.asList(
                new Tarif("Bilan sanguin complet", new BigDecimal("25000"), service),
                new Tarif("Glycémie", new BigDecimal("3000"), service),
                new Tarif("Cholestérol total", new BigDecimal("5000"), service),
                new Tarif("Test de grossesse", new BigDecimal("8000"), service),
                new Tarif("Analyse d'urine", new BigDecimal("10000"), service),
                new Tarif("Test HIV", new BigDecimal("15000"), service),
                new Tarif("Hépatite B et C", new BigDecimal("20000"), service),
                new Tarif("Groupe sanguin", new BigDecimal("8000"), service),
                new Tarif("Créatinine", new BigDecimal("5000"), service),
                new Tarif("Bilan hépatique", new BigDecimal("18000"), service)
        );

        // Descriptions pour laboratoire
        for (Tarif tarif : tarifs) {
            tarif.setDescription("Analyse de laboratoire avec résultats rapides");
            tarif.setDuration(15);
        }

        tarifs.get(0).setDescription("Analyse complète du sang (NFS, biochimie)");
        tarifs.get(0).setDuration(30);

        tarifRepository.saveAll(tarifs);
    }

    private void createChirurgieTarifs(Service service) {
        List<Tarif> tarifs = Arrays.asList(
                new Tarif("Appendicectomie", new BigDecimal("350000"), service),
                new Tarif("Cholécystectomie", new BigDecimal("450000"), service),
                new Tarif("Hernie inguinale", new BigDecimal("300000"), service),
                new Tarif("Césarienne", new BigDecimal("400000"), service),
                new Tarif("Chirurgie cataracte", new BigDecimal("250000"), service),
                new Tarif("Prothèse genou", new BigDecimal("800000"), service),
                new Tarif("Chirurgie cardiaque", new BigDecimal("1500000"), service),
                new Tarif("Ablation kyste", new BigDecimal("150000"), service)
        );

        // Descriptions pour chirurgie
        tarifs.get(0).setDescription("Ablation de l'appendice");
        tarifs.get(0).setDuration(90);

        tarifs.get(1).setDescription("Ablation de la vésicule biliaire");
        tarifs.get(1).setDuration(120);

        tarifs.get(2).setDescription("Réparation hernie inguinale");
        tarifs.get(2).setDuration(75);

        tarifs.get(3).setDescription("Accouchement par césarienne");
        tarifs.get(3).setDuration(60);

        tarifs.get(4).setDescription("Chirurgie de la cataracte");
        tarifs.get(4).setDuration(30);

        tarifRepository.saveAll(tarifs);
    }

    private void createSchedulesForHospital(Hospital hospital) {
        // Horaires généraux de l'hôpital (Lundi à Vendredi)
        List<Schedule> weekdaySchedules = Arrays.asList(
                new Schedule(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(18, 0)),
                new Schedule(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(18, 0)),
                new Schedule(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(18, 0)),
                new Schedule(DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(18, 0)),
                new Schedule(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(18, 0))
        );

        // Horaires weekend (services d'urgence uniquement)
        List<Schedule> weekendSchedules = Arrays.asList(
                new Schedule(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(15, 0)),
                new Schedule(DayOfWeek.SUNDAY, LocalTime.of(9, 0), LocalTime.of(15, 0))
        );

        // Assigner à l'hôpital
        for (Schedule schedule : weekdaySchedules) {
            schedule.setHospital(hospital);
        }
        for (Schedule schedule : weekendSchedules) {
            schedule.setHospital(hospital);
        }

        scheduleRepository.saveAll(weekdaySchedules);
        scheduleRepository.saveAll(weekendSchedules);

        // Horaires spécifiques pour le service d'urgence (24h/24)
        Service urgenceService = serviceRepository.findByHospitalIdAndNameContaining(hospital.getId(), "Urgences");
        if (urgenceService != null) {
            List<Schedule> urgenceSchedules = Arrays.asList(
                    new Schedule(DayOfWeek.MONDAY, LocalTime.of(0, 0), LocalTime.of(23, 59)),
                    new Schedule(DayOfWeek.TUESDAY, LocalTime.of(0, 0), LocalTime.of(23, 59)),
                    new Schedule(DayOfWeek.WEDNESDAY, LocalTime.of(0, 0), LocalTime.of(23, 59)),
                    new Schedule(DayOfWeek.THURSDAY, LocalTime.of(0, 0), LocalTime.of(23, 59)),
                    new Schedule(DayOfWeek.FRIDAY, LocalTime.of(0, 0), LocalTime.of(23, 59)),
                    new Schedule(DayOfWeek.SATURDAY, LocalTime.of(0, 0), LocalTime.of(23, 59)),
                    new Schedule(DayOfWeek.SUNDAY, LocalTime.of(0, 0), LocalTime.of(23, 59))
            );

            for (Schedule schedule : urgenceSchedules) {
                schedule.setService(urgenceService);
            }
            scheduleRepository.saveAll(urgenceSchedules);
        }
    }

    private void createTestUsers() {
        List<User1> users = Arrays.asList(
                new User1("Amadou", "Diallo", "amadou.diallo@email.com"),
                new User1("Fatou", "Sall", "fatou.sall@email.com"),
                new User1("Ousmane", "Ba", "ousmane.ba@email.com"),
                new User1("Aïcha", "Ndiaye", "aicha.ndiaye@email.com"),
                new User1("Moussa", "Sy", "moussa.sy@email.com")
        );

        // Ajouter numéros de téléphone et adresses
        users.get(0).setPhone("+221 77 123 45 67");
        users.get(0).setAddress("Parcelles Assainies, Dakar");

        users.get(1).setPhone("+221 76 234 56 78");
        users.get(1).setAddress("Sacré-Cœur, Dakar");

        users.get(2).setPhone("+221 78 345 67 89");
        users.get(2).setAddress("Pikine, Dakar");

        users.get(3).setPhone("+221 77 456 78 90");
        users.get(3).setAddress("Plateau, Dakar");

        users.get(4).setPhone("+221 76 567 89 01");
        users.get(4).setAddress("Yoff, Dakar");

        userRepository1.saveAll(users);
    }
}