package com.lindaring.base.enumerator;

public enum Charset {

    UTF8("UTF-8"),
    ASCII("ASCII"),
    CP1256("CP1256"),
    ISO1("ISO-8859-1"),
    ISO2("ISO-8859-2"),
    ISO6("ISO-8859-6"),
    ISO15("ISO-8859-15"),
    WIN12("Windows-1252");

    private String value;

    private Charset(String value) {
        this.value = value;
    }

    public static String getValue(Charset charset) {
        return charset.value;
    }

}
