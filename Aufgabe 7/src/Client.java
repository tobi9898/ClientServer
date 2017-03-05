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
            Socket clientSocket = new Socket("127.0.0.1", 9080);

            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            String check = "";

            while (!check.equals("exit"))
            {
                Message messageToServer = new Message();
                check = JOptionPane.showInputDialog("Befehl (random, date oder exit) eingeben: ");

                messageToServer.setBefehl(check);
                outToServer.writeObject(messageToServer);
                outToServer.flush();

                Message messageFromServer = new Message();
                messageFromServer = (Message) inFromServer.readObject();

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