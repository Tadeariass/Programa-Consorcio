package Entidades;

import java.util.ArrayList;
import java.util.Date;

public class Pisos {
    private int id_edificio;
    private String numeropiso;
    private ArrayList<Departamento> departamentos;
    private float gastosxpiso;
    private double porcentajeextraxpiso;
    private int cantidaddeptosporpiso;

    public String getNumeropiso() {
        return numeropiso;
    }

    public void setNumeropiso(String numeropiso) {
        this.numeropiso = numeropiso;
    }

    public ArrayList<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(ArrayList<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public float getGastosxpiso() {
        return gastosxpiso;
    }

    public void setGastosxpiso(float gastosxpiso) {
        this.gastosxpiso = gastosxpiso;
    }


    public int getCantidaddeptosporpiso() {
        return cantidaddeptosporpiso;
    }

    public void setCantidaddeptosporpiso(int cantidaddeptosporpiso) {
        this.cantidaddeptosporpiso = cantidaddeptosporpiso;
    }

    public int getId_edificio() {
        return id_edificio;
    }

    public void setId_edificio(int id_edificio) {
        this.id_edificio = id_edificio;
    }

    public Pisos() {
    }
/*
    public Pisos(Edificio edificio, String numeropiso, double porcentajeextraxpiso, int cantidaddeptosporpiso) {

        this.id_edificio = edificio.getId();
        this.departamentos = new ArrayList<>();
        this.numeropiso = numeropiso;
        this.porcentajeextraxpiso = porcentajeextraxpiso;
        this.cantidaddeptosporpiso = cantidaddeptosporpiso;
    }
    public void agregarDepartamento(Departamento departamento) {
        if (departamentos.size()<getCantidaddeptosporpiso()){
            departamentos.add(departamento);
        }
        else{
            System.out.println("El Piso esta ocupado" );
            return;
        }
    }
    public void generargastos(Pisos pisos,Gastos gastos) {
        for (Departamento departamento : departamentos) {
            System.out.println(gastos.getValor());
            Gastos gastos1=new Gastos(new Date(),gastos.getDescripcion(),gastos.getValor(),gastos.isEstado());
            gastos1.setValor(gastos.getValor()+gastos.getValor()*porcentajeextraxpiso);
            departamento.agregarGastos(gastos1);
        }
    }
*/
}
