package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket client;
    private static final List<PrintWriter> clients = new ArrayList<>();

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(),true)){

            clients.add(out);
            String msg;
            while ((msg = in.readLine()) != null) {
                broadCast(msg);
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }finally {
            clients.removeIf(client -> client.equals(this.client));
        }

    }

    private void broadCast(String msg) {
        for (PrintWriter client : clients) {
            client.println(msg);
        }
    }
}
