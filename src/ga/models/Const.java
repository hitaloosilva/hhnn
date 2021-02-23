package ga.models;

public class Const {

	public static final int N_EXECUTIONS = 30;
	public static final int N_INDIVIDUALS = 30;
	public static final int N_ITERATIONS = 100000;
	public static final double PERMUT_RATE = 0.9;
	public static final double MUTAT_RATE = 0.05;
	public static final int N_PERMUT = 10;
	
	public static final double ELITISM_RATE = 0.1;
	
	public static final int SELECTION_ROULETTE = 1;
	public static final int SELECTION_RANDOM = 2;
	public static final int SELECTION_TOURNAMENT = 3;
	
	public static final int PERMUTATION_ONE_POINT = 4;
	public static final int PERMUTATION_TWO_POINT = 5;
	
	public static final int MUTATION_UNIFORM = 6;
	public static final int MUTATION_GAUSSIAN = 7;
	
	public static final int PRUNING_ELITISM = 8;
	public static final int PRUNING_ROULETTE = 9;
	
	public static final double ZERO = 10E-34;

}
