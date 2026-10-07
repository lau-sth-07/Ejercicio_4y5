import java.util.ArrayList;

public class Controller {

    private final RentaMovil sistema;
    private final View view;

    public Controller(RentaMovil sistema, View view) {
        this.sistema = sistema;
        this.view = view;
    }

    public void iniciar() {
        cargarDatosIniciales();
        int opcion = 0;

        while (opcion != 10) {
            view.mostrarMenu();
            try {
                opcion = view.leerEntero("Opcion: ");
                ejecutar(opcion);
            } catch (NumberFormatException exception) {
                view.mensaje("Entrada invalida.");
            }
        }

        view.mensaje("Programa finalizado.");
        view.cerrar();
    }

    private void ejecutar(int opcion) {
        switch (opcion) {
            case 1:
                registrarVehiculo();
                break;
            case 2:
                registrarCliente();
                break;
            case 3:
                sistema.mostrarVehiculos();
                break;
            case 4:
                sistema.mostrarClientes();
                break;
            case 5:
                cotizar();
                break;
            case 6:
                confirmarAlquiler();
                break;
            case 7:
                devolverVehiculo();
                break;
            case 8:
                finalizarMantenimiento();
                break;
            case 9:
                mostrarReportes();
                break;
            case 10:
                break;
            default:
                view.mensaje("Opcion invalida.");
        }
    }

    private void registrarVehiculo() {
        view.mostrarTiposVehiculo();
        int tipo = view.leerEntero("Tipo: ");
        String placa = view.leer("Placa: ");

        if (placa.isEmpty()) {
            view.mensaje("La placa no puede estar vacia.");
            return;
        }
        if (sistema.buscarVehiculo(placa) != null) {
            view.mensaje("La placa ya existe.");
            return;
        }

        String marca = view.leer("Marca: ");
        String modelo = view.leer("Modelo: ");
        double tarifa = view.leerDecimal("Tarifa diaria: ");
        if (tarifa <= 0) {
            view.mensaje("La tarifa debe ser mayor que cero.");
            return;
        }

        Vehiculo vehiculo = crearVehiculo(tipo, placa, marca, modelo, tarifa);
        if (vehiculo == null) {
            view.mensaje("Tipo invalido.");
            return;
        }
        sistema.registrarVehiculo(vehiculo);
        view.mensaje("Vehiculo registrado.");
    }

    private Vehiculo crearVehiculo(int tipo, String placa, String marca,
                                   String modelo, double tarifa) {
        switch (tipo) {
            case 1:
                int pasajerosAuto = view.leerEntero("Cantidad de pasajeros: ");
                if (pasajerosAuto <= 0) {
                    view.mensaje("Cantidad invalida.");
                    return null;
                }
                boolean automatico = view.confirmar("Es automatico? (S/N): ");
                return new Automovil(placa, marca, modelo, tarifa,
                        pasajerosAuto, automatico);
            case 2:
                int cilindraje = view.leerEntero("Cilindraje: ");
                if (cilindraje <= 0) {
                    view.mensaje("Cilindraje invalido.");
                    return null;
                }
                return new Motocicleta(placa, marca, modelo, tarifa, cilindraje);
            case 3:
                double capacidad = view.leerDecimal("Capacidad en toneladas: ");
                if (capacidad <= 0) {
                    view.mensaje("Capacidad invalida.");
                    return null;
                }
                return new CamionetaCarga(placa, marca, modelo, tarifa, capacidad);
            case 4:
                int pasajerosBus = view.leerEntero("Cantidad de pasajeros: ");
                if (pasajerosBus <= 0) {
                    view.mensaje("Cantidad invalida.");
                    return null;
                }
                boolean piloto = view.confirmar("Incluye piloto? (S/N): ");
                return new Microbus(placa, marca, modelo, tarifa, pasajerosBus, piloto);
            default:
                return null;
        }
    }

    private void registrarCliente() {
        view.mostrarTiposCliente();
        int tipo = view.leerEntero("Tipo: ");
        String identificador = view.leer(tipo == 1 ? "DPI: " : "NIT: ");

        if (tipo == 1 && identificador.length() != 13) {
            view.mensaje("El DPI debe tener 13 digitos.");
            return;
        }
        if (tipo == 2 && identificador.isEmpty()) {
            view.mensaje("NIT invalido.");
            return;
        }
        if (sistema.buscarCliente(identificador) != null) {
            view.mensaje("El cliente ya existe.");
            return;
        }

        Cliente cliente;
        ArrayList<String> licencias;
        if (tipo == 1) {
            String nombre = view.leer("Nombre: ");
            licencias = pedirLicencias();
            cliente = new ClienteIndividual(identificador, nombre, licencias);
        } else if (tipo == 2) {
            String empresa = view.leer("Empresa: ");
            String contacto = view.leer("Contacto: ");
            licencias = pedirLicencias();
            cliente = new ClienteCorporativo(identificador, empresa, contacto, licencias);
        } else {
            view.mensaje("Tipo invalido.");
            return;
        }

        sistema.registrarCliente(cliente);
        view.mensaje("Cliente registrado.");
    }

