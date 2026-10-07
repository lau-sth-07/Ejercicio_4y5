import java.util.ArrayList;

public class RentaMovil {

    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Alquiler> alquileres;
    private int siguienteNumeroAlquiler;
    private double ingresosTotales;

    public RentaMovil() {
        vehiculos = new ArrayList<Vehiculo>();
        clientes = new ArrayList<Cliente>();
        alquileres = new ArrayList<Alquiler>();
        siguienteNumeroAlquiler = 1;
        ingresosTotales = 0;
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {

        if (buscarVehiculo(vehiculo.getPlaca()) != null) {
            return false;
        }

        vehiculos.add(vehiculo);
        return true;
    }

    public boolean registrarCliente(Cliente cliente) {

        if (buscarCliente(cliente.getIdentificador()) != null) {
            return false;
        }

        clientes.add(cliente);
        return true;
    }

    public Vehiculo buscarVehiculo(String placa) {

        for (Vehiculo vehiculo : vehiculos) {

            if (vehiculo.getPlaca().equals(placa)) {
                return vehiculo;
            }
        }

        return null;
    }

    public Cliente buscarCliente(String identificador) {

        for (Cliente cliente : clientes) {

            if (cliente.getIdentificador().equals(identificador)) {
                return cliente;
            }
        }

        return null;
    }

    public int contarAlquileresActivos(Cliente cliente) {

        int cantidad = 0;

        for (Alquiler alquiler : alquileres) {

            if (alquiler.isActivo()) {

                if (alquiler.getCliente() == cliente) {
                    cantidad = cantidad + 1;
                }
            }
        }

        return cantidad;
    }

    public ArrayList<String> obtenerRazonesRechazo(
            Cliente cliente, Vehiculo vehiculo) {

        ArrayList<String> razones = new ArrayList<String>();

        if (!vehiculo.getEstado().equals("Disponible")) {
            razones.add("El vehiculo no esta disponible.");
        }

        if (!vehiculo.puedeConducir(cliente.getLicencias())) {
            razones.add("El cliente no posee una licencia adecuada.");
        }

        int activos = contarAlquileresActivos(cliente);
        int limite = cliente.getLimiteAlquileresActivos();

        if (activos >= limite) {
            razones.add(
                    "El cliente alcanzo su limite de alquileres activos.");
        }

        return razones;
    }

    public void cotizar(Cliente cliente, Vehiculo vehiculo, int dias) {

        double subtotal = vehiculo.calcularSubtotal(dias);
        double descuento = cliente.calcularDescuento(subtotal);
        double total = subtotal - descuento;

        System.out.println("\n--- COTIZACION ---");
        System.out.println("Placa: " + vehiculo.getPlaca());
        System.out.println("Marca: " + vehiculo.getMarca());
        System.out.println("Modelo: " + vehiculo.getModelo());
        System.out.println(vehiculo.getDescripcion());
        System.out.println("Estado: " + vehiculo.getEstado());

        System.out.printf("Subtotal: Q%.2f%n", subtotal);
        System.out.printf("Descuento: Q%.2f%n", descuento);
        System.out.printf("Total: Q%.2f%n", total);

        ArrayList<String> razones =
                obtenerRazonesRechazo(cliente, vehiculo);

        if (razones.isEmpty()) {

            System.out.println(
                    "El cliente puede realizar el alquiler.");

        } else {

            System.out.println(
                    "El cliente no puede realizar el alquiler.");

            for (String razon : razones) {
                System.out.println("- " + razon);
            }
        }
    }

    public boolean confirmarAlquiler(
            Cliente cliente, Vehiculo vehiculo, int dias) {

        ArrayList<String> razones =
                obtenerRazonesRechazo(cliente, vehiculo);

        if (!razones.isEmpty()) {
            return false;
        }

        double subtotal = vehiculo.calcularSubtotal(dias);
        double descuento = cliente.calcularDescuento(subtotal);
        double total = subtotal - descuento;

        Alquiler alquiler = new Alquiler(
                siguienteNumeroAlquiler,
                cliente,
                vehiculo,
                dias,
                subtotal,
                descuento,
                total
        );

        alquileres.add(alquiler);

        siguienteNumeroAlquiler =
                siguienteNumeroAlquiler + 1;

        vehiculo.marcarComoAlquilado();

        cliente.incrementarAlquileresConfirmados();

        ingresosTotales = ingresosTotales + total;

        return true;
    }

    public boolean registrarDevolucion(String placa) {

        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            return false;
        }

        if (!vehiculo.getEstado().equals("Alquilado")) {
            return false;
        }

        Alquiler alquilerActivo = null;

        for (Alquiler alquiler : alquileres) {

            if (alquiler.isActivo()) {

                if (alquiler.getVehiculo() == vehiculo) {
                    alquilerActivo = alquiler;
                }
            }
        }

        if (alquilerActivo == null) {
            return false;
        }

        alquilerActivo.finalizar();

        vehiculo.agregarDias(
                alquilerActivo.getDias()
        );

        if (vehiculo.necesitaMantenimiento()) {

            vehiculo.marcarComoMantenimiento();

        } else {

            vehiculo.marcarComoDisponible();
        }

        return true;
    }

