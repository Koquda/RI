package uo.ri.cws.application.repository;

import uo.ri.cws.domain.ContractType;

import java.util.Optional;

public interface ContractTypeRepository extends Repository<ContractType> {

    /**
     * @param name
     * @return the contract type object
     */
    Optional<ContractType> findByName(String name);

}
