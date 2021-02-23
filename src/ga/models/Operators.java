package ga.models;

import java.util.ArrayList;
import java.util.Random;

import ga.models.permutacao.OnePointPermutation;
import ga.models.permutacao.TwoPointPermutation;
import ga.models.pruning.ElitistPruning;
import ga.models.pruning.RandomPruning;
import ga.models.pruning.RoulettePruning;
import ga.models.pruning.TournamentPruning;
import ga.models.selection.RouletteSelection;
import ga.models.selection.RandomSelection;
import ga.models.selection.TournamentSelection;

import org.apache.commons.math3.distribution.NormalDistribution;

import ga.util.Util;

import ga.problems.Problem;

public class Operators {

	// atributos
	private Problem problem;
	private Random rand;
	
	private RouletteSelection rouletteSelection;
	private RandomSelection randomSelection;
	private TournamentSelection tournamentSelection;
	
	private TwoPointPermutation twoPointPerm;
	private OnePointPermutation onePointPerm;
	
	private ElitistPruning elitPruning;
	private RoulettePruning roulettePruning;
	
	private Config config;

	// construtor
	public Operators (Problem _problem, Config _config) {
		setProblem(_problem);
		this.rand = new Random();
		
		this.config = _config;
		rouletteSelection = new RouletteSelection();
		randomSelection = new RandomSelection();
		tournamentSelection = new TournamentSelection();
		
		onePointPerm = new OnePointPermutation();
		twoPointPerm = new TwoPointPermutation();
		
		elitPruning = new ElitistPruning();
		roulettePruning = new RoulettePruning();
	}

	// responsável por escolher os individuos que permanecerão na próxima geração
	public ArrayList<Chromossome> prune(ArrayList<Chromossome> pop) {

		ArrayList<Chromossome> ret = pop;
		switch (config.getPruning()) {
			case Const.PRUNING_ELITISM:
				ret = elitPruning.execute(pop);
				break;
			case Const.PRUNING_ROULETTE:
				ret = roulettePruning.execute(pop);
				break;
		}

		return ret;

	}

	// mutação uniforme ou gaussiana
	public void mutate(ArrayList<Chromossome> pop) {

		double oldValue = 0;
		double newValue = 0;
		double sample = 0;
		int dim = 0;

		double max = getProblem().getMaxValue();
		double min = getProblem().getMinValue();

		int size = pop.size();

		for (int i = 0; i < size; i++) {
			if (Util.mutatTrigger()) {
				dim = rand.nextInt(getProblem().getDimension());

				switch (config.getMutation()) {
				// mutação uniforme
				case Const.MUTATION_UNIFORM:
					oldValue = pop.get(i).getValue(dim);
					newValue = rand.nextDouble() * (max - min) + min;
					break;
				// mutação gaussiana
				case Const.MUTATION_GAUSSIAN:
					oldValue = pop.get(i).getValue(dim);
					NormalDistribution nd = new NormalDistribution(oldValue, (max - min) * 0.1);
					sample = nd.sample();
					newValue = Util.clip(sample, max, min);
					break;
				}

				Chromossome old = new Chromossome(pop.get(i).getValues()); 
				pop.get(i).setValue(newValue, dim);
				
				// realizando busca gulosa. Só realiza mutação se o 
				// novo individuo for melhor que o individuo anterior
				if (getProblem().calculateFitness(old.getValues()) < getProblem().calculateFitness(pop.get(i).getValues())) {
					pop.get(i).setValue(oldValue, dim);
				}
				
			}
		}

	}

	// selecionar pais e realização mutação Const.N_PERMUT vezes
	public void selectAndPermutate(ArrayList<Chromossome> population) {

		ArrayList<Couple> filhos = new ArrayList<Couple>();
		
		for (int i = 0; i < Const.N_PERMUT; i++) {
			// checando se o operador de permutação será disparado
			if (Util.permutTrigger()) {
				filhos.add(permutate(population));
			}
		}
		
		// adicionando filhos à população atual
		for (int i = 0; i < filhos.size(); i++) {
			population.add(filhos.get(i).getFather());
			population.add(filhos.get(i).getMother());
		}

	}

	// avaliando população de acordo com problema
	public void evaluateFitness(ArrayList<Chromossome> population) {

		for (int i = 0; i < population.size(); i++) {
			double solution[] = population.get(i).getValues();
			double fitness = getProblem().calculateFitness(solution);
			population.get(i).setFitness(fitness);
		}

	}

	// função de inicialização: atribuindo valores 
	// entre [-min, max] a cada individuo
	public void initialize(ArrayList<Chromossome> population) {

		double min = getProblem().getMinValue();
		double max = getProblem().getMaxValue();

		for (int idx = 0; idx < Const.N_INDIVIDUALS; idx++) {
			population.add(new Chromossome(getProblem().getDimension()));

			for (int i = 0; i < getProblem().getDimension(); i++) {
				double value = rand.nextDouble() * (max - min) + min;
				population.get(idx).setValue(value, i);
			}
			
			// calculando fitness para o individuo inicializado
			double fit = getProblem().calculateFitness(population.get(idx).getValues());
			population.get(idx).setFitness(fit);
		}
	}

	// função responsável por realizar a permutação do casal
	private Couple permutate(ArrayList<Chromossome> pop) {

		// recuperando pais para realizar a permutação
		Couple casal = selection(pop);
		Couple filhos = null;
		
		double valuesP1[] = casal.getMother().getValues(); // pai 1
		double valuesP2[] = casal.getFather().getValues(); // pai 2

		switch (config.getPermutation()) {
		
			// realizando permutação em um ponto
			case Const.PERMUTATION_ONE_POINT:
				filhos = onePointPerm.execute(valuesP1, valuesP2);
				break;
			// realizando permutação em um ponto
			case Const.PERMUTATION_TWO_POINT:
				filhos = twoPointPerm.execute(valuesP1, valuesP2);
				break;
		}
		
		return filhos;

	}

	// selecionando casal de acordo com o tipo de seleção
	private Couple selection(ArrayList<Chromossome> pop) {

		Couple casal = null;

		switch (config.getSelection()) {
			// seleção em roleta
			case Const.SELECTION_ROULETTE:
				casal = rouletteSelection.execute(pop);
				break;
			// seleção aleatória
			case Const.SELECTION_RANDOM:
				casal = randomSelection.execute(pop);
				break;
			// seleção por torneio
			case Const.SELECTION_TOURNAMENT:
				casal = tournamentSelection.execute(pop);
				break;
		}

		return casal;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

}
