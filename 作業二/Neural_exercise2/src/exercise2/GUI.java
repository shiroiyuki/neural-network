package exercise2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**************
 * GUI Design Algorithm implement(BACKPROPAGATION NETWORK)
 * 
 * @author jack
 **************/
public class GUI extends JFrame implements ActionListener {
	/*
	 * Declare Variables
	 **********************/
	private JFileChooser JF = new JFileChooser();
	JTextField JT1, JT2, JT3, JT4, JT5, JT6, JT7, JT8;
	JButton JB1, JB2, JB3;
	int x = 420, y = 50, correcttimes, times, training, size;
	double output;
	BPN bpn;
	ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();

	/*
	 * GUI Design
	 ***********************/
	GUI() {

		super("Perceptron exercise2");
		setBackground(Color.LIGHT_GRAY);
		// set JFrame
		setSize(1200, 800);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel JL1 = new JLabel("學習率");
		JL1.setFont(new Font("標楷體", Font.BOLD, 18));
		JL1.setBounds(15, 10, 71, 30);
		getContentPane().add(JL1);

		JLabel JL2 = new JLabel("收斂條件");
		JL2.setFont(new Font("標楷體", Font.BOLD, 18));
		JL2.setBounds(15, 50, 90, 29);
		getContentPane().add(JL2);

		JLabel JL3 = new JLabel("學習資料數");
		JL3.setFont(new Font("標楷體", Font.BOLD, 18));
		JL3.setBounds(15, 89, 114, 30);
		getContentPane().add(JL3);

		JLabel JL4 = new JLabel("訓練辨識率");
		JL4.setFont(new Font("標楷體", Font.BOLD, 18));
		JL4.setBounds(15, 129, 114, 44);
		getContentPane().add(JL4);

		JLabel JL5 = new JLabel("測試辨識率");
		JL5.setFont(new Font("標楷體", Font.BOLD, 18));
		JL5.setBounds(15, 175, 114, 40);
		getContentPane().add(JL5);

		JLabel JL6 = new JLabel("隱藏層層數");
		JL6.setFont(new Font("標楷體", Font.BOLD, 18));
		JL6.setBounds(15, 230, 100, 23);
		getContentPane().add(JL6);

		JLabel JL7 = new JLabel("隱藏層神經元個數");
		JL7.setFont(new Font("標楷體", Font.BOLD, 18));
		JL7.setBounds(15, 268, 153, 44);
		getContentPane().add(JL7);

		JLabel JL8 = new JLabel("輸出層神經元個數");
		JL8.setFont(new Font("標楷體", Font.BOLD, 18));
		JL8.setBounds(15, 327, 154, 23);
		getContentPane().add(JL8);

		JT1 = new JTextField();
		JT1.setText("0.4");
		JT1.setBounds(179, 11, 160, 29);
		getContentPane().add(JT1);
		JT1.setColumns(10);

		JT2 = new JTextField();
		JT2.setText("20000");
		JT2.setBounds(179, 50, 160, 29);
		getContentPane().add(JT2);
		JT2.setColumns(10);

		JT3 = new JTextField();
		JT3.setText("100");
		JT3.setBounds(179, 90, 160, 29);
		getContentPane().add(JT3);
		JT3.setColumns(10);

		JT4 = new JTextField();
		JT4.setBackground(Color.WHITE);
		JT4.setEditable(false);
		JT4.setBounds(179, 137, 160, 29);
		getContentPane().add(JT4);
		JT4.setColumns(10);

		JT5 = new JTextField();
		JT5.setBackground(Color.WHITE);
		JT5.setEditable(false);
		JT5.setBounds(179, 181, 160, 29);
		getContentPane().add(JT5);
		JT5.setColumns(10);

		JT6 = new JTextField();
		JT6.setBounds(179, 230, 136, 29);
		JT6.setText("2");
		getContentPane().add(JT6);
		JT6.setColumns(10);

		JT7 = new JTextField();
		JT7.setBounds(179, 283, 136, 29);
		JT7.setText("4");
		getContentPane().add(JT7);
		JT7.setColumns(10);

		JT8 = new JTextField();
		JT8.setBounds(179, 336, 136, 29);
		JT8.setText("3");
		getContentPane().add(JT8);
		JT8.setColumns(10);

		JB1 = new JButton("Load");
		JB1.setBounds(15, 621, 111, 31);
		getContentPane().add(JB1);

		JB2 = new JButton("Clear");
		JB2.setBounds(162, 621, 111, 31);
		getContentPane().add(JB2);

		JB3 = new JButton("Start");
		JB3.setBounds(94, 691, 111, 31);
		getContentPane().add(JB3);

		// setting
		JF.setDialogTitle("Choose file:");// Title
		JF.setCurrentDirectory(new File(System.getProperty("user.dir")));
		JF.setFileSelectionMode(JFileChooser.FILES_ONLY);// show file's
															// path,only
															// filename
		JF.setMultiSelectionEnabled(false);// can't MultiSelect
		JF.setFileHidingEnabled(false);// show hiding file
		// show JFrame
		JB1.addActionListener(this);
		JB2.addActionListener(this);
		JB3.addActionListener(this);
		bpn = new BPN(2, 4, 1);
		// bpn.showkey();
		setVisible(true);
	}

