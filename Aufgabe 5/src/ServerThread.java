import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerThread extends Thread
{
    private Socket socket;
    private Server server;
    BufferedReader in = null;
    PrintWriter out;

    public ServerThread(Socket new_socket, Server new_server)
    {
        try
        {
            this.socket = new_socket;
            this.server = new_server;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Öffne Datei

            Message Message = (Message) in.readObject();
            Path Pfad = (Paths.get(Message.getPfad()));

            InputStream fileIn = new FileInputStream(Pfad.toFile());

            // Datei speichern

            byte[] buffer = new byte[1024];

            while (fileIn.available() > 0)
            {
                out.write(buffer, 0, fileIn.read(buffer));
            }

            fileIn.close();
            socket.close();
        }
        catch (FileNotFoundException e)
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