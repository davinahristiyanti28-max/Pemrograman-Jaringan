import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) {
        int port = 5000; // Port server

        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("UDP Server siap menerima pesan di port " + port + "...");

            // Buffer untuk menampung data masuk
            byte[] receiveBuffer = new byte[1024];

            while (true) {

                // Siapkan paket untuk menerima data
                DatagramPacket receivePacket =
                    new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Menerima pesan dari Client
                serverSocket.receive(receivePacket);

                // Mengubah byte data menjadi String
                String message = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength()
                );

                // Mendapatkan informasi Client
                String clientIP =
                    receivePacket.getAddress().getHostAddress();

                int clientPort =
                    receivePacket.getPort();

                System.out.println(
                    "[" + clientIP + ":" + clientPort + "] Pesan: " + message
                );

                // Jika Client mengirim EXIT
                if (message.equalsIgnoreCase("EXIT")) {
                    System.out.println("Client keluar dari program.");
                    break;
                }

                // Mengubah pesan menjadi HURUF KAPITAL
                String response = message.toUpperCase();

                // Konversi response menjadi byte
                byte[] sendBuffer = response.getBytes();

                // Membuat paket balasan
                DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer,
                    sendBuffer.length,
                    receivePacket.getAddress(),
                    receivePacket.getPort()
                );

                // Mengirim balasan ke Client
                serverSocket.send(sendPacket);

                System.out.println("Balasan dikirim: " + response);
                System.out.println("--------------------------------");
                
                // Reset buffer
                receiveBuffer = new byte[1024];
            }

        } catch (Exception e) {
            System.err.println(
                "Terjadi kesalahan pada Server: " + e.getMessage()
            );
        }
    }
}