	/*
	 * Action Listener Click Button(Load,Clear,Start) Load-->Read the File
	 * Clear-->Clear the Graphics Start-->implement Perceptron Algorithm
	 *********************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Graphics g = getGraphics();
		if (e.getSource() == JB1)// open JFileChooser
		{
			update(g);
			paint2D(g);
			bpn.newBPN();
			textEmpty();
			JT4.setText("");
			JT5.setText("");
			list.clear();
			int result = JF.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String data = JF.getSelectedFile().getPath();
				readFile(data);
				size = list.get(0).size() - 1;
				for (int i = 0; i < list.size(); i++)
					paintDot(g, i);
			}
		} else if (e.getSource() == JB2) {
			list.clear();
			update(g);
			paint2D(g);
		} else if (e.getSource() == JB3) {
			correcttimes = 0;
			textEmpty();
			update(g);
			paint2D(g);
			if (list.size() > times) {
				for (int j = 0; j < training; j++) {
					for (int i = 0; i < list.size() * 2 / 3; i++) {
						bpn.reset();
						bpn.datainput(list.get(i));
						bpn.active();
						bpn.check(list.get(i).get(size), list.get(i));
						// paintdot(g,i);
					}
					if (j % 3000 == 0)
						bpn.adjustLearning();
				}
				for (int i = 0; i < list.size() * 2 / 3; i++) {
					bpn.reset();
					bpn.datainput(list.get(i));
					bpn.active();
					bpn.showdetermine();
					correct(i);
				}
				JT4.setText("" + rate(list.size() * 2 / 3));
				correcttimes = 0;
				update(g);
				paint2D(g);
				for (int i = list.size() * 2 / 3; i < list.size(); i++) {
					bpn.reset();
					bpn.datainput(list.get(i));
					bpn.active();
					bpn.showdetermine();
					correct(i);
					paintOutputDot(g, i);
				}
				JT5.setText("" + rate(list.size() * 1 / 3));
				correcttimes = 0;
			} else {
				for (int j = 0; j < training; j++) {
					for (int i = 0; i < list.size(); i++) {
						bpn.reset();
						bpn.datainput(list.get(i));
						bpn.active();
						bpn.check(list.get(i).get(size), list.get(i));
					}
					if (j % 3000 == 0)
						bpn.adjustLearning();
				}
				for (int i = 0; i < list.size(); i++) {
					bpn.reset();
					bpn.datainput(list.get(i));
					bpn.active();
					bpn.showdetermine();
					correct(i);
				}
				JT4.setText("" + rate(list.size()));
				correcttimes = 0;
				for (int i = 0; i < list.size(); i++) {
					bpn.reset();
					bpn.datainput(list.get(i));
					bpn.active();
					bpn.showdetermine();
					correct(i);
					paintOutputDot(g, i);
				}
				JT5.setText("" + rate(list.size()));
				correcttimes = 0;
			}
			// paintline(g);
			list.clear();
		}
	}

	/*
	 * user input
	 ************/
	void textEmpty() {

		if (JT2.getText().isEmpty() == true) {
			training = 20000;
			JT2.setText("20000");
		} else {
			if (Integer.parseInt(JT2.getText()) <= 0)
				JT2.setText("20000");
			training = Integer.parseInt(JT2.getText());
		}
		if (JT3.getText().isEmpty() == true) {
			times = 100;
			JT3.setText("100");
		} else {
			if (Integer.parseInt(JT3.getText()) <= 0)
				JT3.setText("100");
			times = Integer.parseInt(JT3.getText());
		}
		if (JT6.getText().isEmpty() == true) {
			bpn.changeLayer(2);
			JT6.setText("2");
		} else {
			if (Integer.parseInt(JT6.getText()) <= 0)
				JT6.setText("2");
			bpn.changeLayer(Integer.parseInt(JT6.getText()));
		}
		if (JT7.getText().isEmpty() == true) {
			bpn.changeLayernum(4);
			JT7.setText("4");
		} else {
			if (Integer.parseInt(JT7.getText()) <= 0)
				JT7.setText("4");
			bpn.changeLayernum(Integer.parseInt(JT7.getText()));
		}
		if (JT8.getText().isEmpty() == true) {
			bpn.changeOutput(3);
			JT8.setText("3");
		} else {
			if (Integer.parseInt(JT8.getText()) > 3 || Integer.parseInt(JT8.getText()) <= 0)
				JT8.setText("3");
			bpn.changeOutput(Integer.parseInt(JT8.getText()));
		}
		if (JT1.getText().isEmpty() == true) {
			bpn.inputLearning(0.4);
			JT1.setText("0.4");
		} else {
			if (Double.parseDouble(JT1.getText()) < 0 || Double.parseDouble(JT1.getText()) > 1)
				JT1.setText("0.4");
			bpn.inputLearning(Double.parseDouble(JT1.getText()));
		}
	}

