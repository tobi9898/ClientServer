import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

public class Client
{
    public static void main(String[] args) throws IOException
    {
        // Verbindung aufbauen

        Socket s = new Socket("127.0.0.1", 8080);

        // Einlesen und speichern in Message

        Message Message = new Message();
        Message.setPfad(JOptionPane.showInputDialog("Pfad eingeben:"));

        // Nachricht an Server senden

        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        out.writeObject(Message);
        out.flush();

        String file = Paths.get(Message.getPfad()).toString();

        String endung = file.substring(file.lastIndexOf('.')+1);

        InputStream in = s.getInputStream();
        FileOutputStream fileOut = new FileOutputStream("Client-downloadet." + endung);

        // File speichern

        byte[] buffer = new byte[1024];

        while (s.isConnected())
        {
            int bytesRead = in.read(buffer);
            fileOut.write(buffer, 0, bytesRead);
        }

        fileOut.close();
        s.close();
    }
}