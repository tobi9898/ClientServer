import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server
{
    public static void main(String[] args)
    {
        try
        {
            // Verbindung erstellen und auf Client warten

            ServerSocket s = new ServerSocket(8080);
            Socket socket = s.accept();

            // Kommunikation mit Client

            ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromClient = new ObjectInputStream(socket.getInputStream());

            String next = "";

            // Schleife bricht ab wenn User exit eingibt

            while (!next.equals("exit"))
            {
                Message message = (Message) inFromClient.readObject();

                // Berechne Zufallszahl und schreibe zurück

                if (message.getBefehl().equals("random"))
                {
                    Message new_randomMessage = new Message();
                    new_randomMessage.setRandom((Math.round(Math.random()*(100*10))));
                    Thread.sleep(2000);

                    outToClient.writeObject(new_randomMessage);
                    outToClient.flush();
                }

                // Berechne Datum und schreibe zurück

                if (message.getBefehl().equals("date"))
                {
                    Message new_dateMessage = new Message();

                    Date dt = new Date();
                    new_dateMessage.setDate(dt.getTime());
                    Thread.sleep(2000);

                    outToClient.writeObject(new_dateMessage);
                    outToClient.flush();
                }

                if (message.getBefehl().equals("exit"))
                {
                    next = "exit";

                    outToClient.writeObject(message);
                    outToClient.flush();
                }
            }

            // Ressourcen schließen

            outToClient.close();
            inFromClient.close();

            socket.close();
            s.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}