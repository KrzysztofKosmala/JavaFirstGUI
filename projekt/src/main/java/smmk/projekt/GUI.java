package smmk.projekt;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame  
{
	private DataBase database;
	private JTabbedPane tabbedPane; //zakladka
	private JPanel firstTab; 
	private JTable employeesTable;
	private JLabel reminder;//tekst
	private int eployeeID = -1;

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
	    String[] colName = { "Id", "Nazwisko", "Imie", "Email", "Oddzial", "Pensja"};
	    int[] width = { 34, 150, 130, 130, 50, 34 };
		
	    employeesTable = createTable(colName, width, new ListSelectionListener(){
            
            public void valueChanged(ListSelectionEvent event) {
                if(employeesTable.getSelectedRow()>=0)
                    eployeeID = Integer.parseInt(employeesTable.getValueAt(employeesTable.getSelectedRow(), 0).toString());
            }
        });
	    updateTable(database.getEmployees(), employeesTable);
	    
		
		
		JScrollPane scrollPane = new JScrollPane(employeesTable);
		scrollPane.setBounds(5, 35, 645, 475);
		firstTab.add(scrollPane);
		
		

		

		

		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.setBounds(655, 10, 120, 30);
		btnDodaj.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) { addPerson(); }
		});
		
		JButton btnUsun = new JButton("Usuń");
		btnUsun.setBounds(655, 50, 120, 30);
		btnUsun.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) { deletePerson(); }
		});
		
		JButton btnEdytuj = new JButton("Zmień");
        btnEdytuj.setBounds(655, 90, 120, 30);
        btnEdytuj.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) { editPerson(); }
        });
		

		firstTab.add(btnDodaj);
		firstTab.add(btnUsun);
		firstTab.add(btnEdytuj);
	
	}
	
	public void addPerson() 
	{
		AddPerson panel = new AddPerson();	
	    int result = JOptionPane.showConfirmDialog(null, panel, "Podaj dane nowego rekordu ", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	    	try {
		
		    	database.addPerson(Integer.parseInt(panel.getTxtID()), panel.getTxtNazwisko(), panel.getTxtImie(),panel.getTxtEmail(), panel.getTxtOddial(),Double.parseDouble(panel.getTxtZarobki().replaceAll(" ", ".")));
		        updateTable(database.getEmployees(), employeesTable);

		    	
	    	} catch(NumberFormatException e) {
	    		JOptionPane.showMessageDialog(this,	"Podane dane były błędne, nie dodano rekordu.", "", JOptionPane.ERROR_MESSAGE);
	    	}
	    }
	}
	
	public void deletePerson() {
		if(employeesTable.getSelectedRow() != -1){
		    int result = JOptionPane.showConfirmDialog(null,"Czy na pewno chcesz usunąć ten rekord?", "Usuń rekord", JOptionPane.OK_CANCEL_OPTION);
		    if (result == JOptionPane.OK_OPTION) {
				database.deletePerson(eployeeID);
		        updateTable(database.getEmployees(), employeesTable);

		    }
		} else {
			JOptionPane.showMessageDialog(this,	"Nie zaznaczono żadnego rekordu.", "", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void editPerson() {
        if(employeesTable.getSelectedRow() != -1){
            AddPerson panel = new AddPerson();
            int result = JOptionPane.showConfirmDialog(null, panel, "Czy na pewno chcesz zmodyfikować ten rekord?", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                database.editPerson(Integer.parseInt(panel.getTxtID()), panel.getTxtNazwisko(), panel.getTxtImie(),panel.getTxtEmail(), panel.getTxtOddial(),Double.parseDouble(panel.getTxtZarobki().replaceAll(" ", ".")));
                updateTable(database.getEmployees(), employeesTable);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Nie zaznaczono żadnego rekordu.", "", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	
	
	   private void updateTable(Object[][] list, JTable jTable) {
	        DefaultTableModel contactTableModel = (DefaultTableModel) jTable.getModel();
	        contactTableModel.setRowCount(0);
	        
	        for(Object[] t : list){
	            contactTableModel.addRow(t);
	        }
	    }
	
	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                try {
                    GUI frame = new GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
