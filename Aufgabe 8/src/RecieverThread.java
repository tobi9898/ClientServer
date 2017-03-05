import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

class ReceiverThread extends Thread
{
    private DatagramSocket udpClientSocket;

    public ReceiverThread(DatagramSocket socket) { this.udpClientSocket = socket; }

    public void run()
    {
        byte[] receiveData = new byte[1024];

        while (true)
        {
            try
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Paket holen und Inhalt ausgeben

                udpClientSocket.receive(receivePacket);
                String serverReply =  new String(receivePacket.getData(), 0, receivePacket.getLength());

                if(!serverReply.equals(""))
                {
                    System.out.println("Nachricht erhalten: " + serverReply);
                }

                Thread.yield();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}