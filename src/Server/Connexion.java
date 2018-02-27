package Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Connexion implements Runnable {

    private InetAddress clientAdress;
    private int portEcoute;
    private Socket clientSocket;
    private int clientPort = 0;
    private state currentstate = state.ATTENTE_CONNEXION;
    private String USER;
    private final int i_USER = 0;
    private final int i_PASS = 1;

    private enum state {ATTENTE_CONNEXION, AUTHORIZATION, AUTHENTIFICATION, TRANSACTION}


    public Connexion(InetAddress clientAdress, Socket clientSocket, int clientPort) {
        this.clientAdress = clientAdress;
        this.clientSocket = clientSocket;
        this.clientPort = clientPort;
    }

    @Override
    public void run() {
        switch (currentstate) {
            /*case ATTENTE_CONNEXION:
                break;
            case AUTHENTIFICATION:
                break;
            case AUTHORIZATION:
                break;
            case TRANSACTION:
                break;
            default:
                break;*/


        }
    }


    private void authorization(String requete){

    }
    private void transaction(String requete){

    }

    public boolean isApopValid() {
        return false;
    }

    public boolean isUserValid(String USER) {
        return false;
    }

    public boolean isPassValid(String PASS) {
        if (this.USER == null) {
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
