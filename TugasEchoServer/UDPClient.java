import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        String serverHost = "localhost"; // IP tujuan
        int serverPort = 5000; // Port tujuan
        
        try (DatagramSocket clientSocket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {
             
            InetAddress serverAddress = InetAddress.getByName(serverHost);
            byte[] receiveBuffer = new byte[1024];

            System.out.println("=== UDP Echo Client ===");
            System.out.println("Ketik pesan (ketik 'EXIT' untuk keluar)");

            while (true) {
                System.out.print("Anda: ");
                String messageStr = scanner.nextLine();

                // Cek jika user mengetik EXIT
                if (messageStr.equalsIgnoreCase("EXIT")) {
                    System.out.println("Keluar dari aplikasi...");
                    break;
                }

                // 1. Kirim pesan ke Server
                byte[] sendBuffer = messageStr.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, serverPort);
                clientSocket.send(sendPacket);

                // 2. Terima balasan dari Server
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket); // Menunggu balasan (blocking)

                // 3. Tampilkan balasan
                String reply = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server: " + reply);
            }

        } catch (Exception e) {
            System.err.println("Terjadi kesalahan pada Client: " + e.getMessage());
        }
    }
}