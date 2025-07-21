package dao;

import Entidades.Ocupantes;
import exepcion.DAOException;

import java.util.ArrayList;

public interface IDAOOCUPANTES<T> {
    public void guardarint(int idEdificio, String numeroPiso, String idDepartamento, Ocupantes ocupantes, String nombreselc) throws DAOException;
    public void eliminar(int idEdificio, String numPiso, String idDepartamento, String nyape) throws DAOException;
    public ArrayList<T> reporteTodosOcupantesEdificio(int idEdificio) throws DAOException;
    public ArrayList<T> reporteOcupantesxPiso(int idEdificio, String numeroPiso) throws DAOException;
    public ArrayList<T> reporteOcupantesxDepto(int idEdificio, String numeroPiso, String idDepto) throws DAOException;
}
