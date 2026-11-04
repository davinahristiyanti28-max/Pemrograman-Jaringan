import socket

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

host = "127.0.0.1"
port = 5000

client.connect((host, port))

pesan = "Halo Server!"

client.send(pesan.encode())

response = client.recv(1024).decode()

print("Pesan dari server:", response)

client.close()