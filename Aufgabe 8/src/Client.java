import java.net.InetAddress;

public class Client
{
    public static void main(String args[]) throws Exception
    {
        SenderThread sender = new SenderThread(InetAddress.getByName("localhost"), 7777);
        sender.start();

        ReceiverThread receiver = new ReceiverThread(sender.getSocket());
        receiver.start();
    }
}