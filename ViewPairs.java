package com;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.table.TableColumn;
public class ViewPairs extends JFrame{
	DefaultTableModel dtm;
	JScrollPane jsp;
	JTable table;
public ViewPairs(){
	setTitle("View Pairs");
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(){
			return false;
		}
	};
	dtm.addColumn("Reviews");
	dtm.addColumn("Aspect Term");
	dtm.addColumn("Opinion Word");
	dtm.addColumn("Pairs");
	table = new JTable(dtm);
	table.getTableHeader().setFont(new Font("Courier New",Font.BOLD,15));
	table.setFont(new Font("Courier New",Font.BOLD,14));
	table.setRowHeight(30);
	jsp = new JScrollPane(table);
	getContentPane().add(jsp);
}
}