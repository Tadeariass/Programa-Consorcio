package dao;

import Entidades.Departamento;
import Entidades.Pisos;
import exepcion.DAOException;

import java.sql.*;
import java.util.ArrayList;

public class DaoDepartamento implements IDAODEPARTAMENTOS<Departamento> {
    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:~/test";
    private final String DB_USER = "Consorcio";
    private final String DB_PASSWORD = "Tema8";

    public void guardardpto(Departamento departamento) throws DAOException {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = connection.prepareStatement(
                    "INSERT INTO  DEPARTAMENTO VALUES (?,?,?,?)"
            );
            stmt.setString(1,departamento.getNumeropiso());
            stmt.setString(2,departamento.getId_depto());
            stmt.setDouble(3, departamento.getCuentacredito());
            stmt.setDouble(4,departamento.getCuentaafavor());
            System.out.println("Depto Guardado");
            stmt.executeUpdate();

        }
        catch (ClassNotFoundException | SQLException e) {

            throw new DAOException("Error al insertar Piso: " + e.getMessage());
        }



    }
    public void eliminardpto(int id, String piso, String dpto) throws DAOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(
                     "DELETE FROM departamento " +
                             "WHERE ID_DEPTO = ? " +
                             "AND numeropiso IN ( " +
                             "    SELECT numeropiso FROM piso " +
                             "    WHERE id_edificio = ? " +
                             "    AND numeropiso = ? " +
                             ")" )) {


            stmt.setString(1, dpto);
            stmt.setInt(2, id);
            stmt.setString(3, piso);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el departamento: " + e.getMessage());
        }
    }

    public void modificarDepartamento(Departamento departamento, String idDeptoAnterior, String numeroPisoAnterior) throws DAOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE DEPARTAMENTO SET " +
                             "NUMEROPISO = ?, " +
                             "ID_DEPTO = ?, " +
                             "CUENTA_CREDITO = ?, " +
                             "CUENTA_A_FAVOR = ? " +
                             "WHERE ID_DEPTO = ? AND NUMEROPISO = ?")) {

            Class.forName(DB_JDBC_DRIVER);

            stmt.setString(1, departamento.getNumeropiso());
            stmt.setString(2, departamento.getId_depto());
            stmt.setDouble(3, departamento.getCuentacredito());
            stmt.setDouble(4, departamento.getCuentaafavor());
            stmt.setString(5, idDeptoAnterior);
            stmt.setString(6, numeroPisoAnterior);

            stmt.executeUpdate();

            System.out.println("Departamento modificado: " + departamento.getId_depto());

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Error al modificar departamento: " + e.getMessage());
        }
    }


       public ArrayList<Departamento> buscarTodosDpto(int id_edificio, String id_numeropiso) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Departamento dpto = null;
        ArrayList<Departamento> dptos = new ArrayList<>();
        try {
            System.out.println("Departamento encontrados0"+ id_edificio+ "  "+ id_numeropiso);
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT d.* FROM DEPARTAMENTO d " +
                    "INNER JOIN PISO p ON  d.NUMEROPISO = p.NUMEROPISO " +
                    "WHERE p.ID_EDIFICIO = ? AND p.NUMEROPISO = ?");
            preparedStatement.setInt(1, id_edificio);
            preparedStatement.setString(2, id_numeropiso);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Deptooo encontrados");
            while (rs.next()) {
                dpto = new Departamento();
                System.out.println("creo");
                dpto.setNumeropiso(rs.getString("NUMEROPISO"));
                dpto.setId_depto(rs.getString("ID_DEPTO"));
                System.out.println(rs.getString("ID_DEPTO")+"ACAAA");
                dpto.setCuentaafavor(rs.getFloat("CUENTA_A_FAVOR"));
                dpto.setCuentacredito(rs.getFloat("CUENTA_CREDITO"));
                dptos.add(dpto);
                System.out.println("DEPARTAMENTO encontrado id piso: " + dpto.getNumeropiso() + ", Id depto " + dpto.getId_depto());
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ID recibido: " + id_edificio);
            throw new DAOException("Ocurrió un error en la base de datos");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("No se pudo cerrar la conexión");
            }
        }
        return dptos;
    }
    public void normalizarCuentas(int idEdificio, String numeroPiso) throws DAOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmtSelect = connection.prepareStatement(
                     "SELECT ID_DEPTO, CUENTA_CREDITO, CUENTA_A_FAVOR " +
                             "FROM DEPARTAMENTO d " +
                             "INNER JOIN PISO p ON d.NUMEROPISO = p.NUMEROPISO " +
                             "WHERE p.ID_EDIFICIO = ? AND p.NUMEROPISO = ? " +
                             "AND CUENTA_CREDITO > 0 AND CUENTA_A_FAVOR > 0");
             PreparedStatement stmtUpdate = connection.prepareStatement(
                     "UPDATE DEPARTAMENTO SET CUENTA_CREDITO = ?, CUENTA_A_FAVOR = ? " +
                             "WHERE ID_DEPTO = ? AND NUMEROPISO = ?")) {

            Class.forName(DB_JDBC_DRIVER);
            stmtSelect.setInt(1, idEdificio);
            stmtSelect.setString(2, numeroPiso);

            try (ResultSet rs = stmtSelect.executeQuery()) {
                while (rs.next()) {
                    String idDepto = rs.getString("ID_DEPTO");
                    float credito = rs.getFloat("CUENTA_CREDITO");
                    float favor = rs.getFloat("CUENTA_A_FAVOR");

                    if (credito > favor) {
                        credito -= favor;
                        favor = 0;
                    } else {
                        favor -= credito;
                        credito = 0;
                    }

                    stmtUpdate.setFloat(1, credito);
                    stmtUpdate.setFloat(2, favor);
                    stmtUpdate.setString(3, idDepto);
                    stmtUpdate.setString(4, numeroPiso);
                    stmtUpdate.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Error al normalizar cuentas: " + e.getMessage());
        }
    }
    public void modificarCreditoDepartamento(String idDepto, double monto) throws DAOException {
        String sql = "UPDATE DEPARTAMENTO SET CUENTA_CREDITO = CUENTA_CREDITO + ? WHERE ID_DEPTO = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, monto);
            stmt.setString(2, idDepto);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al modificar el crédito del departamento: " + e.getMessage());
        }
    }
}