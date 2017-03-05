import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server
{
    public void start()
    {
        try
        {
            ServerSocket listener = new ServerSocket(9080);
            ExecutorService executor = Executors.newCachedThreadPool();

            String weiter = "";

            while (!weiter.equals("exit"))
            {
                Socket socket = listener.accept();
                executor.execute(new ServerThread(socket));

                weiter = "exit";
            }

            shutdown(executor);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // Erweiterung zu Aufgabe 6

    void shutdown(ExecutorService pool)
    {
        pool.shutdown();

        try
        {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS))
            {
                pool.shutdownNow();

                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                {
                    System.err.println("Pool nicht beendet");
                }
            }
        }
        catch (InterruptedException e)
        {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}