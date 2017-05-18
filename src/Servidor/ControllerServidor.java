package Servidor;

public class ControllerServidor {

    private final static int DEFAULT_PORT = 5025;

    public static void main(String[] args) {
        ServidorTCPConcorrente sv = new ServidorTCPConcorrente(DEFAULT_PORT);
        new Thread(sv).start();

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Stopping Server");
        sv.stop();
    }
}