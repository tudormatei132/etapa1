package org.poo.system;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrencyGraph extends HashMap<String, ArrayList<Edge>> {

    public void add(String from, Edge edge) {
        ArrayList<Edge> array;
        if (!containsKey(from)) {
            array = new ArrayList<>();
        } else {
            array = get(from);
        }
        array.add(edge);
        put(from, array);

    }

}
