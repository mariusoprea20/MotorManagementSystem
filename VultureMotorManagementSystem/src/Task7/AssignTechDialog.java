package Task7;



import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AssignTechDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public JTable tableTech;
	/**TaskAllocationApp  connects AssignTechDialog with TaskAllocationComponent
	 * inside the TaskAllocationComponent Class and enables AssignTechDialog
	 * to use all functions.
	 */
	private TaskAllocationApp launcher; // declared to user TaskAllocationApp from DB
	protected int taskID; // retrieve the task id from TaskAllocationComponent Class
	private int techID;  // assign selected Tech ID from this class table when pressing OK JButton


	/*** Create the dialog constructor***/
	public AssignTechDialog(TaskAllocationApp newLauncher) {
		// connect TaskAllocationApp here to use it's functions
		launcher=newLauncher;
		setTitle("Filter Tasks");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPaneAssignTechDialog = new JScrollPane();
			contentPanel.add(scrollPaneAssignTechDialog, BorderLayout.CENTER);
			{
				tableTech = new JTable();
				tableTech.setModel(new DefaultTableModel(
					new Object[][] {
					}, 
					new String[] {
						"Tech ID", "Technician", "Skills"
					}
				) {
					/**
					 * serialVersionUID for serialisation runtime
					 */
					private static final long serialVersionUID = 1L;
					boolean[] columnEditables = new boolean[] {
						false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				tableTech.getColumnModel().getColumn(1).setPreferredWidth(195);
				tableTech.getColumnModel().getColumn(2).setPreferredWidth(440);
				scrollPaneAssignTechDialog.setViewportView(tableTech);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");// Ok Button
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setModal(false);
						dispose();
						/***
						 * The taskID gets the value from the selected row and column in TaskAllocationComponent
						 * and the techID selected in this class.
						 * Both values are being updated in the DB and displayed on the screen
						 */
						int selectedRow=tableTech.getSelectedRow();
						if(selectedRow>=0) {
						techID=(int)tableTech.getValueAt(selectedRow, 0);
						launcher.updateTechList(taskID, techID);
						}
					}
					
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel"); //Cancel Button
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setModal(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				}
		}
		//display all technicians from the DB
		this.displayTableData(launcher.dbQuery.getAllTech());
	}
	
	//Create function to display all Technicians in DB
	void displayTableData(ArrayList<Technician> techList){
		
		// Empty the existing data
		DefaultTableModel tableModel = (DefaultTableModel) tableTech.getModel();
		tableModel.setRowCount(0);
		
		for( Technician s: techList) {
			tableModel.addRow(new Object[] {s.getTech_id(), s.getFirstName()+" "+s.getLastName(),s.getSkills()});
		}
	}
}
