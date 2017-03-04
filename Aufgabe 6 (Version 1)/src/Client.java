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

            while (check != "exit")
            {
                Message messageToServer = new Message();
                Message messageFromServer = new Message();

                // Eingabe und Speicherung des Befehls

                check = JOptionPane.showInputDialog("Befehl (random oder date) eingeben: ");
                messageToServer.setBefehl(check);

                // Senden Nachricht

                outToServer.writeObject(messageToServer);
                outToServer.flush();

                // Hole Antwort Server

                messageFromServer = (Message) inFromServer.readObject();

                // Ausgabe oder Exit

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