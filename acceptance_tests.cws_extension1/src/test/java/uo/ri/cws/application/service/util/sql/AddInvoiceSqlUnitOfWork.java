package uo.ri.cws.application.service.util.sql;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.util.db.ConnectionData;
import uo.ri.cws.application.service.util.db.JdbcTransaction;
import uo.ri.cws.application.service.util.db.PersistenceXmlScanner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddInvoiceSqlUnitOfWork {


    private static final String INSERT_INTO_TINVOICES =
            "INSERT INTO TINVOICES" +
                    " ( ID, VERSION, NUMBER, AMOUNT, VAT, DATE, STATE )" +
                    " VALUES ( ?, ?, ?, ?, ?, ?, ?)";
    private final InvoiceDto record;
    private final ConnectionData connectionData;
    private PreparedStatement insertIntoInvoices;

    public AddInvoiceSqlUnitOfWork(InvoiceDto arg) {
        this.connectionData = PersistenceXmlScanner.scan();
        this.record = arg;
    }

    public void execute() {
        JdbcTransaction trx = new JdbcTransaction(connectionData);
        trx.execute((con) -> {
            prepareStatements(con);
            insertInvoice();
        });
    }

    private void insertInvoice() throws SQLException {

        PreparedStatement st = insertIntoInvoices;
        int i = 1;

        st.setString(i++, record.id);
        st.setLong(i++, record.version);
        st.setLong(i++, record.number);
        st.setDouble(i++, record.total);
        st.setDouble(i++, record.vat);
        st.setDate(i++, Date.valueOf(record.date));
        st.setString(i++, record.state);
        st.executeUpdate();

    }

    private void prepareStatements(Connection con) throws SQLException {
        insertIntoInvoices = con.prepareStatement(INSERT_INTO_TINVOICES);

    }

}
