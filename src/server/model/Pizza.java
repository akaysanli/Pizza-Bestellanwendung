package server.model;

/**
 * Die {@code Pizza}-Klasse repräsentiert eine Pizza mit Titel, Preis und Beschreibung.
 * <p>
 * Sie dient als einfaches Datenmodell für den Austausch zwischen Server und Client.
 */
public class Pizza {

    /** Der Titel bzw. Name der Pizza. */
    private String title;

    /** Der Preis der Pizza (als String, z. B. "7.90"). */
    private String price;

    /** Die Beschreibung der Pizza (z. B. Zutaten oder Bemerkungen). */
    private String description;

    /**
     * Erstellt eine neue Pizza mit den angegebenen Eigenschaften.
     *
     * @param title       der Titel der Pizza
     * @param price       der Preis der Pizza
     * @param description die Beschreibung der Pizza
     */
    public Pizza(String title, String price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
    }

    /**
     * Gibt den Titel der Pizza zurück.
     *
     * @return der Titel der Pizza
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gibt den Preis der Pizza zurück.
     *
     * @return der Preis der Pizza
     */
    public String getPrice() {
        return price;
    }

    /**
     * Gibt eine lesbare Textdarstellung der Pizza zurück.
     *
     * @return die Pizza als String
     */
    @Override
    public String toString() {
        return title + " - " + "  -  " + description + price + "€";
    }
}