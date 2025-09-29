# Pizza-Bestellanwendung

Eine kleine Java-Anwendung zur Simulation einer **Pizza-Bestellanwendung**.  
Das Projekt nutzt eine **SQLite-Datenbank** zur Verwaltung der Bestellungen.

---

## 🚀 Funktionen
- Auswahl verschiedener Pizzen inkl. Such- und Kauffunktion
- Speicherung der Bestellungen in einer SQLite-Datenbank
- GUI-Ausgabe

---

## 📂 Projektstruktur
```
pizzabestellung/
│
├── src/                     # Java-Quellcode
├── bin/                     # Kompilierte Klassen
├── lib/                     # externe Libraries (z. B. SQLite-JDBC)
│   └── sqlite-jdbc-3.49.1.0.jar
├── Pizza_Datenbank.db       # SQLite-Datenbank
└── README.md                # Projektdokumentation
```

---

## ⚙️ Voraussetzungen
- **Java 8+** installiert  
- Ein beliebiges IDE (z. B. Eclipse) oder die Kommandozeile  

---

## ▶️ Starten

### Mit Eclipse (oder einem anderen IDE)
1. Projekt importieren  
2. Rechtsklick auf Projekt → **Build Path → Configure Build Path**  
3. Unter „Libraries“ die Datei `lib/sqlite-jdbc-3.49.1.0.jar` hinzufügen  
4. `Main.java` (oder deine Startklasse) ausführen  

---

## 📊 Datenbank
- Die SQLite-Datenbank liegt im Projekt (`Pizza_Datenbank.db`).  
- Beim Start verbindet sich die Anwendung automatisch mit der Datenbank.  

---

## 🖥️ Beispiel-Output
```text
Willkommen zur Pizza-Bestellanwendung!

Suche      Kaufen

Margherita - Preis - Beschreibung
Salami - Preis - Beschreibung
Hawaii - Preis - Beschreibung
...

Ihre Auswahl: Salami

Sie haben eine Pizza Salami bestellt.

(Die Bestellung wurde erfolgreich in der Datenbank gespeichert!)
```

---

## 📝 Hinweise
- Das Projekt enthält die SQLite-JAR bereits im Ordner `lib/`.  
- Falls du eine andere Version nutzen möchtest:  
  [SQLite-JDBC Releases](https://github.com/xerial/sqlite-jdbc)  

---

## Kontakt
Fragen oder Verbesserungsvorschläge?  
- [LinkedIn: akay-mert-sanli](https://www.linkedin.com/in/akay-mert-sanli/)
- E-Mail: [akaysanli2005@gmail.com](mailto:akaysanli2005@gmail.com)
