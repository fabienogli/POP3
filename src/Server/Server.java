package Server;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.*;

public class Server {
    final static int BUF_SIZE = 1024;
    public  int portEcoute;
    private ServerSocket serverSocket;

    public Server(){
        portEcoute = 2026;
        try {
            ServerSocketFactory fabrique =  SSLServerSocketFactory.getDefault();
            serverSocket = fabrique.createServerSocket(portEcoute);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lancer(){

        byte [] buffer = new byte[BUF_SIZE];
        try {
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                int portClient = receivePacket.getPort();
                InetAddress address = receivePacket.getAddress();
                Connexion serveurHTTP = new Connexion(clientSocket);
                Thread thread = new Thread(serveurHTTP);
                System.out.println("Lancement du serveur");
                thread.start();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.lancer();
    }
}
