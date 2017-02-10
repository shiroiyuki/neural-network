package exercise2;

import java.util.ArrayList;

/**
 * BPN network hidden layer --> user define, output layer =1 number of output
 * --> user define(range 1~3) number of hidden --> user define
 * 
 * @author jack
 *
 ***************/
public class BPN {
	public ArrayList<ArrayList<Perceptron>> hidden = new ArrayList<ArrayList<Perceptron>>();
	ArrayList<Perceptron> output = new ArrayList<Perceptron>();
	private int hiddenlayer, hiddennum, outputnum;
	double determine;

	BPN() {

	}

	/*
	 * Initialize
	 *************/
	BPN(int hiddenlayer, int hiddennum, int outputnum) {
		this.hiddenlayer = hiddenlayer;
		this.hiddennum = hiddennum;
		this.outputnum = outputnum;
		determine = 0;
		for (int i = 0; i < hiddenlayer; i++) {
			ArrayList<Perceptron> layertemp = new ArrayList<Perceptron>();
			for (int j = 0; j < hiddennum; j++) {
				Perceptron temp = new Perceptron();
				layertemp.add(temp);
			}
			hidden.add(layertemp);
		}
		for (int i = 0; i < outputnum; i++) {
			Perceptron temp = new Perceptron();
			output.add(temp);
		}
	}

	/*
	 * learning rate
	 ***************/
	public void inputLearning(double learning) {
		for (int i = 0; i < hiddenlayer; i++)
			for (int j = 0; j < hiddennum; j++)
				hidden.get(i).get(j).inputLearning(learning);
		for (int i = 0; i < outputnum; i++)
			output.get(i).inputLearning(learning);
		System.out.println("learning = " + learning);
	}

	public void adjustLearning() {
		for (int i = 0; i < hiddenlayer; i++)
			for (int j = 0; j < hiddennum; j++) {
				hidden.get(i).get(j).learning += (0.3 - 0.05 * i);
				if (hidden.get(i).get(j).learning > 1)
					hidden.get(i).get(j).learning = (1 - 0.05 * i);
			}
		for (int i = 0; i < outputnum; i++) {
			output.get(i).learning -= 0.1;
			if (output.get(i).learning <= 0)
				output.get(i).learning = 0.05;
		}
	}

	/*
	 * show perceptron key
	 *********************/
	void showkey() {
		for (int i = 0; i < hiddenlayer; i++)
			for (int j = 0; j < hiddennum; j++)
				for (int k = 0; k < 2; k++)
					System.out.println("key = " + hidden.get(i).get(j).key[k]);
		for (int i = 0; i < outputnum; i++)
			for (int j = 0; j < 2; j++)
				System.out.println("key = " + output.get(i).key[j]);
	}

	/*
	 * check determine
	 ********************/
	void check(double expect, ArrayList<Double> list) {
		decode(outputnum);
		if (expect != determine)
			backPropagation(expect, list);
		// System.out.println("determine= "+output.get(0).determine);
	}

	private void decode(int num) {
		if (num == 1) {
			if (output.get(0).determine == 0) {
				determine = 0;
			} else {
				determine = 1;
			}
		} else if (num == 2) {
			if (output.get(0).determine == 0) {
				if (output.get(1).determine == 0) {
					determine = 0;
				} else {
					determine = 2;
				}
			} else {
				if (output.get(1).determine == 0) {
					determine = 1;
				} else {
					determine = 3;
				}
			}
		} else if (num == 3) {
			if (output.get(0).determine == 0) {
				if (output.get(1).determine == 0) {
					if (output.get(2).determine == 0) {
						determine = 0;
					} else {
						determine = 4;
					}
				} else {
					if (output.get(2).determine == 0) {
						determine = 2;
					} else {
						determine = 6;
					}
				}
			} else {
				if (output.get(1).determine == 0) {
					if (output.get(2).determine == 0) {
						determine = 1;
					} else {
						determine = 5;
					}
				} else {
					if (output.get(2).determine == 0) {
						determine = 3;
					} else {
						determine = 7;
					}
				}
			}
		}
	}

