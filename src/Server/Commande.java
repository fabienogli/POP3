package Server;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpRetryException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commande {

    protected static final int i_USER = 0;
    protected static final int i_PASS = 1;
    protected static final int i_ADRESSE = 2;
    private static String cheminDatabase = "src/database/";
    public static String quit(Connexion connexion) {
        String result = "+OK Serveur POP3 de Mark-Florian-Fabien signing off";
        connexion.setCurrentstate(StateEnum.ATTENTE_CONNEXION);
        //gerer le cas ou des messages ont ete marque a efface
        return result;

    }

    public static String user(String user, Connexion connexion) {
        String result = "-ERR";
        //verifier si la boite au lettres existe
        if (isUserValid(user)){
            connexion.setUSER(user);
            connexion.setCurrentstate(StateEnum.AUTHENTIFICATION);
            result="+OK Boite aux lettre valide";
        }

        //si oui

        return result;
    }

    public static String pass(String password, Connexion connexion) {
        String result = "-ERR";
        //verifier le mot de passe pour l'identifiant donné
        if(isPassValid(password,connexion.getUSER())){
            connexion.setCurrentstate(StateEnum.TRANSACTION);
            result = "+OK Authentification reussie";
        }

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
        return "Serveur POP3 de Mark-Fabien-Florian Ready";
    }

    public static String encryptApop(String toEncrypt) {
        StringBuilder encryptMd5 = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] encrApop = md5.digest(toEncrypt.getBytes());
            for (int i = 0; i < encrApop.length; ++i) {
                encryptMd5.append(Integer.toHexString((encrApop[i] & 0xFF) | 0x100).substring(1,3));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptMd5.toString();
    }

    public static boolean isApopValid(String APOP, String USER) {
        if (USER == null || APOP == null) {
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
                        if (x == i_USER && USER.equals(tabChaine[x]) && encryptApop(tabChaine[x + 2]).equals(APOP)) {
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
    private MessageBox addMail(String user) {
        MessageBox mailBox = new MessageBox();
        File repository = new File(cheminDatabase+"/"+user+"_messages");
        List<Message> mails = new ArrayList<>();
        File[] files = repository.listFiles();
        if (files != null && files.length > 0)
            Arrays.stream(files).forEach(file -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String line;
                    String content = "";
                    while ((line = reader.readLine()) != null) {
                        content += line + "\r\n";
                    }
                    mails.add(parseMail(content));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        mailBox.setMessages(mails);
        return mailBox;
    }

    private Message parseMail(String rawMail){
        Message mail = new Message();
        String line = "";
        int index = 0;
        boolean end = false;


        //Parse header
        while (!end) {
            if (!(index < rawMail.length()))
               // throw new BadMailFormat("End of header \"\\r\\n'\" not found ");
            if (rawMail.charAt(index) == '\r' && rawMail.charAt(index + 1) == '\n') {
                index += 2;
                if (line.isEmpty())
                    end = true;
                else {
                    this.parseHeader(mail, line);
                    line = "";
                }
            } else
                line += rawMail.charAt(index++);
        }

        //Parse content
        String content = "";
        end = false;
        while (!end) {
            if (!(index < rawMail.length()))
               // throw new BadMailFormat("End of header \".\\r\\n'\" not found ");
            if (rawMail.charAt(index) == '.' && rawMail.charAt(index + 1) == '\r' && rawMail.charAt(index + 2) == '\n')
                end = true;
            if (rawMail.charAt(index) == '\r' && rawMail.charAt(index + 1) == '\n')
                index += 2;
            else
                content += rawMail.charAt(index++);
        }
        mail.setContent(content);

        return mail;
    }

}