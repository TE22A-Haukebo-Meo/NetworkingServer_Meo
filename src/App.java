import java.net.*;
import java.io.*;

public class App {
    public static void main(String[] args) throws Exception {
        ServerSocket server = null;
        Socket client;

        int portNumber = 3000;
        if (args.length>=1) {
            portNumber = Integer.parseInt(args[0]);
        }

        try {
            server = new ServerSocket(portNumber);

        } catch (Exception e) {
            System.out.println("Cannot open socket");
            System.exit(1);
        }
        System.out.println("Serversocket is created" + server);

        while (true) {
            try {
                System.out.println("Waiting for a connect reques...");
                client = server.accept();
                System.out.println("Connect request is accepted...");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host = "+ clientHost + " Client port = "+clientPort);
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();
                System.out.println("Message received from client: "+msgFromClient);
                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    String ansMsg = "Hello, "+ msgFromClient;
                    pw.println(ansMsg);
                }
                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {
                    server.close();
                    client.close();
                    break;
                }
            } catch (Exception e) {
                System.out.println("Could not close connection");
            }
        }
    }
}
