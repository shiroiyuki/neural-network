package HopField;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * HopField Algorithm
 * 
 * @author jack
 *
 */
public class GUI extends JFrame implements ActionListener {
	Toolkit tk = Toolkit.getDefaultToolkit();
	Random r = new Random();
	int screenSizeX = (int) tk.getScreenSize().getWidth();
	int screenSizeY = (int) tk.getScreenSize().getHeight();
	JButton LoadTraining;
	JButton LoadTesting;
	JButton LoadHopFieldTraining;
	JButton LoadHopFieldTesting;
	JButton Testing;
	JButton TrainNext;
	JButton TrainBack;
	JButton TestNext;
	JButton TestBack;
	JButton Exit;
	JTextField JT[][] = new JTextField[12][10];
	JTextField JT1[][] = new JTextField[12][10];
	JTextField JT2[][] = new JTextField[12][10];
	JLabel DataState;
	JLabel TrainingDataPath;
	JLabel TestingDataPath;
	JFileChooser JF;
	ArrayList<int[]> TrainingData;
	ArrayList<int[]> TestingData;
	ArrayList<int[]> TestingDataStore;
	int[][] key = new int[120][120];
	int[] threshold = new int[120];
	int col, row, testNum, trainNum;

	public GUI() {
		super("HopField");
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(screenSizeX, screenSizeY);
		TrainingData = new ArrayList<int[]>();
		TestingData = new ArrayList<int[]>();
		TestingDataStore = new ArrayList<int[]>();
		LoadTraining = new JButton("基本題訓練");
		LoadTesting = new JButton("基本題測試");
		LoadHopFieldTraining = new JButton("HopField訓練");
		LoadHopFieldTesting = new JButton("HopField測試");
		Testing = new JButton("回想");
		TrainNext = new JButton("下一個訓練資料");
		TrainBack = new JButton("上一個訓練資料");
		TestNext = new JButton("下一個測試資料");
		TestBack = new JButton("上一個測試資料");
		Exit = new JButton("結束");
		DataState = new JLabel();
		TrainingDataPath = new JLabel();
		TestingDataPath = new JLabel();
		JF = new JFileChooser();
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 10; j++) {
				JT[i][j] = new JTextField();
				JT[i][j].setBounds(screenSizeX / 3 + 30 * j, 80 + 30 * i, 30, 30);
				JT[i][j].setEnabled(false);
				add(JT[i][j]);
			}
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 10; j++) {
				JT1[i][j] = new JTextField();
				JT1[i][j].setBounds(screenSizeX / 3 + 400 + 30 * j, 80 + 30 * i, 30, 30);
				JT1[i][j].setEnabled(false);
				add(JT1[i][j]);
			}
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 10; j++) {
				JT2[i][j] = new JTextField();
				JT2[i][j].setBounds(screenSizeX / 3 + 400 + 30 * j, 400 + 80 + 30 * i, 30, 30);
				JT2[i][j].setEnabled(false);
				add(JT2[i][j]);
			}
		DataState.setBounds(screenSizeX / 13, screenSizeY / 5, 140, 40);
		TrainingDataPath.setBounds(screenSizeX / 13, screenSizeY / 5 + 50, 150, 40);
		TestingDataPath.setBounds(screenSizeX / 13, screenSizeY / 5 + 100, 150, 50);

		LoadTraining.setBounds(screenSizeX / 13, screenSizeY / 5 * 3, 150, 40);
		LoadTesting.setBounds(screenSizeX / 13 + 160, screenSizeY / 5 * 3, 150, 40);

		LoadHopFieldTraining.setBounds(screenSizeX / 13, screenSizeY / 5 * 3 + 50, 150, 40);
		LoadHopFieldTesting.setBounds(screenSizeX / 13 + 160, screenSizeY / 5 * 3 + 50, 150, 40);

		TrainBack.setBounds(screenSizeX / 13, screenSizeY / 5 * 3 + 100, 150, 40);
		TrainNext.setBounds(screenSizeX / 13 + 160, screenSizeY / 5 * 3 + 100, 150, 40);

		TestBack.setBounds(screenSizeX / 13, screenSizeY / 5 * 3 + 150, 150, 40);
		TestNext.setBounds(screenSizeX / 13 + 160, screenSizeY / 5 * 3 + 150, 150, 40);

		Testing.setBounds(screenSizeX / 13, screenSizeY / 5 * 3 + 200, 150, 40);
		Exit.setBounds(screenSizeX / 13 + 160, screenSizeY / 5 * 3 + 200, 150, 40);
		add(LoadTraining);
		add(LoadTesting);
		add(LoadHopFieldTraining);
		add(LoadHopFieldTesting);
		add(Testing);
		add(Exit);
		add(TrainNext);
		add(TrainBack);
		add(TestNext);
		add(TestBack);
		add(DataState);
		add(TrainingDataPath);
		add(TestingDataPath);
		JF.setDialogTitle("Choose file:");// Title
		JF.setCurrentDirectory(new File(System.getProperty("user.dir")));
		JF.setFileSelectionMode(JFileChooser.FILES_ONLY);
		JF.setMultiSelectionEnabled(false);// can't MultiSelect
		JF.setFileHidingEnabled(false);// show hiding file
		LoadTraining.addActionListener(this);
		LoadTesting.addActionListener(this);
		LoadHopFieldTraining.addActionListener(this);
		LoadHopFieldTesting.addActionListener(this);
		Testing.addActionListener(this);
		Exit.addActionListener(this);
		TrainNext.addActionListener(this);
		TrainBack.addActionListener(this);
		TestNext.addActionListener(this);
		TestBack.addActionListener(this);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == LoadTraining) {
			ran();
			int result = JF.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String data = JF.getSelectedFile().getPath();
				TrainingDataPath.setText(JF.getSelectedFile().getName());
				col = 12;
				row = 10;
				testNum = 0;
				trainNum = 0;
				TrainingData.clear();
				TestingData.clear();
				TestingDataStore.clear();
				reset();
				readTrainingFile(data, col, row);
				trainingModel();
				DataState.setText("訓練完成");
				showData();
				//ran();
			}
		}
		if (e.getSource() == LoadTesting) {
			int result = JF.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String data = JF.getSelectedFile().getPath();
				TestingDataPath.setText(JF.getSelectedFile().getName());
				TestingData.clear();
				TestingDataStore.clear();
				readTestingFile(data, col, row);
				showResult();
			}
		}
		if (e.getSource() == LoadHopFieldTraining) {
			int result = JF.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String data = JF.getSelectedFile().getPath();
				TrainingDataPath.setText(JF.getSelectedFile().getName());
				col = 10;
				row = 10;
				testNum = 0;
				trainNum = 0;
				TrainingData.clear();
				TestingData.clear();
				TestingDataStore.clear();
				reset();
				readTrainingFile(data, col, row);
				trainingModel();
				showData();
			}
		}
		if (e.getSource() == LoadHopFieldTesting) {
			int result = JF.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String data = JF.getSelectedFile().getPath();
				TestingData.clear();
				TestingDataStore.clear();
				readTestingFile(data, col, row);
				showResult();
			}
		}
		if (e.getSource() == Testing) {
			test();
			showResult();
			DataState.setText("第 " + (testNum + 1) + "筆資料回想完成");
		}
		if (e.getSource() == TrainNext) {
			trainNum++;
			if (trainNum >= TrainingData.size()) {
				DataState.setText("資料已是最後一筆");
				trainNum--;
			}
			showData();
			showResult();
		}
		if (e.getSource() == TrainBack) {
			trainNum--;
			if (trainNum < 0) {
				DataState.setText("資料已是第一筆");
				trainNum++;
			}
			showData();
			showResult();
		}
		if (e.getSource() == TestNext) {
			testNum++;
			if (testNum >= TestingData.size()) {
				DataState.setText("資料已是最後一筆");
				testNum--;
			}
			showData();
			showResult();
		}
		if (e.getSource() == TestBack) {
			testNum--;
			if (testNum < 0) {
				DataState.setText("資料已是第一筆");
				testNum++;
			}
			showData();
			showResult();
		}
		if (e.getSource() == Exit) {
			this.dispose();
		}
	}

	public void reset() {
		for (int i = 0; i < 120; i++)
			for (int j = 0; j < 120; j++)
				key[i][j] = 0;

		for (int i = 0; i < 120; i++)
			threshold[i] = 0;

		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 10; j++) {
				JT2[i][j].setBackground(Color.white);
				JT1[i][j].setBackground(Color.white);
				JT[i][j].setBackground(Color.white);
			}
	}

	public void showResult() {
		for (int i = 0; i < col; i++)
			for (int j = 0; j < row; j++)
				if (TestingDataStore.get(testNum)[row * i + j] == 1)
					JT1[i][j].setBackground(Color.black);
				else if (TestingDataStore.get(testNum)[row * i + j] == -1)
					JT1[i][j].setBackground(Color.red);
		for (int i = 0; i < col; i++)
			for (int j = 0; j < row; j++)
				if (TestingData.get(testNum)[row * i + j] == 1)
					JT2[i][j].setBackground(Color.black);
				else if (TestingData.get(testNum)[row * i + j] == -1)
					JT2[i][j].setBackground(Color.red);
	}

	public void showData() {
		for (int i = 0; i < col; i++)
			for (int j = 0; j < row; j++)
				if (TrainingData.get(trainNum)[row * i + j] == 1)
					JT[i][j].setBackground(Color.black);
				else if (TrainingData.get(trainNum)[row * i + j] == -1)
					JT[i][j].setBackground(Color.red);
	}

	public void ran() {
		int a = 0;
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				a = r.nextInt(4);
				if (a == 1)
					if (TrainingData.get(2)[row * i + j] == 1)
						System.out.print(" ");
					else
						System.out.print(1);
				else
					if (TrainingData.get(2)[row * i + j] == 1)
						System.out.print(1);
					else
						System.out.print(" ");
			}
			System.out.println();
		}
	}

	public void test() {
		int a;
		for (int i = 0; i < col * row; i++) {
			a = 0;
			for (int j = 0; j < col * row; j++)
				a += TestingData.get(testNum)[j] * key[i][j];
			if (a > threshold[i])
				a = 1;
			else if (a < threshold[i])
				a = -1;
			TestingData.get(testNum)[i] = a;
		}
	}

	public void trainingModel() {
		for (int i = 0; i < TrainingData.size(); i++)
			for (int j = 0; j < (col * row); j++)
				for (int k = 0; k < (row * col); k++)
					key[j][k] += (TrainingData.get(i)[j] * TrainingData.get(i)[k]);

		for (int i = 0; i < (col * row); i++)
			key[i][i] = 0;

		/*
		 * for (int i = 0; i < (col * row); i++) { for (int j = 0; j < (col *
		 * row); j++) { threshold[i] += key[i][j]; } }
		 */

	}

	public void readTrainingFile(String data, int col, int row) {
		try {
			FileReader fr = new FileReader(data);
			BufferedReader br = new BufferedReader(fr);
			String line;
			int i = 0;
			int[] temp = new int[col * row];
			for (int k = 0; k < col * row; k++)
				temp[k] = -1;
			while ((line = br.readLine()) != null) {
				if (i % col == 0 && i != 0) {
					i %= col;
					i--;
					TrainingData.add(temp);
					temp = new int[col * row];
					for (int k = 0; k < col * row; k++)
						temp[k] = -1;
				}
				for (int j = 0; j < line.length(); j++) {
					if (line.charAt(j) != '1')
						temp[row * i + j] = -1;
					else
						temp[row * i + j] = 1;
				}
				i++;
			}
			System.out.println(TrainingData.size());
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void readTestingFile(String data, int col, int row) {
		try {
			FileReader fr = new FileReader(data);
			BufferedReader br = new BufferedReader(fr);
			String line;
			int i = 0;
			int[] temp = new int[col * row];
			int[] temp1 = new int[col * row];
			for (int k = 0; k < col * row; k++) {
				temp[k] = -1;
				temp1[k] = -1;
			}
			while ((line = br.readLine()) != null) {
				if (i % col == 0 && i != 0) {
					i %= col;
					i--;
					TestingData.add(temp);
					TestingDataStore.add(temp1);
					temp = new int[col * row];
					temp1 = new int[col * row];
					for (int k = 0; k < col * row; k++) {
						temp[k] = -1;
						temp1[k] = -1;
					}
				}
				for (int j = 0; j < line.length(); j++) {
					if (line.charAt(j) != '1') {
						temp[row * i + j] = -1;
						temp1[row * i + j] = -1;
					} else {
						temp[row * i + j] = 1;
						temp1[row * i + j] = 1;
					}
				}
				i++;
			}
			System.out.println(TestingDataStore.size());
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}