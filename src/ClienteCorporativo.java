import java.util.ArrayList;

public class ClienteCorporativo extends Cliente {

    private String nombreContacto;

    public ClienteCorporativo(String nit, String nombreEmpresa,
                              String nombreContacto,
                              ArrayList<String> licencias) {

        super(nit, nombreEmpresa, licencias);

        this.nombreContacto = nombreContacto;
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * 0.10;
    }

    @Override
    public int getLimiteAlquileresActivos() {
        return 3;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }
}