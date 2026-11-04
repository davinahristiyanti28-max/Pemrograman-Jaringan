import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        String serverHost = "localhost"; // IP tujuan
        int serverPort = 5000; // Port tujuan

        try (
            DatagramSocket clientSocket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in)
        ) {

            // Ambil objek alamat IP server
            InetAddress serverAddress =
                InetAddress.getByName(serverHost);

            System.out.println("=== UDP CLIENT ===");
            System.out.println("Terhubung ke Server " + serverHost
                    + ":" + serverPort);

            while (true) {

                // Membaca input dari keyboard
                System.out.print("Masukkan pesan: ");
                String messageStr = scanner.nextLine();

                // Konversi teks menjadi byte array
                byte[] sendBuffer = messageStr.getBytes();

                // Membungkus data ke dalam DatagramPacket
                DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer,
                    sendBuffer.length,
                    serverAddress,
                    serverPort
                );

                // Mengirim paket ke Server
                clientSocket.send(sendPacket);

                // Jika mengetik EXIT
                if (messageStr.equalsIgnoreCase("EXIT")) {
                    System.out.println("Client berhenti.");
                    break;
                }

                // Menyiapkan buffer untuk menerima balasan
                byte[] receiveBuffer = new byte[1024];

                DatagramPacket receivePacket =
                    new DatagramPacket(
                        receiveBuffer,
                        receiveBuffer.length
                    );

                // Menunggu balasan dari Server
                clientSocket.receive(receivePacket);

                // Mengubah balasan menjadi String
                String response = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength()
                );

                // Menampilkan balasan dari Server
                System.out.println(
                    "Balasan dari Server: " + response
                );
            }

        } catch (Exception e) {
            System.err.println(
                "Terjadi kesalahan pada Client: " + e.getMessage()
            );
        }
    }
}