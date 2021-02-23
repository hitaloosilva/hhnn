package ga.models.pruning;

import ga.models.Chromossome;
import ga.models.Const;

import java.util.ArrayList;
import java.util.Collections;

import ga.util.FitnessComparator;
import ga.util.Util;

public class RoulettePruning {

	public ArrayList<Chromossome> execute (ArrayList<Chromossome> pop) {
		FitnessComparator comparator = new FitnessComparator();
		ArrayList<Chromossome> newPop = new ArrayList<>();
		
		double fitnessSum = 0;
		for (int j = 0; j < pop.size(); j++) {
			if (pop.get(j).getFitness() == 0) {
				pop.get(j).setFitness(Const.ZERO);
			}
			fitnessSum += 1 / pop.get(j).getFitness();
		}

		Collections.sort(pop, comparator);
		for (int i = 0; i < Const.N_INDIVIDUALS; i++) {
			int index = Util.executeRouletteIndex(pop, fitnessSum);
			fitnessSum -= 1 / pop.get(index).getFitness();
			newPop.add(pop.remove(index));
		}

		return newPop;
	}
	
}
