package ga;

import ga.models.Config;
import ga.models.Const;
import ga.models.GA;
import ga.problems.Griewank;
import ga.problems.Problem;
import ga.problems.Rosenbrock;
import ga.problems.Sphere;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// inicializando o problema com dimensão dimension
		int dimension = 30;
		Problem prob = new Griewank(dimension);
		
		// setando configuração: de acordo com documento
		// entre pelo Prof. Carmelo
		Config configA = new Config(Const.SELECTION_ROULETTE,
				Const.PERMUTATION_ONE_POINT, Const.MUTATION_UNIFORM,
				Const.PRUNING_ELITISM);
		
		Config configB = new Config(Const.SELECTION_TOURNAMENT,
				Const.PERMUTATION_ONE_POINT, Const.MUTATION_GAUSSIAN,
				Const.PRUNING_ELITISM);
		
		Config configC = new Config(Const.SELECTION_TOURNAMENT,
				Const.PERMUTATION_TWO_POINT, Const.MUTATION_GAUSSIAN,
				Const.PRUNING_ROULETTE);
		
		Config configD = new Config(Const.SELECTION_RANDOM,
				Const.PERMUTATION_TWO_POINT, Const.MUTATION_UNIFORM,
				Const.PRUNING_ELITISM);
		
		GA ga = new GA(prob, configD);
		
		// rodando
		ga.run();

	}

}
