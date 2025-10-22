package xyz.ibudai.translation.engine.enums;

import lombok.Getter;

@Getter
public enum Language {

    /**
     * 英语
     */
    en("en-US"),

    /**
     * 中文.
     */
    zh("zh-CN"),

    /**
     * 日语
     */
    ja("ja-JP"),

    /**
     * 越南语
     */
    vi("vi-VN"),

    /**
     * 德语
     */
    de("de-DE");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public static Language getLanguage(String code) {
        for (Language language : values()) {
            if (language.code.equals(code)) {
                return language;
            }
        }
        return null;
    }
}
