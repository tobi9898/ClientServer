import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class ServerThread extends Thread
{
    private Socket connection;
    String next = "";

    public ServerThread(Socket con) { this.connection = con; }

    public void run()
    {
        try
        {
            // Kommunikation mit Client

            ObjectOutputStream outToClient = new ObjectOutputStream(this.connection.getOutputStream());
            ObjectInputStream inFromClient = new ObjectInputStream(this.connection.getInputStream());

            while(!next.equals("exit"))
            {
                // Hole Befehl

                Message message = (Message)inFromClient.readObject();
                Message new_dateMessage;

                // Suche richtige Funktion + Berechnung + Ergebnis zurücksenden (oder Exit)

                if(message.getBefehl().equals("random"))
                {
                    new_dateMessage = new Message();
                    new_dateMessage.setRandom((float)Math.round(Math.random() * 1000.0D));
                    Thread.sleep(2000L);

                    outToClient.writeObject(new_dateMessage);
                    outToClient.flush();
                }

                if(message.getBefehl().equals("date"))
                {
                    new_dateMessage = new Message();
                    Date dt = new Date();
                    new_dateMessage.setDate(dt.getTime());
                    Thread.sleep(2000L);

                    outToClient.writeObject(new_dateMessage);
                    outToClient.flush();
                }

                if(message.getBefehl().equals("exit"))
                {
                    next = "exit";
                    outToClient.writeObject(message);
                    outToClient.flush();
                }
            }

            // Ressourcen schließen

            outToClient.close();
            inFromClient.close();
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
