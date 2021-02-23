package ga.models.pruning;

import java.util.ArrayList;
import java.util.Collections;

import ga.models.Chromossome;
import ga.models.Const;
import ga.util.FitnessComparator;
import ga.util.Util;

public class ElitistPruning {

	public ArrayList<Chromossome> execute(ArrayList<Chromossome> pop) {

		FitnessComparator comparator = new FitnessComparator();
		
		ArrayList<Chromossome> newPop = new ArrayList<>();
		Collections.sort(pop, comparator);

		int numIndElitism = (int) Math.round(Const.ELITISM_RATE * Const.N_INDIVIDUALS); 

		for (int i = 0; i < numIndElitism; i++) {
			Chromossome aux = pop.remove(0);
			newPop.add(aux);
		}

		double fitnessSum = 0;
		for (int j = 0; j < pop.size(); j++) {
			if (pop.get(j).getFitness() == 0) {
				pop.get(j).setFitness(Const.ZERO);
			}
			fitnessSum += 1 / pop.get(j).getFitness();
		}

		for (int i = 0; i < Const.N_INDIVIDUALS - numIndElitism; i++) {
			int index = Util.executeRouletteIndex(pop, fitnessSum);
			fitnessSum -= 1 / pop.get(index).getFitness();
			newPop.add(pop.remove(index));
		}

		return newPop;
	}
	
}
