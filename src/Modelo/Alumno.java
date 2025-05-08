package Modelo;

public class Alumno extends Persona {

    private String matricula;
    private int licenciatura;
    private int semestre;

    public Alumno() {

    }

    public int getLicenciatura() {
        return licenciatura;
    }

    public void setLicenciatura(int licenciatura) {
        this.licenciatura = licenciatura;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Alumno{");
        sb.append(super.toString());
        sb.append("matricula=").append(matricula);
        sb.append(", licenciatura=").append(licenciatura);
        sb.append('}');
        return sb.toString();
    }

}
