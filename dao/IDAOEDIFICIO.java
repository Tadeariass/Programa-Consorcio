package dao;

import exepcion.DAOException;

import java.util.ArrayList;

public interface IDAOEDIFICIO<T>{
    public void guardar(T elemento) throws DAOException;
    public void eliminar(int id);
    public T buscar(int id) throws DAOException;
    public ArrayList<T> buscarTodos() throws DAOException;

}
