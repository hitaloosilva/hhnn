package ga.models.pruning;

import java.util.ArrayList;
import java.util.Random;

import ga.models.Chromossome;
import ga.models.Const;

public class TournamentPruning {

	public ArrayList<Chromossome> execute(ArrayList<Chromossome> pop) {

		Random rand = new Random();
		ArrayList<Chromossome> newPop = new ArrayList<>();

		for (int i = 0; i < Const.N_INDIVIDUALS; i++) {

			int randNumber1 = 0;
			int randNumber2 = 0;

			while (randNumber1 == randNumber2) {
				randNumber1 = rand.nextInt(pop.size());
				randNumber2 = rand.nextInt(pop.size());
			}

			if (pop.get(randNumber1).getFitness() <= pop.get(randNumber2).getFitness()) {
				newPop.add(pop.remove(randNumber1));
			} else {
				newPop.add(pop.remove(randNumber2));
			}

		}

		return newPop;
	}
}
