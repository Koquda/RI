package uo.ri.cws.application.service.util.sql;

import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.application.service.util.db.ConnectionData;
import uo.ri.cws.application.service.util.db.JdbcTransaction;
import uo.ri.cws.application.service.util.db.PersistenceXmlScanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddContractTypeSqlUnitOfWork {

    private static final String INSERT_INTO_TCONTRACT_TYPES =
            "INSERT INTO TCONTRACTTYPES" +
                    " ( ID, VERSION, NAME, COMPENSATIONDAYS )" +
                    " VALUES ( ?, ?, ?, ?)";
    private final ContractTypeDto dto;
    private final ConnectionData connectionData;
    private PreparedStatement insertIntoTContractTypes;

    public AddContractTypeSqlUnitOfWork(ContractTypeDto dto) {
        this.connectionData = PersistenceXmlScanner.scan();
        this.dto = dto;
    }

    public void execute() {
        JdbcTransaction trx = new JdbcTransaction(connectionData);
        trx.execute((con) -> {
            prepareStatements(con);
            insertContractType();
        });
    }

    private void insertContractType() throws SQLException {
        PreparedStatement st = insertIntoTContractTypes;
        int i = 1;
        st.setString(i++, dto.id);
        st.setLong(i++, dto.version);
        st.setString(i++, dto.name);
        st.setDouble(i++, dto.compensationDays);

        st.executeUpdate();
    }

    private void prepareStatements(Connection con) throws SQLException {
        insertIntoTContractTypes =
                con.prepareStatement(INSERT_INTO_TCONTRACT_TYPES);
    }

}
