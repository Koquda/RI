package uo.ri.cws.application.business.mechanic.crud.command;

import uo.ri.cws.application.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.*;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;

import java.sql.*;
import java.util.List;

public class FindAllMechanics {

    private static final String SQL = "select id, dni, name, surname, version from TMechanics";
    private static final String URL = "jdbc:hsqldb:hsql://localhost";
    private static final String USER = "sa";
    private static final String PASSWORD = "";


    public List<MechanicBLDto> execute() throws BusinessException {
        List<MechanicBLDto> mechanics;

        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;



        try {
            c = DriverManager.getConnection(URL, USER, PASSWORD);

            pst = c.prepareStatement(SQL);

            rs = pst.executeQuery();
            mechanics = MechanicAssembler.toMechanicDtoList(rs);
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
            if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
            if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
        }

        return mechanics;
    }
}
