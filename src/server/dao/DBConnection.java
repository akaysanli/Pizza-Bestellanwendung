// === server/dao/DBConnection.java ===
package server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Die {@code DBConnection}-Klasse stellt eine zentrale Methode zum Aufbau einer
 * Verbindung zur SQLite-Datenbank bereit.
 * <p>
 * Die Verbindungseinstellungen (Pfad zur Datenbankdatei) sind in der Klasse hinterlegt.
 */
public class DBConnection {

    /** Die URL der SQLite-Datenbankdatei. */
//	private static final String DB_URL = "jdbc:sqlite:C:/eclipse/pizzabestellung/Pizza_Datenbank.db";
	
	private static final String DB_URL = "jdbc:sqlite:Pizza_Datenbank.db";
	/**
     * Stellt eine Verbindung zur SQLite-Datenbank her.
     *
     * @return eine aktive {@link Connection} zur Datenbank
     * @throws SQLException wenn beim Aufbau der Verbindung ein Fehler auftritt
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
