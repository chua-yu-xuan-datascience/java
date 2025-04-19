import java.util.*;
import java.awt.geom.Point2D;

public class Fireball {

    static class Edge {
        int to;
        double time;

        Edge(int to, double time) {
            this.to = to;
            this.time = time;
        }
    }

    static class State implements Comparable<State> {
        int node;
        double time;

        State(int node, double time) {
            this.node = node;
            this.time = time;
        }

        public int compareTo(State other) {
            return Double.compare(this.time, other.time);
        }
    }

    static double dist(Point2D.Double a, Point2D.Double b) {
        return a.distance(b);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Point2D.Double src = new Point2D.Double(sc.nextDouble(), sc.nextDouble());
        Point2D.Double dest = new Point2D.Double(sc.nextDouble(), sc.nextDouble());
        int n = sc.nextInt();

        // make vertices
        Point2D.Double[] vertices = new Point2D.Double[n + 2];
        vertices[0] = src;
        vertices[1] = dest;
        for (int i = 0; i < n; i++) {
            vertices[i + 2] = new Point2D.Double(sc.nextDouble(), sc.nextDouble());
        }

        // Put vertices into graph
        int totalV = n + 2;
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < totalV; i++) graph.add(new ArrayList<>());

        // Calculate edge weights btw vertices (time cost) and build the graph
        for (int i = 0; i < totalV; i++) {
            for (int j = 0; j < totalV; j++) {
                if (i == j) continue;

                double d = dist(vertices[i], vertices[j]);

                double time;
                if (i == 0) {
                    // From start: only running
                    time = d / 5.0;
                } else {
                    // From cannon: fixed 2 sec cannon time + time to cover the difference from 50m (run before/after)
                    time = 2.0 + Math.abs(d - 50) / 5.0; // cannon: 2 sec + extra run
                }

                graph.get(i).add(new Edge(j, time));
            }
        }

        double[] minTimeTo = new double[totalV];
        Arrays.fill(minTimeTo, Double.MAX_VALUE);
        minTimeTo[0] = 0;

        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(0, 0)); // Start at node 0 with time 0
        
        // Dijkstra using Priority Queue, from whatever is polled, calculate new time to reach next nodes
        // If it is an improvement, change the time to reach that node and enqueue into pq (as it is part of a shortest path)
        while (!pq.isEmpty()) {
            State cur = pq.poll();

            if (cur.time > minTimeTo[cur.node]) continue;

            for (Edge e : graph.get(cur.node)) {
                double newTime = cur.time + e.time;
                if (newTime < minTimeTo[e.to]) {
                    minTimeTo[e.to] = newTime;
                    pq.add(new State(e.to, newTime));
                }
            }
        }

        System.out.printf("%.6f\n", minTimeTo[1]);
    }
}
