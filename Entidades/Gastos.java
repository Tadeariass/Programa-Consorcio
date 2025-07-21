package Entidades;

import java.time.LocalDate;
import java.util.Date;

public class Gastos {
    private LocalDate fecha;
    private String descripcion;
    double valor;



    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }



    public Gastos(LocalDate fecha, String descripcion, double valor) {
        this.fecha = LocalDate.now();
        this.descripcion = descripcion;
        this.valor = valor;

    }

    public Gastos() {
    }

    @Override
    public String toString() {
        return "GASTOS" +
                "fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", valor=" + valor +
                '}';
    }
}
