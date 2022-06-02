package ex3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMultiClient {
    public static void main(String[] args) {
        int port = 1973;
        ServerSocket SocketServeur = null;
        Socket socket = null;
        int client = 0;
        if (args.length == 1) {
            port = Integer.parseInt(args[ 0]);
        }

        try { // 5 personnes en attente maxi 
            SocketServeur = new ServerSocket(port, 5);
        } catch (IOException e) {
            System.err.println("Impossible de creer un ServerSocket");
            return;
        }

        System.out.println("Serveur l'écoute sur le port :" + port);
        while (true){
            try {
                socket = SocketServeur.accept();
                client++;
                //on le passe a un nouveau thread et on le demarre
                ThreadClient neo= new ThreadClient(socket, client);
                neo.start();
            } catch (IOException e) {
                System.err.println( "Erreur : " + e );
            }
        }
    }
}