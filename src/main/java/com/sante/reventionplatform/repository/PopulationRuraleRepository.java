package com.sante.reventionplatform.repository;


import com.sante.reventionplatform.entity.PopulationRurale;
import com.sante.reventionplatform.entity.Sexe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PopulationRuraleRepository extends JpaRepository<PopulationRurale, Long> {
    Optional<PopulationRurale> findByUserId(Long userId);
    List<PopulationRurale> findBySexe(Sexe sexe);
    List<PopulationRurale> findByLanguePreferee(String languePreferee);
    List<PopulationRurale> findByAlphabetise(Boolean alphabetise);
    List<PopulationRurale> findByGroupeCommunautaire(String groupeCommunautaire);

    @Query("SELECT p FROM PopulationRurale p WHERE p.user.region = :region")
    List<PopulationRurale> findByRegion(@Param("region") String region);

    @Query("SELECT p FROM PopulationRurale p WHERE p.user.commune = :commune")
    List<PopulationRurale> findByCommune(@Param("commune") String commune);

    @Query("SELECT p FROM PopulationRurale p WHERE p.user.village = :village")
    List<PopulationRurale> findByVillage(@Param("village") String village);

    @Query("SELECT p FROM PopulationRurale p WHERE p.age BETWEEN :ageMin AND :ageMax")
    List<PopulationRurale> findByAgeBetween(@Param("ageMin") Integer ageMin, @Param("ageMax") Integer ageMax);
}