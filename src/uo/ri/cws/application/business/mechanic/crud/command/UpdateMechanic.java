package uo.ri.cws.application.business.mechanic.crud.command;

import uo.ri.cws.application.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.*;
import java.sql.*;

public class UpdateMechanic {

    private static String SQL_UPDATE =
            "update TMechanics " +
                    "set name = ?, surname = ?, version = version+1 " +
                    "where id = ?";
    private static final String URL = "jdbc:hsqldb:hsql://localhost";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private Connection c = null;

    private MechanicBLDto mechanicBLDto;

    public UpdateMechanic(MechanicBLDto mechanicBLDto) {
        this.mechanicBLDto = mechanicBLDto;
    }

    public void execute() throws BusinessException {

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            pst = c.prepareStatement(SQL_UPDATE);
            pst.setString(1, mechanicBLDto.name);
            pst.setString(2, mechanicBLDto.surname);
            pst.setString(3, mechanicBLDto.id);

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
            if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
            if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
        }

    }
}
