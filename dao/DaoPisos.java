package dao;

import Entidades.Edificio;
import Entidades.Pisos;
import exepcion.DAOException;

import java.sql.*;
import java.util.ArrayList;

public class DaoPisos implements IDAOPISOS<Pisos> {
    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:~/test";
    private final String DB_USER = "Consorcio";
    private final String DB_PASSWORD = "Tema8";

    public void guardarpiso(Pisos piso) throws DAOException {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                stmt = connection.prepareStatement(
                        "INSERT INTO PISO VALUES (?,?,?)"
                );
                stmt.setInt(1, piso.getId_edificio());
                stmt.setString(2,piso.getNumeropiso());
                stmt.setFloat(3,piso.getGastosxpiso());
                System.out.println("Piso Guardado");
                stmt.executeUpdate();

            }
        catch (ClassNotFoundException | SQLException e) {

            throw new DAOException("Error al insertar Piso: " + e.getMessage());
        }



    }
    public void eliminarpisos(int id, String piso) throws DAOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM piso WHERE numeropiso = ? AND id_edificio = ?")) {
            stmt.setInt(2, id);
            stmt.setString(1, piso);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el Piso");
        }
    }

    public void modificarpiso(Pisos piso,String numeroviejo, int id_edificio) throws DAOException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            stmt = connection.prepareStatement("UPDATE piso SET NUMEROPISO = ?, PORCENTAJE_EXTRA_X_PISO = ? WHERE numeropiso = ? AND id_edificio = ?");
            stmt.setString(1, piso.getNumeropiso());
            stmt.setFloat(2,piso.getGastosxpiso());
            stmt.setString(3,numeroviejo);
            stmt.setInt(4,id_edificio);
            stmt.executeUpdate();
            System.out.println("Edificio MODIFICADO con ID: " + piso.getNumeropiso());
        } catch (ClassNotFoundException | SQLException e) {

            throw new DAOException("Error al insertar O Modificar Edificio: " + e.getMessage());
        }
    }
    public Pisos buscarpisos(int idedificio,String numeropiso) throws DAOException {
        Connection connection = null;
        PreparedStatement stmt = null;
        Pisos piso = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = connection.prepareStatement("SELECT * FROM PISO WHERE id_edificio = ? and numeropiso = ?");
            stmt.setInt(1, idedificio);
            stmt.setString(2,numeropiso);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                piso = new Pisos();
                piso.setId_edificio(rs.getInt("ID_EDIFICIO"));
                piso.setNumeropiso(rs.getString("NUMEROPISO"));
                piso.setGastosxpiso(rs.getFloat("PORCENTAJE_EXTRA_X_PISO"));

            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("No se encuentra el Piso del edificio con ID: " + idedificio + "con numero de piso"+ numeropiso +  "Error: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("No se pudo cerrar la conexión");
            }
        }
        return piso;
    }

    public ArrayList<Pisos> buscarTodosPisosdetodosEdificos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Pisos piso = null;
        ArrayList<Pisos> pisos = new ArrayList<>();
        try {
            System.out.println("Pisos encontrados0");
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM piso");
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Pisos encontrados");
            while (rs.next()) {
                piso = new Pisos();
                piso.setId_edificio(rs.getInt("ID_EDIFICIO"));
                piso.setNumeropiso(rs.getString("NUMEROPISO"));
                piso.setGastosxpiso(rs.getFloat("PORCENTAJE_EXTRA_X_PISO"));
                pisos.add(piso);
                System.out.println("Piso encontrado: " + piso.getNumeropiso() + ", Id edificio " + piso.getId_edificio());
            }
        } catch (ClassNotFoundException | SQLException e) {

            throw new DAOException("Ocurrió un error en la base de datos");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("No se pudo cerrar la conexión");
            }
        }
        return pisos;
    }

    public ArrayList<Pisos> buscarTodosPisos(int id_edificio) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Pisos piso = null;
        ArrayList<Pisos> pisos = new ArrayList<>();
        try {
            System.out.println("Pisos encontrados0");
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM piso  WHERE id_edificio = (?)");
            preparedStatement.setInt(1, id_edificio);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Pisos encontrados");
            while (rs.next()) {
                piso = new Pisos();
                piso.setId_edificio(rs.getInt("ID_EDIFICIO"));
                piso.setNumeropiso(rs.getString("NUMEROPISO"));
                piso.setGastosxpiso(rs.getFloat("PORCENTAJE_EXTRA_X_PISO"));
                pisos.add(piso);
                System.out.println("Piso encontrado: " + piso.getNumeropiso() + ", Id edificio " + piso.getId_edificio());
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
        return pisos;
    }
}


