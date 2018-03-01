package Server;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpRetryException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        if (isUserValid(user)) {
            connexion.setUSER(user);
            connexion.setCurrentstate(StateEnum.AUTHENTIFICATION);
            result = "+OK Boite aux lettre valide";
        }

        //si oui

        return result;
    }

    public static String pass(String password, Connexion connexion) {
        String result = "-ERR";
        //verifier le mot de passe pour l'identifiant donné
        if (isPassValid(password, connexion.getUSER())) {
            connexion.setCurrentstate(StateEnum.TRANSACTION);
            connexion.setMailBox(addMail(connexion.getUSER()));
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

    public static String retrieve(int num, Connexion connexion) {
        StringBuilder mailSb = new StringBuilder();
        if (num <= 0 || num > connexion.getMailBox().getNumberMessages()) {
            System.out.println(connexion.getMailBox().getNumberMessages());
            System.out.print("RETR echec mauvais numero de message");
            return "-ERR no such messages,only " + connexion.getMailBox().getNumberMessages() + " messages in maildrop";
        } else {
            Message mail = connexion.getMailBox().getMessage(num - 1);
            if (mail.isDeleteMark()) {
                System.out.print("RETR echec mail supprime");
                return "-ERR message " + num + " is deleted";
            }
            System.out.print("RETR succes");
            mailSb.append("+OK " + mail.size() + " octets").append("\r\n").append(mail.toString());
        }
        return mailSb.toString();
    }

    public static String ready() {
        //envoi message ready
        return "Serveur POP3 de Mark-Fabien-Florian Ready";
    }

    public static String encryptApop(String toEncrypt) {
        StringBuilder encryptMd5 = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] encrApop = md5.digest(toEncrypt.getBytes());
            for (int i = 0; i < encrApop.length; ++i) {
                encryptMd5.append(Integer.toHexString((encrApop[i] & 0xFF) | 0x100).substring(1, 3));
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
            while ((chaine = db.readLine()) != null) {
                if (i > 1) {
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
        } catch (FileNotFoundException e) {
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
            while ((chaine = db.readLine()) != null) {
                if (i > 1) {
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
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier est introuvable !");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPassValid(String PASS, String USER) {
        if (USER == null || PASS == null) {
            return false;
        }
        try {
            FileReader fileReader = new FileReader(cheminDatabase + "users.csv");
            BufferedReader db = new BufferedReader(fileReader);
            String chaine;
            int i = 1;
            while ((chaine = db.readLine()) != null) {
                if (i > 1) {
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
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier est introuvable !");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static MessageBox addMail(String user) {
        MessageBox mailBox = new MessageBox();
        List<Message> mails = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(cheminDatabase + user +"_messages");
            BufferedReader db = new BufferedReader(fileReader);
            String line;
            String content = "";
            String lineTmp="";
            while ((line = db.readLine()) != null) {
                System.out.println("in the mailbox 1 ");
                lineTmp = line + "\r\n";
                content += lineTmp;
                System.out.println(line);
                if(lineTmp.equals(".\r\n")){
                    System.out.println("in the mailbox 2");
                    //System.out.println(line);
                    //System.out.println(content);
                    mails.add(parseMail(content));
                    content = "";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        mailBox.setMessages(mails);
        return mailBox;
    }

    private static Message parseMail(String rawMail) {
        Message mail = new Message();
        String line = "";
        int index = 0;
        boolean end = false;
        //Parse header
        while (!end) {

            //if (!(index < rawMail.length()))
                // throw new BadMailFormat("End of header \"\\r\\n'\" not found ");
                if (rawMail.charAt(index) == '\r' && rawMail.charAt(index + 1) == '\n') {

                    index += 2;
                    if (line.isEmpty())
                        end = true;
                    else {

                        parseHeader(mail, line);
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
        mail.setCorps(content);

        return mail;
    }

    private static void parseHeader(Message mail, String line)
    //throws BadMailFormat
    {
        boolean delimiteurFound = false;
        String key = "";
        String value = "";
        char item;
        String test = "";
        for (int i = 0; i < line.length(); i++) {
            item = line.charAt(i);
            test += item;
            if (!delimiteurFound && item == ':')
                delimiteurFound = true;
            else {
                if (delimiteurFound)
                    value += item;
                else
                    key += item;
            }

        }

        if (key.isEmpty() || value.isEmpty())
            //throw new BadMailFormat(line + "\r\n" + "Incorrect Header");

            key = key.trim();
        value = value.trim();
        System.out.println(key);
        System.out.println(value);

        switch (key.toUpperCase()) {
            case "TO":
                String valuesTo[] = value.split(" ");
                mail.setDestinataire(new Utilisateur(valuesTo[0], valuesTo[1]));
                break;
            case "FROM":
                String valuesFrom[] = value.split(" ");
                mail.setAuteur(new Utilisateur(valuesFrom[0], valuesFrom[1]));
                break;
            case "SUBJECT":
                mail.setSujet(value);
                break;
            case "DATE":
                DateFormat format = new SimpleDateFormat("EEE, d MMM YYYY HH:mm:ss Z");
                try {
                    mail.setDate(format.parse(value));
                } catch (ParseException e) {
                    //throw new BadMailFormat(line + "\r\n" + "Unparsable date");
                }
                break;
            case "MESSAGE-ID":
                mail.setId(value);
                break;
            default:
                mail.addOptionalHeader(key, value);
        }

    }

}