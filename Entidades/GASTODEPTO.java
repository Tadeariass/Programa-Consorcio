package Entidades;

import java.time.LocalDate;
import java.util.Date;

public class GASTODEPTO extends Gastos{
    private String id_depto;
    private boolean estado;
    private int id_expensa;

    public GASTODEPTO(String id_depto, String descripcion, double valor, LocalDate fecha, boolean estado) {
        super(fecha, descripcion, valor);
        this.id_depto = id_depto;
        this.estado = estado;
        this.id_expensa=id_expensa;
    }

    public GASTODEPTO() {
    }

    public String getId_depto() {
        return id_depto;
    }

    public void setId_depto(String id_depto) {
        this.id_depto = id_depto;
    }


    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getId_expensa() {
        return id_expensa;
    }

    public void setId_expensa(int id_expensa) {
        this.id_expensa = id_expensa;
    }
}
