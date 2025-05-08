package Modelo;

public class Sesion {

    private int idRegistro;
    private String fecha;
    private String horaEntrada;
    private String horaSalida;
    private int idUsuario;

    public Sesion() {

    }

    public Sesion(int idRegistro, String fecha, String horaEntrada, String horaSalida, int idUsuario) {
        this.idRegistro = idRegistro;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.idUsuario = idUsuario;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Sesion{"
                + "idRegistro=" + idRegistro
                + ", fecha='" + fecha + '\''
                + ", horaEntrada='" + horaEntrada + '\''
                + ", horaSalida='" + horaSalida + '\''
                + ", idUsuario=" + idUsuario
                + '}';
    }
}
