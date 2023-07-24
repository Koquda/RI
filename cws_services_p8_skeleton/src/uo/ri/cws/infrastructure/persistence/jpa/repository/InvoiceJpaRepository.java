package uo.ri.cws.infrastructure.persistence.jpa.repository;

import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

import java.util.Optional;

public class InvoiceJpaRepository extends BaseJpaRepository<Invoice>
        implements InvoiceRepository {

    @Override
    public Optional<Invoice> findByNumber(Long numero) {
        return null;
    }

    @Override
    public Long getNextInvoiceNumber() {
        return Jpa.getManager()
                .createNamedQuery("Invoice" + ".getNextInvoiceNumber",
                        Long.class).getSingleResult();
    }

}
