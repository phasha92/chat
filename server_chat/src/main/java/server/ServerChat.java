package server;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerChat {

    private static final int PORT = 8956;
    private static final ExecutorService service = Executors.newCachedThreadPool();


    public static void main(String[] args) {
        // Запускаем графический интерфейс
        SwingUtilities.invokeLater(() ->
            new ServerForm(() -> {
                shutdown();
                System.exit(0); // Завершаем приложение после остановки сервера
            })
        );

        start();
    }

    public static void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                service.submit(new ClientHandler(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            shutdown();
        }
    }
    public static void shutdown() {
        service.shutdown();
    }
}
