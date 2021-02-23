package hnn;

import java.util.ArrayList;
import java.util.List;

import hnn.models.HNN;
import hnn.models.Neuron;

public class CopyOfStaticalExecution {

	private long meanTime;
	private long standardDerivationTime;
	private int meanIterations;
	private int standardDerivationIterations;
	private HNN hnn;
	private List<CopyOfStaticalExecution> executions;

	public CopyOfStaticalExecution() {
		// TODO Auto-generated constructor stub
	}
	
	public CopyOfStaticalExecution(long meanTime, long standardDerivationTime,
			int meanIterations, int standardDerivationIterations, HNN hnn) {
		this.meanTime = meanTime;
		this.standardDerivationTime = standardDerivationTime;
		this.meanIterations = meanIterations;
		this.standardDerivationIterations = standardDerivationIterations;
		this.hnn = hnn;
		this.executions = new ArrayList<CopyOfStaticalExecution>();
	}


	public CopyOfStaticalExecution(long meanTime, long standardDerivationTime,
			int meanIterations, int standardDerivationIterations, HNN hnn,
			List<CopyOfStaticalExecution> executions) {
		this.meanTime = meanTime;
		this.standardDerivationTime = standardDerivationTime;
		this.meanIterations = meanIterations;
		this.standardDerivationIterations = standardDerivationIterations;
		this.hnn = hnn;
		this.executions = executions;
	}

	public long getMeanTime() {
		return meanTime;
	}


	public void setMeanTime(long meanTime) {
		this.meanTime = meanTime;
	}


	public long getStandardDerivationTime() {
		return standardDerivationTime;
	}


	public void setStandardDerivationTime(long standardDerivationTime) {
		this.standardDerivationTime = standardDerivationTime;
	}


	public int getMeanIterations() {
		return meanIterations;
	}


	public void setMeanIterations(int meanIterations) {
		this.meanIterations = meanIterations;
	}


	public int getStandardDerivationIterations() {
		return standardDerivationIterations;
	}


	public void setStandardDerivationIterations(int standardDerivationIterations) {
		this.standardDerivationIterations = standardDerivationIterations;
	}


	public HNN getHnn() {
		return hnn;
	}


	public void setHnn(HNN hnn) {
		this.hnn = hnn;
	}


	public List<CopyOfStaticalExecution> getExecutions() {
		return executions;
	}


	public void setExecutions(List<CopyOfStaticalExecution> executions) {
		this.executions = executions;
	}
	
	public void createPath() {
		this.hnn.getNeurons();
		for (Neuron neuron : this.hnn.getNeurons()) {
			if (neuron.isActivated()) {
				System.out.println(neuron.getInterfaceParent().getLevel()
						+ " - " + neuron);
			}
		}
	}


}
