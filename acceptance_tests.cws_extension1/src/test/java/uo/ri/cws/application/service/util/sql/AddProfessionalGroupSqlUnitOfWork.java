package uo.ri.cws.application.service.util.sql;

import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.service.util.db.ConnectionData;
import uo.ri.cws.application.service.util.db.JdbcTransaction;
import uo.ri.cws.application.service.util.db.PersistenceXmlScanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddProfessionalGroupSqlUnitOfWork {

    private static final String INSERT_INTO_TPROFESSIONALGROUPS =
            "INSERT INTO TPROFESSIONALGROUPS" +
                    " ( ID, VERSION, NAME, TRIENNIUMPAYMENT, " +
                    "PRODUCTIVITYBONUSPERCENTAGE )" +
                    " VALUES ( ?, ?, ?, ?, ?)";
    private final ProfessionalGroupBLDto dto;
    private final ConnectionData connectionData;
    private PreparedStatement insertIntoWorkOrders;

    public AddProfessionalGroupSqlUnitOfWork(ProfessionalGroupBLDto dto) {
        this.connectionData = PersistenceXmlScanner.scan();
        this.dto = dto;
    }

    public void execute() {
        JdbcTransaction trx = new JdbcTransaction(connectionData);
        trx.execute((con) -> {
            prepareStatements(con);
            insertWorkOrder();
        });
    }

    private void insertWorkOrder() throws SQLException {
        PreparedStatement st = insertIntoWorkOrders;
        int i = 1;
        st.setString(i++, dto.id);
        st.setLong(i++, dto.version);
        st.setString(i++, dto.name);
        st.setDouble(i++, dto.trieniumSalary);
        st.setDouble(i++, dto.productivityRate);

        st.executeUpdate();
    }

    private void prepareStatements(Connection con) throws SQLException {
        insertIntoWorkOrders =
                con.prepareStatement(INSERT_INTO_TPROFESSIONALGROUPS);
    }

}
