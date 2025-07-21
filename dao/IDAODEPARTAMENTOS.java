package dao;

import Entidades.Departamento;
import exepcion.DAOException;

import java.util.ArrayList;

public interface IDAODEPARTAMENTOS <T>{
    public void guardardpto(Departamento departamento) throws DAOException;
    public void eliminardpto(int id, String piso, String dpto);
    public void modificarDepartamento(Departamento departamento, String idDeptoAnterior, String numeroPisoAnterior) throws DAOException;
    public void normalizarCuentas(int idEdificio, String numeroPiso) throws DAOException;
    public ArrayList<T> buscarTodosDpto(int id_edificio, String id_numeropiso)throws DAOException;
}
