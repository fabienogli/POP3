package Server;

public class Commande {

    public static String quit() {

        return null;
    }

    public static String user(String user, Connexion connexion) {
        String result = "-ERR";
        //verifier si la boite au lettres existe

        //si oui
        connexion.setUSER(user);
        return result;
    }

    public static String pass(String password, Connexion connexion) {
        String result = "-ERR";
        //verifier le mot de passe pour l'identifiant donné

        return result;
    }

    public static String apop() {
        String result = "-ERR";

        return result;
    }

    public static String list() {
        String result = "-ERR";

        return null;
    }

    public static String stat() {
/*" +OK " suivi par un simple espace, le nombre de message dans le dépôt de courrier,
 un simple espace et la taille du dépôt de courrier en octets*/
        return null;
    }

    public static String delete(int numMessage) {

        return null;
    }

    public static String retrieve(int numMessage) {

        return null;
    }
    public static String ready(){
        //envoi message ready
        return "Serveur POP3 Ready";
    }

    public boolean isApopValid() {
        return false;
    }

    public boolean isUserValid(String USER) {
        return false;
    }

    public boolean isPassValid(String PASS,String USER) {
        if (USER == null) {
            return false;
        }
        return false;
    }

}