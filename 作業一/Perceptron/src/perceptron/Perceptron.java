package perceptron;

import java.util.ArrayList;
import java.util.Random;
/************
 * Define Perceptron
 * Can read data stream
 * Have keys and threshold to active the output signal(0 or 1)
 *
 * @author jack
 ************/
public class Perceptron {
	Random r = new Random();
	public double datainput[] = new double[50];
	public double key[] = new double[50];
	public double prekey[] = new double[50];
	public double output, threshold, learning;
	public int xinput;
	public int thdata;
	/*********************
	 * Initialize
	 *********************/
	Perceptron() {
		for (int i = 0; i < key.length; i++) {
			key[i] = Math.rint((1 - 2 * r.nextDouble()) * 1000) / 1000;
			prekey[i] = key[i];
		}
		threshold = Math.rint((1 - 2 * r.nextDouble()) * 1000) / 1000;
		learning = 0.8;
		threshold=-1;
		thdata=-1;
		output = 0;
	}
	/***********
	 * check the signal is correct
	 ***********/
	public void check(double num)
	{
		int check=(int)num;
		if (output !=check ) {
			if(output==1){
			for (int i = 0; i < xinput; i++)
				key[i] -= learning * datainput[i];
			threshold -= learning * thdata;}
			else
			{
				for (int i = 0; i < xinput; i++)
					key[i] += learning * datainput[i];
				threshold += learning * thdata;}
		} else
			prekey = key.clone();
	}
	/**************
	 * determine the signal
	 **************/
	private void active() {
		if (output > 0)
			output = 1;
		else
			output = 0;
	}
	/******************
	 * datainput stream
	 ******************/
	public void read(ArrayList<Double> list) {
		reset();
		for (int i = 0; i < list.size(); i++){
			datainput[i] = list.get(i);
		}
		xinput = list.size() - 1;
		data();
		active();
	}
	/******************
	 * add each data
	 ******************/
	private void data() {
		for (int i = 0; i < xinput; i++)
			output += datainput[i] * key[i];
		output += threshold*thdata;
	}
	/***************
	 * output reset*
	 ***************/
	public void reset() {
		output = 0;
	}
}
