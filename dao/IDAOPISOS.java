package dao;

import Entidades.Pisos;
import exepcion.DAOException;

import java.util.ArrayList;

public interface IDAOPISOS <P>{
    public void guardarpiso(P elemento) throws DAOException;
    public void eliminarpisos(int id, String numero) throws DAOException;
    public void modificarpiso(Pisos piso,String numeroviejo, int id_edificio) throws DAOException;
    public P buscarpisos(int idedificio,String numeropiso) throws DAOException;
    public ArrayList<P> buscarTodosPisos(int id_edificio) throws DAOException;
    public ArrayList <Pisos> buscarTodosPisosdetodosEdificos() throws DAOException;

    }
