package Service;


import Entidades.ENTRADAEDIFICIO;
import Entidades.GASTODEPTO;
import Entidades.GASTOSEDIFICIO;
import Entidades.Gastos;
import dao.DaoExpensa;
import exepcion.DAOException;

import java.util.ArrayList;

public class ServiceExpensas {

    private final DaoExpensa dao;

    public ServiceExpensas() {
        this.dao = new DaoExpensa();
    }

    public void guardarGasto(Gastos gasto) throws DAOException {
        dao.guardarExpensa(gasto);
    }

    public ArrayList<GASTOSEDIFICIO> obtenerGastosEdificio(int idEdificio) throws DAOException {
        return dao.buscarGastosEdificio(idEdificio);
    }

    public ArrayList<GASTODEPTO> obtenerExpensasEdificio(int idEdificio) throws DAOException {
        return dao.buscarExpensasDeEdificio(idEdificio);
    }

    public ArrayList<GASTODEPTO> obtenerExpensasDePiso(int idEdificio, String numeroPiso) throws DAOException {
        return dao.buscarExpensasDePiso(idEdificio, numeroPiso);
    }

    public ArrayList<GASTODEPTO> obtenerExpensasDepartamento(int idEdificio, String numeroPiso, String idDepto) throws DAOException {
        return dao.buscarExpensasDelDepartamento(idEdificio, numeroPiso, idDepto);
    }

    public ArrayList<ENTRADAEDIFICIO> obtenerEntradasEdificio(int idEdificio) throws DAOException {
        return dao.buscarEntradasEdificio(idEdificio);
    }


    public void marcarExpensaComoPagada(int idExpensa, int idEdificio) throws DAOException {
       dao.convertirExpensasPagasAEntradas(idEdificio, dao.marcarExpensaComoPagada(idExpensa));
    }
}

