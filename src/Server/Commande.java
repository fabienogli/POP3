package Server;

public class Commande {

    public static String quit() {

        return null;
    }

    public static String user() {

        return null;
    }

    public static String pass() {

        return null;
    }

    public static String apop() {

        return null;
    }

    public static String list() {

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

}