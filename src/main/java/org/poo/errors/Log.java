package org.poo.errors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Log {

    private String command, error, description, success;
    private int timestamp, detailsTimestamp;
    public static class Builder {
        private String command, error = null, description = null, success = null;
        private int timestamp, detailsTimestamp = 0;
        public Builder(final String command, final int timestamp) {
            this.command = command;
            this.timestamp = timestamp;
        }

        public Builder setDetailsTimestamp(final int timestamp) {
            this.detailsTimestamp = timestamp;
            return this;
        }

        public Builder setDescription(final String description) {
            this.description = description;
            return this;
        }

        public Builder setError(final String error) {
            this.error = error;
            return this;
        }

        public Builder setSucces(final String success) {
            this.success = success;
            return this;
        }

        public Log build() {
            return new Log(this);
        }

    }

    public Log(Builder builder) {
        this.command = builder.command;
        this.error = builder.error;
        this.timestamp = builder.timestamp;
        this.detailsTimestamp = builder.detailsTimestamp;
        this.description = builder.description;
        this.success = builder.success;
    }

    public ObjectNode print(ObjectMapper mapper) {
        ObjectNode errorNode = mapper.createObjectNode();
        errorNode.put("command", command);
        ObjectNode details = mapper.createObjectNode();
        if (error != null) {
            details.put("error", error);
        } else if (description != null) {
            details.put("description", description);
        } else {
            details.put("success", success);
        }
        if (detailsTimestamp != 0) {
            details.put("timestamp", detailsTimestamp);
        }
        errorNode.put("output", details);
        errorNode.put("timestamp", timestamp);
        return errorNode;
    }

}
