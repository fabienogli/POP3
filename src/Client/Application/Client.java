package Client.Application;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private InetAddress adresseIp;
    private String reponse;
    private String requete;

    private enum requete {USER, PASS, APOP, STAT, LIST, RETR, DELE}


    public Client() throws IOException {

        adresseIp = java.net.InetAddress.getByName("localhost");
        clientSocket = new Socket(adresseIp, 2026);
    }

    public String recevoirReponseServeur(String requete) throws IOException {
        System.out.println("Dans réception");
        DataInputStream inFromServer;
        DataOutputStream outToServer;

        //Ouverture des flux
        inFromServer = new DataInputStream(clientSocket.getInputStream());
        outToServer = new DataOutputStream(clientSocket.getOutputStream());


        //Envoi requete au serveur
        outToServer.writeBytes(requete);
        outToServer.flush();

        //Determination de l'header
        String recu = inFromServer.readLine();
        //System.out.println(recu);
        String[] tmp_header = recu.split("SEPARATEUR");

        //On ferme la socket du client
        clientSocket.close();
        return recu;
    }
}

