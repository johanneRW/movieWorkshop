package com.example.movie_workshop.services;

import java.time.Duration;

public class Movie implements Comparable<Movie>{
    private int year;
    private Duration length;
    private String title;
    private String subject;
    private int popularity;
    private boolean awards;

    public Movie(int year, Duration length, String title, String subject, int popularity, boolean awards) {
        this.year = year;
        this.length = length;
        this.title = title;
        this.subject = subject;
        this.popularity = popularity;
        this.awards = awards;
    }

    public Duration getLength() {
        return length;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public int getPopularity() {
        return popularity;
    }

    public boolean isAwarded() {
        return awards;
    }

   @Override
    public int compareTo(Movie otherMovie) {
        return otherMovie.getPopularity()-popularity;
    }

    @Override
    public String toString() {
        return title + " (" +subject+ ", "+year + ", " + getDurationAsFormattedString() + ") Popularity-rating: " + popularity + ". Awarded: " + awards;
    }

    private String getDurationAsFormattedString(){
        String durationAsString=length.toString();
        String lengthAsString=durationAsString.substring(2);
        return lengthAsString.toLowerCase();
    }
}


