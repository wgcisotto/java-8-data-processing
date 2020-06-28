package com.wgcisotto.model;

import java.util.HashSet;
import java.util.Set;

public class Movie {

    private String title;

    private int releaseYear;

    private Set<Actor> actors = new HashSet<>();

    public Movie(String title, int releaseYear){
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void addActor(Actor actor){
        this.actors.add(actor);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
