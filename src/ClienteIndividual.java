import java.util.ArrayList;

public class ClienteIndividual extends Cliente {

    public ClienteIndividual(String dpi, String nombre,
                             ArrayList<String> licencias) {

        super(dpi, nombre, licencias);
    }

    @Override
    public double calcularDescuento(double subtotal) {

        if (getAlquileresConfirmados() >= 3) {
            return subtotal * 0.05;
        }

        return 0;
    }

    @Override
    public int getLimiteAlquileresActivos() {
        return 1;
    }
}