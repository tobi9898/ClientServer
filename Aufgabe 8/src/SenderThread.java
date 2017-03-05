import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class SenderThread extends Thread
{
    private InetAddress serverIPAddress;
    private DatagramSocket udpClientSocket;
    private int serverport;

    public SenderThread(InetAddress address, int serverport)
    {
        try
        {
            this.serverIPAddress = address;
            this.serverport = serverport;

            this.udpClientSocket = new DatagramSocket();
            this.udpClientSocket.connect(serverIPAddress, serverport);
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
    }

    public DatagramSocket getSocket() { return this.udpClientSocket; }

    public void run()
    {
        try
        {
            byte[] data = "".getBytes();
            DatagramPacket blankPacket = new DatagramPacket(data,data.length , serverIPAddress, serverport);
            udpClientSocket.send(blankPacket);

            BufferedReader eingabe = new BufferedReader(new InputStreamReader(System.in));

            while (true)
            {
                // Buffer mit Nachricht füllen

                System.out.println("Nachricht eingeben: ");
                String clientMessage = eingabe.readLine();
                byte[] sendData = clientMessage.getBytes();

                // Sende Paket zu Server

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverport);
                System.out.println(clientMessage + " wurde gesendet");
                udpClientSocket.send(sendPacket);

                Thread.yield();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}   