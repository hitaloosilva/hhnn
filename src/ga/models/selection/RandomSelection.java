package ga.models.selection;

import java.util.ArrayList;
import java.util.Random;

import ga.models.Chromossome;
import ga.models.Couple;

public class RandomSelection {

	public Couple execute (ArrayList<Chromossome> pop) {

		Random rand = new Random();
		
		int rand1 = 0;
		int rand2 = 0;
		
		while (rand1 == rand2) {
			rand1 = rand.nextInt(pop.size());
			rand2 = rand.nextInt(pop.size());
		}
		
		return new Couple(pop.get(rand1), pop.get(rand2));
	}
	
}
