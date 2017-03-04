import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class Server
{
    public static void main(String[] args)
    {
        try
        {
            // Verbindung erstellen und auf Client warten

            ServerSocket listener = new ServerSocket(8080);
            Socket s = listener.accept();

            // Kommunikation mit Client

            ObjectInputStream inFromClient = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(s.getOutputStream());

            // URL von Client holen

            Message fromClient = (Message) inFromClient.readObject();
            URL url = new URL(fromClient.getURL());

            // Website speichern

            Files.copy(url.openStream(), FileSystems.getDefault().getPath("Server-" + fromClient.getName()+ ".html"));
            Message message = new Message(new File("Server-" + fromClient.getName() + ".html"));
            message.setName(fromClient.getName());

            // zurücksenden

            outToClient.writeObject(message);
            outToClient.flush();

            // Ressourcen schließen

            inFromClient.close();
            outToClient.close();

            s.close();
            listener.close();
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