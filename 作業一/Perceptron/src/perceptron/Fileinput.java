package perceptron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/*******************
 * deal with input streams
 * 
 * @author jack
 *******************/
public class Fileinput {
	
	public void readfile(String data,ArrayList<ArrayList<Double>> listtemp )
	{
		    try {
		        FileReader fr=new FileReader(data);
		        BufferedReader br=new BufferedReader(fr);
		        String line;
		        ArrayList<ArrayList<Double>> list=new ArrayList<ArrayList<Double>>();
		        while ((line=br.readLine()) != null){
		          String test[]=line.trim().split("\\s+");
		          ArrayList<Double> temp = new ArrayList<>();
		          for(int i=0;i<test.length;i++)
		          {
		        	  temp.add(Double.parseDouble(test[i]));
		          }
		          list.add(temp);
		        }
		        int x=list.size();
		        for(int i=0;i<x;i++)
		        {
		        	int a=(int)(Math.random()*list.size());
		        	listtemp.add(list.get(a));
		        	list.remove(a);
		        }
		        br.close();
		        fr.close();
		        }
		      catch (IOException e) {System.out.println(e);}
		      } 
}
