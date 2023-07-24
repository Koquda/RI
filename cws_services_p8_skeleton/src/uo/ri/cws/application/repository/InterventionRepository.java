package uo.ri.cws.application.repository;

import uo.ri.cws.domain.Intervention;

import java.time.LocalDateTime;
import java.util.List;

public interface InterventionRepository extends Repository<Intervention> {

    /**
     * @param id,       refers to the mechanic id
     * @param startDate
     * @param endDate
     * @return a list with all interventions done by the mechanic in between the
     * to dates (both included), or an empty list if there are none
     */
    List<Intervention> findByMechanicIdBetweenDates(Long id,
                                                    LocalDateTime startDate,
                                                    LocalDateTime endDate);

}
