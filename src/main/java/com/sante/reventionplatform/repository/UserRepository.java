package com.sante.reventionplatform.repository;


import com.sante.reventionplatform.entity.TypeActeur;
import com.sante.reventionplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByTelephone(String telephone);
    List<User> findByTypeActeur(TypeActeur typeActeur);
    List<User> findByRegion(String region);
    List<User> findByCommune(String commune);
    List<User> findByVillage(String village);
    List<User> findByActifTrue();

    @Query("SELECT u FROM User u WHERE u.typeActeur = :typeActeur AND u.region = :region")
    List<User> findByTypeActeurAndRegion(@Param("typeActeur") TypeActeur typeActeur, @Param("region") String region);

    @Query("SELECT COUNT(u) FROM User u WHERE u.typeActeur = :typeActeur AND u.actif = true")
    Long countByTypeActeurAndActifTrue(@Param("typeActeur") TypeActeur typeActeur);
}
