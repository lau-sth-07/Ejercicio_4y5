public class Main {

    public static void main(String[] args) {
        View view = new View();
        RentaMovil sistema = new RentaMovil();
        Controller controller = new Controller(sistema, view);
        controller.iniciar();
    }
}
