import java.util.ArrayList;

public abstract class Cliente {

    private String identificador;
    private String nombre;
    private ArrayList<String> licencias;
    private int alquileresConfirmados;

    public Cliente(String identificador, String nombre,
                   ArrayList<String> licencias) {

        this.identificador = identificador;
        this.nombre = nombre;
        this.licencias = licencias;
        this.alquileresConfirmados = 0;
    }

    public abstract double calcularDescuento(double subtotal);

    public abstract int getLimiteAlquileresActivos();

    public void incrementarAlquileresConfirmados() {
        alquileresConfirmados = alquileresConfirmados + 1;
    }

    public boolean tieneLicencia(String licencia) {

        if (licencias.contains(licencia)) {
            return true;
        }

        return false;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<String> getLicencias() {
        return licencias;
    }

    public int getAlquileresConfirmados() {
        return alquileresConfirmados;
    }
}