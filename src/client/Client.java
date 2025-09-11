package client;

import client.gui.LoginFrame;

import javax.swing.*;

/**
 * Einstiegspunkt des Client-Programms.
 * 
 * Startet die Anwendung, indem das Login-Fenster angezeigt wird
 * und ein Shutdown-Hook zur ordnungsgemäßen Trennung der Netzwerkverbindung gesetzt wird.
 */
public class Client {

    /**
     * Hauptmethode des Clients.
     * 
     * @param args Startparameter (nicht verwendet)
     */
    public static void main(String[] args) {
        new LoginFrame();

        // Shutdown-Hook: Schließt Verbindung beim Beenden des Programms
        Runtime.getRuntime().addShutdownHook(new Thread(client.net.ClientConnection::close));
    }
}
