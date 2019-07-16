package com.gangoffours.pprs.pprs.viewmodels;

import java.util.HashMap;
import java.util.Map;

public enum DrugInteractionType {
    None(0),
    Minor(1),
    Moderate(2),
    Major(3);

    private int value;
    private static Map<Integer, DrugInteractionType> map = new HashMap<Integer, DrugInteractionType>();

    private DrugInteractionType(int value) {
        this.value = value;
    }

    static {
        for (DrugInteractionType interactionType : DrugInteractionType.values()) {
            map.put(interactionType.value, interactionType);
        }
    }

    public static DrugInteractionType valueOf(int interactionType) {
        return (DrugInteractionType) map.get(interactionType);
    }

    public int getValue() {
        return value;
    }
}