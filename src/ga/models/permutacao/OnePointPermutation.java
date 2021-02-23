package ga.models.permutacao;

import java.util.Random;

import ga.models.Chromossome;
import ga.models.Couple;

public class OnePointPermutation {

	public Couple execute(double[] valuesP1, double[] valuesP2) {

		Random rand = new Random();
		int size = valuesP1.length;
		double valuesCh1[] = new double[size];
		double valuesCh2[] = new double[size];

		int randNumber = rand.nextInt(valuesP1.length+1);
		for (int i = 0; i < valuesP1.length; i++) {
			if (i > randNumber) {
				valuesCh1[i] = valuesP1[i];
				valuesCh2[i] = valuesP2[i];
			} else {
				valuesCh1[i] = valuesP2[i];
				valuesCh2[i] = valuesP1[i];
			}
		}
		
		Chromossome filho = new Chromossome(valuesCh1);
		Chromossome filha = new Chromossome(valuesCh2);
		
		return new Couple(filho, filha);
		
	}
	
}
