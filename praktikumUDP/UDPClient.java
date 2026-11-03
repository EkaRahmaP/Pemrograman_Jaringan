
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 5001;

        try (
            DatagramSocket clientSocket =
                new DatagramSocket();
            Scanner input = new Scanner(System.in)
        ) {
            InetAddress serverAddress =
                InetAddress.getByName(serverHost);

            System.out.println("UDP Client siap.");
            System.out.println(
                "Ketik EXIT untuk mengakhiri percakapan."
            );

            while (true) {
                System.out.print("Kamu: ");
                String message = input.nextLine();

                // Mengirim pesan ke Server
                byte[] sendBuffer =
                    message.getBytes(StandardCharsets.UTF_8);

                DatagramPacket sendPacket =
                    new DatagramPacket(
                        sendBuffer,
                        sendBuffer.length,
                        serverAddress,
                        serverPort
                    );

                clientSocket.send(sendPacket);

                // Menerima balasan dari Server
                byte[] receiveBuffer = new byte[1024];

                DatagramPacket receivePacket =
                    new DatagramPacket(
                        receiveBuffer,
                        receiveBuffer.length
                    );

                clientSocket.receive(receivePacket);

                String reply = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
                );

                System.out.println("Server: " + reply);

                // Mengakhiri Client setelah EXIT dibalas
                if (message.trim().equalsIgnoreCase("EXIT")) {
                    break;
                }
            }

            System.out.println("Client berhenti.");

        } catch (Exception e) {
            System.err.println(
                "Error Client: " + e.getMessage()
            );
        }
    }
}
