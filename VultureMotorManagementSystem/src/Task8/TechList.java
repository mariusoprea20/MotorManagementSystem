package Task8;

import java.awt.BorderLayout;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TechList extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
    private LogInLauncher launcher;
    private AddTech addTech;
    private EditTech editTech;


	public TechList(LogInLauncher newLauncher) {
	    launcher= newLauncher;
	    editTech= new EditTech(launcher, this);
	    
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 829, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		//Method to add a technician
		JButton btnAddTech = new JButton("Add Tech");
		btnAddTech.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Call addTech method
			     addTech();
			}
		});
		panel.add(btnAddTech);
		
		//Method to edit  technician info in DB
		JButton btnEditTech = new JButton("Edit Tech");
		btnEditTech.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				
				if(selectedRow>=0) {
					//call the EditTech Class 
					editTech.setVisible(true);
					
					int selectedTechID=(int)table.getValueAt(selectedRow, 0);
					//Pass all the existing tech data from the DB, to the (EditTech) textFields
					for(Technician tech: launcher.dbQuery.getAllTech()) {
						if(tech.getTech_id()==selectedTechID) {
							editTech.TechIDField.setText(selectedTechID+"");
							editTech.FirstNameField.setText(tech.getFirstName());
							editTech.LastNameField.setText(tech.getLastName());
							editTech.TechAgeField.setText(tech.getAge()+"");
							editTech.TechSkillsField.setText(tech.getSkills());
							editTech.HealthStatusField.setText(tech.getHealth_status());
						}
					}
				}	
			}
		});
		panel.add(btnEditTech);
		
		//Method to delete a technician from DB
		JButton btnDetele = new JButton("Delete Tech");
		btnDetele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= table.getSelectedRow();
				if(selectedRow>=0) {
				  int selectedTechID=(int) table.getValueAt(selectedRow, 0);
				  //for each tech
			        for(Technician tech:launcher.dbQuery.getAllTech()) {
						if(tech.getTech_id()==selectedTechID) {
							// re-confirm the action with the user by displaying a message dialog
							int result = JOptionPane.showConfirmDialog(null,"Do you want to delete '"+tech.getTech_id()+"'?", "CONFIRMATION", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					            if(result == JOptionPane.YES_OPTION){
					            	
					            	//delete teh selected tech id
					            	launcher.dbQuery.deleteTech(selectedTechID);
					            	//refreh the JTable of this class
					            	displayTableData(launcher.dbQuery.getAllTech());
					            	
					            }
						}
					}
				}
			}
		});
		panel.add(btnDetele);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tech ID", "First Name", "Last Name", "Tech Age", "Skills", "Health Status"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		this.displayTableData(launcher.dbQuery.getAllTech());
	}
	
	
	/** Display the data **/
	void displayTableData(ArrayList<Technician> techList){
		
		
		// Empty the existing data
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		
		// Some safety code missing here
		for( Technician tech: techList) {
				
				tableModel.addRow(new Object[] {tech.getTech_id(), tech.getFirstName(), tech.getLastName(), tech.getAge(), tech.getSkills(), tech.getHealth_status()});
			}
		}	
	
	//method will be invoked to add a new tech
	public void addTech() {
		addTech = new AddTech(launcher, this);
		addTech.setVisible(true);
	}
	

}
