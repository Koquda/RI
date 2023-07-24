package uo.ri.cws.application.service.util.sql;

import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.cws.application.service.util.db.ConnectionData;
import uo.ri.cws.application.service.util.db.JdbcTransaction;
import uo.ri.cws.application.service.util.db.PersistenceXmlScanner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddContractSqlUnitOfWork {

    private static final String INSERT_INTO_TCONTRACTS =
            "INSERT INTO TCONTRACTS" +
                    " ( ID, VERSION, MECHANIC_ID, STARTDATE, ENDDATE, " +
                    "CONTRACTTYPE_ID," +
                    " PROFESSIONALGROUP_ID, ANNUALBASEWAGE, STATE, SETTLEMENT" +
                    " )" +
                    " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final ContractDto dto;
    private final ConnectionData connectionData;
    private String mechanic, type, group;
    private PreparedStatement insertIntoTContracts;

    public AddContractSqlUnitOfWork(ContractDto dto) {
        this.connectionData = PersistenceXmlScanner.scan();
        this.dto = dto;
    }

    public AddContractSqlUnitOfWork(ContractDto dto, String mechanicId,
                                    String contractTypeId,
                                    String professionalGroupId) {
        this.connectionData = PersistenceXmlScanner.scan();
        this.dto = dto;
        this.mechanic = mechanicId;
        this.group = professionalGroupId;
        this.type = contractTypeId;
    }

    public void execute() {
        JdbcTransaction trx = new JdbcTransaction(connectionData);
        trx.execute((con) -> {
            prepareStatements(con);
            insertContract();
        });
    }

    private void insertContract() throws SQLException {
        PreparedStatement st = insertIntoTContracts;
        int i = 1;
        st.setString(i++, dto.id);
        st.setLong(i++, dto.version);
        st.setString(i++, mechanic);
        st.setDate(i++, Date.valueOf(dto.startDate));
        if (dto.endDate != null) {
            st.setDate(i++, Date.valueOf(dto.endDate));
        } else {
            st.setNull(i++, java.sql.Types.DATE);
        }
        st.setString(i++, type);
        st.setString(i++, group);
        st.setDouble(i++, dto.annualBaseWage);
        st.setString(i++, dto.state.toString());
        st.setDouble(i++, dto.settlement);

        st.executeUpdate();
    }

    private void prepareStatements(Connection con) throws SQLException {
        insertIntoTContracts = con.prepareStatement(INSERT_INTO_TCONTRACTS);
    }

}
