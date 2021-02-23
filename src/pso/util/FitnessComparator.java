package pso.util;

import java.util.Comparator;
import pso.models.Particle;

public class FitnessComparator implements Comparator<Particle>{

	@Override
	public int compare(Particle particle1, Particle particle2) {

		if (particle1.getFitnessPBest() < particle2.getFitnessPBest()) {
			return -1;
		} else if (particle1.getFitnessPBest() > particle2.getFitnessPBest()) {
			return 1;
		} 
		
		return 0;
	}

}
