package Modelo;

public class Licenciatura {

    private int id;
    private String nombre;
    private int facultad;

    public Licenciatura() {
    }

    public Licenciatura(int id, String nombre, int facultad) {
        this.id = id;
        this.nombre = nombre;
        this.facultad = facultad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFacultad() {
        return facultad;
    }

    public void setFacultad(int facultad) {
        this.facultad = facultad;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
