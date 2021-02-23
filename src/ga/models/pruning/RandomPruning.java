package ga.models.pruning;

import java.util.ArrayList;
import java.util.Random;

import ga.models.Chromossome;
import ga.models.Const;

public class RandomPruning {

	public ArrayList<Chromossome> execute(ArrayList<Chromossome> pop) {

		Random rand = new Random();
		ArrayList<Chromossome> newPop = new ArrayList<>();

		for (int i = 0; i < Const.N_INDIVIDUALS; i++) {
			int position = rand.nextInt(pop.size());
			Chromossome offspring = pop.remove(position);
			newPop.add(offspring);
		}

		return newPop;

	}
	
}
