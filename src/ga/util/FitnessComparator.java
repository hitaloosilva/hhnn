package ga.util;

import java.util.Comparator;

import ga.models.Chromossome;

public class FitnessComparator implements Comparator<Chromossome>{

	@Override
	public int compare(Chromossome chromo1, Chromossome chromo2) {

		if (chromo1.getFitness() < chromo2.getFitness()) {
			return -1;
		} else if (chromo1.getFitness() > chromo2.getFitness()) {
			return 1;
		} 
		
		return 0;
	}

}
