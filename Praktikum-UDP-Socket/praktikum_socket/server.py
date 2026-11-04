import socket

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

host = "127.0.0.1"
port = 5000

server.bind((host, port))
server.listen(1)

print("Server berjalan...")
print(f"Menunggu koneksi pada {host}:{port}")

conn, addr = server.accept()

print("Client terhubung:", addr)

data = conn.recv(1024).decode()

print("Pesan dari client:", data)

conn.send("Pesan diterima oleh server".encode())

conn.close()
server.close()