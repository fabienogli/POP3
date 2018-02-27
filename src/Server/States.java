package Server;
import  Server.Commande;

public class States {

    public static void authorization(String requete){
        String[] arg = requete.split(" ");
        String returnCode = "";

        switch(arg[0]){
            //commandes prises en charge par cet etat
            case "APOP":
                returnCode=Commande.apop();
                 break;
            case "USER":
                returnCode=Commande.user();
                break;
            case "QUIT":
                returnCode=Commande.quit();
           //commandes non prises en charge cet etat
            default:
                returnCode= "-ERR";
                break;
        }
        

    }
    public static void transaction(String requete){
        String[] arg = requete.split(" ");
        String returnCode = "";

        switch (arg[0]) {
            //commandes prises en charge par cet etat
            case "LIST":
            case "STAT":
            case "RETR":
            case "DELE":
            case "NOOP":
            //commandes non prises en charge cet etat
            default:
                returnCode= "-ERR";
                break;
        }

    }
    public static void authentification(String requete){
        String[] arg = requete.split(" ");
        String returnCode = "";

        switch (arg[0]) {
            case "PASS":
                default:
                    returnCode= "-ERR";
                    break;
        }
    }
}
