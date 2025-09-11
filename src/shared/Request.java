package shared;

import java.io.Serializable;

/**
 * Repräsentiert eine Anfrage, die vom Client an den Server gesendet wird.
 * 
 * Enthält die Art der Aktion sowie optionale Nutzdaten.
 */
public class Request implements Serializable {

    private String action;
    private String payload;

    /**
     * Konstruktor zum Erstellen eines neuen Requests.
     * 
     * @param action  Der Name der Aktion (z. B. "login", "search")
     * @param payload Die Nutzdaten (z. B. Benutzerdaten oder Suchbegriffe)
     */
    public Request(String action, String payload) {
        this.action = action;
        this.payload = payload;
    }

    /**
     * @return Name der angeforderten Aktion
     */
    public String getAction() {
        return action;
    }

    /**
     * @return Die mitgesendeten Daten (z. B. Suchtext, Login-Daten)
     */
    public String getPayload() {
        return payload;
    }
}
