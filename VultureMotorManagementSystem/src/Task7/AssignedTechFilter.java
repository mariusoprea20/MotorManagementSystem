package Task7;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.table.DefaultTableModel;

public class AssignedTechFilter extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable techTable;
	private JTextField text_searchField;
	protected int taskID;
	/**TaskAllocationApp  connects AssignedTechFilter with TaskAllocationComponent
	 * inside the TaskAllocationComponent Class and enables AssignedTechFilterClass
	 * to use all functions.
	 */
	protected TaskAllocationApp launcher;

	public AssignedTechFilter(TaskAllocationApp newLauncher) {
		launcher =newLauncher;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 200, 900, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel SouthPanel = new JPanel();
		contentPane.add(SouthPanel, BorderLayout.SOUTH);
		
		JButton btnCancelButton = new JButton("Cancel");
		btnCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		SouthPanel.add(btnCancelButton);
		/************************************************************************************************************/
		JButton btnFindButton = new JButton("Find ");
		btnFindButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow= techTable.getSelectedRow();
				if(selectedRow>=0) {
				  // get the taskID
			      taskID=(int) techTable.getValueAt(selectedRow, 1);
			      // set the main window table to default
			      DefaultTableModel tableModel = (DefaultTableModel)launcher.theMainWindow.table.getModel();
				  tableModel.setRowCount(0);
				  for(Task t:newLauncher.dbQuery.getAllTasks()) //iterate through all tasks
				  //display only the tasks with the selected technician 
					  if(t.getTaskId()==taskID) {
							tableModel.addRow(new Object[] {t.getTaskId(), t.getTask_type(), t.getTask_status(), t.getTask_deadline(), t.getRemainingDays(), t.getTask_startDate(), t.getTask_endDate(), t.getTech().getFirstName()+" "+t.getTech().getLastName()});
					  }
				}
				dispose();
				
			}
		});
		SouthPanel.add(btnFindButton);
		
		
		
		/************************************************************************************************************/
		JPanel NorthPanel = new JPanel();
		contentPane.add(NorthPanel, BorderLayout.NORTH);
		
		text_searchField = new JTextField();
		text_searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//Set everything to default
			    DefaultTableModel tableModel = (DefaultTableModel) techTable.getModel();
				tableModel.setRowCount(0);
				//get the text entered in the JTextField and use it for later
				String txtSearch= text_searchField.getText();
				
				for(Task tsk:launcher.dbQuery.getAllTasks()) // iterate through all tasks
				  for(Technician tech:launcher.dbQuery.getAllTech())//iterate through all technicians
					  
					//if-conditions to display data when typing in the JTextField  
					  
					//if typing, display the technicians you are looking for
				    if(!(txtSearch.equals("")) && !(tsk.getTech()==null) &&(tsk.getTech().equals(tech))) { 
						if((tech.getFirstName().contains(txtSearch)) || (tech.getLastName().contains(txtSearch))) {
						tableModel.addRow(new Object [] { tech.getFirstName()+" "+tech.getLastName(), tsk.getTaskId() , " "  , tsk.getTask_type(), " "});
						} 
						//not typing, display all technicians
					}else if(txtSearch.equals("") && !(tsk.getTech()==null) && (tsk.getTech().equals(tech))) {
						tableModel.addRow(new Object [] { tech.getFirstName()+" "+tech.getLastName(), tsk.getTaskId() , " "  , tsk.getTask_type(), " "});
					}
			}
		});
		text_searchField.setColumns(30);
		NorthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblFilterTechLabel = new JLabel("Tech Filter");
		NorthPanel.add(lblFilterTechLabel);
		NorthPanel.add(text_searchField);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		techTable = new JTable();
		techTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Task ID", "Motor ID", "Task Name", "Motor Name"
			}
		) {
			/**
			 * serialVersionUID for serialisation runtime
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(techTable);
		this.displayTechs(); // displays technicians and task info
		
	}
	
	//display the technicians and their assigned jobs
	void displayTechs() {
		  DefaultTableModel tableModel = (DefaultTableModel) techTable.getModel();
		  tableModel.setRowCount(0);
		for(Task s:launcher.dbQuery.getAllTasks()) {
		  for(Technician t:launcher.dbQuery.getAllTech()) {
			  if(s.getRemainingDays()>=-30) // display the tasks younger than 31 days
				  if(!(s.getTech()==null) && s.getTech().equals(t)) {
					  tableModel.addRow(new Object [] { t.getFirstName()+" "+t.getLastName(), s.getTaskId() , " "  , s.getTask_type(), " "});
				  }
		   }
		}  
	}
	

}
