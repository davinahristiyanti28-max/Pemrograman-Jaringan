import socket

# FUNGSI LAYER 6: Enkripsi Caesar Cipher (Menggeser karakter sebanyak 3 posisi)
def enkripsi_presentation_layer(teks, pergeseran=3):
    hasil = ""
    for i in range(len(teks)):
        char = teks[i]
        # Enkripsi huruf kapital
        if char.isupper():
            hasil += chr((ord(char) + pergeseran - 65) % 26 + 65)
        # Enkripsi huruf kecil
        elif char.islower():
            hasil += chr((ord(char) + pergeseran - 97) % 26 + 97)
        else:
            hasil += char
    return hasil

def client_program():
    host = '127.0.0.1'  # Alamat IP (Network Layer - Layer 3)
    port = 5005         # Nomor Port (Transport Layer - Layer 4)

    # [LAYER 5 - SESSION] Menginisiasi sesi koneksi
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((host, port))
    print(f"[LAYER 5] Sesi terkoneksi ke Server {host}:{port}")

    # [LAYER 7 - APPLICATION] Membuat data/pesan asli
    pesan_mentah = "Halo Budi ini pesan rahasia dari Andi"
    print(f"[LAYER 7] Data dibuat: '{pesan_mentah}'")

    # [LAYER 6 - PRESENTATION] Enkripsi Data
    pesan_terenkripsi = enkripsi_presentation_layer(pesan_mentah)
    print(f"[LAYER 6] Data dienkripsi menjadi: '{pesan_terenkripsi}'")

    # [LAYER 6 - PRESENTATION] Mengubah teks menjadi format Bytes (Biner)
    data_terenkapsulasi = pesan_terenkripsi.encode('utf-8')
    print(f"[LAYER 6] Data diubah ke format Bytes/Biner untuk siap kirim.")

    # [OSI ALUR] Enkapsulasi & Pengiriman data fisik
    print("[OSI ALUR] Mengirimkan data melewati Kabel/Wi-Fi (Physical Layer)...\n")
    client_socket.send(data_terenkapsulasi)

    # Tutup koneksi
    client_socket.close()
    print("[LAYER 5] Sesi komunikasi ditutup.")

if __name__ == '__main__':
    client_program()