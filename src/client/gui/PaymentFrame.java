package client.gui;

import java.awt.*;
import javax.swing.*;
/**
 * Das {@code PaymentFrame} ist ein Fenster zur Auswahl der Bezahlungsart
 * nach einer Pizzabestellung.
 * <p>
 * Es bietet zwei Optionen:
		* <ul>
 *   <li>{@code Online-Zahlung} – zeigt eine Nachricht für digitale Bezahlung an</li>
		*   <li>{@code Offline-Zahlung} – zeigt eine Nachricht für Bar- oder Kartenbezahlung bei Abholung an</li>
		* </ul>
		* Nach Auswahl einer Option wird eine entsprechende Bestätigungsnachricht angezeigt
 * und das Programm beendet.
		*/
public class PaymentFrame extends JFrame{
	private JLabel paymentDecisison;
	private JButton onlinePayment;
	private JButton offlinePayment;
	/**
	 * Erstellt ein neues {@code PaymentFrame}-Fenster mit Auswahlmöglichkeiten
	 * für die Bezahlungsart (online oder offline).
	 */
	public PaymentFrame() {
		setTitle("Bezahlungsart");
		setSize(300,150);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel(new GridLayout(3, 1));
		paymentDecisison = new JLabel("Wählen Sie eine Bezahlungsart:");
		onlinePayment = new JButton("Online-Zahlung");
		offlinePayment = new JButton("Offline-Zahlung");
		panel.add(paymentDecisison);
		panel.add(onlinePayment);
		panel.add(offlinePayment);
		
		onlinePayment.addActionListener(e -> successOnline("Bitte gehen Sie zu Ihrer Bank-App, um die Zahlung zu beenden."));
		offlinePayment.addActionListener(e -> successOffline("Bitte halten Sie bei der Abholung Ihre Bankkarte oder das Bargeld bereit."));
		
		add(panel);

	}
	/**
	 * Zeigt eine Nachricht für Online-Zahlung an und beendet das Programm.
	 *
	 * @param text Die anzuzeigende Nachricht.
	 */
	private void successOnline(String text) {
		JOptionPane.showMessageDialog(this, text);
		System.exit(0);
		
	}
	/**
	 * Zeigt eine Nachricht für Offline-Zahlung an und beendet das Programm.
	 *
	 * @param text Die anzuzeigende Nachricht.
	 */
	private void successOffline(String text) {
		JOptionPane.showMessageDialog(this, text);
		System.exit(0);
	}
	
}