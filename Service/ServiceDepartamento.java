package Service;

import Entidades.Departamento;
import Entidades.Pisos;
import dao.DaoDepartamento;
import exepcion.DAOException;

import java.util.ArrayList;

public class ServiceDepartamento {
    private DaoDepartamento daoDepartamento;

    public ServiceDepartamento() {
        this.daoDepartamento = new DaoDepartamento();
    }

    public void guardar(Departamento dpto) throws ServiceException
    {
        try {
            daoDepartamento.guardardpto(dpto);
        }
        catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }
    public void modificarDepartamento(Departamento departamento, String idDeptoAnterior, String numeroPisoAnterior) throws ServiceException {
        try {
            daoDepartamento.modificarDepartamento(departamento, idDeptoAnterior, numeroPisoAnterior);
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar departamento: " + e.getMessage());
        }
    }

    public void eliminar(int id,String piso, String dpto) throws  ServiceException
    {try{
        daoDepartamento.eliminardpto(id, piso, dpto);
    } catch (DAOException e) {
        throw new ServiceException("Error al Eliminar Departamento: " + e.getMessage());
    }
    }

    public ArrayList<Departamento> buscarTodos(int idedificio, String id_piso) throws DAOException
    {
        return daoDepartamento.buscarTodosDpto(idedificio, id_piso);
    }

    public void normalizarCuentas(int idEdificio, String numeroPiso) throws ServiceException {
        try {
            daoDepartamento.normalizarCuentas(idEdificio, numeroPiso);
        } catch (DAOException e) {
            throw new ServiceException("Error al normalizar cuentas: " + e.getMessage());
        }


    }
    public void modificarCreditodepartamento( String idDepto,double monto) throws ServiceException {
        try {
            daoDepartamento.modificarCreditoDepartamento(idDepto, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar Credito Departamento departamento: " + e.getMessage());
        }
    }
}