import Client.Application.Client;
import Server.Utilisateur;

import java.io.IOException;

public class testClient {

    //The server need to be launche for the test
    public static void main(String[] args) {
        Utilisateur utilisateur = new Utilisateur("john");
        utilisateur.setMdp("doe");
        try {
            Client client = new Client();
            client.start();
            client.setUtilisateur(utilisateur);
            client.authentification();
            client.list();
            client.retr(1);
            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
