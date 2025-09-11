package server.dao;

import java.sql.*;

/**
 * Das {@code UserDAO} stellt Methoden zum Zugriff auf die Benutzerdatenbank bereit.
 * <p>
 * Aktuell ermöglicht es:
 * <ul>
 *     <li>Die Überprüfung von Benutzeranmeldedaten auf Gültigkeit</li>
 * </ul>
 */
public class UserDAO {

    /**
     * Prüft, ob ein Benutzer mit dem angegebenen Benutzernamen und Passwort existiert.
     * <p>
     * Die Methode führt ein {@code SELECT}-Statement gegen die Tabelle {@code users} aus
     * und gibt {@code true} zurück, wenn ein entsprechender Datensatz gefunden wird.
     *
     * @param username der Benutzername, der überprüft werden soll
     * @param password das Passwort, das überprüft werden soll
     * @return {@code true}, wenn Benutzername und Passwort gültig sind, andernfalls {@code false}
     */
    public boolean validate(String username, String password) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true, wenn ein Datensatz gefunden wurde
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}