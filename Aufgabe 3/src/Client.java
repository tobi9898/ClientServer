import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            // Verbindung aufbauen

            Socket s = new Socket("127.0.0.1", 8080);
            Message messageServer = new Message();

            // Komminikation mit Server

            ObjectOutputStream outToServer = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(s.getInputStream());

            // Eingabe Daten

            messageServer.setURL(JOptionPane.showInputDialog("URL eingeben: \n"));
            messageServer.setName(JOptionPane.showInputDialog("Dateiname eingeben: \n"));

            // Senden an Server

            outToServer.writeObject(messageServer);
            outToServer.flush();

            // Hole Ergebnis

            Message fromServer = (Message) inFromServer.readObject();
            Files.write(FileSystems.getDefault().getPath("Client-" + fromServer.getName() + ".html"), fromServer.getInhalt());

            // Ressourcen schließen

            outToServer.close();
            inFromServer.close();

            s.close();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}