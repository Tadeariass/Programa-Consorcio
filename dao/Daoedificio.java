package dao;

import Entidades.Edificio;
import exepcion.DAOException;

import java.sql.*;
import java.util.ArrayList;

public class Daoedificio implements IDAOEDIFICIO<Edificio> {

    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:~/test";
    private final String DB_USER = "Consorcio";
    private final String DB_PASSWORD = "Tema8";


    public void guardar(Edificio edificio) throws DAOException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            stmt = connection.prepareStatement("SELECT * FROM EDIFICIO WHERE id_edificio = ?");
            stmt.setInt(1, edificio.getId());
            generatedKeys = stmt.executeQuery();

            if (generatedKeys.next()) {

                stmt = connection.prepareStatement("UPDATE EDIFICIO SET nombre_calle = ? WHERE id_edificio = ?");
                stmt.setString(1, edificio.getCalle());
                stmt.setInt(2, edificio.getId());
                stmt.executeUpdate();
                System.out.println("Edificio MODIFICADO con ID: " + edificio.getId());

            } else {

                stmt = connection.prepareStatement(
                        "INSERT INTO EDIFICIO (nombre_calle) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                stmt.setString(1, edificio.getCalle());
                System.out.println("Edificio Guardado"+ stmt.getGeneratedKeys());
                stmt.executeUpdate();

            }
        } catch (ClassNotFoundException | SQLException e) {

            throw new DAOException("Error al insertar O Modificar Edificio: " + e.getMessage());
        }
    }





    public void eliminar(int id) throws DAOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM edificio WHERE id_edificio = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el departamento");
        }
    }



    public Edificio buscar(int idedificio) throws DAOException {
        Connection connection = null;
        PreparedStatement stmt = null;
        Edificio edificio = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = connection.prepareStatement("SELECT * FROM edificio WHERE id_edificio = ?");
            stmt.setInt(1, idedificio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                edificio = new Edificio();
                edificio.setId(rs.getInt("ID_EDIFICIO"));
                edificio.setCalle(rs.getString("NOMBRE_CALLE"));

            }






        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("No se encuentra el Edificio con ID: " + idedificio + ". Error: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("No se pudo cerrar la conexión");
            }
        }
        return edificio;
    }

    public ArrayList<Edificio> buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Edificio edificio = null;
        ArrayList<Edificio> edificios = new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM edificio");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                edificio = new Edificio();
                edificio.setId(rs.getInt("ID_EDIFICIO"));
                edificio.setCalle(rs.getString("NOMBRE_CALLE"));
                edificios.add(edificio);
                System.out.println("Edificio encontrado: " + edificio.getId() + ", Calle: " + edificio.getCalle());
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
        return edificios;
    }
}

