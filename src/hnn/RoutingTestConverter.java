package hnn;

import hnn.models.HNN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RoutingTestConverter {
	List<RoutingTest> data;
	double size;

	public RoutingTestConverter(List<RoutingTest> data) {
		this.data = data;
		size = data.size();
	}

	public StaticalRoutingTest convert() {
		StaticalRoutingTest staticalRoutingTest = new StaticalRoutingTest();
		double sumTime = 0;
		double meanTime = 0;
		double sdTime = 0;
		double sumIteration = 0;
		double meanIteration = 0;
		double sdIteration = 0;
		Map<HNN, List<Execution>> executionsMap = new HashMap<HNN, List<Execution>>();
		Execution execution = null;
		for (RoutingTest test : data) {
			// System.out.println(test.getFinishTime() - test.getStartTime());
			execution = test.getExecution();
			sumTime += (test.getFinishTime() - test.getStartTime());
			sumIteration += execution.getIterations();
			fillExecutionsMap(executionsMap, execution);
		}
		meanTime = sumTime / size;
		meanIteration = sumIteration / size;

		for (RoutingTest test : data) {
			sdTime += (meanTime - (test.getFinishTime() - test.getStartTime()))
					* (meanTime - (test.getFinishTime() - test.getStartTime()));
		}
		sdTime = Math.sqrt(sdTime / size);
		sdIteration = Math.sqrt(sdIteration / size);

		List<StaticalExecution> staticalExecutions = calculateMeanAndStandardDeviationByHNN(executionsMap);

		StaticalExecution total = calculateMeanAndStandardDeviationTotal(executionsMap);

		staticalRoutingTest = new StaticalRoutingTest();
		staticalRoutingTest.setExecutions(staticalExecutions);
		staticalRoutingTest.setMeanTime(meanTime);
		staticalRoutingTest.setStandardDeviationTime(Math.sqrt(sdTime));
		staticalRoutingTest.setMeanIteration(total.getMeanIterations());
		staticalRoutingTest.setStandardDeviationIteration(total
				.getStandardDerivationIterations());
		return staticalRoutingTest;
	}

	private List<StaticalExecution> calculateMeanAndStandardDeviationByHNN(
			Map<HNN, List<Execution>> executionsMap) {
		List<Execution> executions;
		StaticalExecution staticalExecution;
		Entry<HNN, List<Execution>> entry = null;
		double sumTime = 0;
		double meanTime = 0;
		double sdTime = 0;
		double sumIterations = 0;
		double meanIterations = 0;
		double sdIterations = 0;
		List<StaticalExecution> staticalExecutions = new ArrayList<StaticalExecution>();
		Iterator<Entry<HNN, List<Execution>>> ite = executionsMap.entrySet()
				.iterator();
		while (ite.hasNext()) {
			entry = (Map.Entry<HNN, java.util.List<Execution>>) ite.next();
			executions = entry.getValue();
			sumTime = 0;
			sumIterations = 0;
			staticalExecution = new StaticalExecution();
			for (Execution exec : executions) {
				sumTime += exec.getTime();
				sumIterations += exec.getIterations();
			}
			meanTime = sumTime / size;
			meanIterations = sumIterations / size;
			for (Execution exec : executions) {
				sdTime += (meanTime - exec.getTime())
						* (meanTime - exec.getTime());
				sdIterations += (meanIterations - exec.getIterations())
						* (meanIterations - exec.getIterations());
			}
			sdTime = Math.sqrt(sdTime / size);
			sdIterations = Math.sqrt(sdIterations / size);
			staticalExecution.setHnn(entry.getKey());
			staticalExecution.setMeanIterations(meanIterations);
			staticalExecution.setMeanTime(meanTime);
			staticalExecution.setStandardDerivationIterations(sdIterations);
			staticalExecution.setStandardDeviationTime(sdTime);
			staticalExecutions.add(staticalExecution);
		}

		return staticalExecutions;
	}

	private StaticalExecution calculateMeanAndStandardDeviationTotal(
			Map<HNN, List<Execution>> executionsMap) {
		List<Execution> executions;
		StaticalExecution staticalExecution;
		Entry<HNN, List<Execution>> entry = null;
		double sumIterations = 0;
		double meanIterations = 0;
		double sdIterations = 0;
		int size = 0;
		List<StaticalExecution> staticalExecutions = new ArrayList<StaticalExecution>();
		Iterator<Entry<HNN, List<Execution>>> ite = executionsMap.entrySet()
				.iterator();
		int qtdIterations = 0;
		if (ite.hasNext()) {
			entry = (Map.Entry<HNN, java.util.List<Execution>>) ite.next();
			qtdIterations = entry.getValue().size();

		}
		staticalExecution = new StaticalExecution();
		double totalIterations = 0;
		sumIterations = 0;
		for (int i = 0; i < qtdIterations; i++) {
			ite = executionsMap.entrySet().iterator();
			totalIterations = 0;
			while (ite.hasNext()) {
				entry = (Map.Entry<HNN, java.util.List<Execution>>) ite.next();
				executions = entry.getValue();
				Execution exec = executions.get(i);
				totalIterations += exec.getIterations();
			}
			sumIterations += totalIterations;
		}

		meanIterations = sumIterations / qtdIterations;
		for (int i = 0; i < qtdIterations; i++) {
			ite = executionsMap.entrySet().iterator();
			totalIterations = 0;
			while (ite.hasNext()) {
				entry = (Map.Entry<HNN, java.util.List<Execution>>) ite.next();
				executions = entry.getValue();
				Execution exec = executions.get(i);
				totalIterations += exec.getIterations();
			}
			sdIterations += (meanIterations - totalIterations)
					* (meanIterations - totalIterations);
		}
		sdIterations = Math.sqrt(sdIterations / qtdIterations);

		staticalExecution.setMeanIterations(meanIterations);
		staticalExecution.setStandardDerivationIterations(sdIterations);
		staticalExecutions.add(staticalExecution);

		return staticalExecution;
	}

	private void fillExecutionsMap(Map<HNN, List<Execution>> executionsMap,
			Execution execution) {
		List<Execution> executions;
		executions = executionsMap.get(execution.getHnn());
		if (executions == null)
			executions = new ArrayList<Execution>();
		executions.add(execution);
		executionsMap.put(execution.getHnn(), executions);
		if (execution.getExecutions() != null
				&& execution.getExecutions().size() > 0) {
			for (int i = 0; i < execution.getExecutions().size(); i++) {
				fillExecutionsMap(executionsMap,
						execution.getExecutions().get(i));
			}
		}
	}

}