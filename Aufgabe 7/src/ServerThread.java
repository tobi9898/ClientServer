import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class ServerThread extends Thread
{
    private Socket connection;
    String next = "";

    public ServerThread(Socket s) { this.connection = s; }

    public void run()
    {
        try
        {
            ObjectOutputStream outToClient = new ObjectOutputStream(this.connection.getOutputStream());
            ObjectInputStream inFromClient = new ObjectInputStream(this.connection.getInputStream());

            while(!next.equals("exit"))
            {
                Message message = (Message)inFromClient.readObject();
                Message new_dateMessage;

                if(message.getBefehl().equals("random"))
                {
                    new_dateMessage = new Message();
                    new_dateMessage.setRandom((float)Math.round(Math.random() * 1000));
                    Thread.sleep(2000);

                    outToClient.writeObject(new_dateMessage);
                    outToClient.flush();
                }

                if(message.getBefehl().equals("date"))
                {
                    new_dateMessage = new Message();
                    Date dt = new Date();
                    new_dateMessage.setDate(dt.getTime());
                    Thread.sleep(2000);

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
