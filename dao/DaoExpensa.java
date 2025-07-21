package dao;

import Entidades.ENTRADAEDIFICIO;
import Entidades.GASTODEPTO;
import Entidades.GASTOSEDIFICIO;
import Entidades.Gastos;
import Service.ServiceDepartamento;
import Service.ServiceException;
import exepcion.DAOException;

import java.sql.*;
import java.util.ArrayList;

public class DaoExpensa implements IDAOGASTOS {
    private final String DB_URL = "jdbc:h2:~/test";
    private final String DB_USER = "Consorcio";
    private final String DB_PASSWORD = "Tema8";

    public void guardarExpensa(Gastos gasto) throws DAOException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            if (gasto instanceof GASTOSEDIFICIO) {
                String sql = "INSERT INTO GASTOS_EDIFICIO (ID_EDIFICIO, DESCRIPCION, MONTO, FECHA) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    GASTOSEDIFICIO e = (GASTOSEDIFICIO) gasto;
                    stmt.setInt(1, e.getIdEdificio());
                    stmt.setString(2, e.getDescripcion());
                    stmt.setDouble(3, e.getValor());
                    stmt.setDate(4, Date.valueOf(e.getFecha()));
                    stmt.executeUpdate();
                }

            } else if (gasto instanceof GASTODEPTO) {
                String sql = "INSERT INTO EXPENSAS_DE_DEPARTAMENTO (ID_DEPTO, DESCRIPCION, MONTO, FECHA, PAGADO,) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    GASTODEPTO d = (GASTODEPTO) gasto;
                    stmt.setString(1, d.getId_depto());
                    stmt.setString(2, d.getDescripcion());
                    stmt.setDouble(3, d.getValor());
                    stmt.setDate(4, Date.valueOf(d.getFecha()));
                    stmt.setBoolean(5, d.isEstado());

                    stmt.executeUpdate();
                }

            }

        } catch (SQLException e) {
            throw new DAOException("Error al guardar expensa: " + e.getMessage());
        }
    }

    public ArrayList<GASTOSEDIFICIO> buscarGastosEdificio(int idEdificio) throws DAOException {
        ArrayList<GASTOSEDIFICIO> lista = new ArrayList<>();

        String sql = "SELECT * FROM GASTOS_EDIFICIO WHERE ID_EDIFICIO = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEdificio);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                GASTOSEDIFICIO gasto = new GASTOSEDIFICIO();

                        gasto.setIdEdificio(rs.getInt("ID_EDIFICIO"));

                        gasto.setDescripcion(rs.getString("DESCRIPCION"));

                        gasto.setValor(rs.getDouble("MONTO"));
                        gasto.setFecha(rs.getDate("FECHA").toLocalDate());

                lista.add(gasto);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar gastos del edificio: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<GASTODEPTO> buscarExpensasDeEdificio(int idEdificio) throws DAOException {
        ArrayList<GASTODEPTO> lista = new ArrayList<>();
        String sql = """
            SELECT EDD.*
            FROM EXPENSAS_DE_DEPARTAMENTO EDD
            JOIN DEPARTAMENTO D ON EDD.ID_DEPTO = D.ID_DEPTO
            JOIN D ON PISO P = D.NUM_PISO
            JOIN P ON EDIFICIO ED = P.ID_EDIFICIO
            WHERE D.ID_EDIFICIO = ? 
            """;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEdificio);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                GASTODEPTO gasto = new GASTODEPTO(
                        rs.getString("ID_DEPTO"),
                        rs.getString("DESCRIPCION"),
                        rs.getDouble("MONTO"),
                        rs.getDate("FECHA").toLocalDate(),
                        rs.getBoolean("PAGADO")
                );
                lista.add(gasto);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar expensas de edificio: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<GASTODEPTO> buscarExpensasDePiso(int idEdificio, String numeroPiso) throws DAOException {
        ArrayList<GASTODEPTO> lista = new ArrayList<>();
        String sql = """
            SELECT EDD.*
            FROM EXPENSAS_DE_DEPARTAMENTO EDD
            JOIN DEPARTAMENTO D ON EDD.ID_DEPTO = D.ID_DEPTO
            JOIN D ON PISO P = D.NUM_PISO
            JOIN P ON EDIFICIO ED = P.ID_EDIFICIO
            WHERE D.ID_EDIFICIO = ? AND D.NUMEROPISO = ?
            """;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEdificio);
            stmt.setString(2, numeroPiso);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                GASTODEPTO gasto = new GASTODEPTO(
                        rs.getString("ID_DEPTO"),
                        rs.getString("DESCRIPCION"),
                        rs.getDouble("MONTO"),
                        rs.getDate("FECHA").toLocalDate(),
                        rs.getBoolean("PAGADO")
                );
                lista.add(gasto);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar expensas de piso: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<GASTODEPTO> buscarExpensasDelDepartamento(int id_edificio,String numpiso, String idDepto) throws DAOException {
        ArrayList<GASTODEPTO> lista = new ArrayList<>();
        String sql ="""
        SELECT EDD.*
        FROM EXPENSAS_DE_DEPARTAMENTO EDD
        JOIN DEPARTAMENTO D ON EDD.ID_DEPTO = D.ID_DEPTO
        JOIN PISO P ON D.NUMEROPISO = P.NUMEROPISO
        JOIN EDIFICIO ED ON P.ID_EDIFICIO = ED.ID_EDIFICIO
        WHERE P.ID_EDIFICIO = ? AND D.NUMEROPISO = ? AND D.ID_DEPTO = ?
        """;;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_edificio);
            stmt.setString(2, numpiso);
            stmt.setString(3, idDepto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                GASTODEPTO gasto = new GASTODEPTO();

                gasto.setId_depto(rs.getString("ID_DEPTO"));
                        gasto.setDescripcion(rs.getString("DESCRIPCION"));
                       gasto.setValor( rs.getDouble("MONTO"));
                        gasto.setFecha( rs.getDate("FECHA").toLocalDate() );
                        gasto.setEstado(rs.getBoolean("PAGADO"));
                        gasto.setId_expensa( rs.getInt("ID_EXPENSA"));
                    System.out.println(gasto.getId_expensa());
                lista.add(gasto);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar expensas del departamento: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ENTRADAEDIFICIO> buscarEntradasEdificio(int idEdificio) throws DAOException {
        ArrayList<ENTRADAEDIFICIO> lista = new ArrayList<>();
        String sql = "SELECT * FROM ENTRADA_EDIFICIO WHERE ID_EDIFICIO = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEdificio);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ENTRADAEDIFICIO entrada = new ENTRADAEDIFICIO(
                        rs.getInt("ID_EDIFICIO"),
                        rs.getString("DESCRIPCION"),
                        rs.getDouble("MONTO"),
                        rs.getDate("FECHA").toLocalDate()
                );
                lista.add(entrada);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar entradas del edificio: " + e.getMessage());
        }
        return lista;
    }

    public void convertirExpensasPagasAEntradas(int idEdificio, int id_expensa) throws DAOException {
        String sqlSelect ="""
        SELECT EDD.DESCRIPCION, EDD.MONTO, EDD.FECHA
        FROM EXPENSAS_DE_DEPARTAMENTO EDD
        WHERE EDD.ID_EXPENSA = ?
    """;
        String sqlInsert = "INSERT INTO ENTRADA_EDIFICIO (ID_EDIFICIO, DESCRIPCION, MONTO, FECHA) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {

            stmtSelect.setInt(1, id_expensa);
            ResultSet rs = stmtSelect.executeQuery();

            while (rs.next()) {
                stmtInsert.setInt(1, idEdificio);
                stmtInsert.setString(2, rs.getString("DESCRIPCION"));
                stmtInsert.setDouble(3, rs.getDouble("MONTO"));
                stmtInsert.setDate(4, rs.getDate("FECHA"));
                stmtInsert.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DAOException("Error al convertir expensas en entradas: " + e.getMessage());
        }
    }

    public int marcarExpensaComoPagada(int idExpensa) throws DAOException {
        String sqlUpdate = "UPDATE EXPENSAS_DE_DEPARTAMENTO SET PAGADO = TRUE WHERE ID_EXPENSA = ?";
        String sqlSelect = "SELECT ID_DEPTO, MONTO FROM EXPENSAS_DE_DEPARTAMENTO WHERE ID_EXPENSA = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
             PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {


            stmtSelect.setInt(1, idExpensa);
            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                String idDepto = rs.getString("ID_DEPTO");
                double monto = rs.getDouble("MONTO");


                stmtUpdate.setInt(1, idExpensa);
                stmtUpdate.executeUpdate();

                ServiceDepartamento serviceDepartamento = new ServiceDepartamento();
                serviceDepartamento.modificarCreditodepartamento(idDepto, monto);

                return idExpensa;

            } else {
                throw new DAOException("No se encontró una expensa con el ID especificado.");
            }

        } catch (SQLException | ServiceException e) {
            throw new DAOException("Error al marcar la expensa como pagada: " + e.getMessage());
        }
    }

}