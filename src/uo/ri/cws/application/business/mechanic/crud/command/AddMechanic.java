package uo.ri.cws.application.business.mechanic.crud.command;

import uo.ri.cws.application.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.*;
import java.sql.*;
import java.util.UUID;


// Esta clase es la parte de lógica de AddMechanic
public class AddMechanic {

    private static String SQL = "insert into TMechanics(id, dni, name, surname, version) values (?, ?, ?, ?, ?)";
    private static final String URL = "jdbc:hsqldb:hsql://localhost";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private MechanicBLDto mechanicBLDto;

    public AddMechanic(MechanicBLDto mechanicBLDto) {
        this.mechanicBLDto = mechanicBLDto;
    }



    public MechanicBLDto execute() throws BusinessException {


        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DriverManager.getConnection(URL, USER, PASSWORD);

            pst = c.prepareStatement(SQL);
            mechanicBLDto.id = UUID.randomUUID().toString();
            pst.setString(1, mechanicBLDto.id);
            pst.setString(2, mechanicBLDto.dni);
            pst.setString(3, mechanicBLDto.name);
            pst.setString(4, mechanicBLDto.surname);
            pst.setLong(5, 1L);

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
            if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
            if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
        }

        return mechanicBLDto;
    }
}
