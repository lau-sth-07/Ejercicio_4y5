import java.util.ArrayList;

public class Automovil extends Vehiculo {

    private int cantidadPasajeros;
    private boolean transmisionAutomatica;

    public Automovil(String placa, String marca, String modelo,
                     double tarifaDiaria, int cantidadPasajeros,
                     boolean transmisionAutomatica) {

        super(placa, marca, modelo, tarifaDiaria);

        this.cantidadPasajeros = cantidadPasajeros;
        this.transmisionAutomatica = transmisionAutomatica;
    }

    @Override
    public double calcularSubtotal(int dias) {

        double subtotal = getTarifaDiaria() * dias;

        if (transmisionAutomatica) {
            subtotal = subtotal + (50 * dias);
        }

        return subtotal;
    }

    @Override
    public boolean puedeConducir(ArrayList<String> licencias) {

        if (licencias.contains("A")) {
            return true;
        }

        if (licencias.contains("B")) {
            return true;
        }

        if (licencias.contains("C")) {
            return true;
        }

        return false;
    }

    @Override
    public int getUmbralMantenimiento() {
        return 30;
    }

    @Override
    public String getDescripcion() {

        if (transmisionAutomatica) {
            return "Automovil - Pasajeros: " + cantidadPasajeros
                    + ", Transmision: Automatica";
        }

        return "Automovil - Pasajeros: " + cantidadPasajeros
                + ", Transmision: Manual";
    }

    @Override
    public String getCategoria() {
        return "Automovil";
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public boolean isTransmisionAutomatica() {
        return transmisionAutomatica;
    }
}