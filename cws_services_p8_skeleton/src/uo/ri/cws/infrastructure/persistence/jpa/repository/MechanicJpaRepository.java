package uo.ri.cws.infrastructure.persistence.jpa.repository;

import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

import java.util.List;
import java.util.Optional;

import static uo.ri.cws.domain.Contract.ContractState;

public class MechanicJpaRepository extends BaseJpaRepository<Mechanic>
        implements MechanicRepository {

    @Override
    public Optional<Mechanic> findByDni(String dni) {
        // Lo de la query es Nombre de la entidad.Nombre del metodo
        return Jpa.getManager()
                .createNamedQuery("Mechanic.findByDni", Mechanic.class)
                .setParameter(1, dni).getResultStream().findFirst();
    }

    @Override
    public List<Mechanic> findAllInForce() {
        return Jpa.getManager()
                .createNamedQuery("Mechanic" + ".findByAllContractsInForce",
                        Mechanic.class).setParameter(1, ContractState.IN_FORCE)
                .getResultList();
    }

    @Override
    public List<Mechanic> findInForceInContractType(ContractType contractType) {
        return Jpa.getManager()
                .createNamedQuery("Mechanic" + ".findInForceInContractType",
                        Mechanic.class).setParameter(1, contractType.getName())
                .setParameter(2, ContractState.IN_FORCE).getResultList();
    }

    @Override
    public List<Mechanic> findAllInProfessionalGroup(ProfessionalGroup group) {
        return Jpa.getManager().createNamedQuery(
                        "Mechanic" + ".findMechanicInProfessionalGroup",
                        Mechanic.class)
                .setParameter(1, group).getResultList();
    }

}
