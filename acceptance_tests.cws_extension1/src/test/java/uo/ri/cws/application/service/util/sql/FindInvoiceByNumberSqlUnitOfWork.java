package uo.ri.cws.application.service.util.sql;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.util.db.ConnectionData;
import uo.ri.cws.application.service.util.db.JdbcTransaction;
import uo.ri.cws.application.service.util.db.PersistenceXmlScanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FindInvoiceByNumberSqlUnitOfWork {

    private static final String FIND_BY_NUMBER =
            "SELECT * FROM TINVOICES " + " WHERE number = ?";
    private final Long number;
    private final ConnectionData connectionData;
    private InvoiceDto result = null;
    private PreparedStatement findInvoice;

    public FindInvoiceByNumberSqlUnitOfWork(String id) {
        this.connectionData = PersistenceXmlScanner.scan();
        this.number = Long.parseLong(id);
    }

    public void execute() {
        JdbcTransaction trx = new JdbcTransaction(connectionData);
        trx.execute((con) -> {
            prepareStatements(con);
            findInvoice();
        });
    }

    public InvoiceDto get() {
        return result;
    }

    private void findInvoice() throws SQLException {
        PreparedStatement st = findInvoice;

        int i = 1;
        st.setLong(i++, number);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            result = new InvoiceDto();
            result.id = rs.getString("id");
            result.number = rs.getLong("number");
            result.state = rs.getString("state");
            result.total = rs.getDouble("amount");
        }
    }

    private void prepareStatements(Connection con) throws SQLException {
        findInvoice = con.prepareStatement(FIND_BY_NUMBER);
    }

}
