package Server;

public class States {

    public static String authorization(String requete){
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


        return returnCode;
    }
    public static String transaction(String requete){
        String[] arg = requete.split(" ");
        String returnCode = "";

        switch (arg[0]) {
            //commandes prises en charge par cet etat
            case "LIST":
                Commande.list();
                break;
            case "STAT":
                Commande.stat();
                break;
            case "RETR":
                Commande.retrieve(Integer.parseInt(arg[1]));
                break;
            case "DELE":
                Commande.delete(Integer.parseInt(arg[1]));
            case "NOOP":
                break;
            //commandes non prises en charge cet etat
            default:
                returnCode= "-ERR";
                break;
        }

        return returnCode;
    }
    public static String authentification(String requete){
        String[] arg = requete.split(" ");
        String returnCode = "";

        switch (arg[0]) {
            case "PASS":
                default:
                    returnCode= "-ERR";
                    break;
        }
        return returnCode;
    }
    public static String attenteConnexion(){
        return Commande.ready();
    }
}
