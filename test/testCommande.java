import Server.Commande;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class testCommande{

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

    public static void main(String[] args) {
        System.out.println("Doit être vrai\n");
        System.out.println(Commande.isUserValid("john"));
        System.out.println(Commande.isUserValid("root"));
        System.out.println(Commande.isUserValid("foo"));
        System.out.println(Commande.isPassValid("doe","john"));
        System.out.println(Commande.isPassValid("toor","root"));
        System.out.println(Commande.isPassValid("bar","foo"));


        System.out.println("\nDoit être faux\n");
        System.out.println(Commande.isUserValid("jon"));
        System.out.println(Commande.isUserValid("rot"));
        System.out.println(Commande.isUserValid("unknown"));
        System.out.println(Commande.isUserValid(""));
        System.out.println(Commande.isPassValid("toor","john"));
        System.out.println(Commande.isPassValid("bar","root"));
        System.out.println(Commande.isPassValid("doe","foo"));
        System.out.println(Commande.isPassValid("","foo"));



    }
}
