package server.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import server.model.Pizza;

/**
 * Das {@code PizzaDAO} stellt Methoden zum Zugriff auf die Pizzadatenbank bereit.
 * <p>
 * Es ermöglicht:
 * <ul>
 *     <li>Das Auslesen aller Pizzen</li>
 *     <li>Die Suche nach Pizzen anhand eines Stichworts</li>
 *     <li>Das Kaufen einer bestimmten Pizza (bzw. das Simulieren eines Kaufvorgangs)</li>
 * </ul>
 */
public class PizzaDAO {

    /**
     * Gibt eine Liste aller in der Datenbank vorhandenen Pizzen zurück.
     * <p>
     * Für jede Zeile in der Tabelle {@code pizza} wird ein {@link Pizza}-Objekt erstellt
     * und zur Ergebnisliste hinzugefügt.
     *
     * @return eine Liste aller Pizzen aus der Datenbank
     */
    public List<Pizza> getAll() {
        List<Pizza> pizzas = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pizza")) {

            // Jede Pizza der Liste hinzufügen
            while (rs.next()) {
                pizzas.add(new Pizza(rs.getString("title"), rs.getString("price"), rs.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pizzas;
    }

    /**
     * Sucht Pizzen in der Datenbank, deren Titel oder Preis das übergebene Stichwort enthält.
     * <p>
     * Die Suche erfolgt unscharf (mit {@code LIKE}) und ist case-insensitiv.
     *
     * @param keyword das Stichwort, nach dem gesucht werden soll
     * @return eine Liste der gefundenen Pizzen, die dem Suchkriterium entsprechen
     */
    public List<Pizza> search(String keyword) {
        List<Pizza> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pizza WHERE title LIKE ? OR price LIKE ?")) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(new Pizza(rs.getString("title"), rs.getString("price"), rs.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Simuliert den Kauf einer bestimmten Pizza, indem ein entsprechender Eintrag in die Tabelle {@code orders} geschrieben wird.
     * <p>
     * Zuvor wird geprüft, ob die Pizza mit dem angegebenen Titel in der Tabelle {@code pizza} existiert.
     *
     * @param title    der Titel der gewünschten Pizza (unscharfe Suche)
     * @param quantity die gewünschte Anzahl
     * @return eine Erfolgs- oder Fehlermeldung je nach Ergebnis des Vorgangs
     */
    public String buy(String title, int quantity) {
        String sqlCheck = "SELECT title FROM pizza WHERE LOWER(title) LIKE ?";
        String sqlInsertOrder = "INSERT INTO orders (pizza_title, quantity) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection()) {
            String foundPizzaTitle = null;

            // 1. Prüfen, ob Pizza existiert
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {	
                psCheck.setString(1, "%" + title.toLowerCase() + "%");
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        foundPizzaTitle = rs.getString("title");
                    } else {
                        return "Pizza nicht gefunden!";
                    }
                }
            }
            
            // 2. Bestellung speichern
            try (PreparedStatement psOrder = conn.prepareStatement(sqlInsertOrder)) {
                psOrder.setString(1, foundPizzaTitle);
                psOrder.setInt(2, quantity);
                psOrder.executeUpdate();
            }
            
            return "Pizza erfolgreich gekauft: " + quantity + "x " + foundPizzaTitle;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Fehler beim Kauf: " + e.getMessage();
        }
    }
}