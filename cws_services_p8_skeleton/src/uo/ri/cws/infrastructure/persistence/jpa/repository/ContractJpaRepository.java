package uo.ri.cws.infrastructure.persistence.jpa.repository;

import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

import java.time.LocalDate;
import java.util.List;

public class ContractJpaRepository extends BaseJpaRepository<Contract>
        implements ContractRepository {

    @Override
    public List<Contract> findAllInForce() {
        return Jpa.getManager()
                .createNamedQuery("Contract.findAllInForce", Contract.class)
                .getResultList();
    }

    @Override
    public List<Contract> findByMechanicId(String id) {
        return Jpa.getManager()
                .createNamedQuery("Contract.findByMechanicId", Contract.class)
                .setParameter(1, id).getResultList();
    }

    @Override
    public List<Contract> findByProfessionalGroupId(String id) {
        return Jpa.getManager()
                .createNamedQuery("Contract" + ".findByProfessionalGroupId",
                        Contract.class).setParameter(1, id).getResultList();
    }

    @Override
    public List<Contract> findByContractTypeId(String id2Del) {
        return Jpa.getManager()
                .createNamedQuery("Contract" + ".findByContractTypeId",
                        Contract.class).setParameter(1, id2Del).getResultList();
    }

    @Override
    public List<Contract> findAllInForceThisMonth(LocalDate present) {
        return null;
    }

}
