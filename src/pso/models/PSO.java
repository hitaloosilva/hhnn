package pso.models;

import java.util.ArrayList;
import java.util.Collections;

import pso.problems.Problem;
import pso.util.Const;
import pso.util.FitnessComparator;

public class PSO {

	// atributos
	private Operator op;
	private FitnessComparator comparator;

	// construtor
	public PSO(Problem _problem, Config _config) {
		this.op = new Operator(_problem, _config);
		this.comparator = new FitnessComparator();
	}

	// método principal de execução
	public void run() {

		double results[][] = new double[Const.N_ITERATIONS][Const.N_EXECUTIONS];
		double averageResults[] = new double[Const.N_ITERATIONS];
		double fitnessBest = Double.MAX_VALUE;
		double averageFitness = 0;

		// executando 30 vezes
		for (int k = 0; k < Const.N_EXECUTIONS; k++) {
			System.out.println("Execucao " + k);
			// inicializando enxame15
			ArrayList<Particle> swarm = new ArrayList<Particle>();
			op.initialize(swarm);

			for (int i = 0; i < Const.N_ITERATIONS; i++) {
				System.out.println("\tIterations " + i);
				// calculando fitness
				op.calculateFitness(swarm);

				// atualizando memórias cognitivas e sociais
				op.updateLeaders(swarm);

				// atualizando velocidade
				op.updateVelocity(swarm, i);

				// atualizando posicao
				op.updatePosition(swarm);

				// imprimindo fitness do NBest
				fitnessBest = Double.MAX_VALUE;
				averageFitness = 0;
				for (int j = 0; j < swarm.size(); j++) {
					double fitnessAux = swarm.get(j).getFitnessNBest();
					if (fitnessAux < fitnessBest) {
						fitnessBest = fitnessAux;
					}
					averageFitness += fitnessAux;
				}

				averageFitness /= swarm.size();
				results[i][k] = fitnessBest;
				if(averageFitness < 20)
					break;
			}
			// imprimindo o melhor de cada execução
			System.out.println("Bst: " + fitnessBest);
		}

		// somando resultados obtidos
		for (int i = 0; i < Const.N_EXECUTIONS; i++) {
			for (int j = 0; j < Const.N_ITERATIONS; j++) {
				averageResults[j] += results[j][i];
			}
		}

		// calculando média e imprimindo resultados no console
		for (int j = 0; j < averageResults.length; j++) {
			averageResults[j] = averageResults[j] / Const.N_EXECUTIONS;
			System.out.println(averageResults[j]);
		}
	}

}
