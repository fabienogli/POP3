import Server.Commande;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class testCommande extends Commande {

    public static String getDataFromDb(int i_data) {
        String toReturn = "";
        try {
            FileReader fileReader = new FileReader("src/database/users.csv");
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
                        if (x == 0) {
                            System.out.println("user = " + tabChaine[x]+"\n");
                        } else if (x == 1) {
                            System.out.println("pass = " + tabChaine[x]+"\n");
                        } else {
                            System.out.println("email = " + tabChaine[x]+"\n");
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

    private static void testUser() {
        System.out.println("Doit être vrai\n");
        System.out.println(Commande.isUserValid("john"));
        System.out.println(Commande.isUserValid("root"));
        System.out.println(Commande.isUserValid("foo"));
        System.out.println("\nDoit être faux\n");
        System.out.println(Commande.isUserValid("jon"));
        System.out.println(Commande.isUserValid("rot"));
        System.out.println(Commande.isUserValid("unknown"));
        System.out.println(Commande.isUserValid(""));
    }

    private static void testPass() {
        System.out.println("Doit être vrai\n");
        System.out.println(Commande.isPassValid("doe","john"));
        System.out.println(Commande.isPassValid("toor","root"));
        System.out.println(Commande.isPassValid("bar","foo"));
        System.out.println("\nDoit être faux\n");
        System.out.println(Commande.isPassValid("toor","john"));
        System.out.println(Commande.isPassValid("bar","root"));
        System.out.println(Commande.isPassValid("doe","foo"));
        System.out.println(Commande.isPassValid("","foo"));
    }

    private static void testEncyptApop() {
        System.out.println("Doit être vrai\n");
        System.out.println("c4c9334bac560ecc979e58001b3e22fb".equals(Commande.encryptApop("<1896.697170952@dbc.mtview.ca.us>tanstaaf")));
    }

    protected static void testAddMail() {
        addMail("john");
    }

    public static void main(String[] args) {
//        testPass();
//        testUser();
//        testEncyptApop();
        testAddMail();
    }
}
