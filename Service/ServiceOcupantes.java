package Service;

import Entidades.Ocupantes;
import dao.DaoOcupante;
import exepcion.DAOException;

import java.util.ArrayList;

public class ServiceOcupantes {
    private DaoOcupante daoOcupante;

    public ServiceOcupantes() {
        this.daoOcupante = new DaoOcupante();
    }

    public void guardar(int idEdificio, String numeroPiso, String idDepartamento, Ocupantes ocupantes) throws ServiceException
    {
        try {
            daoOcupante.guardarint( idEdificio,  numeroPiso,  idDepartamento,ocupantes,null);
        }
        catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }
    public ArrayList<Ocupantes> BuscarOcupantes(int idedificio, String numeroPiso, String idDepto) throws ServiceException {
        try {
            if(numeroPiso==null){
            return daoOcupante.reporteTodosOcupantesEdificio(idedificio);}
            else{
                if(idDepto==null){
                    return daoOcupante.reporteOcupantesxPiso(idedificio,numeroPiso);
                }else{
                    return daoOcupante.reporteOcupantesxDepto(idedificio,numeroPiso,idDepto);
                }
            }


        }
        catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public void eliminar(int idEdificio,String numeroPiso,String idDepto,String nombreSeleccionado) throws  ServiceException
    {
        daoOcupante.eliminar(idEdificio, numeroPiso, idDepto, nombreSeleccionado);
    }
    public void actualizar(int idEdificio, String numeroPiso, String idDepartamento, Ocupantes ocupanteSeleccionado,String nombreselec) throws ServiceException {
        try {
            daoOcupante.guardarint(idEdificio, numeroPiso, idDepartamento, ocupanteSeleccionado, nombreselec);
        }
    catch (DAOException d){
        throw new ServiceException(d.getMessage());
    }
    }

}

