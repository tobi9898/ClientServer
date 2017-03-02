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
            // Verbindung erstellen

            Socket clientSocket = new Socket("127.0.0.1", 9090);

            // Kommunikation zu Server

            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            // Authentifizieren

            AuthentifizierungMessage anfrage = new AuthentifizierungMessage();
            anfrage.setAbsender((JOptionPane.showInputDialog("Benutzer: \n")));
            outToServer.writeObject(anfrage);

            // Rückmeldung von Server

            AuthentifizierungMessage reply = (AuthentifizierungMessage) inFromServer.readObject();

            if(reply.getAuthentifiziert() == true)
            {
                // Eingabe + Schicken der Werte

                Message messageToServer = new Message();

                messageToServer.setZahl1(Integer.parseInt(JOptionPane.showInputDialog("Zahl 1 eingeben: \n")));
                messageToServer.setOperation(JOptionPane.showInputDialog("Operation eingeben: \n"));
                messageToServer.setZahl2(Integer.parseInt(JOptionPane.showInputDialog("Zahl 2 eingeben: \n")));

                outToServer.writeObject(messageToServer);
                outToServer.flush();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "User nicht authentifiziert");
                outToServer.writeObject(new Message());
            }

            // Ergebnis von Server

            Message messageFromServer = (Message) inFromServer.readObject();

            if(!messageFromServer.getOperation().equals(" "))
            {
                JOptionPane.showMessageDialog(null, messageFromServer.getErgebnis());
            }

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