    private ArrayList<String> pedirLicencias() {
        ArrayList<String> licencias = new ArrayList<String>();
        int cantidad = view.leerEntero("Cantidad de licencias: ");
        while (cantidad <= 0) {
            view.mensaje("Debe presentar al menos una licencia.");
            cantidad = view.leerEntero("Cantidad de licencias: ");
        }
        while (licencias.size() < cantidad) {
            String licencia = view.leer("Licencia (A, B, C o M): ").toUpperCase();
            if ("A".equals(licencia) || "B".equals(licencia)
                    || "C".equals(licencia) || "M".equals(licencia)) {
                licencias.add(licencia);
            } else {
                view.mensaje("Licencia invalida.");
            }
        }
        return licencias;
    }

    private void cotizar() {
        Cliente cliente = buscarClienteSolicitado();
        if (cliente == null) return;
        Vehiculo vehiculo = buscarVehiculoSolicitado();
        if (vehiculo == null) return;
        int dias = leerDias();
        if (dias > 0) sistema.cotizar(cliente, vehiculo, dias);
    }

    private void confirmarAlquiler() {
        Cliente cliente = buscarClienteSolicitado();
        if (cliente == null) return;
        Vehiculo vehiculo = buscarVehiculoSolicitado();
        if (vehiculo == null) return;
        int dias = leerDias();
        if (dias <= 0) return;

        sistema.cotizar(cliente, vehiculo, dias);
        if (!sistema.obtenerRazonesRechazo(cliente, vehiculo).isEmpty()) return;
        if (view.confirmar("Confirmar alquiler? (S/N): ")) {
            sistema.confirmarAlquiler(cliente, vehiculo, dias);
            view.mensaje("Alquiler confirmado.");
        } else {
            view.mensaje("Alquiler cancelado.");
        }
    }

    private Cliente buscarClienteSolicitado() {
        Cliente cliente = sistema.buscarCliente(view.leer("Identificador del cliente: "));
        if (cliente == null) view.mensaje("Cliente inexistente.");
        return cliente;
    }

    private Vehiculo buscarVehiculoSolicitado() {
        Vehiculo vehiculo = sistema.buscarVehiculo(view.leer("Placa: "));
        if (vehiculo == null) view.mensaje("Vehiculo inexistente.");
        return vehiculo;
    }

    private int leerDias() {
        int dias = view.leerEntero("Dias: ");
        if (dias <= 0) view.mensaje("Cantidad de dias invalida.");
        return dias;
    }

    private void devolverVehiculo() {
        boolean resultado = sistema.registrarDevolucion(view.leer("Placa: "));
        view.mensaje(resultado ? "Devolucion registrada."
                : "No se puede registrar la devolucion.");
    }

    private void finalizarMantenimiento() {
        boolean resultado = sistema.finalizarMantenimiento(view.leer("Placa: "));
        view.mensaje(resultado ? "Mantenimiento finalizado."
                : "No se puede finalizar el mantenimiento.");
    }

    private void mostrarReportes() {
        view.mostrarTiposReporte();
        int opcion = view.leerEntero("Opcion: ");
        switch (opcion) {
            case 1:
                sistema.mostrarReporteVehiculos();
                break;
            case 2:
                sistema.mostrarReporteIngresos();
                break;
            case 3:
                sistema.mostrarAlquileresActivos();
                break;
            case 4:
                Cliente cliente = buscarClienteSolicitado();
                if (cliente != null) sistema.mostrarHistorialCliente(cliente);
                break;
            default:
                view.mensaje("Opcion invalida.");
        }
    }

    private void cargarDatosIniciales() {
        ArrayList<String> licenciaC = licencia("C");
        ArrayList<String> licenciaA = licencia("A");
        ArrayList<String> licenciaM = licencia("M");
        ArrayList<String> licenciaB = licencia("B");

        ClienteIndividual cliente1 = new ClienteIndividual("1111111111111", "Ana", licenciaC);
        ClienteIndividual cliente2 = new ClienteIndividual("2222222222222", "Luis", licenciaA);
        sistema.registrarCliente(cliente1);
        sistema.registrarCliente(cliente2);
        sistema.registrarCliente(new ClienteCorporativo("1111", "Empresa A", "Juan", licenciaM));
        sistema.registrarCliente(new ClienteCorporativo("2222", "Empresa B", "Maria", licenciaB));

        sistema.registrarVehiculo(new Automovil("A1", "Toyota", "Corolla", 200, 5, true));
        sistema.registrarVehiculo(new Automovil("A2", "Honda", "Civic", 200, 5, false));
        sistema.registrarVehiculo(new Motocicleta("M1", "Honda", "Moto1", 100, 300));
        sistema.registrarVehiculo(new Motocicleta("M2", "Yamaha", "Moto2", 100, 200));
        CamionetaCarga camioneta = new CamionetaCarga("C1", "Toyota", "Hilux", 200, 1.5);
        camioneta.agregarDias(14);
        sistema.registrarVehiculo(camioneta);
        sistema.registrarVehiculo(new CamionetaCarga("C2", "Ford", "Ranger", 200, 2.0));
        sistema.registrarVehiculo(new Microbus("B1", "Toyota", "Bus1", 300, 12, true));
        sistema.registrarVehiculo(new Microbus("B2", "Hyundai", "Bus2", 300, 12, false));

        cliente2.incrementarAlquileresConfirmados();
        cliente2.incrementarAlquileresConfirmados();
        cliente2.incrementarAlquileresConfirmados();
    }

    private ArrayList<String> licencia(String valor) {
        ArrayList<String> licencias = new ArrayList<String>();
        licencias.add(valor);
        return licencias;
    }
}
