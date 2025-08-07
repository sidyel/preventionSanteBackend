package com.sante.reventionplatform.repository;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByHospitalId(Long hospitalId);
    List<Schedule> findByServiceId(Long serviceId);
    List<Schedule> findByDayOfWeek(DayOfWeek dayOfWeek);
    List<Schedule> findByHospitalIdAndDayOfWeek(Long hospitalId, DayOfWeek dayOfWeek);
}