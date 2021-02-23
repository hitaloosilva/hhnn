package hnn;

public class MainFPGA {

	public static void main(String[] args) {

		// int[][] vector = new int[][] { { 0, 0, 1, 2 }, { 3, 0, 4, 5 },
		// { 6, 7, 0, 8 }, { 9, 10, 11, 0 } };
								   // 0  1  2  3  4  5  6  7
		int[][] p = new int[][] { 	{ 1, 0, 0, 1, 1, 1, 1, 1 }, // 0
									{ 0, 1, 1, 1, 0, 1, 1, 1 }, // 1
									{ 0, 1, 1, 0, 1, 1, 1, 1 }, // 2
									{ 1, 1, 0, 1, 0, 1, 0, 1 }, // 3
									{ 1, 0, 1, 0, 1, 0, 1, 1 }, // 4
									{ 1, 1, 1, 1, 0, 1, 0, 1 }, // 5
									{ 1, 1, 1, 0, 1, 0, 1, 0 }, // 6
									{ 1, 1, 1, 1, 1, 1, 0, 1 }, // 7
		};
		double[][] cost = new double[][] {
				// 0    1    2    3    4    5    6    7
				{ 0.0, 0.1, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0 }, // 0
				{ 0.1, 0.0, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0 }, // 1
				{ 0.1, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0 }, // 2
				{ 0.0, 0.0, 0.1, 0.0, 0.1, 0.0, 0.3, 0.0 }, // 3
				{ 0.0, 0.5, 0.0, 0.1, 0.0, 0.1, 0.0, 0.0 }, // 4
				{ 0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.1, 0.0 }, // 5
				{ 0.0, 0.0, 0.0, 0.3, 0.0, 0.1, 0.0, 0.1 }, // 6
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0 }, // 7
		};
		
		
		int indice = 0;
		int indiceExterno = 0;
		int size = 8;
		
		StringBuffer buff = new StringBuffer();
				
		buff.append("ps: 		p_map			:=	(");
		for (int x = 0; x < size; x++) {
			for (int i = 0; i < size; i++) {
				if (x != i) {
					buff.append(p[x][i]);
					buff.append(", ");
				}
			}
		}
		buff.delete(buff.length() - 2, buff.length() - 1);
		buff.append(")\ncosts: 	cost_map		:=	(");
		for (int x = 0; x < size; x++) {
			for (int i = 0; i < size; i++) {
				if (x != i) {
					buff.append(cost[x][i]);
					buff.append(", ");
				}
			}
		}
		buff.delete(buff.length() - 2, buff.length() - 1);
		buff.append(")");
		
		System.out.println(buff.toString());
		
		for (int x = 0; x < size; x++) {
			for (int i = 0; i < size; i++) {
				if (x != i) {
					buff = new StringBuffer("neuronXI");
					buff.append(x);
					buff.append(i);

					buff.append(": Neuron generic map (( ");
					indice = 0;
					for (int y = 0; y < size; y++) {
						for (int j = 0; j < size; j++) {
							if (y != j) {
								if (x == y || x == j || i == y || i == j) {
									buff.append("i+" + y + ", i+" + j);
									if (indice > 9)
										buff.append("," + indice + ", ");
									else
										buff.append(", " + indice + ", ");
								}
								indice++;
							}
						}
					}
					buff.delete(buff.length() - 2, buff.length() - 1);
					buff.append(")");
					buff.append(" , i + " + x);
					buff.append(" , i + " + i);
					buff.append(") port map (clk, rst, enable,");
					buff.append(" ps("+indiceExterno+")");
					buff.append(", to_fixeds(");
					buff.append("costs("+indiceExterno+")");
					buff.append(", yLBits , -RBits), outputs, source, destination, outputs(");
					buff.append(indiceExterno);
					buff.append(") , y(");
					buff.append(indiceExterno);
					buff.append(") , nstate(");
					buff.append(indiceExterno);
					buff.append("), nstate);");
					indiceExterno++;
					System.out.println(buff.toString());
				}
			}
		}

		// int[] a = new int[] { 4, 1, 8, 2, 12, 3, 1, 4, 9, 6, 12, 7, 2, 8, 6,
		// 9,
		// 12, 11, 3, 12, 7, 12, 11, 12 };
		// StringBuffer buf = null;
		// for (int x = 0; x < 4; x++) {
		// buf = new StringBuffer();
		// buf.append("(");
		// for (int i = 0; i < 12; i++) {
		// // buf.append(16 * x + a[2 * i]);
		// // buf.append(",");
		// buf.append(16 * x + a[2 * i + 1]);
		// if (i != 11)
		// buf.append(",");
		// }
		// buf.append("),");
		// System.out.println(buf.toString());
		// }

	}
}
