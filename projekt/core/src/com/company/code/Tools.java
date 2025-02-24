package com.company.code;

public enum Tools {


    M41 (4),    //Frez
    M61 (35),
    M62 (20),
    M63 (8),
    M64 (3),
    M65 (5),
    M66 (5),
    M67 (5),
    M68 (5),
    M69 (10),
    M70 (7),
    M71 (8),
    M72 (8),
    M73 (8),
    M74 (8),
    M75 (4),
    M76 (4),
    M95(2);     //Piła


    private final Float diameter;

    Tools(float diameter) {
        this.diameter = diameter;
    }

    public Float getDiameter() {
        return diameter;
    }
}
