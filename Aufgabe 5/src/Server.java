import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
    public void start()
    {
        while (true)
        {
            try
            {
                // Verwaltung von Client als Treads

                ServerSocket listener = new ServerSocket(8080);
                ExecutorService executor = Executors.newCachedThreadPool();

                while (true)
                {
                    // Auf Clients warten

                    Socket socket = listener.accept();
                    executor.execute(new ServerThread(socket, this));
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}