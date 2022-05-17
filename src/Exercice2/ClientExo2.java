package Exercice2;

import java.net.*;
import java.io.*;

public class ClientExo2 {

    public static void main(String[] args) {
        int port = 1973;
        InetAddress hote = null;
        Socket socket = null ;

        try {
            if (args.length >= 1)
                hote = InetAddress.getByName(args[0]);
            else
                hote = InetAddress.getLocalHost();

            if (args.length == 2)

                port = Integer.parseInt(args[1]);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            socket = new Socket(hote, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());
            for (int compteur=1; compteur <= 10; compteur++) {
                out.println("Je suis le client "+hote+" et j'ai fait "+compteur+" appels");
                System.out.println(in.readLine());
                try {
                    System.out.println("Pause de 2 secondes");
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    System.out.println("erreur"+ e);
                }
            }
            out.println();
        } catch (Exception e) {
            System.out.println("erreur");
            e.printStackTrace();
        } finally{
            try {
                assert socket != null;
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
