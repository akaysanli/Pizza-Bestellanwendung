package client.gui;

import client.net.ClientConnection;
import shared.Request;
import shared.Response;

import javax.swing.*;
import java.awt.*;
/**
 * Das {@code LoginFrame} ist das erste Fenster der Anwendung und stellt die Login-Oberfläche bereit.
 * <p>
 * Es enthält:
 * <ul>
 *   <li>Ein {@link JTextField} zur Eingabe des Benutzernamens</li>
 *   <li>Ein {@link JPasswordField} zur Eingabe des Passworts</li>
 *   <li>Einen {@link JButton} zum Absenden der Login-Daten</li>
 * </ul>
 * Beim Klick auf den Login-Button wird ein Request an den Server gesendet,
 * der die Zugangsdaten validiert. Bei Erfolg wird das Hauptfenster ({@link MainFrame}) geöffnet.
 */
public class LoginFrame extends JFrame {

    private final JTextField     userField  = new JTextField(15);
    private final JPasswordField passField  = new JPasswordField(15);
    private final JButton        loginBtn   = new JButton("Login");
    /**
     * Erstellt das Login-Fenster mit Eingabefeldern für Benutzername und Passwort
     * sowie einem Button zur Anmeldung.
     */
    public LoginFrame() {
        setTitle("Login");
        setSize(300, 160);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));
        p.add(new JLabel("Benutzername:"));
        p.add(userField);
        p.add(new JLabel("Passwort:"));
        p.add(passField);
        p.add(new JLabel());   // Platzhalter
        p.add(loginBtn);
        setVisible(true);
        
        add(p);

        loginBtn.addActionListener(e -> doLogin());
        getRootPane().setDefaultButton(loginBtn);
    }
    /**
     * Liest Benutzername und Passwort aus den Eingabefeldern {@link JTextField},{@link JPasswordField},
     * sendet die Daten als Request an den Server und verarbeitet die Antwort.
     * <p>
     * Bei erfolgreicher Anmeldung wird dieses Fenster geschlossen und ein
     * {@link MainFrame} geöffnet. Andernfalls wird eine Fehlermeldung angezeigt.
     */
    private void doLogin() {
        String user = userField.getText().trim();
        String pwd  = new String(passField.getPassword());

        if (user.isEmpty() || pwd.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Bitte Benutzername und Passwort eingeben!");
            return;
        }

        try {
            Response resp = ClientConnection.sendRequest(
                    new Request("login", user + "," + pwd));

            if ("ok".equals(resp.getStatus())) {
                new MainFrame();           // Hauptfenster öffnen
                dispose();                 // Login schließen
            } else {
                JOptionPane.showMessageDialog(this,
                        "Login fehlgeschlagen – überprüfe deine Daten!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Fehler bei der Kommunikation mit dem Server:\n" + ex);
        }
    }
}