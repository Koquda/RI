package uo.ri.cws.infrastructure.persistence.jpa.repository;

import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.domain.Client;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;

import java.util.List;
import java.util.Optional;

public class ClientJpaRepository extends BaseJpaRepository<Client>
        implements ClientRepository {


    @Override
    public Optional<Client> findByDni(String dni) {
        return Optional.empty();
    }

    @Override
    public List<Client> findSponsoredByClient(String id) {
        return null;
    }

    @Override
    public List<Client> findRecomendedBy(String id) {
        return null;
    }

    @Override
    public List<Client> findWithThreeUnusedWorkOrders() {
        return null;
    }

    @Override
    public List<Client> findWithRecomendationsDone() {
        return null;
    }
}
