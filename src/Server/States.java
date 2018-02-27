package Server;
import  Server.Commande;

public class States {

    private void authorization(String requete){
        String[] arg = requete.split(" ");

        switch(arg[0]){
            //commandes prises en charge par cet etat
            case "APOP":
                 break;
            case "USER":
                break;
            case "QUIT":
           //commandes non prises en charge cet etat
            default:break;


        }

    }
    private void transaction(String requete){
        String[] arg = requete.split(" ");

        switch (arg[0]) {
            //commandes prises en charge par cet etat
            case "LIST":
            case "STAT":
            case "RETR":
            case "DELE":
            case "NOOP":
            //commandes non prises en charge cet etat
            default:break;
        }

    }
    private void authentification(String requete){
        String[] arg = requete.split(" ");

        switch (arg[0]) {
            case "PASS":
                default:break;;
        }
    }
}
