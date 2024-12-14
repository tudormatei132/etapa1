package org.poo.system;

import lombok.Getter;

@Getter
public class Edge {

    private String to;
    private double rate;

    public Edge(String to, double rate) {
        this.to = to;
        this.rate = rate;
    }


}
