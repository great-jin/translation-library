package xyz.ibudai.translation.engine.enums;

import lombok.Getter;

@Getter
public enum Model {

    NLLB("nllbTranslator");


    private final String components;

    Model(String components) {
        this.components = components;
    }
}
