import socket

# FUNGSI LAYER 6: Dekripsi Caesar Cipher (Mengembalikan pergeseran 3 posisi)
def dekripsi_presentation_layer(teks, pergeseran=3):
    return enkripsi_presentation_layer(teks, -pergeseran)

# Fungsi pembantu untuk enkripsi/dekripsi (sama seperti di client)
def enkripsi_presentation_layer(teks, pergeseran):
    hasil = ""
    for i in range(len(teks)):
        char = teks[i]
        if char.isupper():
            hasil += chr((ord(char) + pergeseran - 65) % 26 + 65)
        elif char.islower():
            hasil += chr((ord(char) + pergeseran - 97) % 26 + 97)
        else:
            hasil += char
    return hasil

def server_program():
    host = '127.0.0.1'  # IP Address (Layer 3)
    port = 5000         # Port (Layer 4)

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((host, port))
    server_socket.listen(1)
    print("[SERVER READY] Menunggu koneksi masuk di port 5000...")

    # [LAYER 5 - SESSION] Menerima sesi koneksi
    conn, address = server_socket.accept()
    print(f"[LAYER 5] Sesi terhubung dengan klien dari: {address}")

    while True:
        # [LAYER 1 & 2 - PHYSICAL & DATA LINK] Menerima aliran bit/bytes fisik
        data_mentah = conn.recv(1024)
        if not data_mentah:
            break

        print("\n[OSI ALUR] Data fisik diterima! Mengawali proses Dekapsulasi...")
        print(f"[LAYER 1-2] Data biner mentah yang ditangkap: {data_mentah}")

        # [LAYER 3 & 4 - NETWORK & TRANSPORT] Verifikasi Alamat
        print(f"[LAYER 3-4] Header IP dan Port diverifikasi oleh OS.")

        # [LAYER 6 - PRESENTATION] Decode Biner & Dekripsi Teks
        pesan_terbaca = data_mentah.decode('utf-8')
        print(f"[LAYER 6] Biner diubah ke teks (Format Teks: {pesan_terbaca})")

        pesan_asli = dekripsi_presentation_layer(pesan_terbaca)
        print(f"[LAYER 6] Data sukses didekripsi!")

        # [LAYER 7 - APPLICATION] Menampilkan teks asli ke pengguna
        print(f"[LAYER 7] Pesan sukses ditampilkan di layar aplikasi: '{pesan_asli}'")

    conn.close()
    print("\n[LAYER 5] Sesi selesai, koneksi diputus.")

if __name__ == '__main__':
    server_program()