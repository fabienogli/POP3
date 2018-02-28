import Server.Message;
import Server.Utilisateur;

import java.util.Date;

public class testMessage {

    public static void main(String [] args) {
        Utilisateur john = new Utilisateur("john", "john@mail.com");
        Utilisateur root = new Utilisateur("root", "root@mail.com");
        Utilisateur foo = new Utilisateur("foo", "foo@mail.com");
        Utilisateur info = new Utilisateur("Martin", "martin@mail.com");
        String corps = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. \n" +
                "Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. " +
                "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, \n" +
                "aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. " +
                "Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. ";
        String corps1 = "Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. \n" +
                "Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. \n" +
                "Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. ";
        String corps2 = "Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, \n" +
                "sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. \n" +
                "Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. ";
        Date date = new Date();
        Message m1 = new Message("1", john, foo, date, "Message 1", corps);
        Message m2 = new Message("2", john, root, date, "Message 2", corps1);
        Message m3 = new Message("3", john, info, date, "Message 3", corps2);

        System.out.println(m1);
        System.out.println("\n" + m2);
        System.out.println("\n" + m3);
    }
}
