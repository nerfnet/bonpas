package net.insidebits.bonpas.backend;
import org.springframework.boot.SpringApplication;
public class Serve {

    private static ServeServer serve;

    public static void main(String[] args) {
        serveMain();
    }

    /**
     * Starts Serve on the main thread.
     */
    public static void serveMain() {
        serve = new ServeServer();
        serve.start();
    }

    public static ServeServer get() {
        return serve;
    }
}
