package Client.Application;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private InetAddress adresseIp;
    private String fichier;
    private String requete;

    private enum requete {USER, PASS, APOP, STAT, LIST, RETR, DELE}


    public Client() throws IOException {

        adresseIp = java.net.InetAddress.getByName("localhost");
        clientSocket = new Socket(adresseIp, 2026);
    }

    public Client(String adresseIp, String fichier) throws IOException{

        this.adresseIp = InetAddress.getByName(adresseIp);
        this.fichier = fichier;
        clientSocket = new Socket(adresseIp, 2026);
    }

    public Client(String adresseIp, String fichier, int port)throws IOException {
        this.adresseIp = InetAddress.getByName(adresseIp);
        this.fichier = fichier;
        clientSocket = new Socket(adresseIp, port);
    }

    public static void main(String [] args) {
//        String askUser = "Quelle est votre identifiant ?";
//        System.out.println(askUser);
//        Scanner scanner = new Scanner(System.in);
//        String user = scanner.nextLine();
//        System.out.println("Votre user est : " + user);
//        String askPass = "Quelle est votre mot de passe ?";
//        System.out.println(askPass);
//        String pass = scanner.nextLine();
//        System.out.println(pass);

    }
}
