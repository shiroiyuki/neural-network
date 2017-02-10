package perceptron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;

/**************
 * GUI Design Algorithm implement
 * 
 * @author jack
 **************/
public class Gui extends JFrame implements ActionListener {
	/**********************
	 * Declare Variables
	 **********************/
	private JFileChooser JF = new JFileChooser();
	Perceptron element = new Perceptron();
	Perceptron element1 = new Perceptron();
	Perceptron element2 = new Perceptron();
	JTextField JT1, JT2, JT3, JT4, JT5, JT6, JT7, JT8;
	JButton JB1, JB2, JB3;
	int x = 330, y = 70;
	int output, correcttime, times, training;
	ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();

	/***********************
	 * GUI Design
	 ***********************/
	Gui() {
		super("Perceptron exercise1");
		setBackground(Color.LIGHT_GRAY);
		// set JFrame
		setSize(785, 551);
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

		JLabel JL6 = new JLabel("鍵結值");
		JL6.setFont(new Font("標楷體", Font.BOLD, 18));
		JL6.setBounds(15, 225, 71, 23);
		getContentPane().add(JL6);

		JT1 = new JTextField();
		JT1.setText("0.8");
		JT1.setBounds(136, 13, 160, 29);
		getContentPane().add(JT1);
		JT1.setColumns(10);

		JT2 = new JTextField();
		JT2.setText("100");
		JT2.setBounds(136, 52, 160, 29);
		getContentPane().add(JT2);
		JT2.setColumns(10);

		JT3 = new JTextField();
		JT3.setText("100");
		JT3.setBounds(136, 91, 160, 29);
		getContentPane().add(JT3);
		JT3.setColumns(10);

		JT4 = new JTextField();
		JT4.setBackground(Color.WHITE);
		JT4.setEditable(false);
		JT4.setBounds(136, 139, 160, 29);
		getContentPane().add(JT4);
		JT4.setColumns(10);

		JT5 = new JTextField();
		JT5.setBackground(Color.WHITE);
		JT5.setEditable(false);
		JT5.setBounds(136, 183, 160, 29);
		getContentPane().add(JT5);
		JT5.setColumns(10);

		JT6 = new JTextField();
		JT6.setBackground(Color.WHITE);
		JT6.setEditable(false);
		JT6.setBounds(136, 224, 160, 29);
		getContentPane().add(JT6);
		JT6.setColumns(10);

		JT7 = new JTextField();
		JT7.setBounds(136, 263, 160, 29);
		JT7.setEditable(false);
		getContentPane().add(JT7);
		JT7.setColumns(10);
		JT7.setBackground(Color.WHITE);

		JT8 = new JTextField();
		JT8.setBounds(136, 302, 160, 29);
		JT8.setEditable(false);
		getContentPane().add(JT8);
		JT8.setColumns(10);
		JT8.setBackground(Color.WHITE);

		JB1 = new JButton("Load");
		JB1.setBounds(15, 409, 111, 31);
		getContentPane().add(JB1);

		JB2 = new JButton("Clear");
		JB2.setBounds(141, 409, 111, 31);
		getContentPane().add(JB2);

		JB3 = new JButton("Start");
		JB3.setBounds(70, 465, 111, 31);
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
		setVisible(true);
	}

