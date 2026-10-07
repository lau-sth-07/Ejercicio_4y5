import java.util.ArrayList;

public class CamionetaCarga extends Vehiculo {

    private double capacidadToneladas;

    public CamionetaCarga(String placa, String marca, String modelo,
                          double tarifaDiaria, double capacidadToneladas) {

        super(placa, marca, modelo, tarifaDiaria);

        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public double calcularSubtotal(int dias) {

        double subtotal = getTarifaDiaria() * dias;
        double recargo = 100 * capacidadToneladas * dias;

        subtotal = subtotal + recargo;

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

        return false;
    }

    @Override
    public int getUmbralMantenimiento() {
        return 15;
    }

    @Override
    public String getDescripcion() {
        return "Camioneta de carga - Capacidad maxima: "
                + capacidadToneladas + " toneladas";
    }

    @Override
    public String getCategoria() {
        return "Camioneta de carga";
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }
}