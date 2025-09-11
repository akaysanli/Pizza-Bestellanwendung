package server.model;

/**
 * Die {@code User}-Klasse repräsentiert einen Benutzer mit Benutzername und Passwort.
 * <p>
 * Sie wird verwendet, um Benutzerdaten zu speichern und zu übertragen.
 */
public class User {

    /** Der Benutzername des Users. */
    private String username;

    /** Das Passwort des Users. */
    private String password;

    /**
     * Erstellt einen neuen Benutzer mit angegebenem Benutzernamen und Passwort.
     *
     * @param username der Benutzername des Users
     * @param password das Passwort des Users
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gibt den Benutzernamen des Users zurück.
     *
     * @return der Benutzername
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gibt das Passwort des Users zurück.
     *
     * @return das Passwort
     */
    public String getPassword() {
        return password;
    }
}