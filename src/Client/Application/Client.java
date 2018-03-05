package Client.Application;

import Server.StateEnum;
import Server.Utilisateur;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {

    private Socket clientSocket;
    private InetAddress adresseIp;
    private Utilisateur utilisateur;
    private int port;
    private StateEnum stateEnum= null;

    public Client(InetAddress adresseIp, int port) throws IOException {
        this.adresseIp = adresseIp;
        this.port = port;
        this.clientSocket = new Socket(this.getAdresseIp(), this.getPort());
    }

    public Client() throws IOException {
        this(java.net.InetAddress.getByName("localhost"), 2026);
    }

    public Client(Utilisateur utilisateur) throws IOException {
        this();
        this.utilisateur = utilisateur;
    }

    public Client(InetAddress adresseIp, Utilisateur utilisateur, int port) throws IOException {
        this(adresseIp, port);
        this.utilisateur = utilisateur;
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
        String reponseServer = read();
        if (reponseServer.contains("Ready")) {
            this.stateEnum = StateEnum.ATTENTE_CONNEXION;
        }
    }

    public boolean authentification() {
        if (!this.stateEnum.equals(StateEnum.ATTENTE_CONNEXION)) {
            return false;
        }
        System.out.println("dans authentification");
        if (this.getUtilisateur() == null) {
            return false;
        }
        write("USER " + this.getUtilisateur().getNom());
        String reponseServer = read();
        if (reponseServer.contains("-ERR")) {
            return false;
        }
        write("PASS " + this.getUtilisateur().getMdp());
        reponseServer = read();
        if (reponseServer.contains("+OK")) {
            this.stateEnum = StateEnum.AUTHORIZATION;
        }
        return true;
    }

    public void createMailFile(){
        Path file = Paths.get("src/Client/Mails/"+this.utilisateur.getNom()) ;
        try {
            // Create the empty file with default permissions, etc.
            Files.createFile(file);
        } catch (FileAlreadyExistsException x) {
            System.err.format("file named %s" +
                    " already exists%n", file);
        } catch (IOException x) {
            // Some other sort of failure, such as permissions.
            System.err.format("createFile error: %s%n", x);
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

    public void list() {
        write("LIST");
    }

    public void stat() {
        write("STAT");
    }

    public void retr(int i_message) {
        write("RETR" + i_message);
    }

    public void del(int i_message) {
        write("DELE" + i_message);
    }

    public StateEnum getStatus() {
        return this.stateEnum;
    }

    public void logout() {
        write("QUIT");
    }
}