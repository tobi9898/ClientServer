import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            // Verbindung aufbauen

            Socket clientSocket = new Socket("127.0.0.1", 8080);

            // Kommunikation mit Server

            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            String check = "";

            while (!check.equals("exit"))
            {
                Message messageToServer = new Message();

                // Eingabe und Speicherung des Befehls

                check = JOptionPane.showInputDialog("Befehl (random, date oder exit) eingeben: ");
                messageToServer.setBefehl(check);

                // Senden der Message

                outToServer.writeObject(messageToServer);
                outToServer.flush();

                // Hole Ergebnis von Server

                Message messageFromServer = new Message();
                messageFromServer = (Message) inFromServer.readObject();

                // Ausgabe Ergebnis oder Exit

                if(check.equals("random"))
                {
                    JOptionPane.showMessageDialog(null, "Number: " + messageFromServer.getRandom());
                }
                if(check.equals("date"))
                {
                    JOptionPane.showMessageDialog(null, String.format("Date: " + messageFromServer.getDate()));
                }
                if(check.equals("exit"))
                {
                    check = "exit";
                    JOptionPane.showMessageDialog(null, String.format("Anwendung wird beendet..."));
                }
            }

            // Ressourcen schließen

            outToServer.close();
            inFromServer.close();

            clientSocket.close();
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