	/*
	 * output determine
	 ******************/
	public void active() {
		for (int i = 0; i < outputnum; i++) {
			if (output.get(i).output <= 0.5)
				output.get(i).determine = 0;
			else if (output.get(i).output > 0.5)
				output.get(i).determine = 1;
		}
	}

	/*
	 * show determine
	 *****************/
	void showdetermine() {
		decode(outputnum);
		System.out.println("determine= " + determine);
	}

	/*
	 * reset output & deviation
	 ***************************/
	public void reset() {
		for (int i = 1; i < hiddenlayer; i++)
			for (int j = 0; j < hiddennum; j++)
				hidden.get(i).get(j).reset();
		for (int i = 1; i < outputnum; i++)
			output.get(i).reset();
	}
	public void newBPN()
	{
		for (int i = 1; i < hiddenlayer; i++)
			for (int j = 0; j < hiddennum; j++)
				hidden.get(i).get(j).newPerceptron();;
		for (int i = 1; i < outputnum; i++)
			output.get(i).newPerceptron();;
	}
	/*
	 * read data stream
	 ******************/
	public void datainput(ArrayList<Double> list) {
		for (int i = 0; i < hiddennum; i++) {
			for (int j = 0; j < (list.size() - 1); j++) {
				hidden.get(0).get(i).count(list.get(j), j);
			}
			hidden.get(0).get(i).addThreshold();
		}
		if (hiddenlayer >= 2) {
			for (int i = 1; i < hiddenlayer; i++) {
				for (int j = 0; j < hiddennum; j++) {
					for (int k = 0; k < hiddennum; k++) {
						hidden.get(i).get(j).count(hidden.get(i - 1).get(k).output, k);
					}
					hidden.get(i).get(j).addThreshold();
				}
			}
		}
		for (int i = 0; i < outputnum; i++) {
			for (int j = 0; j < hiddennum; j++) {
				output.get(i).count(hidden.get(hiddenlayer - 1).get(j).output, j);
			}
			output.get(i).addThreshold();
		}
	}

	/*
	 * backPropagation
	 *****************/
	public void backPropagation(double ExpectedValue, ArrayList<Double> list) {
		double Expect[] = { 0, 0, 0 };
		if (ExpectedValue == 0) {
			Expect[0] = 0;
			Expect[1] = 0;
			Expect[2] = 0;

		} else if (ExpectedValue == 1) {
			Expect[0] = 1;
			Expect[1] = 0;
			Expect[2] = 0;

		} else if (ExpectedValue == 2) {
			Expect[0] = 0;
			Expect[1] = 1;
			Expect[2] = 0;

		} else if (ExpectedValue == 3) {
			Expect[0] = 1;
			Expect[1] = 1;
			Expect[2] = 0;

		} else if (ExpectedValue == 4) {
			Expect[0] = 0;
			Expect[1] = 0;
			Expect[2] = 1;

		} else if (ExpectedValue == 5) {
			Expect[0] = 1;
			Expect[1] = 0;
			Expect[2] = 1;

		} else if (ExpectedValue == 6) {
			Expect[0] = 0;
			Expect[1] = 1;
			Expect[2] = 1;
		} else if (ExpectedValue == 7) {
			Expect[0] = 1;
			Expect[1] = 1;
			Expect[2] = 1;
		}

		for (int i = 0; i < outputnum; i++)
			if (output.get(i).determine != Expect[i])
				output.get(i).deviation = (Expect[i] - output.get(i).output) * output.get(i).output
						* (1 - output.get(i).output);
		for (int i = 0; i < hiddennum; i++) {
			for (int j = 0; j < outputnum; j++) {
				hidden.get(hiddenlayer - 1).get(i).deviation += output.get(j).deviation * output.get(j).key[i];
			}
			hidden.get(hiddenlayer - 1).get(i).deviation *= hidden.get(hiddenlayer - 1).get(i).output
					* (1 - hidden.get(hiddenlayer - 1).get(i).output);
		}
		if (hiddenlayer >= 2) {
			for (int i = hiddenlayer - 2; i >= 0; i--)
				for (int j = 0; j < hiddennum; j++) {
					for (int k = 0; k < hiddennum; k++) {
						hidden.get(i).get(j).deviation += hidden.get(i + 1).get(k).deviation
								* hidden.get(i + 1).get(k).key[i];
					}
					hidden.get(i).get(j).deviation *= hidden.get(i).get(j).output * (1 - hidden.get(i).get(j).output);
				}
			// System.out.println("hello");
		}
		changeKey(list);
	}

