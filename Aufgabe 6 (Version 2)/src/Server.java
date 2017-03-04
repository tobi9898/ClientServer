import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
    public void start()
    {
        try
        {
            // Verbindung und ThreadPool erstellt

            ServerSocket listener = new ServerSocket(8080);
            ExecutorService executor = Executors.newCachedThreadPool();

            while (true)
            {
                // Warte auf Clients

                Socket socket = listener.accept();
                executor.execute(new ServerThread(socket));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}