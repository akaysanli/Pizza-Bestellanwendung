package server;

import server.dao.DatabaseSetup;
import server.dao.PizzaDAO;
import server.dao.UserDAO;
import shared.Request;
import shared.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Serverklasse, die auf eingehende Clientverbindungen wartet und 
 * deren Anfragen verarbeitet. 
 * 
 * Der Server verwendet TCP und hält pro Clientverbindung einen eigenen Thread offen.
 * Unterstützte Aktionen: login, search, getAllPizzas, buy.
 */
public class Server {

    private static final int PORT = 2345;

    /**
     * Einstiegspunkt des Servers. Öffnet einen ServerSocket, akzeptiert eingehende
     * Verbindungen und startet für jede Verbindung einen eigenen Thread.
     *
     * @param args wird nicht verwendet
     */
    public static void main(String[] args) {
    	
        // Datenbanktabellen anlegen (falls noch nicht vorhanden)
        DatabaseSetup.initialize();
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server gestartet auf Port " + PORT);

            // DAO-Objekte werden einmalig erstellt und an die Threads übergeben
            PizzaDAO pizzaDAO = new PizzaDAO();
            UserDAO  userDAO  = new UserDAO();

            while (true) {
                Socket client = serverSocket.accept();
                // Jeder Client wird in einem eigenen Thread behandelt
                new Thread(() -> handleClient(client, pizzaDAO, userDAO)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verarbeitet alle Anfragen eines verbundenen Clients. 
     * Die Methode bleibt aktiv, bis der Client die Verbindung schließt.
     * 
     * @param socket   die vom Client angenommene Verbindung
     * @param pizzaDAO DAO für den Zugriff auf Pizzadaten
     * @param userDAO  DAO für den Zugriff auf Benutzerdaten
     */
    private static void handleClient(Socket socket,
                                     PizzaDAO pizzaDAO,
                                     UserDAO  userDAO) {

        System.out.println("[Server] Client: " + socket.getRemoteSocketAddress());

        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream  in  = new ObjectInputStream(socket.getInputStream())) {

            while (true) {
                Object obj = in.readObject();  // blockiert, bis Anfrage kommt
                if (!(obj instanceof Request req)) break;

                Response resp;
                switch (req.getAction()) {
                    case "login" -> resp = handleLogin(req.getPayload(), userDAO);
                    case "search" -> resp = handleSearch(req.getPayload(), pizzaDAO);
                    case "getAllPizzas" -> resp = handleGetAll(pizzaDAO);
                    case "buy"   -> resp = handleBuy(req.getPayload(), pizzaDAO);
                    default      -> resp = new Response("fail", "Unbekannte Aktion");
                }

                out.writeObject(resp);
                out.flush();
            }

        } catch (EOFException eof) {
            System.out.println("[Server] Client getrennt: " + socket.getRemoteSocketAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===================== Request-Handler ==========================

    /**
     * Verarbeitet eine Login-Anfrage.
     *
     * @param data     Kombination aus Benutzername und Passwort (z. B. "admin,1234")
     * @param userDAO  DAO für Benutzerprüfung
     * @return Response mit "ok" oder "fail" und passender Nachricht
     */
    private static Response handleLogin(String data, UserDAO userDAO) {
        String[] creds = data.split(",", 2);
        boolean ok = userDAO.validate(creds[0], creds[1]);
        return new Response(ok ? "ok" : "fail",
                ok ? "Willkommen!" : "Falsche Login-Daten!");
    }

    /**
     * Sucht nach Pizzen, die zum angegebenen Suchbegriff passen.
     *
     * @param keyword Suchbegriff, z. B. "Salami"
     * @param dao     PizzaDAO mit Zugriff auf Pizzadatenbank
     * @return Response mit Suchergebnissen als Payload
     */
    private static Response handleSearch(String keyword, PizzaDAO dao) {
        StringBuilder sb = new StringBuilder();
        dao.search(keyword).forEach(p -> sb.append(p).append("\n"));
        return new Response("ok", sb.toString());
    }

    /**
     * Gibt alle verfügbaren Pizzen zurück.
     *
     * @param dao PizzaDAO
     * @return Response mit vollständiger Pizza-Liste
     */
    private static Response handleGetAll(PizzaDAO dao) {
        List<?> all = dao.getAll();
        StringBuilder sb = new StringBuilder();
        all.forEach(p -> sb.append(p).append("\n"));
        return new Response("ok", sb.toString());
    }

    /**
     * Verarbeitet einen Kaufwunsch (Pizza + Menge).
     *
     * @param payload z. B. "Margherita,2"
     * @param dao     PizzaDAO mit Zugriff auf DB
     * @return Response mit Kaufbestätigung oder Fehlermeldung
     */
    private static Response handleBuy(String payload, PizzaDAO dao) {
        String[] parts = payload.split(",", 2);
        String title = parts[0].trim();
        int qty      = Integer.parseInt(parts[1].trim());
        String msg   = dao.buy(title, qty);  // löst SQL-Eintrag aus
        return new Response("ok", msg);
    }
}
