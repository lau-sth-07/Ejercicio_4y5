import java.util.Scanner;

public class View {

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("\n===== RENTAMOVIL =====");
        System.out.println("1. Registrar vehiculo");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Consultar vehiculos");
        System.out.println("4. Consultar clientes");
        System.out.println("5. Cotizar alquiler");
        System.out.println("6. Confirmar alquiler");
        System.out.println("7. Registrar devolucion");
        System.out.println("8. Finalizar mantenimiento");
        System.out.println("9. Reportes");
        System.out.println("10. Salir");
    }

    public String leer(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public int leerEntero(String mensaje) {
        return Integer.parseInt(leer(mensaje));
    }

    public double leerDecimal(String mensaje) {
        return Double.parseDouble(leer(mensaje));
    }

    public boolean confirmar(String mensaje) {
        return leer(mensaje).equalsIgnoreCase("S");
    }

    public void mensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void cerrar() {
        scanner.close();
    }

    public void mostrarTiposVehiculo() {
        System.out.println("\n1. Automovil");
        System.out.println("2. Motocicleta");
        System.out.println("3. Camioneta de carga");
        System.out.println("4. Microbus");
    }

    public void mostrarTiposCliente() {
        System.out.println("\n1. Individual");
        System.out.println("2. Corporativo");
    }

    public void mostrarTiposReporte() {
        System.out.println("\n===== REPORTES =====");
        System.out.println("1. Vehiculos");
        System.out.println("2. Ingresos y descuentos");
        System.out.println("3. Alquileres activos");
        System.out.println("4. Historial de cliente");
    }
}
