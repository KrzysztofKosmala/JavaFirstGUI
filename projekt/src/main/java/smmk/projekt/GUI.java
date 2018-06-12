package smmk.projekt;

import java.awt.Color;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame  
{
	private DataBase database;
	private JTabbedPane tabbedPane; //zakladka
	private JPanel firstTab, secondTab; 
	private JTable employeesTable;
	private JTextField txtMinCost, txtMaxCost, txtSearchQuery; // pole tekstowe
	private JLabel reminder;//tekst
	private int eployeeID = -1, filter = -1, eventID = -1;
	private JSpinner spinStartDate, spinEndDate, olderThan;
	private SpinnerDateModel model, model2;
	private Date startDate, endDate;
	private String searchQuery;
	private double minCost = -9999999999999.00, maxCost = 9999999999999.00;

	GUI() throws SQLException 
	{
		database = new DataBase();
		setTitle("Aplikacja do niczego");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		createFirstTab();
		tabbedPane = new JTabbedPane();
		tabbedPane.add("Rekordy", firstTab);
		tabbedPane.setBounds(0, 0, 785, 540);
		reminder = new JLabel();
		  reminder.setBounds(180, 0, 600, 20);
		  reminder.setForeground(Color.RED);
		  reminder.setHorizontalAlignment(SwingConstants.RIGHT);
		  add(firstTab);
		  add(tabbedPane); add(reminder);
		  
		
		
	}
	private JTable createTable(String[] colName, int[] width, ListSelectionListener listener){
	    final JTable jTable = new JTable() {
			@Override
			public boolean isCellEditable(int nRow, int nCol) {
		    	return false;
		    }
	    };
	    	    
	    DefaultTableModel contactTableModel = (DefaultTableModel) jTable.getModel();
	    contactTableModel.setColumnIdentifiers(colName);
	    jTable.setAutoCreateRowSorter(true);
	    jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    
	    for(int i=0; i < width.length; i++){
	    	jTable.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
	    }

	    jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    jTable.getSelectionModel().addListSelectionListener(listener);
	    return jTable;
	}
	
	
	private void createFirstTab() 
	{
		firstTab = new JPanel();
		firstTab.setLayout(null);
		setVisible(true);
	    String[] colName = { "Id", "Nazwisko", "Imie", "Email", "Departament", "Pensja"};
	    int[] width = { 34, 230, 130, 130, 50, 34 };
		
		employeesTable = createTable(colName, width, new ListSelectionListener(){
	   
			public void valueChanged(ListSelectionEvent event) 
			{
	        	if(employeesTable.getSelectedRow()>=0)
	        		eployeeID = Integer.parseInt(employeesTable.getValueAt(employeesTable.getSelectedRow(), 0).toString());
	        }
	    });
		
		
		JScrollPane scrollPane = new JScrollPane(employeesTable);
		scrollPane.setBounds(5, 35, 645, 475);
		firstTab.add(scrollPane);
		
		

		

		

		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.setBounds(655, 105, 120, 30);
		btnDodaj.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) { addPerson(); }
		});
		
		JButton btnUsun = new JButton("Usuń");
		btnUsun.setBounds(655, 140, 120, 30);
		btnUsun.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) { deleteBicycle(); }
		});
		

		  firstTab.add(btnDodaj);
		firstTab.add(btnUsun);
	
	}
	
	public void addPerson() 
	{
		AddPerson panel = new AddPerson();	
	    int result = JOptionPane.showConfirmDialog(null, panel, "Podaj dane nowego rekordu ", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	    	try {
		
		    	database.addPerson(Integer.parseInt(panel.getTxtID()), panel.getTxtNazwisko(), panel.getTxtImie(),panel.getTxtEmail(), panel.getTxtOddial(),Double.parseDouble(panel.getTxtZarobki()));
				
	    	} catch(NumberFormatException e) {
	    		JOptionPane.showMessageDialog(this,	"Podane dane były błędne, nie dodano rekordu.", "", JOptionPane.ERROR_MESSAGE);
	    	}
	    }
	}
	
	public void deleteBicycle() {
		if(employeesTable.getSelectedRow() != -1){
		    int result = JOptionPane.showConfirmDialog(null,"Czy na pewno chcesz usunąć ten rkord?", "Usuń rekord", JOptionPane.OK_CANCEL_OPTION);
		    if (result == JOptionPane.OK_OPTION) {
				database.deletePerson(eployeeID);
				
		    }
		} else {
			JOptionPane.showMessageDialog(this,	"Nie zaznaczono żadnego rekordu.", "", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
