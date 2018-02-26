package Server.Model;

import java.io.IOException;
import java.net.*;

public class ServeurPrimaire {
    final static int BUF_SIZE = 1024;
    public  int portEcoute;
    private ServerSocket serverSocket;

    public ServeurPrimaire() {
        this.portEcoute = 2028;
        try {
            serverSocket = new ServerSocket(portEcoute);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lancer() {
        byte [] buffer = new byte[BUF_SIZE];
        try {
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                int portClient = receivePacket.getPort();
                InetAddress address = receivePacket.getAddress();
                ServeurPop3 serverPop3 = new ServeurPop3(address,clientSocket, portClient);
                Thread thread = new Thread(serverPop3);
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
        ServeurPrimaire serveurPrimaire = new ServeurPrimaire();
        serveurPrimaire.lancer();
    }
}
