import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class Message extends AbstactMessage
{
    private byte[] inhalt;

    public Message(File new_file)
    {
        try
        {
            this.inhalt = Files.readAllBytes(FileSystems.getDefault().getPath(new_file.getAbsolutePath()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Message()
    {

    }

    public byte[] getInhalt()
    {
        return this.inhalt;
    }
}