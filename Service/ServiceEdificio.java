package Service;

import Entidades.Edificio;
import dao.Daoedificio;
import exepcion.DAOException;

import java.util.ArrayList;

public class ServiceEdificio {
    private Daoedificio daoedificio;

    public ServiceEdificio() {
        this.daoedificio = new Daoedificio();
    }

    public void guardar(Edificio edificio) throws ServiceException
    {
        try {
            daoedificio.guardar(edificio);
        }
        catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }
    public void modificar(Edificio edificio) throws ServiceException
    {
        daoedificio.guardar(edificio);
    }
    public void eliminar(int id) throws  ServiceException
    {
        daoedificio.eliminar(id);
    }

    public ArrayList<Edificio> buscarTodos() throws DAOException
    {
        return daoedificio.buscarTodos();
    }
}
