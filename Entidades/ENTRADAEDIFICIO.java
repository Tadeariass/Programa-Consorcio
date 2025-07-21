package Entidades;

import java.time.LocalDate;

public class ENTRADAEDIFICIO extends Gastos {
    private int idEdificio;

    public ENTRADAEDIFICIO(int idEdificio, String descripcion, double valor, LocalDate fecha ) {
        super(fecha, descripcion, valor);
        this.idEdificio = idEdificio;
    }

    public ENTRADAEDIFICIO() {
    }

    public int getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(int idEdificio) {
        this.idEdificio = idEdificio;
    }
}
