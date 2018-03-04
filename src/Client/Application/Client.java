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

    String[] recevoirReponseServeur(String adresse, String requete, int port) throws IOException {
        System.out.println("Dans réception");
        DataInputStream inFromServer;
        DataOutputStream outToServer;

        //Ouverture des flux
        inFromServer = new DataInputStream(clientSocket.getInputStream());
        outToServer = new DataOutputStream(clientSocket.getOutputStream());


        //On fait le header requete
        outToServer.writeBytes(requete);
        outToServer.flush();

        //Determination de l'header
        String recu = inFromServer.readLine();
        //System.out.println(recu);
        if (recu.contains("Error-404")) {
            return new String[]{"Error-404", recu, requete};
        } else if (recu.contains("Error-504")) {
            return new String[]{"Error-504", recu, requete};
        } else {
            String[] tmp_header = recu.split("SEPARATEUR");
            //System.out.println(tmp_header.length);
//        for(int i =0; i<tmp_header.length; i++){
//            System.out.println(tmp_header[i]);
//        }
            String date, lastModified, contentLength, contentType, server;
            date = tmp_header[1];
            lastModified = tmp_header[2];
            contentLength = tmp_header[3];
            server = tmp_header[4];
            contentType = tmp_header[5];
            String header = date + "\n" + lastModified + "\n" + contentLength + "\n" + server + "\n" + contentType + "\n";

            String extension = contentType.split(":")[1];
            int longueur = Integer.parseInt(contentLength.split(":")[1]);
            //System.out.println(header);

            //Creation du table de byte pour la reception
            byte data[] = new byte[longueur];

            //Reception du fichier
            for (int i = 0; i < data.length; i++) {
                data[i] = inFromServer.readByte();
            }
            inFromServer.close();

            //Creation du fichier
            String nomFichier = "image_Recu_";
            nomFichier += fichier;
            String dossierReception = "Reception/";
            File file = new File("src/" + dossierReception + nomFichier);
            file.createNewFile();

            //Ecriture du contenu du fichier
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.close();
            //On ferme la socket du client
            clientSocket.close();
            return new String[]{nomFichier, header, requete,extension};
        }
    }
}
