package client.gui;

import client.net.ClientConnection;
import shared.Request;
import shared.Response;

import javax.swing.*;
import java.awt.*;
/**
 * Das {@code MainFrame} ist das Hauptfenster der Anwendung.
 * <p>
 * Es ermöglicht:
 * <ul>
 *   <li>Die Suche nach Pizzen anhand eines Suchbegriffs</li>
 *   <li>Den Kauf einer bestimmten Pizza</li>
 *   <li>Die Anzeige aller verfügbaren Pizzen beim Start im ScrollPane</li>
 * </ul>
 * Die Ergebnisse werden in einem scrollbaren Textbereich angezeigt.
 */
public class MainFrame extends JFrame {
    /**
     * Erstellt das Hauptfenster, initialisiert die Oberfläche und lädt alle verfügbaren Pizzen.
     */
    private final JTextArea  resultArea  = new JTextArea();
    private final JTextField searchField = new JTextField(20);
    private final JButton    searchBtn   = new JButton("Suchen");
    private final JButton    buyBtn      = new JButton("Kaufen");

    public MainFrame() {
        setTitle("Pizza-Bestellung");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* --- Such- & Kauf-Leiste ------------------------------------------------ */
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(searchField);
        top.add(searchBtn);
        top.add(buyBtn);
        add(top, BorderLayout.NORTH);

        /* --- Ergebnisliste ------------------------------------------------------ */
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        /* --- Aktionen ----------------------------------------------------------- */
        searchBtn.addActionListener(e -> search());
        buyBtn.addActionListener   (e -> buy());

        setVisible(true);
        loadAll();          // alle Pizzen beim Start laden
    }

    /* ---------- Netzwerk-Operationen ------------------------------------------ */

    private void search() {
        try {
            Response r = ClientConnection.sendRequest(
                    new Request("search", searchField.getText().trim()));
            resultArea.setText(r.getPayload());
        } catch (Exception ex) {
            showError(ex);
        }
    }
    /**
     * Lädt alle verfügbaren Pizzen vom Server und zeigt das Ergebnis im {@code resultArea} an.
     * <p>
     * Diese Methode sendet eine Anfrage mit der Aktion {@code "getAllPizzas"} an den Server über
     * die {@code ClientConnection}. Die Antwort wird im Textbereich {@code resultArea} angezeigt.
     * Falls ein Fehler auftritt, wird dieser über
     * {@code showError(ex)} behandelt.
     * </p>
     */
    private void loadAll() {
        try {
            Response r = ClientConnection.sendRequest(
                    new Request("getAllPizzas", ""));
            resultArea.setText(r.getPayload());
        } catch (Exception ex) {
            showError(ex);
        }
    }
    /**
     * Führt den Kaufprozess für eine ausgewählte Pizza durch.
     * <p>
     * Der Benutzer wird aufgefordert, den Namen der gewünschten Pizza und die Anzahl der
     * zu kaufenden Einheiten einzugeben. Die eingegebenen Daten werden dann an den Server
     * gesendet, um den Kauf durchzuführen. Die Antwort des Servers wird als Nachricht
     * angezeigt. Danach wird die Liste der verfügbaren Pizzen aktualisiert und ein neues
     * Fenster für den Bezahlvorgang geöffnet.
     * </p>
     * <p>
     * Bei ungültiger Eingabe der Menge (keine Zahl) wird eine Fehlermeldung angezeigt.
     * Unerwartete Fehler werden über {@code showError(Exception)} behandelt.
     * </p>
     */
    private void buy() {
        String title = JOptionPane.showInputDialog(this,
                "Welche Pizza möchtest du kaufen?");
        if (title == null || title.isBlank()) return;

        String qtyStr = JOptionPane.showInputDialog(this,
                "Wie viele möchtest du kaufen?");
        if (qtyStr == null || qtyStr.isBlank()) return;

        try {
            int qty = Integer.parseInt(qtyStr.trim());
            Response r = ClientConnection.sendRequest(
                    new Request("buy", title + "," + qty));
            JOptionPane.showMessageDialog(this, r.getPayload());
            loadAll();                  // Liste aktualisieren
            new PaymentFrame();         // “Bezahlen”-Dialog
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Bitte eine Zahl eingeben!");
        } catch (Exception ex) {
            showError(ex);
        }
    }
    /**
     * Zeigt eine Fehlermeldung in einem Dialogfenster an und gibt den Stacktrace auf der Konsole aus.
     * <p>
     * Diese Methode dient der zentralen Fehlerbehandlung. Sie gibt den vollständigen Stacktrace
     * des übergebenen {@code Exception}-Objekts zur Konsole aus und zeigt anschließend die
     * Fehlermeldung in einem {@code JOptionPane}-Dialog an.
     * </p>
     *
     * @param ex die aufgetretene Ausnahme, deren Details angezeigt werden sollen
     */
    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Fehler:\n" + ex.getMessage());
    }
    
}