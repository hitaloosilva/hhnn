package ga.models.selection;

import java.util.ArrayList;

import ga.models.Chromossome;
import ga.models.Const;
import ga.models.Couple;
import ga.util.Util;

public class RouletteSelection {
	
	public Couple execute(ArrayList<Chromossome> pop) {
		Couple casal = null;

		double fitSum = 0;
		for (int i = 0; i < pop.size(); i++) {
			if (pop.get(i).getFitness() == 0) {
				pop.get(i).setFitness(Const.ZERO);
			}
			fitSum += 1 / pop.get(i).getFitness();
		}

		@SuppressWarnings("unchecked")
		ArrayList<Chromossome> popClone = (ArrayList<Chromossome>) pop.clone();

		int indexPai1 = Util.executeRouletteIndex(popClone, fitSum);
		fitSum -= 1 / popClone.get(indexPai1).getFitness();
		Chromossome pai1 = popClone.remove(indexPai1);
		Chromossome pai2 = Util.executeRoulette(popClone, fitSum);

		if (pai1 != null && pai2 != null) {
			casal = new Couple(pai1, pai2);
		}

		return casal;
	}

}
