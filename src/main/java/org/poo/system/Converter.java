package org.poo.system;

import org.poo.graph.CurrencyGraph;
import org.poo.graph.Edge;

import java.util.HashSet;

public class Converter {


    private CurrencyGraph graph;
    public Converter() {
        graph = new CurrencyGraph();
    }

    public void addNewRate(String from, String to, double rate) {
        Edge edge = new Edge(to, rate);
        Edge reverseEdge = new Edge(from, 1/rate);

        graph.add(from, edge);
        graph.add(to, reverseEdge);
    }


    private double dfs(final String start, final String searched, final HashSet<String> visited,
                     final double conversionRate) {

        if (start.equals(searched)) {
            return conversionRate;
        }

        visited.add(start);

        for (Edge edge : graph.get(start)) {
            if (!visited.contains(edge.getTo())) {
                double result = dfs(edge.getTo(), searched, visited,
                        conversionRate * edge.getRate());
                if (result != -1) {
                    return result;
                }
            }
        }
        return -1;
    }


    public double convert(String from, String to) {
        HashSet<String> visited = new HashSet<>();
        return dfs(from, to, visited, 1);
    }


}
