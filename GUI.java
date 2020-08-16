package com;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.io.File;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import java.awt.Cursor;
import org.jfree.ui.RefineryUtilities;
import java.io.PrintStream;
public class GUI extends JFrame implements Runnable{
	JLabel l1,l2;
	JTextField tf1;
	Font f1;
	JPanel p1,p2,p3;
	LineBorder border;
	TitledBorder title;
	JFileChooser chooser;
	JScrollPane jsp;
	JButton b1,b2,b3,b4,b5,b6,b7;
	Thread thread;
	File file;
	JTextArea area;

	ArrayList<Aspects> pairs = new ArrayList<Aspects>();
	ArrayList<Sentiment> sentiment = new ArrayList<Sentiment>();

	static int positive;
	static int negative;
public GUI(){
	super("Sentiments Analysis");
	getContentPane().setLayout(new BorderLayout());
	p1 = new JPanel();
	p1.setBackground(new Color(81,123,138));
	f1 = new Font("Times New Roman", Font.BOLD, 16);
	l1 = new JLabel("<HTML><BODY><CENTER>Analyzing Sentiments in One Go: A Supervised Joint<br/>Topic Modeling Approach</CENTER></BODY></HTML>".toUpperCase());
	l1.setFont(this.f1);
    l1.setForeground(Color.blue);
    p1.add(l1);
	p2 = new JPanel();
	p2.setPreferredSize(new Dimension(600,150));
	p2.setBackground(Color.white);
	p2.setLayout(new MigLayout("wrap 2"));
	border = new LineBorder(new Color(42,140,241),1,true);
	title = new TitledBorder (border,"Input Configuration Screen",TitledBorder.CENTER,TitledBorder.DEFAULT_POSITION, new Font("Tahoma",Font.BOLD,16),Color.darkGray);
	p2.setBorder(title);
	f1 = new Font("Courier New",Font.BOLD,14);

	chooser = new JFileChooser(new File("dataset"));
	
	l2 = new JLabel("Upload Dataset");
	l2.setFont(f1);
	p2.add(l2);
	tf1 = new JTextField(15);
	tf1.setFont(f1);
	p2.add(tf1,"split 2");
	b1 = new JButton("Browse Here");
	b1.setFont(f1);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(GUI.this);
			if(option == chooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				tf1.setText(file.getName());
				area.append("Dataset loaded\n");
			}
		}
	});

	b7 = new JButton("Read & Preprocess Reviews");
	b7.setFont(f1);
	p2.add(b7,"span,split 3");
	b7.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			ReadReviews.readReviews(file.getPath());
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
			area.append("Total no of reviews found : "+ReadReviews.reviews.size()+"\n");
		}
	});
	
	b3 = new JButton("Load POS Dictionary");
	b3.setFont(f1);
	p2.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			POSIdentifier.loadDicitionary();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
			area.append("Dictionary Loaded\n");
			JOptionPane.showMessageDialog(GUI.this,"Dictionary Loaded");
		}
	});

	b4 = new JButton("Find Aspect Opinion Word Pair");
	b4.setFont(f1);
	p2.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			try{
				findPairs();
			}catch(Exception e){
				e.printStackTrace();
			}
			area.append("Aspect Opinion Word Pair Generated\n");
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b2 = new JButton("Aspect-level Sentiment Identification & Prediction");
	b2.setFont(f1);
	p2.add(b2,"span,split 3");
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			sentimentAnalysis();
			area.append("Aspect-level sentiment identification & prediction processing completed\n");
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b5 = new JButton("Sentiment Prediction Chart");
	b5.setFont(f1);
	p2.add(b5);
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			positive = 0;
			negative = 0;
			for(int i=0;i<sentiment.size();i++){
				Sentiment sent = sentiment.get(i);
				if(sent.getSentiment().startsWith("Positive"))
					positive = positive + 1;
				if(sent.getSentiment().startsWith("Negative"))
					negative = negative + 1;
			}
			Chart chart1 = new Chart("Sentiment Prediction Chart");
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});

	b6 = new JButton("Exit");
	b6.setFont(f1);
	p2.add(b6);
	b6.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			System.exit(0);
		}
	});

	p3 = new JPanel();
	p3.setLayout(new BorderLayout());
	area = new JTextArea();
	area.setFont(f1);
	area.setEditable(false);
	area.setLineWrap(true);
	jsp = new JScrollPane(area);
	jsp.getViewport().setBackground(Color.white);
	jsp.setPreferredSize(new Dimension(600,530));
	p3.add(jsp,BorderLayout.CENTER);

	//PrintStream printStream = new PrintStream(new CustomOutputStream(area));
	//System.setOut(printStream);
	//System.setErr(printStream);
	
	getContentPane().add(p1,BorderLayout.NORTH);
	getContentPane().add(p2,BorderLayout.CENTER);
	getContentPane().add(p3,BorderLayout.SOUTH);
	thread = new Thread(this);
	thread.start();
}

public void run(){
	try{
		while(true){
			l1.setForeground(Color.black);
			thread.sleep(500);
			l1.setForeground(Color.magenta);
			thread.sleep(500);
			
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
	GUI screen = new GUI();
	screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
	screen.setVisible(true);
}

public void findPairs()throws Exception{
	pairs.clear();
	for(int i=0;i<ReadReviews.reviews.size();i++){
		String review = ReadReviews.reviews.get(i);
		ArrayList<String> list = POSIdentifier.getPos(review);
		String noun = "none";
		String subject = "none";
		for(int j=0;j<list.size();j++){
			String arr[] = list.get(j).split("#");
			if(arr[1].trim().equals("NN") && noun.equals("none"))
				noun = arr[0];
			if(arr[1].trim().equals("JJ") && !noun.equals("none") && subject.equals("none"))
				subject = arr[0];
			if(!noun.equals("none") && !subject.equals("none")){
				j = list.size();
			}
		}
		if(!noun.equals("none") && !subject.equals("none")){
			Aspects as = new Aspects();
			as.setReview(review);
			as.setAspectTerm(noun);
			as.setOpinionWord(subject);
			as.setPair(noun+","+subject);
			pairs.add(as);
		}
	}
	ViewPairs vp = new ViewPairs();
	for(int i=0;i<pairs.size();i++){
		Aspects as = pairs.get(i);
		Object row[] = {as.getReview(),as.getAspectTerm(),as.getOpinionWord(),as.getPair()};
		vp.dtm.addRow(row);
	}
	vp.setVisible(true);
	vp.setSize(800,600);
}

public void sentimentAnalysis(){
	sentiment.clear();
	for(int i=0;i<20;i++){
		Aspects as = pairs.get(i);
		String result = SentimentAnalysis.findSentiment(as.getReview());
		Sentiment sent = new Sentiment();
		sent.setSentiment(result);
		sent.setAspect(as);
		sent.setRate(SentimentAnalysis.rate);
		sentiment.add(sent);
		System.out.print(i+" ");
	}
	ViewSentimentPrediction vsp = new ViewSentimentPrediction();
	for(int i=0;i<sentiment.size();i++){
		Sentiment sent = sentiment.get(i);
		Aspects as = sent.getAspect();
		Object row[] = {as.getReview(),as.getPair(),sent.getRate(),sent.getSentiment()};
		vsp.dtm.addRow(row);
	}
	vsp.setVisible(true);
	vsp.setSize(800,600);
}
}