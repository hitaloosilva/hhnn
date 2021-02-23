package ga.models.selection;

import java.util.ArrayList;
import java.util.Random;

import ga.models.Chromossome;
import ga.models.Couple;

public class TournamentSelection {

	public Couple execute(ArrayList<Chromossome> pop) {

		Random rand = new Random();

		int randNumber1 = 0;
		int randNumber2 = 0;

		while (randNumber1 == randNumber2) {
			randNumber1 = rand.nextInt(pop.size());
			randNumber2 = rand.nextInt(pop.size());
		}

		Chromossome father = null;
		Chromossome mother = null;
		
		if (pop.get(randNumber1).getFitness() <= pop.get(randNumber2).getFitness()) {
			father = pop.get(randNumber1);
		} else {
			father = pop.get(randNumber2);
		}
		
		randNumber1 = 0;
		randNumber2 = 0;
		
		while (randNumber1 == randNumber2) {
			randNumber1 = rand.nextInt(pop.size());
			randNumber2 = rand.nextInt(pop.size());
		}
		
		if (pop.get(randNumber1).getFitness() <= pop.get(randNumber2).getFitness()) {
			mother = pop.get(randNumber1);
		} else {
			mother = pop.get(randNumber2);
		}
		
		return new Couple(father, mother);

	}

}
