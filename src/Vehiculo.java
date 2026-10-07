import java.util.ArrayList;

public abstract class Vehiculo {

    private String placa;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private String estado;
    private int diasAcumulados;

    public Vehiculo(String placa, String marca, String modelo,
                    double tarifaDiaria) {

        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tarifaDiaria = tarifaDiaria;
        this.estado = "Disponible";
        this.diasAcumulados = 0;
    }

    public abstract double calcularSubtotal(int dias);

    public abstract boolean puedeConducir(ArrayList<String> licencias);

    public abstract int getUmbralMantenimiento();

    public abstract String getDescripcion();

    public abstract String getCategoria();

    public boolean necesitaMantenimiento() {

        if (diasAcumulados >= getUmbralMantenimiento()) {
            return true;
        }

        return false;
    }

    public void agregarDias(int dias) {
        diasAcumulados = diasAcumulados + dias;
    }

    public void marcarComoAlquilado() {
        estado = "Alquilado";
    }

    public void marcarComoDisponible() {
        estado = "Disponible";
    }

    public void marcarComoMantenimiento() {
        estado = "Mantenimiento";
    }

    public void finalizarMantenimiento() {
        diasAcumulados = 0;
        estado = "Disponible";
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public String getEstado() {
        return estado;
    }

    public int getDiasAcumulados() {
        return diasAcumulados;
    }
}