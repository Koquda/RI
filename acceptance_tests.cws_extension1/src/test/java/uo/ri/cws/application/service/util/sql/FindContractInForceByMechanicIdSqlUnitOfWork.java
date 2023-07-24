package uo.ri.cws.application.service.util.sql;

import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.cws.application.service.util.db.ConnectionData;
import uo.ri.cws.application.service.util.db.JdbcTransaction;
import uo.ri.cws.application.service.util.db.PersistenceXmlScanner;
import uo.ri.cws.domain.Contract.ContractState;

import java.sql.*;
import java.util.Optional;


public class FindContractInForceByMechanicIdSqlUnitOfWork {

    private static final String FIND_CONTRACT_INFORCE_BY_MECHANICID =
            "SELECT * FROM TCONTRACTS" + " WHERE MECHANIC_ID = ?" +
                    " AND STATE = 'IN_FORCE'";
    private final String mid;
    private final ConnectionData connectionData;
    private PreparedStatement find;
    private String mId, ctId, pgId;
    private Optional<ContractDto> result = Optional.empty();


    public FindContractInForceByMechanicIdSqlUnitOfWork(String id) {
        this.connectionData = PersistenceXmlScanner.scan();
        this.mid = id;
    }

    public void execute() {
        JdbcTransaction trx = new JdbcTransaction(connectionData);
        trx.execute((con) -> {
            prepareStatements(con);
            findContract();
            if (result.isPresent()) fillContract();
        });
    }

    private void fillContract() {
        ContractDto c = this.result.get();

        FindMechanicByIdSqlUnitOfWork munit =
                new FindMechanicByIdSqlUnitOfWork(mId);
        munit.execute();

        c.dni = munit.get().dni;

        FindContractTypeByIdSqlUnitOfWork ctunit =
                new FindContractTypeByIdSqlUnitOfWork(ctId);
        ctunit.execute();
        c.contractTypeName = ctunit.get().get().name;

        FindProfessionalGroupByIdSqlUnitOfWork pgunit =
                new FindProfessionalGroupByIdSqlUnitOfWork(pgId);
        pgunit.execute();
        c.professionalGroupName = pgunit.get().name;
        this.result = Optional.of(c);
    }

    public Optional<ContractDto> get() {
        return result;
    }

    private void findContract() throws SQLException {
        ContractDto result = new ContractDto();
        PreparedStatement st = find;

        int i = 1;
        st.setString(i++, mid);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            result.id = rs.getString("id");
            result.version = rs.getLong("version");
            result.annualBaseWage = rs.getDouble("annualBaseWage");

            ctId = rs.getString("contractType_id");
            result.startDate = rs.getDate("startDate").toLocalDate();
            pgId = rs.getString("professionalGroup_id");
            result.state = ContractState.valueOf(rs.getString("state"));
            mId = rs.getString("mechanic_id");

            // Optional fields
            result.settlement = rs.getDouble("settlement");
            Date d = rs.getDate("endDate");
            if (rs.wasNull()) {
                result.endDate = null;
            } else {
                result.endDate = d.toLocalDate();
            }
            this.result = Optional.ofNullable(result);
        }
    }

    private void prepareStatements(Connection con) throws SQLException {
        find = con.prepareStatement(FIND_CONTRACT_INFORCE_BY_MECHANICID);
    }

}
