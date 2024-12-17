package org.poo.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommerciantSpendings implements Comparable<CommerciantSpendings> {

    private String commerciant;
    private double total;

    public CommerciantSpendings(String commerciant, double total) {
        this.commerciant = commerciant;
        this.total = total;
    }

    @Override
    public int compareTo(CommerciantSpendings o) {
        return commerciant.compareTo(o.getCommerciant());
    }

    public ObjectNode print(ObjectMapper mapper) {
        ObjectNode result = mapper.createObjectNode();
        result.put("commerciant", commerciant);
        result.put("total", total);
        return result;
    }

}
