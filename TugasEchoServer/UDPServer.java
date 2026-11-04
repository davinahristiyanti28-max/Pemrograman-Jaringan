import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) {
        int port = 5000; // Port server

        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("UDP Server siap menerima pesan di port " + port + "...");

            // Buffer untuk menampung data masuk
            byte[] receiveBuffer = new byte[1024];

            while (true) {
                // 1. Siapkan paket untuk menerima data
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                
                // 2. Menunggu dan menerima data dari Client (blocking)
                serverSocket.receive(receivePacket);

                // 3. Ekstrak data menjadi String
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                
                // Mendapatkan info pengirim (Client)
                String clientIP = receivePacket.getAddress().getHostAddress();
                int clientPort = receivePacket.getPort();
                System.out.println("[" + clientIP + ":" + clientPort + "] Pesan masuk: " + message);

                // 4. Ubah pesan menjadi HURUF KAPITAL (Uppercase)
                String replyMessage = message.toUpperCase();

                // 5. Siapkan data balasan
                byte[] sendBuffer = replyMessage.getBytes();
                InetAddress clientAddress = receivePacket.getAddress(); // Alamat IP Client
                int destPort = receivePacket.getPort(); // Port Client

                // 6. Bungkus dan kirim balik ke Client
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, destPort);
                serverSocket.send(sendPacket);
                
                System.out.println("Pesan dibalas dengan: " + replyMessage);

                // Reset buffer untuk iterasi berikutnya
                receiveBuffer = new byte[1024];
            }
        } catch (Exception e) {
            System.err.println("Terjadi kesalahan pada Server: " + e.getMessage());
        }
    }
}