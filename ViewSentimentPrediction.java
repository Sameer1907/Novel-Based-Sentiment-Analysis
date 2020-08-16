package com;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.table.TableColumn;
public class ViewSentimentPrediction extends JFrame{
	DefaultTableModel dtm;
	JScrollPane jsp;
	JTable table;
public ViewSentimentPrediction(){
	setTitle("View Sentiment Prediction");
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(){
			return false;
		}
	};
	dtm.addColumn("Reviews");
	dtm.addColumn("Aspect Pairs");
	dtm.addColumn("Rating");
	dtm.addColumn("Sentiment Prediction");
	table = new JTable(dtm);
	table.getTableHeader().setFont(new Font("Courier New",Font.BOLD,15));
	table.setFont(new Font("Courier New",Font.BOLD,14));
	table.setRowHeight(30);
	jsp = new JScrollPane(table);
	getContentPane().add(jsp);
}
}