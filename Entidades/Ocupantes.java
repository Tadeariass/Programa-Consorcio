package Entidades;

public class Ocupantes {
    String deptonombre;
    String nombre;
    String apellido;
    int edad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDeptonombre() {
        return deptonombre;
    }

    public void setDeptonombre(String deptonombre) {
        this.deptonombre = deptonombre;
    }

    public Ocupantes() {
    }

    public Ocupantes(Departamento departamento, String nombre, String apellido, int edad) {
        deptonombre=departamento.getId_depto();
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Entidades.Ocupantes{" +
                "nombre='" + nombre  +
                ", apellido='" + apellido +
                ", edad=" + edad +
                '}';
    }
}