    public boolean finalizarMantenimiento(String placa) {

        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            return false;
        }

        if (!vehiculo.getEstado().equals("Mantenimiento")) {
            return false;
        }

        vehiculo.finalizarMantenimiento();

        return true;
    }

    public void mostrarVehiculos() {

        for (Vehiculo vehiculo : vehiculos) {

            System.out.println(
                    vehiculo.getPlaca()
                    + " - "
                    + vehiculo.getMarca()
                    + " "
                    + vehiculo.getModelo()
                    + " - "
                    + vehiculo.getDescripcion()
                    + " - Estado: "
                    + vehiculo.getEstado()
            );
        }
    }

    public void mostrarClientes() {

        for (Cliente cliente : clientes) {

            System.out.println(
                    cliente.getIdentificador()
                    + " - "
                    + cliente.getNombre()
            );
        }
    }

    public void mostrarAlquileresActivos() {

        for (Alquiler alquiler : alquileres) {

            if (alquiler.isActivo()) {

                System.out.println(
                        "Alquiler #" + alquiler.getNumero()
                        + " - Cliente: "
                        + alquiler.getCliente().getNombre()
                        + " - Vehiculo: "
                        + alquiler.getVehiculo().getPlaca()
                        + " - Total: Q"
                        + alquiler.getTotal()
                );
            }
        }
    }

    public void mostrarHistorialCliente(Cliente cliente) {

        double totalPagado = 0;

        for (Alquiler alquiler : alquileres) {

            if (alquiler.getCliente() == cliente) {

                System.out.println(
                        "Alquiler #" + alquiler.getNumero()
                        + " - Vehiculo: "
                        + alquiler.getVehiculo().getPlaca()
                        + " - Total: Q"
                        + alquiler.getTotal()
                );

                totalPagado =
                        totalPagado + alquiler.getTotal();
            }
        }

        System.out.printf(
                "Total pagado: Q%.2f%n",
                totalPagado
        );
    }

    public void mostrarReporteVehiculos() {

        String[] categorias = {
                "Automovil",
                "Motocicleta",
                "Camioneta de carga",
                "Microbus"
        };

        for (String categoria : categorias) {

            int total = 0;
            int disponibles = 0;
            int alquilados = 0;
            int mantenimiento = 0;

            for (Vehiculo vehiculo : vehiculos) {

                if (vehiculo.getCategoria().equals(categoria)) {

                    total = total + 1;

                    if (vehiculo.getEstado().equals("Disponible")) {
                        disponibles = disponibles + 1;
                    }

                    if (vehiculo.getEstado().equals("Alquilado")) {
                        alquilados = alquilados + 1;
                    }

                    if (vehiculo.getEstado().equals("Mantenimiento")) {
                        mantenimiento = mantenimiento + 1;
                    }
                }
            }

            System.out.println("\n" + categoria);
            System.out.println("Registrados: " + total);
            System.out.println("Disponibles: " + disponibles);
            System.out.println("Alquilados: " + alquilados);
            System.out.println(
                    "En mantenimiento: " + mantenimiento);
        }
    }

    public void mostrarReporteIngresos() {

        System.out.printf(
                "Ingresos totales: Q%.2f%n",
                ingresosTotales
        );

        double descuentosTotales = 0;

        for (Alquiler alquiler : alquileres) {

            descuentosTotales =
                    descuentosTotales
                    + alquiler.getDescuento();
        }

        System.out.printf(
                "Descuentos otorgados: Q%.2f%n",
                descuentosTotales
        );
    }

    public double getIngresosTotales() {
        return ingresosTotales;
    }
}