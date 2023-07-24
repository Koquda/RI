package uo.ri.cws.infrastructure.persistence.jpa.repository;

import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

import java.util.Optional;

public class ProfessionalGroupJpaRepository
        extends BaseJpaRepository<ProfessionalGroup>
        implements ProfessionalGroupRepository {
    @Override
    public Optional<ProfessionalGroup> findByName(String name) {
        return Jpa.getManager()
                .createNamedQuery("ProfessionalGroup" + ".findByName")
                .setParameter(1, name).getResultStream().findFirst();
    }
}
