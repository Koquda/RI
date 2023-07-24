package uo.ri.cws.infrastructure.persistence.jpa.repository;

import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

import java.util.List;
import java.util.Optional;

public class ContractTypeJpaRepository extends BaseJpaRepository<ContractType>
        implements ContractTypeRepository {


    @Override
    public Optional<ContractType> findByName(String name) {
        return Jpa.getManager()
                .createNamedQuery("ContractType.findByName", ContractType.class)
                .setParameter(1, name).getResultStream().findFirst();
    }


    @Override
    public Optional<ContractType> findById(String id) {
        return Jpa.getManager()
                .createNamedQuery("ContractType.findById", ContractType.class)
                .setParameter(1, id).getResultStream().findFirst();
    }

    @Override
    public List<ContractType> findAll() {
        return Jpa.getManager()
                .createNamedQuery("ContractType.findAll", ContractType.class)
                .getResultList();
    }
}
