package Server;

import java.io.*;

public class Commande {

    private final int i_USER = 0;
    private final int i_PASS = 1;
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
    public String getDataFromDb(int i_data) {
        String toReturn = "";
        try {
            String chemin = "./storage.csv";
            File file = new File(chemin);
            FileReader fileReader = new FileReader("./storage.csv");
            BufferedReader db = new BufferedReader(fileReader);
            String chaine;
            int i = 1;
            while((chaine = db.readLine())!= null)
            {
                if(i > 1)
                {
                    String[] tabChaine = chaine.split(",");
                    //Tu effectues tes traitements avec les données contenues dans le tableau
                    //La première information se trouve à l'indice 0
                    for (int x = 0; x < tabChaine.length; x++) {
                        if (x == i_USER) {
                            System.out.println("user = " + tabChaine[x]+"\n");
                        } else if (x == i_PASS) {
                            System.out.println("pass = " + tabChaine[x]+"\n");
                        } else {
                            System.out.println("message = " + tabChaine[x]+"\n");
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
        return toReturn;
    }

}