package com.example.movie_workshop.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class MovieList {
    private ArrayList<Movie> allMovies = new ArrayList<>();

    private File imdbFile = new File("data/imdb-data.csv");
    private Random random = new Random();


    public MovieList(){
        try {
            Scanner scanner = new Scanner(imdbFile);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineAsArray = line.split(";");
                int year = Integer.parseInt(lineAsArray[0]);
                int lengthAsInt = Integer.parseInt(lineAsArray[1]);
                Duration length = Duration.ofMinutes(lengthAsInt);
                String title = lineAsArray[2];
                String subject = lineAsArray[3];
                int popularity = Integer.parseInt(lineAsArray[4]);
                boolean awards = parseYesNo(lineAsArray[5]);
                Movie currentMovie = new Movie(year, length, title, subject, popularity, awards);
                allMovies.add(currentMovie);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't finde the file");
        }
    }

    private boolean parseYesNo(String awardString) {
        return awardString.equalsIgnoreCase("yes");
    }

    public Movie getFirst() {
        Movie firstMovie = allMovies.get(0);
        return firstMovie;
    }


    public Movie getRandom() {
        int randNumber = random.nextInt(allMovies.size());
        Movie randMovie = allMovies.get(randNumber);
        return randMovie;
    }

    public Movie[] getTenSortByPopularity() {
        Movie[] tenMovies = new Movie[10];
        for (int i = 0; i < tenMovies.length; i++) {
            tenMovies[i] = getRandom();
        }
        Arrays.sort(tenMovies);

        return tenMovies;
    }

    public int howManyWonAnAward() {
        int counter = 0;
        for (Movie currentMovie : allMovies) {
            if (currentMovie.isAwarded()) {
                counter++;
            }
        }
        return counter;
    }

    public ArrayList<Movie> filter(char letter, int amount) {
        ArrayList<Movie> matchingMovies = new ArrayList<>();
        for (Movie currentMovie : allMovies) {
            String movieTitle = currentMovie.getTitle().toLowerCase();
            int letterCount = countChars(movieTitle, letter);
            if (letterCount == amount) {
                matchingMovies.add(currentMovie);
            }
        }
        return matchingMovies;
    }

    private int countChars(String movieTitle, char letter) {
        int letterCount = 0;
        for (int i = 0; i < movieTitle.length(); i++) {
            if (movieTitle.charAt(i) == letter) {
                letterCount++;
            }
        }
        return letterCount;
    }

    public String getLongest(String genre1, String genre2) {
        Duration averageGenre1 = getAverageDuration(genre1);
        Duration averageGenre2 = getAverageDuration(genre2);

        int compereResult = averageGenre2.compareTo(averageGenre1);

        if (compereResult > 0) {
            return "The genre whit the longest movies is " + genre2 + " whit an average length of " + getDurationAsFormatted(averageGenre2);
        } else {
            return "The genre whit the longest movies is " + genre1 + " whit an average length of " + getDurationAsFormatted(averageGenre1);
        }
    }

    private Duration getAverageDuration(String genre) {
        int numberOfMovies = 0;
        Duration totalDurationOfMovies = Duration.ofMinutes(0);
        for (Movie currentMovie : allMovies) {
            String subject = currentMovie.getSubject();
            if (subject.equalsIgnoreCase(genre)) {
                numberOfMovies++;
                Duration duration = currentMovie.getLength();
                totalDurationOfMovies = totalDurationOfMovies.plus(duration);
            }
        }
        Duration averageLength = totalDurationOfMovies.dividedBy(numberOfMovies);
        Duration formatAverageLength = averageLength.truncatedTo(ChronoUnit.MINUTES);
        return formatAverageLength;
    }

    private String getDurationAsFormatted(Duration averageLength) {
        String raw = averageLength.toString();
        String lengthAsString = raw.substring(2);
        return lengthAsString.toLowerCase();
    }
}
