package org.mustabelmo.validation.visitor.wrappers;

public class LengthWrapper {
    final int min;
    final int max;
    final boolean failOnNull;

    public LengthWrapper(int min, int max, boolean failOnNull) {
        this.min = min;
        this.max = max;
        this.failOnNull = failOnNull;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public boolean isFailOnNull() {
        return failOnNull;
    }

}
