package smmk.projekt;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPerson extends JPanel {
	private JTextField txtID, txtNazwisko, txtImie, txtEmail, txtOddial, txtZarobki;
	
	AddPerson(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel labID = new JLabel("ID:");
		txtID = new JTextField(20);
		
		JLabel labNazwisko = new JLabel("Nazwiko:");
		txtNazwisko = new JTextField(20);
		
		JLabel labImie = new JLabel("Imie");
		txtImie = new JTextField(20);
		
		JLabel labEmail = new JLabel("Email: ");
		txtEmail = new JTextField(20);
		
		JLabel labOddzial = new JLabel("Oddial:");
		txtOddial = new JTextField(20);
		
		JLabel labZarobki = new JLabel("Zarobki: ");
		txtOddial = new JTextField(20);
		
		add(labID); add(txtID); 
		add(labNazwisko); add(txtNazwisko);
		add(labImie); add(txtImie);
		add(labEmail); add(txtEmail);
		add(labOddzial); add(txtOddial);
		add(labZarobki); add(txtOddial);
	}

	public String getTxtID() {
		return txtID.getText();
	}

	public String getTxtNazwisko() {
		return txtNazwisko.getText();
	}

	public String getTxtImie() {
		return txtImie.getText();
	}

	public String getTxtEmail() {
		return txtEmail.getText();
	}

	public String getTxtOddial() {
		return txtOddial.getText();
	}

	public String getTxtZarobki() {
		return txtZarobki.getText();
	}

	
}
