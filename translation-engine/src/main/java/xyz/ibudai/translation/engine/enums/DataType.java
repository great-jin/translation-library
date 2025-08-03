package xyz.ibudai.translation.engine.enums;

import lombok.Getter;

@Getter
public enum DataType {

    JSON("application/json;");


    private final String type;

    DataType(String type) {
        this.type = type;
    }
}
