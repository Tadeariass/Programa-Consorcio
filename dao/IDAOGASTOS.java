package dao;

import Entidades.*;
import exepcion.DAOException;

import java.util.ArrayList;

public interface IDAOGASTOS <O> {
    public void guardarExpensa(Gastos gasto) throws DAOException;

    public ArrayList<GASTOSEDIFICIO> buscarGastosEdificio(int idEdificio) throws DAOException;

    public ArrayList<GASTODEPTO> buscarExpensasDeEdificio(int idEdificio) throws DAOException;

    public ArrayList<GASTODEPTO> buscarExpensasDePiso(int idEdificio, String numeroPiso) throws DAOException;

    public ArrayList<GASTODEPTO> buscarExpensasDelDepartamento(int id_edificio, String numpiso, String idDepto) throws DAOException;

    public ArrayList<ENTRADAEDIFICIO> buscarEntradasEdificio(int idEdificio) throws DAOException;

    public void convertirExpensasPagasAEntradas(int idEdificio, int id_expensa) throws DAOException;

    public int marcarExpensaComoPagada(int idExpensa) throws DAOException;
}

