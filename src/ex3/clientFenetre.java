package ex3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class clientFenetre extends Frame implements Runnable,ActionListener
{
    TextArea Output;
    TextField Input;
    Socket socket ;
    PrintWriter out =null;
    BufferedReader in =null;
    InetAddress hote;
    int port;
    public clientFenetre(InetAddress hote, int port) throws IOException {

        super("Client en fenetre");
        this.hote=hote;
        this.port=port;
        // mise en forme de la fenetre (frame)
        setSize(500,700);
        setLayout(new BorderLayout());
        add( Output=new TextArea(),BorderLayout.CENTER );
        Output.setEditable(false);
        add( Input=new TextField(), BorderLayout.SOUTH );
        Input.addActionListener(this);
        pack();
        show();
        Input.requestFocus();
        // ajout d'un window adapter pour reagir si on ferme la fenetre
        addWindowListener(new WindowAdapter ()
                          { public void windowClosing (WindowEvent e)
                          {
                              setVisible(false); dispose(); System.exit(0);
                          }
                          }
        );
        this.socket = new Socket(hote,port);
        Thread t=new Thread(this);
        t.start();
    }
    public void run ()
    {
        // boucle qui receptionne les messages du serveur
        // et les affiche dans le textarea

        try {
             out = new PrintWriter(socket.getOutputStream(), true);
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int compteur = 0;
            while(compteur<10){
                String msg = in.readLine();
                System.out.println(msg);
                Output.append(msg+"\n");
                compteur++;
                out.println("Je suis le client "+hote+" et j'ai fait "+compteur+" appels");
                try{
                    System.out.println("pause de 2 secondes");
                    Thread.sleep(2000);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }

            } }catch(IOException e){
                e.printStackTrace();
            }
        }

    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource()==Input)
        {
            String phrase=Input.getText();
             //... (envoie au serveur)
            out.println(phrase);


            // efface la zone de saisie
            Input.setText("");
        }
    }
    protected void finalize()
    {
        // Fermer ici toute les soquettes
        try {
            if(out!=null)
                out=null;
            if(in!=null)
                in=null;
            if (socket != null)
            socket.close();
        } catch (IOException e) {
            System.err.println("Erreur : " + e);
    }
    }
    public static void main(String[] args) throws IOException {
        InetAddress hote =InetAddress.getLocalHost();
        int port = 1973;
        Socket socket = null;
 //... ( gestion des paramatres )
        clientFenetre chatwindow = new clientFenetre(hote, port);
    }}