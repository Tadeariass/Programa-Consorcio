package dao;

import Entidades.Edificio;
import Entidades.Ocupantes;
import exepcion.DAOException;

import java.sql.*;
import java.util.ArrayList;

public class DaoOcupante implements IDAOOCUPANTES {
    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:~/test";
    private final String DB_USER = "Consorcio";
    private final String DB_PASSWORD = "Tema8";

    public void guardarint(int idEdificio, String numeroPiso, String idDepartamento, Ocupantes ocupantes,String nombreselc) throws DAOException {

        String sqlVerificar = "SELECT 1 FROM OCUPANTES O " +
                "INNER JOIN DEPARTAMENTO D ON O.ID_DEPTO = D.ID_DEPTO " +
                "INNER JOIN PISO P ON D.NUMEROPISO = P.NUMEROPISO " +
                "INNER JOIN EDIFICIO E ON P.ID_EDIFICIO = E.ID_EDIFICIO " +
                "WHERE E.ID_EDIFICIO = ? AND P.NUMEROPISO = ? AND D.ID_DEPTO = ? AND O.NYAPE = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmtVerificar = conn.prepareStatement(sqlVerificar)) {

            stmtVerificar.setInt(1, idEdificio);
            stmtVerificar.setString(2, numeroPiso);
            stmtVerificar.setString(3, idDepartamento);
            stmtVerificar.setString(4, nombreselc);


            try (ResultSet rs = stmtVerificar.executeQuery()) {
                if (rs.next()) {
                    System.out.println("entre");
                    String sqlActualizar = "UPDATE OCUPANTES SET NYAPE = ?, EDAD = ? WHERE NYAPE = ?";
                    try (PreparedStatement stmtActualizar = conn.prepareStatement(sqlActualizar)) {
                        stmtActualizar.setString(1, ocupantes.getNombre());
                        stmtActualizar.setInt(2, ocupantes.getEdad());
                        stmtActualizar.setString(3, nombreselc);
                        stmtActualizar.executeUpdate();
                    }
                } else {

                    String sqlInsertar = "INSERT INTO OCUPANTES (ID_DEPTO, NYAPE, EDAD) VALUES (?, ?, ?)";
                    try (PreparedStatement stmtInsertar = conn.prepareStatement(sqlInsertar)) {
                        stmtInsertar.setString(1,idDepartamento);
                        stmtInsertar.setString(2, ocupantes.getNombre());
                        stmtInsertar.setInt(3, ocupantes.getEdad());
                        stmtInsertar.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al guardar o actualizar ocupante: " + e.getMessage());
        }
    }


    public void eliminar(int idEdificio, String numPiso, String idDepartamento, String nyape) throws DAOException {
        String sql = "DELETE FROM OCUPANTES " +
                "WHERE ID_DEPTO IN ( " +
                "    SELECT D.ID_DEPTO " +
                "    FROM DEPARTAMENTO D " +
                "    INNER JOIN PISO P ON D.NUMEROPISO = P.NUMEROPISO " +
                "    INNER JOIN EDIFICIO E ON P.ID_EDIFICIO = E.ID_EDIFICIO " +
                "    WHERE E.ID_EDIFICIO = ? AND P.NUMEROPISO = ? AND D.ID_DEPTO = ? " +
                ") AND NYAPE = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEdificio);
            stmt.setString(2, numPiso);
            stmt.setString(3, idDepartamento);
            stmt.setString(4, nyape);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar ocupante: " + e.getMessage());
        }
    }


    public ArrayList<Ocupantes> reporteTodosOcupantesEdificio(int idEdificio) throws DAOException {
        ArrayList<Ocupantes> ocupantes = new ArrayList<>();
        System.out.println(idEdificio);

        String sql = "SELECT O.ID_DEPTO, O.NYAPE, O.EDAD, D.NUMEROPISO, E.ID_EDIFICIO " +
                "FROM OCUPANTES O " +
                "INNER JOIN DEPARTAMENTO D ON O.ID_DEPTO = D.ID_DEPTO " +
                "INNER JOIN PISO P ON D.NUMEROPISO = P.NUMEROPISO " +
                "INNER JOIN EDIFICIO E ON P.ID_EDIFICIO = E.ID_EDIFICIO " +
                "WHERE E.ID_EDIFICIO = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEdificio);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ocupantes o = new Ocupantes();
                o.setDeptonombre(rs.getString("ID_DEPTO"));
                o.setNombre(rs.getString("NYAPE"));
                o.setEdad(rs.getInt("EDAD"));
                ocupantes.add(o);
                System.out.println(ocupantes);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar ocupantes por edificio: " + e.getMessage());
        }

        return ocupantes;
    }

    public ArrayList<Ocupantes> reporteOcupantesxPiso(int idEdificio, String numeroPiso) throws DAOException {
        ArrayList<Ocupantes> ocupantes = new ArrayList<>();

        String sql = "SELECT O.ID_DEPTO, O.NYAPE, O.EDAD, D.NUMEROPISO, E.ID_EDIFICIO " +
                "FROM OCUPANTES O " +
                "INNER JOIN DEPARTAMENTO D ON O.ID_DEPTO = D.ID_DEPTO " +
                "INNER JOIN PISO P ON D.NUMEROPISO = P.NUMEROPISO " +
                "INNER JOIN EDIFICIO E ON P.ID_EDIFICIO = E.ID_EDIFICIO " +
                "WHERE E.ID_EDIFICIO = ? AND P.NUMEROPISO = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEdificio);
            stmt.setString(2, numeroPiso);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ocupantes o = new Ocupantes();
                o.setDeptonombre(rs.getString("ID_DEPTO"));
                o.setNombre(rs.getString("NYAPE"));
                o.setEdad(rs.getInt("EDAD"));

                ocupantes.add(o);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar ocupantes por piso: " + e.getMessage());
        }

        return ocupantes;
    }
    public ArrayList<Ocupantes> reporteOcupantesxDepto(int idEdificio, String numeroPiso, String idDepto) throws DAOException {
        ArrayList<Ocupantes> ocupantes = new ArrayList<>();

        String sql = "SELECT O.ID_DEPTO, O.NYAPE, O.EDAD, D.NUMEROPISO, E.ID_EDIFICIO " +
                "FROM OCUPANTES O " +
                "INNER JOIN DEPARTAMENTO D ON O.ID_DEPTO = D.ID_DEPTO " +
                "INNER JOIN PISO P ON D.NUMEROPISO = P.NUMEROPISO " +
                "INNER JOIN EDIFICIO E ON P.ID_EDIFICIO = E.ID_EDIFICIO " +
                "WHERE E.ID_EDIFICIO = ? AND P.NUMEROPISO = ? AND D.ID_DEPTO = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEdificio);
            stmt.setString(2, numeroPiso);
            stmt.setString(3, idDepto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ocupantes o = new Ocupantes();
                o.setDeptonombre(rs.getString("ID_DEPTO"));
                o.setNombre(rs.getString("NYAPE"));
                o.setEdad(rs.getInt("EDAD"));

                ocupantes.add(o);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar ocupantes por departamento: " + e.getMessage());
        }

        return ocupantes;
    }


}

