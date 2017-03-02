import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            // Socket wird erstellt

            Socket clientSocket = new Socket("127.0.0.1", 9090);
            AnfrageMessage messageToServer = new AnfrageMessage();

            // Kommunikation mit Server

            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            // Werte einlesen von User

            messageToServer.setZahl1(Integer.parseInt(JOptionPane.showInputDialog("Zahl 1 eingeben: \n")));
            messageToServer.setOperation(JOptionPane.showInputDialog("Operation eingeben: \n"));
            messageToServer.setZahl2(Integer.parseInt(JOptionPane.showInputDialog("Zahl 2 eingeben: \n")));

            // Sende Server die Anfrage mit Werte

            outToServer.writeObject(messageToServer);
            outToServer.flush();

            // Antwort des Servers holen und anzeigen

            ErgebnisMessage messageFromServer = (ErgebnisMessage) inFromServer.readObject();
            JOptionPane.showMessageDialog(null, messageFromServer.getErgebnis());

            // Ressourcen schließen

            outToServer.close();
            inFromServer.close();

            clientSocket.close();
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