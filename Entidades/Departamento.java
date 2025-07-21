package Entidades;

import java.time.Month;
import java.util.ArrayList;

public class Departamento
{

    String numeropiso;
    String id_depto;
    ArrayList<Ocupantes> ocupantes;
    ArrayList<Gastos> gastos;
    double gastosTotal;
    double cuentaafavor;
    double cuentacredito;

    public ArrayList<Ocupantes> getOcupantes()
    {
        return ocupantes;
    }

    public void setOcupantes(ArrayList<Ocupantes> ocupantes)
    {
        this.ocupantes = ocupantes;
    }

    public ArrayList<Gastos> getGastos()
    {
        return gastos;
    }

    public void setGastos(ArrayList<Gastos> gastos)
    {
        this.gastos = gastos;
    }

    public double getGastosTotal()
    {
        return gastosTotal;
    }

    public void setGastosTotal(double gastosTotal)
    {
        this.gastosTotal = gastosTotal;
    }

    public double getCuentaafavor()
    {
        return cuentaafavor;
    }

    public void setCuentaafavor(double cuentaafavor)
    {
        this.cuentaafavor = cuentaafavor;
    }

    public double getCuentacredito()
    {
        return cuentacredito;
    }

    public void setCuentacredito(double cuentacredito)
    {
        this.cuentacredito = cuentacredito;
    }

    public String getId_depto() {
        return id_depto;
    }

    public void setId_depto(String id_depto) {
        this.id_depto = id_depto;
    }

    public String getNumeropiso() {
        return numeropiso;
    }

    public void setNumeropiso(String numeropiso) {
        this.numeropiso = numeropiso;
    }

    public Departamento(Pisos pisos, String id_depto, double cuentaafavor, double saldonegativo)
    {
        numeropiso = pisos.getNumeropiso();
        this.id_depto = id_depto;
        this.ocupantes = new ArrayList<>();
        this.gastos = new ArrayList<>();
        this.cuentaafavor = 0.0;
        this.cuentacredito = 0.0;
    }

    public Departamento() {}

    public void agregarocupantes(Ocupantes ocupante)
    {
    ocupantes.add(ocupante);
    }
    public void agregarGastos(Gastos gasto)
    {
        gastos.add(gasto);
    }


    /// /////////////////////////////////////////////////////////////////////////////////////////////
    ///
    /*
    public void agregarCuentaafavor(double saldopositivo)
    {
        setCuentaafavor(cuentaafavor+saldopositivo);
    }

    public void seleccionargasto(Month mes, int anio)
    {
        for (Gastos gasto : gastos)
        {       System.out.println("adentro");
            if (gasto.getFecha().getYear()==anio)
            {       System.out.println("adentro2");
                if (gasto.getFecha().getMonth().equals(mes))
                {
                    System.out.println("adentro3");
                    pagargasto(gasto);
                }
            }
        }
    }

    public void pagargasto(Gastos gasto)
    {
        if (gasto.isEstado()) {
            System.out.println("El gasto");
            gasto.setEstado(false);
            setCuentacredito(getCuentacredito() + gasto.getValor());
            System.out.println(gasto.isEstado());
            if (cuentacredito < cuentaafavor) {
                setCuentaafavor(getCuentaafavor() - getCuentacredito());
                setCuentacredito(0);
            }
            else {
                setCuentacredito(getCuentacredito() - getCuentaafavor());
                setCuentaafavor(0);
            }
            mostrarGastos();
        }
    }
    public void mostrarGastos(){
        for (Gastos gasto : gastos){
            System.out.println(gasto.toString());
        }
    }

    public void pagoCuentacredito()
    {
        if(getCuentaafavor()>0)
        {

            if (getCuentaafavor()>getCuentacredito())
            {
                setCuentaafavor(getCuentaafavor()-getCuentacredito());
                setCuentacredito(0);
            }
            else
            {
                setCuentacredito(getCuentacredito()-getCuentaafavor());
                setCuentaafavor(0);
            }
        }

    }

    public double consultarGastototalxdepartamento(Month mes, int anio)
    {
        double total = 0;

        for (Gastos gasto : gastos)
        {
            if (gasto.getFecha().getYear()+1900==anio)
            {
                if (gasto.getFecha().getMonth().equals(mes))
                {
                    total += gasto.getValor();
                }
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "Entidades.Departamento{" +
                "numeropiso='" + numeropiso + '\'' +
                ", nomenclatura='" + id_depto + '\'' +
                ", ocupantes=" + ocupantes +
                ", gastos=" + gastos +
                ", gastosTotal=" + gastosTotal +
                ", cuentaafavor=" + cuentaafavor +
                ", cuentacredito=" + cuentacredito +
                '}';
    }*/
}


