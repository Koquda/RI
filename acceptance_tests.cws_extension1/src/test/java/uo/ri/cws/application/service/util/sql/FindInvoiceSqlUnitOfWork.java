package uo.ri.cws.application.service.util.sql;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.util.db.ConnectionData;
import uo.ri.cws.application.service.util.db.JdbcTransaction;
import uo.ri.cws.application.service.util.db.PersistenceXmlScanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FindInvoiceSqlUnitOfWork {

    private static final String FIND_BY_ID =
            "SELECT * FROM TINVOICES " + " WHERE iD = ?";
    private final String id;
    private final ConnectionData connectionData;
    private InvoiceDto result = null;
    private PreparedStatement findInvoice;

    public FindInvoiceSqlUnitOfWork(String id) {
        this.connectionData = PersistenceXmlScanner.scan();
        this.id = id;
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
        st.setString(i++, id);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            result = new InvoiceDto();
            result.id = rs.getString("id");
            result.number = rs.getLong("number");
            result.state = rs.getString("status");
            result.total = rs.getDouble("amount");
        }
    }

    private void prepareStatements(Connection con) throws SQLException {
        findInvoice = con.prepareStatement(FIND_BY_ID);
    }

}
