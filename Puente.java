public class Puente {
    // Enumeración que representa los posibles estados del puente
    public enum EstatDelPont {
        LLIURE, OCUPAT_DRETA, OCUPAT_ESQUERRA, PLE
    }

    private Enum<EstatDelPont> estat; // Estado actual del puente
    private int contador; // Contador de vehículos en el puente
    private int maxCoches; // Número máximo de vehículos permitidos en el puente

    // Constructor de la clase Pont
    public Puente(int maxC) {
        estat = EstatDelPont.LLIURE;
        contador = 0;
        maxCoches = maxC;
    }

    // Método para obtener el estado actual del puente
    public synchronized Enum<EstatDelPont> getEstat() {
        return estat;
    }

    // Método para que un vehículo entre al puente
    public synchronized void entrar(String p) {
        Enum<EstatDelPont> direccioDelCotxe = null;

        // Asigna el estado correspondiente según la dirección del vehículo
        if (p.equals("esquerra")) {
            direccioDelCotxe = EstatDelPont.OCUPAT_ESQUERRA;
        } else if (p.equals("dreta")) {
            direccioDelCotxe = EstatDelPont.OCUPAT_DRETA;
        }

        try {
            // Espera hasta que el puente esté libre en la dirección correcta o esté lleno
            while ((estat != EstatDelPont.LLIURE && direccioDelCotxe != estat) || estat == EstatDelPont.PLE) {
                wait();
            }

            // Entra al puente si está libre y actualiza el estado del puente
            if (estat == EstatDelPont.LLIURE) {
                estat = direccioDelCotxe;
            }

            // Si se alcanza el número máximo de vehículos permitidos, se marca el puente como lleno
            if (++contador == maxCoches) {
                estat = EstatDelPont.PLE;
            }

            // Muestra el número de vehículos en el puente y notifica a otros hilos
            System.out.println("Hi ha " + contador + " cotxes en sentit " + p);
            notifyAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para que un vehículo salga del puente
    public synchronized void sortir() {
        contador--;

        // Muestra que un vehículo ha salido y el estado actual del puente
        System.out.println("Surt el vehicle número " + contador + " i el pont encara està " + estat);

        // Si no hay más vehículos en el puente, actualiza el estado a libre
        if (contador == 0) {
            estat = EstatDelPont.LLIURE;
        }

        // Notifica a otros hilos
        notifyAll();
    }

    // Método principal que inicia el puente y los vehículos
    public static void main(String[] args) {
        Puente pont = new Puente(2); // Crea un puente con capacidad para 2 vehículos
        Coche vehiclesEsquerra[] = new Coche[10]; // Arreglo de vehículos que van hacia la izquierda
        Coche vehiclesDreta[] = new Coche[10]; // Arreglo de vehículos que van hacia la derecha

        // Inicializa los vehículos
        for (int i = 0; i < 10; i++) {
            vehiclesEsquerra[i] = new Coche("cotxe-" + i, "esquerra", pont);
            vehiclesDreta[i] = new Coche("cotxe-" + i, "dreta", pont);
        }

        // Inicia los hilos de los vehículos
        for (int i = 0; i < 10; i++) {
            vehiclesEsquerra[i].start();
            vehiclesDreta[i].start();
        }
    }
}