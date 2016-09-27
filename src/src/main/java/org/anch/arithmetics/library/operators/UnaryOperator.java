package org.anch.arithmetics.library.operators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum UnaryOperator {
    Negation;

    private static Map<Character, UnaryOperator> map = new HashMap<Character, UnaryOperator>();

    static {
        map.put('-', Negation);
    }

    @JsonCreator
    public static UnaryOperator fromValue(Character value) {
        return map.get(value);
    }

    @JsonValue
    public Character toValue() {
        for (Map.Entry<Character, UnaryOperator> entry : map.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }
        return null;
    }
}
