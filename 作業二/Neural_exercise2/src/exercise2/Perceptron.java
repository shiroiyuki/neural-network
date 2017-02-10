package exercise2;

import java.util.ArrayList;
import java.math.*;
import java.util.Random;

/**
 * Define Perceptron Can read data stream Have keys and threshold to active the
 * output signal(0 or 1)
 *
 * @author jack
 ******************************************************************************/
public class Perceptron {
	Random r = new Random();
	public double key[] = new double[50];
	public double prekey[] = new double[50];
	public double output, threshold, learning, deviation, determine;
	public int thdata;

	/*
	 * Initialize
	 ************/
	Perceptron() {
		for (int i = 0; i < key.length; i++) {
			key[i] = Math.rint((1 - 2 * r.nextDouble()) * 100000) / 100000;
			prekey[i] = key[i];
		}
		threshold = Math.rint((1 - 2 * r.nextDouble()) * 100000) / 100000;
		thdata = -1;
		output = 0;
		deviation = 0;
		learning = 0.8;
	}

	/*
	 * learning
	 **********/
	public void inputLearning(double learning) {
		this.learning = learning;
	}

	/*
	 * change perceptron key
	 ***********************/
	public void changeKey(double input, int index) {
		key[index] += learning * input * deviation;
	}

	/*
	 * store best key
	 ****************/
	public void storeKey() {
		prekey = key.clone();
	}

	/*
	 * change perceptron key (threshold)
	 **********************************/
	public void changeThreshold() {
		threshold += learning * deviation * thdata;
	}

	/*
	 * compute data
	 **************/
	public void count(double data, int i) {
		output += data * key[i];
	}

	/*
	 * Threshold add
	 ***************/
	public void addThreshold() {
		output += threshold * thdata;
		active();
	}

	/*
	 * pereptron active
	 ******************/
	private void active() {
		output = 1 / (1 + Math.exp(-output));
	}

	/*
	 * reset output & deviation
	 **************************/
	public void reset() {
		output = 0;
		deviation = 0;
	}

	public void newPerceptron() {
		for (int i = 0; i < key.length; i++) {
			key[i] = Math.rint((1 - 2 * r.nextDouble()) * 100000) / 100000;
			prekey[i] = key[i];
		}
		threshold = Math.rint((1 - 2 * r.nextDouble()) * 100000) / 100000;
		thdata = -1;
		output = 0;
		deviation = 0;
		learning = 0.8;
	}
}