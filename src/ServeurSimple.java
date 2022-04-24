import java.io.*;

import java.net.*;

 

class ServeurSimple

{

    public static void main( String [] args )

    {

        int port = 1973; 

        ServerSocket SocketServeur = null;

        Socket socket = null;

        int client = 0;

        if( args.length == 1 )

            port = Integer.parseInt( args [ 0 ] );

        try  // 5 personnes en attente maxi  

        {

            SocketServeur = new ServerSocket( port, 5 );

        }

        catch( IOException e )

        {

            System.err.println( "Impossible de créer un ServerSocket" );

            return;

        }

 

        System.out.println( "Serveur à l'écoute sur le port :" + port );

        while( true )

            try

            {

                socket = SocketServeur.accept();

                client++;   // un client s'est connecté

                PrintWriter out = new PrintWriter( socket.getOutputStream() );

                BufferedReader in = new BufferedReader(new InputStreamReader( socket.getInputStream() ) );

                out.println( "Je suis le serveur : quelquechose à declarer?" );

                out.flush();
                String reply = in.readLine();

                System.out.println( "Le client no. " + client + " m'a dit : " + reply );
            }

            catch( IOException e )

            {

                System.err.println( "Erreur : " + e );

            }

            finally

            {

                try

                {

                    socket.close();                   

                }

                catch( IOException e ) {}

            }

    }

}