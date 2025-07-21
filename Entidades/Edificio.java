package Entidades;

import java.util.ArrayList;

public class Edificio {
    private int id;
    private String calle;
    private ArrayList<Pisos> pisos;
    private int cantPisos;

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getCantPisos() {
        return cantPisos;
    }

    public void setCantPisos(int cantPisos) {
        this.cantPisos = cantPisos;
    }

    public ArrayList<Pisos> getPisos() {
        return pisos;
    }

    public void setPisos(ArrayList<Pisos> pisos) {
        this.pisos = pisos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Edificio() {
    }

    public Edificio(String calle) {
        this.id=id;
        this.calle = calle;
        this.pisos = new ArrayList<>();
        cantPisos = 0;
    }

   /*
    public void AgregarPiso(Pisos piso){
        pisos.add(piso);
    }

    public void CreargastoGeneral(Gastos gastos){
        for (Pisos piso : pisos){
            piso.generargastos(piso,gastos);
        }
    }

    public int obtenercantPisos(){
        int cont=0;
        cont=pisos.size();
        return cont;
    }

    @Override
    public String toString() {
        return "Entidades.Edificio{" +
                "calle='" + calle + '\'' +
                ", pisos=" + pisos +
                ", cantPisos=" + obtenercantPisos() +
                '}';
    }*/
}
