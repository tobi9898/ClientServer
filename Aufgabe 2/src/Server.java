import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
    public static void main(String[] args)
    {
        try
        {
            while (true)
            {
                // Authentifizierte Nutzer werden eingetragen

                ArrayList<String> namen = new ArrayList<String>();
                namen.add("simon");
                namen.add("mirco");
                namen.add("loris");
                namen.add("damian");

                // Verbindung herstellen und auf Client warten

                ServerSocket welcomeSocket = new ServerSocket(9090);
                Socket clientSocket = welcomeSocket.accept();

                // Kommunikation zu Client

                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());

                // Erhalte Werte von Client

                AuthentifizierungMessage fromClient = (AuthentifizierungMessage) inFromClient.readObject();

                boolean gefunden = false;

                // Suche User in Array

                for (String i: namen)
                {
                    if(fromClient.getAbsender().toLowerCase().equals(i))
                    {
                        gefunden = true;
                    }
                }

                // true oder false dem Client zurückgeben

                if(gefunden == false)
                {
                    fromClient.setAuthentifiziert(false);
                    outToClient.writeObject(fromClient);
                }
                else
                {
                    fromClient.setAuthentifiziert(true);
                    outToClient.writeObject(fromClient);
                }

                // Rechnung vom Client erhalten

                Message messageClient = (Message) inFromClient.readObject();

                // Berechnung + Rückgabe an Client

                if(!messageClient.getOperation().equals(" "))
                {
                    if (messageClient.getOperation().equals("+"))
                    {
                        messageClient.setErgebnis(messageClient.getZahl1(), "+", messageClient.getZahl2());
                        outToClient.writeObject(messageClient);
                        outToClient.flush();
                    }

                    if (messageClient.getOperation().equals("-"))
                    {
                        messageClient.setErgebnis(messageClient.getZahl1(), "-", messageClient.getZahl2());
                        outToClient.writeObject(messageClient);
                        outToClient.flush();
                    }

                    if (messageClient.getOperation().equals("*"))
                    {
                        messageClient.setErgebnis(messageClient.getZahl1(), "*", messageClient.getZahl2());
                        outToClient.writeObject(messageClient);
                        outToClient.flush();
                    }

                    if (messageClient.getOperation().equals("/"))
                    {
                        messageClient.setErgebnis(messageClient.getZahl1(), "/", messageClient.getZahl2());
                        outToClient.writeObject(messageClient);
                        outToClient.flush();
                    }
                }
                else
                {
                    outToClient.writeObject(new Message());
                    outToClient.flush();
                }

                // Ressourcen schließen

                outToClient.close();
                inFromClient.close();

                clientSocket.close();
                welcomeSocket.close();
            }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}