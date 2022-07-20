package com.diptanshu.quantummod2.block.custom;

import net.minecraft.util.StringRepresentable;

public enum QubitHolding implements StringRepresentable {
    DEFAULT("default");

    private final String name;

    private QubitHolding(String name){
        this.name = name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
