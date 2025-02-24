package com.company.code;

public class Frez {
    private final float spx;
    private final float spy;
    private final float epx;
    private final float epy;

    public Frez(float spx, float spy, float epx, float epy) {
        this.spx = spx;
        this.spy = spy;
        this.epx = epx;
        this.epy = epy;
    }

    public float getSpx() {
        return spx;
    }

    public float getSpy() {
        return spy;
    }

    public float getEpx() {
        return epx;
    }

    public float getEpy() {
        return epy;
    }
}
