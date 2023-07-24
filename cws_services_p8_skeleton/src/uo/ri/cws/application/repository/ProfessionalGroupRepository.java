package uo.ri.cws.application.repository;

import uo.ri.cws.domain.ProfessionalGroup;

import java.util.Optional;

public interface ProfessionalGroupRepository
        extends Repository<ProfessionalGroup> {

    /**
     * @param name
     * @return the contract category object
     */
    Optional<ProfessionalGroup> findByName(String name);

}
