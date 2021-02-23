package pso.models;

import java.util.ArrayList;
import java.util.Random;

import pso.problems.Problem;
import pso.util.Const;

public class Operator {

	// atributos
	private Problem problem;
	private Random rand;
	private Config config;

	// construtores
	public Operator(Problem _problem, Config _config) {
		this.problem = _problem;
		this.rand = new Random();
		this.config = _config;
	}

	// atualizando líderes..
	public void updateLeaders(ArrayList<Particle> swarm) {
		updateCognitiveMemory(swarm);
		updateSocialMemory(swarm);
	}

	// atualização da memória social (NBest) de todas as partículas (swarm)
	public void updateSocialMemory(ArrayList<Particle> swarm) {

		switch (config.getTopology()) {
		case Const.T_GLOBAL:
			updateGlobalSocialMemory(swarm);
			break;
		case Const.T_LOCAL:
			updateLocalSocialMemory(swarm);
			break;
		}
	}

	private void updateLocalSocialMemory(ArrayList<Particle> swarm) {

		for (int i = 0; i < swarm.size(); i++) {
			if (i == 0) {
				if (swarm.get(swarm.size() - 1).getFitnessPBest() < swarm.get(
						i + 1).getFitnessPBest()) {
					swarm.get(i).setFitnessNBest(
							swarm.get(swarm.size() - 1).getFitnessPBest());
					for (int j = 0; j < getProblem().getDimension(); j++)
						swarm.get(i).setnBest(
								swarm.get(swarm.size() - 1).getpBest(j), j);
				} else {
					swarm.get(i).setFitnessNBest(
							swarm.get(i + 1).getFitnessPBest());
					for (int j = 0; j < getProblem().getDimension(); j++)
						swarm.get(i).setnBest(swarm.get(i + 1).getpBest(j), j);

				}
			} else if (i == swarm.size() - 1) {
				if (swarm.get(0).getFitnessPBest() < swarm.get(i - 1)
						.getFitnessPBest()) {
					swarm.get(i)
							.setFitnessNBest(swarm.get(0).getFitnessPBest());
					for (int j = 0; j < getProblem().getDimension(); j++)
						swarm.get(i).setnBest(swarm.get(0).getpBest(j), j);
				} else {
					swarm.get(i).setFitnessNBest(
							swarm.get(i - 1).getFitnessPBest());
					for (int j = 0; j < getProblem().getDimension(); j++)
						swarm.get(i).setnBest(swarm.get(i - 1).getpBest(j), j);
				}
			} else {
				double fitnessAnterior = swarm.get(i - 1).getFitnessPBest();
				double fitnessProximo = swarm.get(i + 1).getFitnessPBest();

				if (fitnessAnterior < fitnessProximo) {
					swarm.get(i).setFitnessNBest(fitnessAnterior);
					for (int j = 0; j < getProblem().getDimension(); j++)
						swarm.get(i).setnBest(swarm.get(i - 1).getpBest(j), j);
				} else {
					swarm.get(i).setFitnessNBest(fitnessProximo);
					for (int j = 0; j < getProblem().getDimension(); j++)
						swarm.get(i).setnBest(swarm.get(i + 1).getpBest(j), j);

				}
			}
		}

	}

	private void updateGlobalSocialMemory(ArrayList<Particle> swarm) {
		int index = -1;
		double bestFitness = Double.MAX_VALUE;

		for (int i = 0; i < swarm.size(); i++) {
			double fitnessI = swarm.get(i).getFitnessPBest();

			if (fitnessI < bestFitness) {
				bestFitness = fitnessI;
				index = i;
			}
		}

		double pBestGeral[] = swarm.get(index).getpBest();

		for (int i = 0; i < swarm.size(); i++) {
			swarm.get(i).setFitnessNBest(bestFitness);
			for (int j = 0; j < getProblem().getDimension(); j++)
				swarm.get(i).setnBest(pBestGeral[j], j);
		}
	}