	/***********************
	 * Action Listener Click Button(Load,Clear,Start) Load-->Read the File
	 * Clear-->Clear the Graphics Start-->comply Perceptron Algorithm
	 ***********************/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Graphics g = getGraphics();
		if (e.getSource() == JB1)// open JFileChooser
		{
			list.clear();
			int result = JF.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String data = JF.getSelectedFile().getPath();
				Fileinput fi = new Fileinput();
				fi.readfile(data, list);
			}
		} else if (e.getSource() == JB2) {
			list.clear();
			update(g);
			paint2D(g);
			// if (JT1.getText().isEmpty() == true)
			// System.out.println("HHHHH" + JT1.getText());
		} else if (e.getSource() == JB3) {
			output = 0;
			correcttime = 0;
			if (JT1.getText().isEmpty() == true) {
				element.learning = 0.8;
				element1.learning = 0.8;
				element2.learning = 0.8;
			} else {
				element.learning = Double.parseDouble(JT1.getText());
				element1.learning = Double.parseDouble(JT1.getText());
				element2.learning = Double.parseDouble(JT1.getText());
			}
			if (JT2.getText().isEmpty() == true)
				training = 10000;
			else
				training = Integer.parseInt(JT2.getText());
			if (JT3.getText().isEmpty() == true)
				times = 100;
			else
				times = Integer.parseInt(JT3.getText());
			update(g);
			paint2D(g);
			if (list.size() > times) {
				for (int j = 0; j < training; j++)
					for (int i = 0; i < list.size() * 2 / 3; i++) {
						element.read(list.get(i));
						element1.read(list.get(i));
						element2.read(list.get(i));
						check(i);
					}
				for (int i = 0; i < list.size() * 2 / 3; i++) {
					element.read(list.get(i));
					element1.read(list.get(i));
					element2.read(list.get(i));
					determine();
					correct(i);
				}
				JT4.setText("" + rate(list.size()*2/3));
				correcttime = 0;
				for (int i = list.size() * 2 / 3; i < list.size(); i++) {
					element.read(list.get(i));
					element1.read(list.get(i));
					element2.read(list.get(i));
					determine();
					correct(i);
					paintdot(g, i);
				}
				JT5.setText("" + rate(list.size()*1/3));
				correcttime=0;
			} else {
				for (int j = 0; j < training; j++)
					for (int i = 0; i < list.size(); i++) {
						element.read(list.get(i));
						element1.read(list.get(i));
						element2.read(list.get(i));
						check(i);
					}
				for (int i = 0; i < list.size(); i++) {
					element.read(list.get(i));
					element1.read(list.get(i));
					element2.read(list.get(i));
					determine();
					correct(i);
				}
				JT4.setText(""+rate(list.size()));
				correcttime=0;
				for (int i = 0; i < list.size(); i++) {
					element.read(list.get(i));
					element1.read(list.get(i));
					element2.read(list.get(i));
					determine();
					correct(i);
					paintdot(g, i);
				}
				JT5.setText("" + rate(list.size()));
				correcttime=0;
			}
			paintline(g);
			JT6.setText("" + (double) ((int) (element.key[0] * 1000)) / 1000 + " "
					+ (double) ((int) (element.key[1] * 1000)) / 1000 + " "
					+ (double) ((int) (element.threshold * 1000)) / 1000);
			JT7.setText("" + (double) ((int) (element1.key[0] * 1000)) / 1000 + " "
					+ (double) ((int) (element1.key[1] * 1000)) / 1000 + " "
					+ (double) ((int) (element1.threshold * 1000)) / 1000);
			JT8.setText("" + (double) ((int) (element2.key[0] * 1000)) / 1000 + " "
					+ (double) ((int) (element2.key[1] * 1000)) / 1000 + " "
					+ (double) ((int) (element2.threshold * 1000)) / 1000);
			list.clear();
		}
	}

	/*****************
	 * show Perceptron judgment
	 *****************/
	void paintline(Graphics g) {
		Shape nx=new Line2D.Double(x + 200 - 5 * 40, y + 200 - 40 * ((5 * element.key[0] - element.threshold * element.thdata)/element.key[1]) , x + 200 + 5 * 40, y + 200 - 40 * ((-5 * element.key[0] - element.threshold * element.thdata) / element.key[1]));
		Shape ny=new Line2D.Double(x + 200 - 5 * 40, y + 200 - 40 * ((5 * element1.key[0] - element1.threshold * element1.thdata)/element1.key[1]) , x + 200 + 5 * 40, y + 200 - 40 * ((-5 * element1.key[0] - element1.threshold * element1.thdata) / element1.key[1]));
		Shape nz=new Line2D.Double(x + 200 - 5 * 40, y + 200 - 40 * ((5 * element2.key[0] - element2.threshold * element2.thdata)/element2.key[1]) , x + 200 + 5 * 40, y + 200 - 40 * ((-5 * element2.key[0] - element2.threshold * element2.thdata) / element2.key[1]));
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		g2.draw(nx);
		g2.setColor(Color.GREEN);
		g2.draw(ny);
		g2.setColor(Color.BLUE);
		g2.draw(nz);
		g2.setColor(Color.BLACK);
	}

	/****************
	 * Current rate(data determine)
	 ****************/
	double rate(int i) {
		return (double) ((int) (10000 * (double) (correcttime) / (double) (i)) / 100);
	}

	/****************
	 * The number of correct streams
	 */
	void correct(int i) {
		if (list.get(i).get(list.get(i).size() - 1) == output)
			correcttime++;
	}

	/****************
	 * Decode Algorithm of each perceptron
	 ****************/
	void determine() {
		if (element.output == 0) {
			if (element1.output == 0) {
				if (element2.output == 0)
					output = 0;
				else
					output = 1;
			} else {
				if (element2.output == 0)
					output = 2;
				else
					output = 3;
			}
		} else {
			if (element1.output == 0) {
				if (element2.output == 0)
					output = 4;
				else
					output = 5;
			} else {
				if (element2.output == 0)
					output = 6;
				else
					output = 7;
			}
		}
	}

	/****************
	 * coding
	 ****************/
	void check(int i) {
		if (list.get(i).get(list.get(i).size() - 1) == 0.0) {
			element.check(0);
			element1.check(0);
			element2.check(0);
		} else if (list.get(i).get(list.get(i).size() - 1) == 1.0) {
			element.check(0);
			element1.check(0);
			element2.check(1);
		} else if (list.get(i).get(list.get(i).size() - 1) == 2.0) {
			element.check(0);
			element1.check(1);
			element2.check(0);
		} else if (list.get(i).get(list.get(i).size() - 1) == 3.0) {
			element.check(0);
			element1.check(1);
			element2.check(1);
		} else if (list.get(i).get(list.get(i).size() - 1) == 4.0) {
			element.check(1);
			element1.check(0);
			element2.check(0);
		} else if (list.get(i).get(list.get(i).size() - 1) == 5.0) {
			element.check(1);
			element1.check(0);
			element2.check(1);
		} else if (list.get(i).get(list.get(i).size() - 1) == 6.0) {
			element.check(1);
			element1.check(1);
			element2.check(0);
		} else if (list.get(i).get(list.get(i).size() - 1) == 7.0) {
			element.check(1);
			element1.check(1);
			element2.check(1);
		}
	}

	/*****************
	 * show the input file
	 */
	void paintdot(Graphics g, int i) {
		if (element.datainput[element.xinput] == 7)
			g.setColor(Color.LIGHT_GRAY);
		else if (element.datainput[element.xinput] == 6)
			g.setColor(Color.ORANGE);
		else if (element.datainput[element.xinput] == 5)
			g.setColor(Color.PINK);
		else if (element.datainput[element.xinput] == 4)
			g.setColor(Color.MAGENTA);
		else if (element.datainput[element.xinput] == 3)
			g.setColor(Color.yellow);
		else if (element.datainput[element.xinput] == 2)
			g.setColor(Color.green);
		else if (element.datainput[element.xinput] == 1)
			g.setColor(Color.BLUE);
		else if (element.datainput[element.xinput] == 0)
			g.setColor(Color.RED);
		g.fillArc(x + 195 + (int) (40 * list.get(i).get(0)), y + 195 - (int) (40 * list.get(i).get(1)), 5, 5, 0, 360);
		g.setColor(Color.BLACK);
	}

	/***********
	 * paint the 2 Dimensional coordinates on JFrame
	 ***********/
	void paint2D(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g.fillRect(x + 0, y + 0, x + 70, y + 330);
		g.setColor(Color.BLACK);
		Graphics2D g2d = (Graphics2D) g;
		Stroke st = g2d.getStroke();
		Stroke bs;
		// LINE_TYPE_DASHED
		bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 16, 4 }, 0);
		g2d.setStroke(bs);
		g.drawLine(x + 200, y + 0, x + 200, y + 400);
		g.drawLine(x + 0, y + 200, x + 400, y + 200);
		g2d.setStroke(st);
		g.drawLine(x + 0, y + 0, x + 0, y + 400);
		g.drawLine(x + 0, y + 400, x + 400, y + 400);
		g.drawLine(x + 0, y + 0, x + 400, y + 0);
		g.drawLine(x + 400, y + 0, x + 400, y + 400);
	}
}