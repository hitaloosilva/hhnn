package ga.models;

import java.util.ArrayList;
import java.util.Collections;

import ga.problems.Problem;
import ga.util.FitnessComparator;

public class GA {

	// atributos
	private Operators op;
	private FitnessComparator comparator;

	// construtor
	public GA (Problem prob, Config config) {
		op = new Operators(prob, config);
		comparator = new FitnessComparator();
	}

	// função responsável por executar o GA
	public void run() {
		ArrayList<Chromossome> population = new ArrayList<Chromossome>();

		double results[][] = new double[Const.N_ITERATIONS][Const.N_EXECUTIONS];
		double averageResults[] = new double[Const.N_ITERATIONS];

		for (int i = 0; i < Const.N_EXECUTIONS; i++) {
			population = new ArrayList<Chromossome>();
			// inicializando população
			op.initialize(population);

			for (int t = 0; t < Const.N_ITERATIONS; t++) {
				// selecionando e permutando pais
				op.selectAndPermutate(population);
				
				// mutando população
				op.mutate(population);
				
				// avaliando fitness da nova população
				op.evaluateFitness(population);
				
				// escolhendo individuos que irão para a próxima geração
				population = op.prune(population);

				// ordenando população de acordo com fitness e imprimindo
				// melhor resultado em contrado nesta iteração
				Collections.sort(population, comparator);
				results[t][i] = population.get(0).getFitness();
			}
			// imprimindo menor valor de cada execução (boxplot)
			//System.out.println(population.get(0).getFitness());
		}

		// somando resultados obtidos
		for (int i = 0; i < Const.N_EXECUTIONS; i++) {
			for (int j = 0; j < Const.N_ITERATIONS; j++) {
				averageResults[j] += results[j][i];
			}
		}

		// calculando média e imprimindo resultados no console
		for (int j = 0; j < averageResults.length; j++) {
			averageResults[j] = averageResults[j] / 30;
			System.out.println(averageResults[j]);
		}
		
	}

}
