package Entidades;

import java.time.LocalDate;

public class GASTOSEDIFICIO extends Gastos {
        private int idEdificio;

    public GASTOSEDIFICIO(int idEdificio, String descripcion, double valor, LocalDate fecha ) {
        super(fecha, descripcion, valor);
        this.idEdificio = idEdificio;
    }

    public GASTOSEDIFICIO() {

    }

    public int getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(int idEdificio) {
        this.idEdificio = idEdificio;
    }
}
