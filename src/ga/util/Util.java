package ga.util;

import java.util.ArrayList;
import java.util.Random;
import ga.models.Chromossome;
import ga.models.Const;

public class Util {

	public static boolean permutTrigger() {
		Random rand = new Random();
		double randDouble = rand.nextDouble();
		
		if (randDouble < Const.PERMUT_RATE)
			return true;
		else
			return false;
	}
	
	public static boolean mutatTrigger() {
		Random rand = new Random();
		double randDouble = rand.nextDouble();
		
		if (randDouble < Const.MUTAT_RATE)
			return true;
		else
			return false;
	}
	
	
	public static void printMatrix (double matrix[][]) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + ", ");
			}
			System.out.println();
		}
	}
	
	public static int executeRouletteIndex(ArrayList<Chromossome> solutionSet, double summatory){
		double rouletteValue = 0.0; 
		double partial = 0.0;
		int randomElement = 0;
		int i = 0;
		rouletteValue = summatory * Math.random();
		
		if (summatory != 0) {
			do {
				partial += 1 / solutionSet.get(i).getFitness();
				i++;
			} while (partial < rouletteValue);
			return i-1;
		} else {
			randomElement = (int) (Math.random() * solutionSet.size() );
			return randomElement;
		}
	}
	
	public static Chromossome executeRoulette(ArrayList<Chromossome> solutionSet, double summatory){
		int index = executeRouletteIndex(solutionSet, summatory);
		return solutionSet.get(index);
	}

	public static double clip(double sample, double max, double min) {
		if (sample > max) {
			sample = max;
		}
		if (sample < min) {
			sample = min;
		}
		return sample;
	}
}
