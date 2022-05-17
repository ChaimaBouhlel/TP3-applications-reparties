package Exercice2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ThreadClient extends Thread {
    private Socket socket;
    public int clientNo;
    public int reqcount=0;
    public ThreadClient(Socket socket, int no) {
        super("ThreadClient");
        this.socket = socket;
        this.clientNo = no;
    }
    private String message_suivant()
    {
        reqcount++;
        return switch (reqcount % 5) {
            case 0 -> "Marrakech est une ville magnifique.";
            case 1 -> "La medina de Fes est splendide au couchant.";
            case 2 -> "Les montagnes de l'Atlas sont impressionnantes.";
            case 3 -> "La place Jamaa alfna est au centre de la ville.";
            case 4 -> "Les cotes du Maroc valent le coup d'oeil.";
            default -> "ca n'arrive jamais";
        };
    }
    void faireUnePause()
    {try
    {
        System.out.println("pause d'une seconde");
        sleep(1000);
    }
    catch(InterruptedException e) {
        e.printStackTrace();
    }
    }

    public void run() {
        System.out.println("Thread Client num°"+clientNo+"commence");
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("\"Je suis le serveur : quelque chose a declarer?\"");
            String reply = in.readLine();
            while (!reply.equals("")){
                faireUnePause();
                out.println(message_suivant());
                reply = in.readLine();
                System.out.println(reply);
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}