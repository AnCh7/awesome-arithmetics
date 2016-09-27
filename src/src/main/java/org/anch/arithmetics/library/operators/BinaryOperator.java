package org.anch.arithmetics.library.operators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum BinaryOperator {
    Addition,
    Subtraction,
    Multiplication,
    Division;

    private static Map<Character, BinaryOperator> map = new HashMap<Character, BinaryOperator>();

    static {
        map.put('+', Addition);
        map.put('-', Subtraction);
        map.put('*', Multiplication);
        map.put('/', Division);
    }

    @JsonCreator
    public static BinaryOperator fromValue(Character value) {
        return map.get(value);
    }

    @JsonValue
    public Character toValue() {
        for (Map.Entry<Character, BinaryOperator> entry : map.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }
        return null;
    }
}
