import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashSet;

public class Server
{
    private static HashSet<Integer> portSet = new HashSet<Integer>();

    public static void main(String args[])
    {
        DatagramSocket udpServerSocket = null;

        try
        {
            udpServerSocket = new DatagramSocket(7777);
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }

        System.out.println("Server gestartet\n");

        while(true)
        {
            try
            {
                byte[] receiveData = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                udpServerSocket.receive(receivePacket);
                String clientMessage = (new String(receivePacket.getData())).trim();

                if(clientMessage.equals(""))
                {
                    System.out.println("Client verbunden: " + receivePacket.getSocketAddress() + "\n");
                }
                else
                {
                    System.out.println("Client Nachricht: " + clientMessage + "\n");
                }

                // Hole IP-address und Port von Verbindung

                InetAddress clientIP = receivePacket.getAddress();
                portSet.add(receivePacket.getPort());

                String returnMessage = clientMessage.toUpperCase();

                byte[] sendData = returnMessage.getBytes();

                // Nachricht an Ports weiterleiten

                for(Integer port : portSet)
                {
                    if(port != receivePacket.getPort())
                    {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, port);
                        udpServerSocket.send(sendPacket);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}