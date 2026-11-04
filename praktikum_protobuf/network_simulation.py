import json
import mahasiswa_pb2  # Hasil kompilasi langkah 3[cite: 1]

# Data yang akan dikirim melalui jaringan[cite: 1]
data_mentah = {
    "nim": 20260001,
    "nama": "Clarissa Utama",
    "ipk": 3.85
}

print("=== PROSES DI SISI PENGIRIM (SERVER/CLIENT) ===\n")

# --- 1. SIMULASI JSON (TEKS) ---[cite: 1]
json_string = json.dumps(data_mentah)
payload_json_bytes = json_string.encode('utf-8')  # Mengubah ke bytes untuk socket jaringan[cite: 1]
print(f"[JSON] String yang dikirim: {json_string}")
print(f"[JSON] Ukuran Payload Jaringan: {len(payload_json_bytes)} bytes\n")

# --- 2. SIMULASI PROTOBUF (BINER) ---[cite: 2]
mhs_proto = mahasiswa_pb2.PaketMahasiswa()
mhs_proto.nim = data_mentah["nim"]
mhs_proto.nama = data_mentah["nama"]
mhs_proto.ipk = data_mentah["ipk"]

payload_proto_bytes = mhs_proto.SerializeToString()  # Mengubah langsung ke biner murni[cite: 2]
print(f"[Protobuf] Data Biner (Raw): {payload_proto_bytes}")
print(f"[Protobuf] Ukuran Payload Jaringan: {len(payload_proto_bytes)} bytes\n")

print("=======================================")
print(f"KESIMPULAN: Protobuf lebih hemat {len(payload_json_bytes) - len(payload_proto_bytes)} bytes dibanding JSON!")
print("=======================================\n")

print("=== PROSES DI SISI PENERIMA (DESERIALISASI) ===")
# Menerima data biner dari jaringan dan merakitnya kembali menjadi objek kode[cite: 2]
data_masuk_jaringan = payload_proto_bytes

objek_diterima = mahasiswa_pb2.PaketMahasiswa()
objek_diterima.ParseFromString(data_masuk_jaringan)

print(f"Berhasil membaca data dari socket!")
print(f"Nama Mahasiswa: {objek_diterima.nama}")
print(f"NIM         : {objek_diterima.nim}")