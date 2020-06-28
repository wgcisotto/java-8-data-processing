package com.wgcisotto.model;

public class Actor {

    public String lasName, firstName;

    public Actor(String lasName, String firstName){
        this.lasName = lasName.toUpperCase();
        this.firstName = firstName;
    }

    public String getLasName() {
        return lasName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Actor " + lasName + " " + firstName;
    }


}
