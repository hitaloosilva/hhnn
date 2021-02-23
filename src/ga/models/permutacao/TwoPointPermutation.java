package ga.models.permutacao;

import java.util.Random;

import ga.models.Chromossome;
import ga.models.Couple;

public class TwoPointPermutation {
	
	public Couple execute(double[] valuesP1, double[] valuesP2) {
		
		Random rand = new Random();

		int size = valuesP1.length;
		double valuesCh1[] = new double[size];
		double valuesCh2[] = new double[size];

		int randNumber1 = 0;
		int randNumber2 = 0;

		do {
			randNumber1 = rand.nextInt(valuesP1.length+1);
			randNumber2 = rand.nextInt(valuesP1.length+1);
		} while (randNumber1 == randNumber2);

		int aux = randNumber1;

		if (randNumber1 > randNumber2) {
			randNumber1 = randNumber2;
			randNumber2 = aux;
		}

		for (int i = 0; i < valuesP1.length; i++) {
			if (i >= randNumber1 && i < randNumber2) {
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