	// atualização memória cognitiva (PBest) de todas as partículas (swarm)
	public void updateCognitiveMemory(ArrayList<Particle> swarm) {
		for (int i = 0; i < swarm.size(); i++) {
			double currentFitness = swarm.get(i).getFitness();
			if (currentFitness < swarm.get(i).getFitnessPBest()) {
				swarm.get(i).setFitnessPBest(currentFitness);
				for (int j = 0; j < getProblem().getDimension(); j++) {
					swarm.get(i).setpBest(swarm.get(i).getPosition(j), j);
				}
			}
		}
	}

	// função resposável por atualizar velocidade de todas as partículas (swarm)
	public void updateVelocity(ArrayList<Particle> swarm, int iteration) {
		double omega = getOmega(iteration);

		for (int i = 0; i < swarm.size(); i++) {
			for (int k = 0; k < getProblem().getDimension(); k++) {
				double newVelocity = Const.X * (
						swarm.get(i).getVelocity(k)
						+ Const.C1
						* rand.nextDouble()
						* (swarm.get(i).getpBest(k) - swarm.get(i).getPosition(
								k))
						+ Const.C2
						* rand.nextDouble()
						* (swarm.get(i).getnBest(k) - swarm.get(i).getPosition(
								k)));

				newVelocity = clipVelocity(newVelocity, k);
				swarm.get(i).setVelocity(newVelocity, k);
			}
		}
	}

	// limitando velocidade
	private double clipVelocity(double newVelocity, int dimension) {

		if (newVelocity > getProblem().getMaxValue()[dimension] / 2) {
			newVelocity = getProblem().getMaxValue()[dimension] / 2;
		}

		if (newVelocity < getProblem().getMinValue()[dimension] / 2) {
			newVelocity = getProblem().getMinValue()[dimension] / 2;
		}

		return newVelocity;
	}

	// decaimento linear da aceleração inercial
	private double getOmega(int iteration) {

		return iteration * (Const.MIN_W - Const.MAX_W) / Const.N_ITERATIONS
				+ Const.MAX_W;
	}

	// função responsável por atualizar posiçao de todas as partículas (swarm)
	public void updatePosition(ArrayList<Particle> swarm) {
		double max;
		double min;

		double value = -1;
		for (int i = 0; i < swarm.size(); i++) {
			for (int j = 0; j < getProblem().getDimension(); j++) {
				max = getProblem().getMaxValue()[j];
				min = getProblem().getMinValue()[j];

				value = swarm.get(i).getPosition(j)
						+ swarm.get(i).getVelocity(j);

				if (value > max) {
					value = max;
					swarm.get(i).setVelocity(
							swarm.get(i).getVelocity(j) * (-1), j);
				}

				if (value < min) {
					value = min;
					swarm.get(i).setVelocity(
							swarm.get(i).getVelocity(j) * (-1), j);
				}

				swarm.get(i).setPosition(value, j);
			}
		}
	}

	// calculando fitness de cada partícula
	public void calculateFitness(ArrayList<Particle> swarm) {

		for (int i = 0; i < swarm.size(); i++) {
			double fitness = getProblem().calculateFitness(
					swarm.get(i).getPosition());
			swarm.get(i).setFitness(fitness);
		}

	}

	// responsável por iniciar as posições entre min e max do problema
	public void initialize(ArrayList<Particle> swarm) {

		int dim = getProblem().getDimension();
		double min;
		double max;

		for (int i = 0; i < Const.N_SWARM; i++) {
			swarm.add(new Particle(dim));
			for (int j = 0; j < dim; j++) {
				min = getProblem().getMinValue()[j];
				max = getProblem().getMaxValue()[j];
				double value = rand.nextDouble() * (max - min) + min;
				swarm.get(i).setPosition(value, j);
				swarm.get(i).setpBest(value, j);
				swarm.get(i).setVelocity(0, j);
			}
		}

	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

}