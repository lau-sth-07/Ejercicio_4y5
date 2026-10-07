import java.util.ArrayList;

public class Motocicleta extends Vehiculo {

    private int cilindraje;

    public Motocicleta(String placa, String marca, String modelo,
                       double tarifaDiaria, int cilindraje) {

        super(placa, marca, modelo, tarifaDiaria);

        this.cilindraje = cilindraje;
    }

    @Override
    public double calcularSubtotal(int dias) {

        double subtotal = getTarifaDiaria() * dias;

        if (cilindraje > 250) {
            subtotal = subtotal + 75;
        }

        return subtotal;
    }

    @Override
    public boolean puedeConducir(ArrayList<String> licencias) {

        if (licencias.contains("M")) {
            return true;
        }

        return false;
    }

    @Override
    public int getUmbralMantenimiento() {
        return 20;
    }

    @Override
    public String getDescripcion() {
        return "Motocicleta - Cilindraje: " + cilindraje + " cc";
    }

    @Override
    public String getCategoria() {
        return "Motocicleta";
    }

    public int getCilindraje() {
        return cilindraje;
    }
}