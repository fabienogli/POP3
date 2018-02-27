package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Connexion implements Runnable {

    private InetAddress clientAdress;
    private int portEcoute;
    private Socket clientSocket;
    private int clientPort = 0;
    private state currentstate = state.ATTENTE_CONNEXION;

    private enum state {ATTENTE_CONNEXION, AUTHORIZATION, AUTHENTIFICATION, TRANSACTION};


    public Connexion(InetAddress clientAdress, Socket clientSocket, int clientPort) {
        this.clientAdress = clientAdress;
        this.clientSocket = clientSocket;
        this.clientPort = clientPort;
    }

    @Override
    public void run() {
        switch (currentstate) {
            /*case ATTENTE_CONNEXION:
                break;
            case AUTHENTIFICATION:
                break;
            case AUTHORIZATION:
                break;
            case TRANSACTION:
                break;
            default:
                break;*/


        }
    }


    private void authorization(String requete){

    }
    private void transaction(String requete){

    }
}
