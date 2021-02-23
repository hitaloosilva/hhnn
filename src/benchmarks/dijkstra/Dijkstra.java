package benchmarks.dijkstra;

import hnn.util.HHNNLoggerRoutingTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra {

	public static void computePaths(Vertex source) {
		source.minDistance = 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source);
		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();
			// Visit each edge exiting u
			for (Edge e : u.adjacencies) {
				Vertex v = e.target;
				double weight = e.weight;
				double distanceThroughU = u.minDistance + weight;
				if (distanceThroughU < v.minDistance) {
					vertexQueue.remove(v);
					v.minDistance = distanceThroughU;
					v.previous = u;
					vertexQueue.add(v);
				}
			}
		}
	}

	public static List<Vertex> getShortestPathTo(Vertex target) {
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
			path.add(vertex);
		Collections.reverse(path);
		return path;
	}

	public static void main(String[] args) {

		simulate64();
	}

	private static void simulate16() {
		Vertex[] vertices = null;

		// long time = System.nanoTime();
		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(
				"d:/log_dijkstra_16.txt");
		Vertex v = null;
		for (int i = 0; i < 16; i++) {
			vertices = createVertices16(0);
			computePaths(vertices[i]);
			for (int j = 0; j < vertices.length; j++) {
				if (i != j) {
					v = vertices[j];
					List<Vertex> path = getShortestPathTo(v);
					logger.exportToCSVDijkstra(vertices, v, i, path);
				}
			}
		}
		// System.out.println("Tempo = " + (System.nanoTime() - time));
	}

	private static void simulate64() {
		Vertex[] vertices = null;

		HHNNLoggerRoutingTest logger = new HHNNLoggerRoutingTest(
				"d:/log_dijkstra_64.txt");
		// long time = System.nanoTime();
		Vertex v = null;
		System.out.println("{");
		for (int i = 0; i < 64; i++) {
			vertices = createVertices64();
			computePaths(vertices[i]);
			System.out.print("{");
			for (int j = 0; j < vertices.length; j++) {
				if (i != j) {
					v = vertices[j];
					List<Vertex> path = getShortestPathTo(v);
					System.out.print(String.format(Locale.US, "%2.2f",
							v.minDistance));
					// logger.exportToCSVDijkstra(vertices, v, i, path);
				} else {
					System.out.print(0.0);
				}
				if (j != vertices.length - 1)
					System.out.print(",");
			}
			System.out.print("}");
			if (i != vertices.length - 1)
				System.out.println(",");
		}
		System.out.println("}");
		// System.out.println("Tempo = " + (System.nanoTime() - time));
	}

	private static Vertex[] createVertices16(int id) {
		Vertex[] vertices = new Vertex[16];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex(i + id + "");
		}

		vertices[0].adjacencies = new Edge[] { new Edge(vertices[1], 0.01),
				new Edge(vertices[2], 0.05) };
		vertices[1].adjacencies = new Edge[] { new Edge(vertices[0], 0.01),
				new Edge(vertices[2], 0.01), new Edge(vertices[3], 0.03),
				new Edge(vertices[4], 0.01) };
		vertices[2].adjacencies = new Edge[] { new Edge(vertices[0], 0.05),
				new Edge(vertices[1], 0.01), new Edge(vertices[3], 0.01),
				new Edge(vertices[8], 0.05) };
		vertices[3].adjacencies = new Edge[] { new Edge(vertices[1], 0.03),
				new Edge(vertices[2], 0.01) };

		vertices[4].adjacencies = new Edge[] { new Edge(vertices[5], 0.01),
				new Edge(vertices[6], 0.05), new Edge(vertices[1], 0.01) };
		vertices[5].adjacencies = new Edge[] { new Edge(vertices[4], 0.01),
				new Edge(vertices[6], 0.01), new Edge(vertices[7], 0.03) };
		vertices[6].adjacencies = new Edge[] { new Edge(vertices[4], 0.05),
				new Edge(vertices[5], 0.01), new Edge(vertices[7], 0.01),
				new Edge(vertices[9], 0.01) };
		vertices[7].adjacencies = new Edge[] { new Edge(vertices[5], 0.03),
				new Edge(vertices[6], 0.01), new Edge(vertices[8], 0.05),
				new Edge(vertices[12], 0.03) };

		vertices[8].adjacencies = new Edge[] { new Edge(vertices[9], 0.01),
				new Edge(vertices[10], 0.05), new Edge(vertices[2], 0.05) };
		vertices[9].adjacencies = new Edge[] { new Edge(vertices[8], 0.01),
				new Edge(vertices[10], 0.01), new Edge(vertices[11], 0.03),
				new Edge(vertices[6], 0.01) };
		vertices[10].adjacencies = new Edge[] { new Edge(vertices[8], 0.05),
				new Edge(vertices[9], 0.01), new Edge(vertices[11], 0.01) };
		vertices[11].adjacencies = new Edge[] { new Edge(vertices[9], 0.03),
				new Edge(vertices[10], 0.01), new Edge(vertices[8], 0.05),
				new Edge(vertices[12], 0.01) };

		vertices[12].adjacencies = new Edge[] { new Edge(vertices[13], 0.01),
				new Edge(vertices[14], 0.05), new Edge(vertices[7], 0.03),
				new Edge(vertices[11], 0.01) };
		vertices[13].adjacencies = new Edge[] { new Edge(vertices[12], 0.01),
				new Edge(vertices[14], 0.01), new Edge(vertices[15], 0.03) };
		vertices[14].adjacencies = new Edge[] { new Edge(vertices[12], 0.05),
				new Edge(vertices[13], 0.01), new Edge(vertices[15], 0.01) };
		vertices[15].adjacencies = new Edge[] { new Edge(vertices[13], 0.03),
				new Edge(vertices[14], 0.01) };
		return vertices;
	}

	private static Vertex[] createVertices64() {
		Vertex[][] vertices = new Vertex[4][];

		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = createVertices16(i * 16);
		}

		addVertices(vertices[0][5],
				new Edge[] { new Edge(vertices[1][0], 0.01) });
		addVertices(vertices[1][0],
				new Edge[] { new Edge(vertices[0][5], 0.01) });

		addVertices(vertices[0][10],
				new Edge[] { new Edge(vertices[2][0], 0.05) });
		addVertices(vertices[2][0],
				new Edge[] { new Edge(vertices[0][10], 0.05) });

		addVertices(vertices[1][10],
				new Edge[] { new Edge(vertices[2][5], 0.01) });
		addVertices(vertices[2][5],
				new Edge[] { new Edge(vertices[1][10], 0.01) });

		addVertices(vertices[1][15],
				new Edge[] { new Edge(vertices[3][0], 0.03) });
		addVertices(vertices[3][0],
				new Edge[] { new Edge(vertices[1][15], 0.03) });

		addVertices(vertices[2][15],
				new Edge[] { new Edge(vertices[3][0], 0.01) });
		addVertices(vertices[3][0],
				new Edge[] { new Edge(vertices[2][15], 0.01) });

		List<Vertex> list = new ArrayList<Vertex>();
		list.addAll(Arrays.asList(vertices[0]));
		list.addAll(Arrays.asList(vertices[1]));
		list.addAll(Arrays.asList(vertices[2]));
		list.addAll(Arrays.asList(vertices[3]));
		Vertex[] retorno = new Vertex[64];
		return list.toArray(retorno);

	}

	private static void addVertices(Vertex vertice, Edge[] edges) {
		List<Edge> list = new ArrayList<Edge>();
		list.addAll(Arrays.asList(vertice.adjacencies));
		list.addAll(Arrays.asList(edges));
		Edge[] retorno = new Edge[vertice.adjacencies.length + edges.length];
		vertice.adjacencies = list.toArray(retorno);
	}
}