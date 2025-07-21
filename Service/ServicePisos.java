package Service;

import Entidades.Edificio;
import Entidades.Pisos;
import dao.DaoPisos;
import exepcion.DAOException;

import java.util.ArrayList;

public class ServicePisos {
    private DaoPisos daoepisos = new DaoPisos();

    public ServicePisos() {
        this.daoepisos = new DaoPisos();
    }

    public void guardar(Pisos piso) throws ServiceException
    {
        try {
            daoepisos.guardarpiso(piso);
        }
        catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }
    public void modificar(Pisos piso,String numeroviejo, int idedificio) throws ServiceException
    {
        daoepisos.modificarpiso(piso, numeroviejo, idedificio);
    }
    public void eliminar(int id,String piso) throws  ServiceException
    {
        daoepisos.eliminarpisos(id, piso);
    }

    public ArrayList<Pisos> buscarTodos(int idedificio) throws DAOException
    {
        return daoepisos.buscarTodosPisos(idedificio);
    }
    public ArrayList<Pisos> buscarTodosSinFiltro() throws DAOException
    {
        return daoepisos.buscarTodosPisosdetodosEdificos();
    }
}