	/*
	 * key change
	 ************/
	public void changeKey(ArrayList<Double> list) {
		for (int i = 0; i < outputnum; i++) {
			output.get(i).storeKey();
			for (int j = 0; j < hiddennum; j++)
				output.get(i).changeKey(hidden.get(hiddenlayer - 1).get(j).output, j);
			output.get(i).changeThreshold();
		}
		if (hiddenlayer >= 2) {
			for (int i = 1; i < hiddenlayer; i++)
				for (int j = 0; j < hiddennum; j++) {
					hidden.get(i).get(j).storeKey();
					for (int k = 0; k < hiddennum; k++) {
						hidden.get(i).get(j).changeKey(hidden.get(i - 1).get(k).output, k);
					}
					hidden.get(i).get(j).changeThreshold();
				}
			// System.out.println("BYE!");
		}
		for (int i = 0; i < hiddennum; i++) {
			hidden.get(0).get(i).storeKey();
			for (int j = 0; j < list.size() - 1; j++)
				hidden.get(0).get(i).changeKey(list.get(j), j);
			hidden.get(0).get(i).changeThreshold();
		}
	}

	/*
	 * layer increase or decrease
	 ****************************/
	public void changeLayer(int layer) {
		if (layer > hiddenlayer) {
			ArrayList<Perceptron> numtemp = new ArrayList<Perceptron>();
			for (int i = 0; i < (layer - hiddenlayer); i++) {
				for (int j = 0; j < hiddennum; j++) {
					Perceptron temp = new Perceptron();
					numtemp.add(temp);
				}
				hidden.add(numtemp);
			}
			System.out.println("layer bigger");
		} else if (layer < hiddenlayer) {
			for (int i = hiddenlayer - 1; i > (layer - 1); i--)
				hidden.remove(i);
			System.out.println("layer smaller");
		}
		hiddenlayer = layer;
		System.out.println("hiddenlayer= " + this.hiddenlayer);
	}

	/*
	 * layer num increase or decrease
	 ****************************/
	public void changeLayernum(int layernum) {
		if (layernum > hiddennum) {
			for (int i = 0; i < hiddenlayer; i++)
				for (int j = 0; j < (layernum - hiddennum); j++) {
					Perceptron temp = new Perceptron();
					hidden.get(i).add(temp);
				}
			System.out.println("layernum bigger");
		} else if (hiddennum > layernum) {
			for (int i = 0; i < hiddenlayer; i++)
				for (int j = hiddennum - 1; j > (layernum - 1); j--)
					hidden.get(i).remove(j);
			System.out.println("layernum smaller");
		}
		hiddennum = layernum;
		System.out.println("hiddennum= " + this.hiddennum);
	}

	/*
	 * output increase or decrease
	 ****************************/
	public void changeOutput(int outputnum) {
		if (outputnum > this.outputnum) {
			for (int i = 0; i < (outputnum - this.outputnum); i++) {
				Perceptron temp = new Perceptron();
				output.add(temp);
			}
			System.out.println("output bigger");
		} else if (this.outputnum > outputnum) {
			for (int i = this.outputnum - 1; i > outputnum; i--)
				output.remove(i);
			System.out.println("outputnum smaller");
		}
		this.outputnum = outputnum;
		System.out.println("outputnum= " + this.outputnum);
	}
}
