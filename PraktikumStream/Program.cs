using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using System.Xml.Serialization;
using PraktikumStream;

// ===== 1. OBJEK ASLI (SISI PENGIRIM) =====
var mhs = new Mahasiswa(
    241313100,
    "Eka Rahma Prasetyo",
    "Teknologi Informasi",
    new List<string> { "Pemrograman Jaringan", "Keamanan Siber", "IMK" }
);

Console.WriteLine("=== OBJEK ASLI ===");
mhs.TampilkanInfo();

// ===== 2. SERIALISASI JSON + SIMPAN KE FILE =====
var opsi = new JsonSerializerOptions { WriteIndented = true };
string jsonString = JsonSerializer.Serialize(mhs, opsi);

Console.WriteLine("=== HASIL SERIALISASI JSON ===");
Console.WriteLine(jsonString);

File.WriteAllText("mahasiswa.json", jsonString);
Console.WriteLine("File mahasiswa.json berhasil disimpan.\n");

// ===== 3. SERIALISASI XML + SIMPAN KE FILE =====
var xmlSerializer = new XmlSerializer(typeof(Mahasiswa));
string xmlString;
using (var writer = new StringWriter())
{
    xmlSerializer.Serialize(writer, mhs);
    xmlString = writer.ToString();
}

Console.WriteLine("=== HASIL SERIALISASI XML ===");
Console.WriteLine(xmlString);

File.WriteAllText("mahasiswa.xml", xmlString);
Console.WriteLine("File mahasiswa.xml berhasil disimpan.\n");

// ===== 4. DESERIALISASI JSON (BACA DARI FILE) =====
string jsonDariFile = File.ReadAllText("mahasiswa.json");
Mahasiswa dariJson = JsonSerializer.Deserialize<Mahasiswa>(jsonDariFile);

Console.WriteLine("=== HASIL DESERIALISASI JSON (dari file) ===");
dariJson.TampilkanInfo();

// ===== 5. DESERIALISASI XML (BACA DARI FILE) =====
string xmlDariFile = File.ReadAllText("mahasiswa.xml");
Mahasiswa dariXml;
using (var reader = new StringReader(xmlDariFile))
{
    dariXml = (Mahasiswa)xmlSerializer.Deserialize(reader);
}

Console.WriteLine("=== HASIL DESERIALISASI XML (dari file) ===");
dariXml.TampilkanInfo();

// ===== 6. VERIFIKASI: APAKAH SAMA DENGAN OBJEK ASLI? =====
bool jsonSama = dariJson.NIM == mhs.NIM
    && dariJson.Nama == mhs.Nama
    && dariJson.Jurusan == mhs.Jurusan
    && string.Join(",", dariJson.MataKuliah) == string.Join(",", mhs.MataKuliah);

bool xmlSama = dariXml.NIM == mhs.NIM
    && dariXml.Nama == mhs.Nama
    && dariXml.Jurusan == mhs.Jurusan
    && string.Join(",", dariXml.MataKuliah) == string.Join(",", mhs.MataKuliah);

Console.WriteLine("=== VERIFIKASI ===");
Console.WriteLine($"JSON sama dengan objek asli: {jsonSama}");
Console.WriteLine($"XML  sama dengan objek asli: {xmlSama}");