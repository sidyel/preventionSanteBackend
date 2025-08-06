package com.sante.reventionplatform.repository;


import com.sante.reventionplatform.entity.StructureSante;
import com.sante.reventionplatform.entity.TypeStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StructureSanteRepository extends JpaRepository<StructureSante, Long> {
    Optional<StructureSante> findByUserId(Long userId);
    List<StructureSante> findByTypeStructure(TypeStructure typeStructure);
    List<StructureSante> findByNomStructureContainingIgnoreCase(String nomStructure);
    Optional<StructureSante> findByNumeroAgrement(String numeroAgrement);

    @Query("SELECT s FROM StructureSante s WHERE s.user.region = :region")
    List<StructureSante> findByRegion(@Param("region") String region);

    @Query("SELECT s FROM StructureSante s WHERE s.typeStructure = :typeStructure AND s.user.region = :region")
    List<StructureSante> findByTypeStructureAndRegion(@Param("typeStructure") TypeStructure typeStructure, @Param("region") String region);
}
