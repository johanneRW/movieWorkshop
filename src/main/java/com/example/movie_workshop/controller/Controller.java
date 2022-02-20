package com.example.movie_workshop.controller;

import com.example.movie_workshop.services.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie_workshop.services.MovieList;

import java.util.ArrayList;


@RestController
public class Controller {
    MovieList movieList = new MovieList();

    public Controller() {
    }

    @GetMapping("/")
    public String hello() {
        return "Welcome to the movie database!\n I would like to write an introduction.\n-But I can't think of anything at the moment";
    }

    @GetMapping("/getFirst")
    public String getFirst() {
        Movie firstMovie = movieList.getFirst();
        String movieTitle = firstMovie.getTitle();
        return "The title of the first movie is: " + movieTitle;
    }

    @GetMapping("/getRandom")
    public String getRandom() {
        Movie randMovie = movieList.getRandom();
        String movieTitle = randMovie.getTitle();
        return "A random movie title is: " + movieTitle;
    }

    @GetMapping("/getTenSortByPopularity")
    public String getTenSortByPopularity() {
        StringBuilder stringBuilder = new StringBuilder();
        Movie[] tenSortByPopularity = movieList.getTenSortByPopularity();

        stringBuilder.append("Ten random movies sorted by their popularity:<br>");

        for (Movie currentMovie : tenSortByPopularity) {
            stringBuilder.append(currentMovie + "<br>");
        }
        return stringBuilder.toString();
    }

    @GetMapping("/howManyWonAnAward")
    public String howManyWonAnAward() {
        int moviesWhitAnAward = movieList.howManyWonAnAward();
        return moviesWhitAnAward + " movies have won an award.";
    }

    @GetMapping("/filter")
    //det var ikke muligt at give variablen navnet char, da IntelliJ ikke vil anerkende det både som type og variabel navn
    public String filter(@RequestParam char letter, @RequestParam int amount) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Movie> matchingMovies = movieList.filter(letter, amount);

        stringBuilder.append("All movie titles containing " + letter + " " + amount + " times :<br>");
        if (matchingMovies.size() > 0) {
            for (Movie currentMovie : matchingMovies) {
                stringBuilder.append(currentMovie + "<br>");
            }
        } else {
            stringBuilder.append("There dosen't seem to be any matching movies");
        }
        return stringBuilder.toString();
    }

    @GetMapping("/longest")
    public String longest(@RequestParam String g1, String g2) {
        String answer = movieList.getLongest(g1, g2);
        return answer;
    }
}

