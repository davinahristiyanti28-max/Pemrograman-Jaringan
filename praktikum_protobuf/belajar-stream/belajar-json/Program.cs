using System;
using System.IO;
using System.Text.Json;
using System.Xml.Serialization;

namespace belajar_json;

class Program
{
    static void Main(string[] args)
    {
        // Membuat objek mahasiswa
        Mahasiswa mahasiswa = new Mahasiswa
        {
            NIM = 24131310026,
            Nama = "Davinah Ristiyanti",
            IPK = 4.00
        };

        // =====================================
        // 1. SERIALISASI KE JSON
        // =====================================

        string jsonString = JsonSerializer.Serialize(
            mahasiswa,
            new JsonSerializerOptions
            {
                WriteIndented = true
            }
        );

        Console.WriteLine("=== DATA JSON ===");
        Console.WriteLine(jsonString);

        // Menyimpan JSON ke file
        File.WriteAllText("mahasiswa.json", jsonString);

        Console.WriteLine("\nFile mahasiswa.json berhasil dibuat.");

        // =====================================
        // 2. MEMBACA FILE JSON
        // =====================================

        string jsonDibaca = File.ReadAllText("mahasiswa.json");

        Mahasiswa? mahasiswaDariJson =
            JsonSerializer.Deserialize<Mahasiswa>(jsonDibaca);

        Console.WriteLine("\n=== HASIL BACA & DESERIALISASI JSON ===");
        Console.WriteLine($"NIM  : {mahasiswaDariJson?.NIM}");
        Console.WriteLine($"Nama : {mahasiswaDariJson?.Nama}");
        Console.WriteLine($"IPK  : {mahasiswaDariJson?.IPK}");

        // =====================================
        // 3. SERIALISASI KE XML
        // =====================================

        XmlSerializer xmlSerializer =
            new XmlSerializer(typeof(Mahasiswa));

        using (StringWriter writer = new StringWriter())
        {
            xmlSerializer.Serialize(writer, mahasiswa);

            string xmlString = writer.ToString();

            Console.WriteLine("\n=== DATA XML ===");
            Console.WriteLine(xmlString);

            // Menyimpan XML ke file
            File.WriteAllText("mahasiswa.xml", xmlString);

            Console.WriteLine("\nFile mahasiswa.xml berhasil dibuat.");
        }

        // =====================================
        // 4. MEMBACA FILE XML
        // =====================================

        string xmlDibaca = File.ReadAllText("mahasiswa.xml");

        using (StringReader reader = new StringReader(xmlDibaca))
        {
            Mahasiswa? mahasiswaDariXml =
                (Mahasiswa?)xmlSerializer.Deserialize(reader);

            Console.WriteLine("\n=== HASIL BACA & DESERIALISASI XML ===");
            Console.WriteLine($"NIM  : {mahasiswaDariXml?.NIM}");
            Console.WriteLine($"Nama : {mahasiswaDariXml?.Nama}");
            Console.WriteLine($"IPK  : {mahasiswaDariXml?.IPK}");
        }

        Console.WriteLine("\n=== PROGRAM SELESAI ===");
    }
}