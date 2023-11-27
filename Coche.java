// Clase que representa un vehículo
class Coche extends Thread {
    private String direccioMarxa; // Dirección de marcha del vehículo
    private Puente pont; // Puente por el que cruzará el vehículo

    // Constructor de la clase Vehicle
    public Coche(String id, String direccioMarxa, Puente pont) {
        super(id);
        this.direccioMarxa = direccioMarxa;
        this.pont = pont;
    }

    // Método que simula el vehículo saliendo de casa
    private void surtDeCasa() {
        try {
            // Simula el tiempo que tarda un vehículo en salir de casa
            Thread.sleep((long) ((Math.random() * 8000) + 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método que simula el vehículo cruzando el puente
    private void creuarElPont() {
        System.out.println(getName() + " està al pont");

        try {
            // Simula el tiempo que tarda un vehículo en cruzar el puente
            Thread.sleep((long) ((Math.random() * 2000) + 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método que se ejecuta cuando inicia el hilo del vehículo
    @Override
    public void run() {
        for (; ; ) {
            surtDeCasa(); // El vehículo sale de casa
            pont.entrar(direccioMarxa); // El vehículo intenta entrar al puente
            creuarElPont(); // El vehículo cruza el puente
            pont.sortir(); // El vehículo sale del puente
        }
    }
}