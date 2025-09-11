package server.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Die Klasse {@code DatabaseSetup} dient zur Initialisierung der SQLite-Datenbank.
 * <p>
 * Beim Start des Servers wird diese Klasse verwendet, um alle benötigten Tabellen
 * (Benutzer, Pizzen, Bestellungen) anzulegen, sofern sie noch nicht existieren.
 * Zusätzlich wird ein Standard-Admin-Benutzer eingefügt, falls dieser noch nicht vorhanden ist.
 * </p>
 * <p>
 * Tabellen, die erstellt werden:
 * <ul>
 *     <li>{@code users} – Benutzername, Passwort</li>
 *     <li>{@code pizza} – Titel, Preis</li>
 *     <li>{@code orders} – Pizza-Titel, Menge, Datum</li>
 * </ul>
 */
public class DatabaseSetup {

    /**
     * Initialisiert die Datenbankstruktur.
     * <p>
     * Führt SQL-Statements zur Erstellung der Tabellen {@code users}, {@code pizza} und {@code orders} aus,
     * falls diese noch nicht existieren. Außerdem wird ein Admin-Benutzer mit Standard-Zugangsdaten angelegt.
     * </p>
     */
    public static void initialize() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // User-Tabelle
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL
                );
            """);

            // Pizza-Tabelle
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS pizza (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    price REAL NOT NULL
                );
            """);

            // Admin-User hinzufügen (falls noch nicht vorhanden)
            stmt.executeUpdate("""
                INSERT OR IGNORE INTO users (username, password)
                VALUES ('admin', '1234');
            """);

            // Bestellungs-Tabelle
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS orders (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    pizza_title TEXT NOT NULL,
                    quantity INTEGER NOT NULL,
                    order_date TEXT NOT NULL
                );
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
