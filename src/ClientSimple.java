import java.net.*;
import java.io.*;

class ClientSimple {

    public static void main(String[] args) {
        int port = 1973;
        InetAddress hote = null;
        Socket socket = null;
        DataInputStream userInput;
        PrintStream theOutputStream;

        try

        {

            if( args.length>=1 )

                hote = InetAddress.getByName( args[0] );

            else

                hote = InetAddress.getLocalHost();



            if( args.length==2 )

                port  = Integer.parseInt( args[1] ) ;

        }

        catch(UnknownHostException e) {}

        try {
            socket = new Socket(hote, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println("client pret");
            System.out.println(in.readLine());}
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}