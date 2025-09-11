package shared;

import java.io.Serializable;

/**
 * Repräsentiert die Antwort, die der Server an den Client zurücksendet.
 * 
 * Enthält einen Status (z. B. "ok" oder "fail") sowie optionale Daten.
 */
public class Response implements Serializable {

    private String status;
    private String payload;

    /**
     * Erstellt ein neues Response-Objekt.
     * 
     * @param status  Status der Antwort ("ok", "fail" etc.)
     * @param payload Daten bzw. Nachricht des Servers
     */
    public Response(String status, String payload) {
        this.status = status;
        this.payload = payload;
    }

    /**
     * @return Antwortstatus
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return Antwortdaten oder Fehlermeldung
     */
    public String getPayload() {
        return payload;
    }
}
