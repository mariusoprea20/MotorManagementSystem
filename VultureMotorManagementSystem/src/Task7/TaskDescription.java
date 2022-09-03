package Task7;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class TaskDescription extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected JTextArea txtrDisplayDesc; // will display the description retrieved from TaskAllocationComponent Class

	/**
	 * Create the frame.
	 */
	public TaskDescription() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 442, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel descPanel = new JPanel();
		contentPane.add(descPanel);
		descPanel.setLayout(new BorderLayout(0, 0));
		
	    txtrDisplayDesc = new JTextArea(12,40);
		txtrDisplayDesc.setEditable(false);
		descPanel.add(txtrDisplayDesc);
		
		JScrollPane scrollPane = new JScrollPane(txtrDisplayDesc);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		descPanel.add(scrollPane);
		
	
		
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		JButton btnCloseDesc = new JButton("Close");
		btnCloseDesc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            //setModal(false);
            dispose();
			}
		});
		btnCloseDesc.setActionCommand("Close");
		southPanel.add(btnCloseDesc);
	}
	

}
