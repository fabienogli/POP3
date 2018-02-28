package Server;

import java.io.*;

public class Commande {

    protected static final int i_USER = 0;
    protected static final int i_PASS = 1;
    protected static final int i_ADRESSE = 2;
    private static String cheminDatabase = "src/database/";
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

    public static boolean isApopValid() {
        return false;
    }

    public static boolean isUserValid(String USER) {
        if (USER == null) {
            return false;
        }

        try {
            FileReader fileReader = new FileReader(cheminDatabase + "users.csv");
            BufferedReader db = new BufferedReader(fileReader);
            String chaine;
            int i = 1;
            while((chaine = db.readLine())!= null)
            {
                if(i > 1)
                {
                    String[] tabChaine = chaine.split(",");
                    for (int x = 0; x < tabChaine.length; x++) {
                        if (x == i_USER && USER.equals(tabChaine[x])) {
                            return true;
                        }
                    }
                }
                i++;
            }
            db.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Le fichier est introuvable !");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPassValid(String PASS,String USER) {
        if (USER == null || PASS == null) {
            return false;
        }
        try {
            FileReader fileReader = new FileReader(cheminDatabase + "users.csv");
            BufferedReader db = new BufferedReader(fileReader);
            String chaine;
            int i = 1;
            while((chaine = db.readLine())!= null)
            {
                if(i > 1)
                {
                    String[] tabChaine = chaine.split(",");
                    for (int x = 0; x < tabChaine.length; x++) {
                        if (x == i_USER && USER.equals(tabChaine[x]) && tabChaine[x + 1].equals(PASS)) {
                            return true;
                        } else if (x == i_USER && USER.equals(tabChaine[x])) { //Le user est bien là mais le mot de passe ne correspond pas
                            return false;
                        }
                    }
                }
                i++;
            }
            db.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Le fichier est introuvable !");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}