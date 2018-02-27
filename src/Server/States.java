package Server;

public class States {

    public static String authorization(String requete, Connexion connexion) {
        String[] arg = requete.split(" ");
        String returnCode = "";

        switch (arg[0]) {
            //commandes prises en charge par cet etat
            case "APOP":
                returnCode = Commande.apop();
                break;
            case "USER":
                if (arg.length == 2) {
                    returnCode = Commande.user(arg[1], connexion);
                }
                break;
            case "QUIT":
                returnCode = Commande.quit();
                //commandes non prises en charge cet etat
            default:
                returnCode = "-ERR";
                break;
        }


        return returnCode;
    }

    public static String transaction(String requete, Connexion connexion) {
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
                returnCode = "-ERR";
                break;
        }

        return returnCode;
    }

    public static String authentification(String requete, Connexion connexion) {
        String[] arg = requete.split(" ");
        String returnCode = "";

        switch (arg[0]) {
            case "PASS":
                if (arg.length == 2) {
                    Commande.pass(arg[1], connexion);
                } else returnCode = "-ERR";
            default:
                returnCode = "-ERR";
                break;
        }
        return returnCode;
    }

    public static String attenteConnexion(Connexion connexion) {
        connexion.setCurrentstate(StateEnum.AUTHORIZATION);
        return Commande.ready();
    }
}
