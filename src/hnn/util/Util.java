package hnn.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hnn.Execution;
import hnn.Main;
import hnn.RoutingTest;
import hnn.StaticalExecution;
import hnn.StaticalRoutingTest;
import hnn.models.HNN;
import hnn.models.Node;
import hnn.models.Pair;

public class Util {

	public static String getCurrentLocal() {
		File f = new File(Main.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath());
		return f.getParent();
	}

	public static int kroneckerDelt(Node nodeA, Node nodeB) {
		boolean _return = false;
		if (nodeA != null && nodeB != null)
			_return = nodeA.equals(nodeB);
		return _return ? 1 : 0;
	}

	public static int gama(Node node, Pair pair) {
		int _return = 0;
		if (node.equals(pair.getFrom()))
			_return = -1;
		else if (node.equals(pair.getTo()))
			_return = 1;
		return _return;
	}

	public static int KroneckerDelt(int a, int b) {
		return a == b ? 1 : 0;
	}

	public static Node getUpToParent(Node children, Node parent) {
		while (!children.getParent().equals(parent)) {
			children = children.getParent();
		}
		return children;
	}

	public static double clip(double sample, double max, double min) {
		if (sample > max) {
			sample = max;
		}
		if (sample < min) {
			sample = min;
		}
		return sample;
	}
	
	public double mean(List<Double> values) {
		double sumTime = 0;
		double meanTime = 0;
		int size =  values.size();
		for (Double value : values) {
			sumTime += value;
		}
		meanTime = sumTime / size;

		return meanTime;
	}
}
