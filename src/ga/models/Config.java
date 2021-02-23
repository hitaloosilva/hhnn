package ga.models;

public class Config {
	
	private int selection;
	private int permutation;
	private int mutation;
	private int pruning;
	
	public Config (int _selection, int _permutation,
			int _mutation, int _pruning) {
		this.selection = _selection;
		this.permutation = _permutation;
		this.mutation = _mutation;
		this.pruning = _pruning;
	}
	
	public int getSelection() {
		return selection;
	}
	public void setSelection(int selection) {
		this.selection = selection;
	}
	public int getPermutation() {
		return permutation;
	}
	public void setPermutation(int permutation) {
		this.permutation = permutation;
	}
	public int getMutation() {
		return mutation;
	}
	public void setMutation(int mutation) {
		this.mutation = mutation;
	}
	public int getPruning() {
		return pruning;
	}
	public void setPruning(int pruning) {
		this.pruning = pruning;
	}

}
