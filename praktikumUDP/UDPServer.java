
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UDPServer {
    public static void main(String[] args) {
        int port = 5001;

        try (DatagramSocket serverSocket =
                 new DatagramSocket(port)) {

            System.out.println("UDP Server berjalan...");
            System.out.println("Menunggu pesan dari Client...");

            while (true) {
                byte[] receiveBuffer = new byte[1024];

                DatagramPacket receivePacket =
                    new DatagramPacket(
                        receiveBuffer,
                        receiveBuffer.length
                    );

                // Menerima pesan dari Client
                serverSocket.receive(receivePacket);

                String message = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
                );

                System.out.println(
                    "Pesan dari Client: " + message
                );

                // Mengubah pesan menjadi huruf kapital
                String reply = message.toUpperCase();

                byte[] sendBuffer =
                    reply.getBytes(StandardCharsets.UTF_8);

                // Mengirim balasan ke Client asal
                DatagramPacket sendPacket =
                    new DatagramPacket(
                        sendBuffer,
                        sendBuffer.length,
                        receivePacket.getAddress(),
                        receivePacket.getPort()
                    );

                serverSocket.send(sendPacket);

                System.out.println(
                    "Balasan Server: " + reply
                );

                // Mengakhiri Server jika menerima EXIT
                if (message.trim().equalsIgnoreCase("EXIT")) {
                    System.out.println("Server berhenti.");
                    break;
                }
            }

        } catch (Exception e) {
            System.err.println(
                "Error Server: " + e.getMessage()
            );
        }
    }
}
