package Client.Application;

import Server.Utilisateur;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private InetAddress adresseIp;
    private String reponse;
    private String requete;

    private Utilisateur utilisateur;
    private int port;

    private enum requete {USER, PASS, APOP, STAT, LIST, RETR, DELE}


    public Client() throws IOException {
        this.adresseIp = java.net.InetAddress.getByName("localhost");
        this.port = 2026;
    }

    public Client(InetAddress adresseIp, int port) throws IOException {
        this.adresseIp = adresseIp;
        this.port = port;
    }

    public Client(Utilisateur utilisateur) throws IOException {
        this();
        this.utilisateur = utilisateur;
    }

    public Client(InetAddress adresseIp, Utilisateur utilisateur, int port) throws IOException {
        this(adresseIp, port);
        this.utilisateur = utilisateur;
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
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public InetAddress getAdresseIp() {
        return adresseIp;
    }

    public void setAdresseIp(InetAddress adresseIp) {
        this.adresseIp = adresseIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        System.out.println("Démarrage client");
        clientSocket = new Socket(this.getAdresseIp(), this.getPort());
        String reponseServer = read();
        System.out.println(reponseServer);
        if (reponseServer.contains("Ready")) {
            write("USER " + this.getUtilisateur().getNom());
            reponseServer = read();
            System.out.println(reponseServer);
            write("PASS " + this.getUtilisateur().getMdp());
        }
    }


    public void write(String data) {
        data += "\r\n";
        try {
            OutputStream outputStream = this.clientSocket.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Le client envoie " + data);
    }

    public String read(){
        String data = "";
        try {
            DataInputStream fromServer = new DataInputStream(this.clientSocket.getInputStream());
            data = fromServer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Le client recoit " + data);
        return data;
    }
}