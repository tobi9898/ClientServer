import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args)
    {
        try
        {
            // Socket wird erstellt um eine Verbindung zu Client zu erstellen

            ServerSocket socket = new ServerSocket(9090);
            Socket clientSocket = socket.accept();

            // Kommunikation zwischen Server und Client

            ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());

            // Anfrage von Client

            AnfrageMessage messageClient = (AnfrageMessage) inFromClient.readObject();
            ErgebnisMessage toClient = new ErgebnisMessage();

            // Fuehre gewuenschte Operation des Clients aus und sende Message zurueck

            if (messageClient.getOperation().equals("+"))
            {
                toClient.setErgebnis(messageClient.getZahl1(), "+", messageClient.getZahl2());
                outToClient.writeObject(toClient);
                outToClient.flush();
            }

            if (messageClient.getOperation().equals("-"))
            {
                toClient.setErgebnis(messageClient.getZahl1(), "-", messageClient.getZahl2());
                outToClient.writeObject(toClient);
                outToClient.flush();
            }

            if (messageClient.getOperation().equals("*"))
            {
                toClient.setErgebnis(messageClient.getZahl1(), "*", messageClient.getZahl2());
                outToClient.writeObject(toClient);
                outToClient.flush();
            }

            if (messageClient.getOperation().equals("/"))
            {
                toClient.setErgebnis(messageClient.getZahl1(), "/", messageClient.getZahl2());
                outToClient.writeObject(toClient);
                outToClient.flush();
            }

            // Ressourcen werden geschlossen

            outToClient.close();
            inFromClient.close();

            clientSocket.close();
            socket.close();
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