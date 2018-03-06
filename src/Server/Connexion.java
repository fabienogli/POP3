package Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class Connexion implements Runnable {

    private Socket clientSocket;
    private StateEnum currentstate = StateEnum.ATTENTE_CONNEXION;
    private String USER;
    private MessageBox mailBox;

    public StateEnum getCurrentstate() {
        return this.currentstate;
    }

    public void setCurrentstate(StateEnum currentstate) {
        this.currentstate = currentstate;
    }


    public Connexion(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Dans le run");
        DataInputStream inFromClient;
        DataOutputStream outToClient;
        //Dans le run de serveur
        System.out.println(this.currentstate);
        while (true) {
            if (this.currentstate.equals(StateEnum.ATTENTE_CONNEXION)) {
                String result = States.attenteConnexion(this);
                try {
                    outToClient = new DataOutputStream(clientSocket.getOutputStream());
                    outToClient.writeBytes(result);
                    outToClient.flush();
                    // outToClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(this.currentstate);
            } else {

                try {
                    inFromClient = new DataInputStream(clientSocket.getInputStream());
                    outToClient = new DataOutputStream(clientSocket.getOutputStream());
                    this.traiterCommande(inFromClient, outToClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void traiterCommande(DataInputStream infromClient, DataOutputStream outToClient) {

        String requete = "";
        String result = "";
        try {
            requete = infromClient.readLine();
            System.out.println("Server recoit" + requete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (getCurrentstate()) {
            case AUTHENTIFICATION:
                result = States.authentification(requete, this);
                break;
            case AUTHORIZATION:
                result = States.authorization(requete, this);
                break;
            case TRANSACTION:
                result = States.transaction(requete, this);
                break;
            default:
                result = "-ERR";
                break;
        }
        System.out.println(this.currentstate);

        try {
            System.out.println("Server envoie" + result);
            outToClient.writeBytes(result + "\n");
            outToClient.flush();
            //outToClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public MessageBox getMailBox() {
        return mailBox;
    }

    public void setMailBox(MessageBox mailBox) {
        this.mailBox = mailBox;
    }
}
