import java.util.ArrayList;

public class Microbus extends Vehiculo {

    private int cantidadPasajeros;
    private boolean incluyePiloto;

    public Microbus(String placa, String marca, String modelo,
                    double tarifaDiaria, int cantidadPasajeros,
                    boolean incluyePiloto) {

        super(placa, marca, modelo, tarifaDiaria);

        this.cantidadPasajeros = cantidadPasajeros;
        this.incluyePiloto = incluyePiloto;
    }

    @Override
    public double calcularSubtotal(int dias) {

        double subtotal = getTarifaDiaria() * dias;

        if (incluyePiloto) {
            subtotal = subtotal + (250 * dias);
        }

        return subtotal;
    }

    @Override
    public boolean puedeConducir(ArrayList<String> licencias) {

        if (incluyePiloto) {
            return true;
        }

        if (licencias.contains("A")) {
            return true;
        }

        if (licencias.contains("B")) {
            return true;
        }

        return false;
    }

    @Override
    public int getUmbralMantenimiento() {
        return 25;
    }

    @Override
    public String getDescripcion() {

        if (incluyePiloto) {
            return "Microbus - Pasajeros: " + cantidadPasajeros
                    + ", Incluye piloto: Si";
        }

        return "Microbus - Pasajeros: " + cantidadPasajeros
                + ", Incluye piloto: No";
    }

    @Override
    public String getCategoria() {
        return "Microbus";
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public boolean isIncluyePiloto() {
        return incluyePiloto;
    }
}