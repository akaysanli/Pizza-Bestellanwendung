package client.net;

import shared.Request;
import shared.Response;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Verwaltet eine TCP-Verbindung zum Server über einen Socket.
 * 
 * Wird einmalig initialisiert (Singleton-artig) und bietet eine
 * zentrale Methode {@code sendRequest()}, um synchron Anfragen zu senden.
 */
public final class ClientConnection {

    private static final String HOST = "localhost";
    private static final int PORT = 2345;

    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    // Statischer Initialisierer: wird bei der ersten Verwendung ausgeführt
    static {
        try {
            socket = new Socket(HOST, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush(); // Header senden
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("[Client] Verbunden mit " + socket.getRemoteSocketAddress());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                "Kann keine Verbindung zu " + HOST + ":" + PORT + " aufbauen:\n" + e.getMessage(),
                "Netzwerkfehler", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Private Konstruktorverhinderung – keine Instanz erlaubt.
     */
    private ClientConnection() {}

    /**
     * Sendet eine Anfrage an den Server und gibt die Antwort zurück.
     * 
     * Diese Methode ist thread-sicher.
     * 
     * @param req Das Request-Objekt mit Aktion und Nutzdaten
     * @return Die Response vom Server
     * @throws IOException bei Netzwerkfehlern
     * @throws ClassNotFoundException wenn das empfangene Objekt nicht deserialisiert werden kann
     */
    public static synchronized Response sendRequest(Request req)
            throws IOException, ClassNotFoundException {
        out.writeObject(req);
        out.flush();
        return (Response) in.readObject();
    }

    /**
     * Beendet die Verbindung zum Server ordnungsgemäß.
     * 
     * Sollte am Programmende aufgerufen werden, um Ressourcen freizugeben.
     */
    public static void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