	/*
	 * Current rate(data determine)
	 ******************************/
	double rate(int i) {
		return (double) ((int) (10000 * (double) ((double) (correcttimes) / (double) (i))) / 100);
	}

	/*
	 * The number of correct check
	 *******************************/
	void correct(int i) {
		if (list.get(i).get(list.get(i).size() - 1) == bpn.determine)
			correcttimes++;
	}

	/*
	 * Decode Algorithm of each perceptron
	 *************************************/

	/*
	 * Read file
	 *********************/
	public void readFile(String data) {
		try {
			FileReader fr = new FileReader(data);
			BufferedReader br = new BufferedReader(fr);
			String line;
			double ttt, max = 0;
			ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
			while ((line = br.readLine()) != null) {
				String test[] = line.trim().split("\\s+");
				ttt = Double.parseDouble(test[test.length - 1]);
				/// System.out.println(ttt);
				if (ttt > max)
					max = ttt;
				ArrayList<Double> temp = new ArrayList<>();
				for (int i = 0; i < test.length; i++)
					temp.add(Double.parseDouble(test[i]));
				list.add(temp);
			}

			int x = list.size();
			for (int i = 0; i < x; i++) {
				int a = (int) (Math.random() * list.size());
				this.list.add(list.get(a));
				list.remove(a);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/*
	 * paint data distribution
	 ************************/
	void paintDot(Graphics g, int i) {
		if (list.get(i).get(size) == 7)
			g.setColor(Color.LIGHT_GRAY);
		else if (list.get(i).get(size) == 6)
			g.setColor(Color.ORANGE);
		else if (list.get(i).get(size) == 5)
			g.setColor(Color.PINK);
		else if (list.get(i).get(size) == 4)
			g.setColor(Color.MAGENTA);
		else if (list.get(i).get(size) == 3)
			g.setColor(Color.yellow);
		else if (list.get(i).get(size) == 2)
			g.setColor(Color.green);
		else if (list.get(i).get(size) == 1)
			g.setColor(Color.BLUE);
		else if (list.get(i).get(size) == 0)
			g.setColor(Color.RED);
		g.fillArc(x + 345 + (int) (35 * list.get(i).get(0)), y + 345 - (int) (35 * list.get(i).get(1)), 5, 5, 0, 360);
		g.setColor(Color.BLACK);
	}

	void paintOutputDot(Graphics g, int i) {
		if (bpn.determine == 7)
			g.setColor(Color.LIGHT_GRAY);
		else if (bpn.determine == 6)
			g.setColor(Color.ORANGE);
		else if (bpn.determine == 5)
			g.setColor(Color.PINK);
		else if (bpn.determine == 4)
			g.setColor(Color.MAGENTA);
		else if (bpn.determine == 3)
			g.setColor(Color.yellow);
		else if (bpn.determine == 2)
			g.setColor(Color.green);
		else if (bpn.determine == 1)
			g.setColor(Color.BLUE);
		else if (bpn.determine == 0)
			g.setColor(Color.RED);
		g.fillArc(x + 345 + (int) (35 * list.get(i).get(0)), y + 345 - (int) (35 * list.get(i).get(1)), 5, 5, 0, 360);
		g.setColor(Color.BLACK);
	}

	/*
	 * show output
	 *************/
	/**
	 * void paintline(Graphics g) { Graphics2D g2 = (Graphics2D) g; Shape nx =
	 * new Line2D.Double(x, y + 350 - 35 * ((5 * bpn.output.get(0).key[0] -
	 * bpn.output.get(0).threshold * bpn.output.get(0).thdata) /
	 * bpn.output.get(0).key[1]), x + 700, y + 350 - 35 * ((-5 *
	 * bpn.output.get(0).key[0] - bpn.output.get(0).threshold *
	 * bpn.output.get(0).thdata) / bpn.output.get(0).key[1]));
	 * g2.setColor(Color.RED); g2.draw(nx); if (bpn.output.size() > 1) { Shape
	 * ny = new Line2D.Double(x, y + 350 - 35 * ((5 * bpn.output.get(1).key[0] -
	 * bpn.output.get(1).threshold * bpn.output.get(1).thdata) /
	 * bpn.output.get(1).key[1]), x + 700, y + 350 - 35 ((-5 *
	 * bpn.output.get(1).key[0] - bpn.output.get(1).threshold *
	 * bpn.output.get(1).thdata) / bpn.output.get(1).key[1]));
	 * g2.setColor(Color.GREEN); g2.draw(ny); if (bpn.output.size() > 2) { Shape
	 * nz = new Line2D.Double(x, y + 350 - 35 * ((5 * bpn.output.get(2).key[0] -
	 * bpn.output.get(2).threshold * bpn.output.get(2).thdata) /
	 * bpn.output.get(2).key[1]), x + 700, y + 350 - 35 * ((-5 *
	 * bpn.output.get(2).key[0] - bpn.output.get(2).threshold *
	 * bpn.output.get(2).thdata) / bpn.output.get(2).key[1]));
	 * g2.setColor(Color.BLUE); g2.draw(nz); } } g2.setColor(Color.BLACK); }
	 **/

	/*
	 * paint the 2 Dimensional coordinates on JFrame
	 ***********************************************/
	void paint2D(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g.fillRect(x + 0, y + 0, x + 280, y + 650);
		g.setColor(Color.BLACK);
		Graphics2D g2d = (Graphics2D) g;
		Stroke st = g2d.getStroke();
		Stroke bs;
		// LINE_TYPE_DASHED
		bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 16, 4 }, 0);
		g2d.setStroke(bs);
		g.drawLine(x + 350, y + 0, x + 350, y + 700);
		g.drawLine(x + 0, y + 350, x + 700, y + 350);
		g2d.setStroke(st);
		g.drawLine(x + 0, y + 0, x + 0, y + 700);
		g.drawLine(x + 0, y + 700, x + 700, y + 700);
		g.drawLine(x + 0, y + 0, x + 700, y + 0);
		g.drawLine(x + 700, y + 0, x + 700, y + 700);
	}
}
