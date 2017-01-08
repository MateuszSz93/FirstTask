package pl.mszkwarkowski.data;

import pl.mszkwarkowski.api.MoviesInformantStorage;
import pl.mszkwarkowski.api.UserInformantStorage;
import pl.mszkwarkowski.movie.Actor;
import pl.mszkwarkowski.movie.Movie;
import pl.mszkwarkowski.movie.MovieCategory;
import pl.mszkwarkowski.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains method, which create objects of various types when starting the application.
 */
public class Creator {
    /**
     * This method create actors/movies/users and add them to the collections.
     */
    public void startingObjectsCreator() {
        MoviesInformantStorage moviesInformantStorage = new MoviesInformantStorage();
        Actor actor = new Actor(1, "Cezary Pazura");
        Actor actor1 = new Actor(2, "Małgorzata Kożuchowska");
        Actor actor2 = new Actor(3, "Jerzy Stuhr");
        Actor actor3 = new Actor(4, "Janusz Rewiński");
        Actor actor4 = new Actor(5, "Maciej Stuhr");
        Actor actor5 = new Actor(6, "Karolina Rosińska");
        Actor actor6 = new Actor(7, "Michał Milowicz");
        Actor actor7 = new Actor(8, "Edward Linde-Lubaszenko");
        Actor actor8 = new Actor(9, "Gerard Butler");
        Actor actor9 = new Actor(10, "Jamie Foxx");
        Actor actor10 = new Actor(11, "Liam Neeson");
        Actor actor11 = new Actor(12, "Maggie Grace");
        Actor actor12 = new Actor(13, "Marlon Brando");
        Actor actor13 = new Actor(14, "Al Pacino");
        Actor actor14 = new Actor(15, "Diane Keaton");
        Actor actor15 = new Actor(16, "Robert Duvall");
        Actor actor16 = new Actor(17, "Robert De Niro");
        Actor actor17 = new Actor(18, "Ray Liotta");
        Actor actor18 = new Actor(19, "Joe Pesci");
        Actor actor19 = new Actor(20, "Leonardo DiCaprio");
        Actor actor20 = new Actor(21, "Matt Damon");
        Actor actor21 = new Actor(22, "Jack Nicholson");
        Actor actor22 = new Actor(23, "Mark Wahlberg");
        Actor actor23 = new Actor(24, "Johnny Depp");
        Actor actor24 = new Actor(25, "Keira Knightley");
        Actor actor25 = new Actor(26, "Orlando Bloom");
        Actor actor26 = new Actor(27, "Mark Ruffalo");
        Actor actor27 = new Actor(28, "Ben Kingsley");
        Actor actor28 = new Actor(29, "Edward Norton");
        Actor actor29 = new Actor(30, "Brad Pitt");
        Actor actor30 = new Actor(31, "Helena Bonham Carter");
        Actor actor31 = new Actor(32, "James Wood");
        Actor actor32 = new Actor(33, "Christian Bale");
        Actor actor33 = new Actor(34, "Heath Ledger");
        Actor actor34 = new Actor(35, "Gary Oldman");
        Actor actor35 = new Actor(36, "Mélanie Laurent");
        Actor actor36 = new Actor(37, "Christoph Waltz");
        Actor actor37 = new Actor(38, "Ashton Kutcher");
        Actor actor38 = new Actor(39, "Amy Smart");
        Actor actor39 = new Actor(40, "Franka Potente");
        Actor actor40 = new Actor(41, "Willem Dafoe");
        Actor actor41 = new Actor(42, "Sean Patrick Flanery");
        Actor actor42 = new Actor(43, "Norman Reedus");
        Actor actor43 = new Actor(44, "Tom Hanks");
        Actor actor44 = new Actor(45, "Michael Clarke Duncan");
        Actor actor45 = new Actor(46, "François Cluzet");
        Actor actor46 = new Actor(47, "Omar Sy");
        Actor actor47 = new Actor(48, "Felicity Jones");
        Actor actor48 = new Actor(49, "Joseph Gordon-Levitt");
        Actor actor49 = new Actor(50, "Shailene Woodley");
        Actor actor50 = new Actor(51, "Marion Cotillard");
        Actor actor51 = new Actor(52, "Michael Fassbender");
        Actor actor52 = new Actor(53, "Benedict Cumberbatch");
        Actor actor53 = new Actor(54, "Rachel McAdams");
        Actor actor54 = new Actor(55, "Anna Kendrick");
        Actor actor55 = new Actor(56, "Ben Affleck");
        Actor actor56 = new Actor(57, "Diego Luna");
        Actor actor57 = new Actor(58, "Andrew Garfield");
        Actor actor58 = new Actor(59, "Sam Worthington");
        Actor actor59 = new Actor(60, "Teresa Palmer");
        Actor actor60 = new Actor(61, "Amy Adams");
        Actor actor61 = new Actor(62, "Jeremy Renner");
        Actor actor62 = new Actor(63, "Forest Whitaker");
        Actor actor63 = new Actor(64, "Aaron Eckhart");
        Actor actor64 = new Actor(65, "Jonah Hill");
        Actor actor65 = new Actor(66, "Miles Teller");
        Actor actor66 = new Actor(67, "Bradley Cooper");
        Actor actor67 = new Actor(68, "Ana de Armas");

        //Hits
        List<Actor> actorList = new ArrayList<>();
        actorList.addAll(Arrays.asList(actor, actor1, actor2, actor3));
        Movie movie = new Movie(1, "Kiler", "17-10-1997", 104, "Comedy", "Juliusz Machulski", actorList, MovieCategory.HIT);

        List<Actor> actorList1 = new ArrayList<>();
        actorList1.addAll(Arrays.asList(actor4, actor5, actor6, actor7));
        Movie movie1 = new Movie(2, "Poranek kojota", "24-08-2001", 92, "Comedy", "Olaf Lubaszenko", actorList1, MovieCategory.HIT);

        List<Actor> actorList2 = new ArrayList<>();
        actorList2.addAll(Arrays.asList(actor4, actor6, actor));
        Movie movie2 = new Movie(3, "Chłopaki nie płaczą", "25-02-2000", 96, "Comedy", "Olaf Lubaszenko", actorList2, MovieCategory.HIT);

        List<Actor> actorList3 = new ArrayList<>();
        actorList3.addAll(Arrays.asList(actor8, actor9));
        Movie movie3 = new Movie(4, "Law Abiding Citizen", "23-09-2009", 109, "Thriller", "F. Gary Gray", actorList3, MovieCategory.HIT);

        List<Actor> actorList4 = new ArrayList<>();
        actorList4.addAll(Arrays.asList(actor10, actor11));
        Movie movie4 = new Movie(5, "Taken", "27-02-2008", 93, "Action", "Pierre Morel", actorList4, MovieCategory.HIT);

        List<Actor> actorList5 = new ArrayList<>();
        actorList5.addAll(Arrays.asList(actor12, actor13, actor14, actor15));
        Movie movie5 = new Movie(6, "The Godfather", "15-03-1972", 175, "Drama", "Francis Ford Coppola", actorList5, MovieCategory.HIT);

        List<Actor> actorList6 = new ArrayList<>();
        actorList6.addAll(Arrays.asList(actor16, actor17, actor18));
        Movie movie6 = new Movie(7, "Goodfellas", "12-09-1990", 146, "Drama", "Martin Scorsese", actorList6, MovieCategory.HIT);

        List<Actor> actorList7 = new ArrayList<>();
        actorList7.addAll(Arrays.asList(actor19, actor20, actor21, actor22));
        Movie movie7 = new Movie(8, "The Departed", "26-09-2006", 152, "Criminal", "Martin Scorsese", actorList7, MovieCategory.HIT);

        List<Actor> actorList8 = new ArrayList<>();
        actorList8.addAll(Arrays.asList(actor23, actor24, actor25));
        Movie movie8 = new Movie(9, "Pirates of the Caribbean: The Curse of the Black Pearl", "28-06-2003", 143, "Adventure", "Gore Verbinski", actorList8, MovieCategory.HIT);

        List<Actor> actorList9 = new ArrayList<>();
        actorList9.addAll(Arrays.asList(actor19, actor26, actor27));
        Movie movie9 = new Movie(10, "Shutter Island", "13-02-2010", 138, "Drama", "Martin Scorsese", actorList9, MovieCategory.HIT);

        //Other
        List<Actor> actorList10 = new ArrayList<>();
        actorList10.addAll(Arrays.asList(actor10, actor27));
        Movie movie10 = new Movie(11, "Schindler's List", "30-11-1993", 195, "Drama", "Steven Spielberg", actorList10, MovieCategory.OTHER);

        List<Actor> actorList11 = new ArrayList<>();
        actorList11.addAll(Arrays.asList(actor28, actor29, actor30));
        Movie movie11 = new Movie(12, "Fight Club", "10-09-1999", 139, "Thriller", "David Fincher", actorList11, MovieCategory.OTHER);

        List<Actor> actorList12 = new ArrayList<>();
        actorList12.addAll(Arrays.asList(actor31, actor16, actor18));
        Movie movie12 = new Movie(13, "Once Upon a Time in America", "17-02-1984", 229, "Drama", "Sergio Leone", actorList12, MovieCategory.OTHER);

        List<Actor> actorList13 = new ArrayList<>();
        actorList13.addAll(Arrays.asList(actor32, actor33, actor34, actor63));
        Movie movie13 = new Movie(14, "The Dark Knight", "14-07-2007", 152, "Action", "Christopher Nolan", actorList13, MovieCategory.OTHER);

        List<Actor> actorList14 = new ArrayList<>();
        actorList14.addAll(Arrays.asList(actor29, actor35, actor36));
        Movie movie14 = new Movie(15, "Inglourious Basterds", "20-05-2009", 153, "War", "Quentin Tarantino", actorList14, MovieCategory.OTHER);

        List<Actor> actorList15 = new ArrayList<>();
        actorList15.addAll(Arrays.asList(actor37, actor38));
        Movie movie15 = new Movie(16, "The Butterfly Effect", "22-01-2004", 113, "Thriller", "J. Mackye Gruber", actorList15, MovieCategory.OTHER);

        List<Actor> actorList16 = new ArrayList<>();
        actorList16.addAll(Arrays.asList(actor20, actor39));
        Movie movie16 = new Movie(17, "The Bourne Identity", "06-06-2002", 119, "Action", "Doug Liman", actorList16, MovieCategory.OTHER);

        List<Actor> actorList17 = new ArrayList<>();
        actorList17.addAll(Arrays.asList(actor40, actor41, actor42));
        Movie movie17 = new Movie(18, "The Boondock Saints", "04-08-1999", 110, "Action", "Troy Duffy", actorList17, MovieCategory.OTHER);

        List<Actor> actorList18 = new ArrayList<>();
        actorList18.addAll(Arrays.asList(actor43, actor44));
        Movie movie18 = new Movie(19, "The Green Mile", "06-12-1999", 188, "Drama", "Frank Darabont", actorList18, MovieCategory.OTHER);

        List<Actor> actorList19 = new ArrayList<>();
        actorList19.addAll(Arrays.asList(actor45, actor46));
        Movie movie19 = new Movie(20, "Intouchables", "23-09-2011", 112, "Drama", "Olivier Nakache", actorList19, MovieCategory.OTHER);

        //News
        List<Actor> actorList20 = new ArrayList<>();
        actorList20.addAll(Arrays.asList(actor43, actor46, actor47));
        Movie movie20 = new Movie(21, "Inferno", "12-10-2016", 121, "Thriller", "Ron Howard", actorList20, MovieCategory.NEW);

        List<Actor> actorList21 = new ArrayList<>();
        actorList21.addAll(Arrays.asList(actor48, actor49));
        Movie movie21 = new Movie(22, "Snowden", "09-09-2016", 134, "Biography", "Oliver Stone", actorList21, MovieCategory.NEW);

        List<Actor> actorList22 = new ArrayList<>();
        actorList22.addAll(Arrays.asList(actor50, actor51));
        Movie movie22 = new Movie(23, "Assassin's Creed", "14-12-2006", 108, "Adventure", "Justin Kurzel", actorList22, MovieCategory.NEW);

        List<Actor> actorList23 = new ArrayList<>();
        actorList23.addAll(Arrays.asList(actor52, actor53));
        Movie movie23 = new Movie(24, "Doctor Strange", "20-10-2016", 115, "Action", "Scott Derrickson", actorList23, MovieCategory.NEW);

        List<Actor> actorList24 = new ArrayList<>();
        actorList24.addAll(Arrays.asList(actor54, actor55));
        Movie movie24 = new Movie(25, "The Accountant", "06-10-2016", 128, "Drama", "Gavin O'Connor", actorList24, MovieCategory.NEW);

        List<Actor> actorList25 = new ArrayList<>();
        actorList25.addAll(Arrays.asList(actor47, actor56));
        Movie movie25 = new Movie(26, "Rogue One: A Star Wars Story", "10-12-2016", 133, "Action", "Gareth Edwards", actorList25, MovieCategory.NEW);

        List<Actor> actorList26 = new ArrayList<>();
        actorList26.addAll(Arrays.asList(actor57, actor58, actor59));
        Movie movie26 = new Movie(27, "Hacksaw Ridge", "04-09-2016", 139, "War", "Mel Gibson", actorList26, MovieCategory.NEW);

        List<Actor> actorList27 = new ArrayList<>();
        actorList27.addAll(Arrays.asList(actor60, actor61, actor62));
        Movie movie27 = new Movie(28, "Arrival", "01-09-2016", 116, "Thriller", "Denis Villeneuve", actorList27, MovieCategory.NEW);

        List<Actor> actorList28 = new ArrayList<>();
        actorList28.addAll(Arrays.asList(actor43, actor63));
        Movie movie28 = new Movie(29, "Sully", "08-09-2016", 96, "Biography", "Clint Eastwood", actorList28, MovieCategory.NEW);

        List<Actor> actorList29 = new ArrayList<>();
        actorList29.addAll(Arrays.asList(actor64, actor65, actor66, actor67));
        Movie movie29 = new Movie(30, "War Dogs", "18-08-2016", 114, "Comedy", "Todd Phillips", actorList29, MovieCategory.NEW);

        moviesInformantStorage.addMovie(movie);
        moviesInformantStorage.addMovie(movie1);
        moviesInformantStorage.addMovie(movie2);
        moviesInformantStorage.addMovie(movie3);
        moviesInformantStorage.addMovie(movie4);
        moviesInformantStorage.addMovie(movie5);
        moviesInformantStorage.addMovie(movie6);
        moviesInformantStorage.addMovie(movie7);
        moviesInformantStorage.addMovie(movie8);
        moviesInformantStorage.addMovie(movie9);
        moviesInformantStorage.addMovie(movie10);
        moviesInformantStorage.addMovie(movie11);
        moviesInformantStorage.addMovie(movie12);
        moviesInformantStorage.addMovie(movie13);
        moviesInformantStorage.addMovie(movie14);
        moviesInformantStorage.addMovie(movie15);
        moviesInformantStorage.addMovie(movie16);
        moviesInformantStorage.addMovie(movie17);
        moviesInformantStorage.addMovie(movie18);
        moviesInformantStorage.addMovie(movie19);
        moviesInformantStorage.addMovie(movie20);
        moviesInformantStorage.addMovie(movie21);
        moviesInformantStorage.addMovie(movie22);
        moviesInformantStorage.addMovie(movie23);
        moviesInformantStorage.addMovie(movie24);
        moviesInformantStorage.addMovie(movie25);
        moviesInformantStorage.addMovie(movie26);
        moviesInformantStorage.addMovie(movie27);
        moviesInformantStorage.addMovie(movie28);
        moviesInformantStorage.addMovie(movie29);

        moviesInformantStorage.addActor(actor);
        moviesInformantStorage.addActor(actor1);
        moviesInformantStorage.addActor(actor2);
        moviesInformantStorage.addActor(actor3);
        moviesInformantStorage.addActor(actor4);
        moviesInformantStorage.addActor(actor5);
        moviesInformantStorage.addActor(actor6);
        moviesInformantStorage.addActor(actor7);
        moviesInformantStorage.addActor(actor8);
        moviesInformantStorage.addActor(actor9);
        moviesInformantStorage.addActor(actor10);
        moviesInformantStorage.addActor(actor11);
        moviesInformantStorage.addActor(actor12);
        moviesInformantStorage.addActor(actor13);
        moviesInformantStorage.addActor(actor14);
        moviesInformantStorage.addActor(actor15);
        moviesInformantStorage.addActor(actor16);
        moviesInformantStorage.addActor(actor17);
        moviesInformantStorage.addActor(actor18);
        moviesInformantStorage.addActor(actor19);
        moviesInformantStorage.addActor(actor20);
        moviesInformantStorage.addActor(actor21);
        moviesInformantStorage.addActor(actor22);
        moviesInformantStorage.addActor(actor23);
        moviesInformantStorage.addActor(actor24);
        moviesInformantStorage.addActor(actor25);
        moviesInformantStorage.addActor(actor26);
        moviesInformantStorage.addActor(actor27);
        moviesInformantStorage.addActor(actor28);
        moviesInformantStorage.addActor(actor29);
        moviesInformantStorage.addActor(actor30);
        moviesInformantStorage.addActor(actor31);
        moviesInformantStorage.addActor(actor32);
        moviesInformantStorage.addActor(actor33);
        moviesInformantStorage.addActor(actor34);
        moviesInformantStorage.addActor(actor35);
        moviesInformantStorage.addActor(actor36);
        moviesInformantStorage.addActor(actor37);
        moviesInformantStorage.addActor(actor38);
        moviesInformantStorage.addActor(actor39);
        moviesInformantStorage.addActor(actor40);
        moviesInformantStorage.addActor(actor41);
        moviesInformantStorage.addActor(actor42);
        moviesInformantStorage.addActor(actor43);
        moviesInformantStorage.addActor(actor44);
        moviesInformantStorage.addActor(actor45);
        moviesInformantStorage.addActor(actor46);
        moviesInformantStorage.addActor(actor47);
        moviesInformantStorage.addActor(actor48);
        moviesInformantStorage.addActor(actor49);
        moviesInformantStorage.addActor(actor50);
        moviesInformantStorage.addActor(actor51);
        moviesInformantStorage.addActor(actor52);
        moviesInformantStorage.addActor(actor53);
        moviesInformantStorage.addActor(actor54);
        moviesInformantStorage.addActor(actor55);
        moviesInformantStorage.addActor(actor56);
        moviesInformantStorage.addActor(actor57);
        moviesInformantStorage.addActor(actor58);
        moviesInformantStorage.addActor(actor59);
        moviesInformantStorage.addActor(actor60);
        moviesInformantStorage.addActor(actor61);
        moviesInformantStorage.addActor(actor62);
        moviesInformantStorage.addActor(actor63);
        moviesInformantStorage.addActor(actor64);
        moviesInformantStorage.addActor(actor65);
        moviesInformantStorage.addActor(actor66);
        moviesInformantStorage.addActor(actor67);

        moviesInformantStorage.addMovieToActor(actor.getId(), movie.getId());
        moviesInformantStorage.addMovieToActor(actor1.getId(), movie.getId());
        moviesInformantStorage.addMovieToActor(actor2.getId(), movie.getId());
        moviesInformantStorage.addMovieToActor(actor3.getId(), movie.getId());
        moviesInformantStorage.addMovieToActor(actor4.getId(), movie1.getId());
        moviesInformantStorage.addMovieToActor(actor5.getId(), movie1.getId());
        moviesInformantStorage.addMovieToActor(actor6.getId(), movie1.getId());
        moviesInformantStorage.addMovieToActor(actor7.getId(), movie1.getId());
        moviesInformantStorage.addMovieToActor(actor4.getId(), movie2.getId());
        moviesInformantStorage.addMovieToActor(actor6.getId(), movie2.getId());
        moviesInformantStorage.addMovieToActor(actor.getId(), movie2.getId());
        moviesInformantStorage.addMovieToActor(actor8.getId(), movie3.getId());
        moviesInformantStorage.addMovieToActor(actor9.getId(), movie3.getId());
        moviesInformantStorage.addMovieToActor(actor10.getId(), movie4.getId());
        moviesInformantStorage.addMovieToActor(actor11.getId(), movie4.getId());
        moviesInformantStorage.addMovieToActor(actor12.getId(), movie5.getId());
        moviesInformantStorage.addMovieToActor(actor13.getId(), movie5.getId());
        moviesInformantStorage.addMovieToActor(actor14.getId(), movie5.getId());
        moviesInformantStorage.addMovieToActor(actor15.getId(), movie5.getId());
        moviesInformantStorage.addMovieToActor(actor16.getId(), movie6.getId());
        moviesInformantStorage.addMovieToActor(actor17.getId(), movie6.getId());
        moviesInformantStorage.addMovieToActor(actor18.getId(), movie6.getId());
        moviesInformantStorage.addMovieToActor(actor10.getId(), movie7.getId());
        moviesInformantStorage.addMovieToActor(actor20.getId(), movie7.getId());
        moviesInformantStorage.addMovieToActor(actor21.getId(), movie7.getId());
        moviesInformantStorage.addMovieToActor(actor22.getId(), movie7.getId());
        moviesInformantStorage.addMovieToActor(actor23.getId(), movie8.getId());
        moviesInformantStorage.addMovieToActor(actor24.getId(), movie8.getId());
        moviesInformantStorage.addMovieToActor(actor25.getId(), movie8.getId());
        moviesInformantStorage.addMovieToActor(actor19.getId(), movie9.getId());
        moviesInformantStorage.addMovieToActor(actor26.getId(), movie9.getId());
        moviesInformantStorage.addMovieToActor(actor27.getId(), movie9.getId());
        moviesInformantStorage.addMovieToActor(actor10.getId(), movie10.getId());
        moviesInformantStorage.addMovieToActor(actor27.getId(), movie10.getId());
        moviesInformantStorage.addMovieToActor(actor28.getId(), movie11.getId());
        moviesInformantStorage.addMovieToActor(actor29.getId(), movie11.getId());
        moviesInformantStorage.addMovieToActor(actor30.getId(), movie11.getId());
        moviesInformantStorage.addMovieToActor(actor31.getId(), movie12.getId());
        moviesInformantStorage.addMovieToActor(actor16.getId(), movie12.getId());
        moviesInformantStorage.addMovieToActor(actor18.getId(), movie12.getId());
        moviesInformantStorage.addMovieToActor(actor32.getId(), movie13.getId());
        moviesInformantStorage.addMovieToActor(actor33.getId(), movie13.getId());
        moviesInformantStorage.addMovieToActor(actor34.getId(), movie13.getId());
        moviesInformantStorage.addMovieToActor(actor63.getId(), movie13.getId());
        moviesInformantStorage.addMovieToActor(actor29.getId(), movie14.getId());
        moviesInformantStorage.addMovieToActor(actor35.getId(), movie14.getId());
        moviesInformantStorage.addMovieToActor(actor36.getId(), movie14.getId());
        moviesInformantStorage.addMovieToActor(actor37.getId(), movie15.getId());
        moviesInformantStorage.addMovieToActor(actor38.getId(), movie15.getId());
        moviesInformantStorage.addMovieToActor(actor20.getId(), movie16.getId());
        moviesInformantStorage.addMovieToActor(actor39.getId(), movie16.getId());
        moviesInformantStorage.addMovieToActor(actor40.getId(), movie17.getId());
        moviesInformantStorage.addMovieToActor(actor41.getId(), movie17.getId());
        moviesInformantStorage.addMovieToActor(actor42.getId(), movie17.getId());
        moviesInformantStorage.addMovieToActor(actor43.getId(), movie18.getId());
        moviesInformantStorage.addMovieToActor(actor44.getId(), movie18.getId());
        moviesInformantStorage.addMovieToActor(actor45.getId(), movie19.getId());
        moviesInformantStorage.addMovieToActor(actor46.getId(), movie19.getId());
        moviesInformantStorage.addMovieToActor(actor43.getId(), movie20.getId());
        moviesInformantStorage.addMovieToActor(actor46.getId(), movie20.getId());
        moviesInformantStorage.addMovieToActor(actor47.getId(), movie20.getId());
        moviesInformantStorage.addMovieToActor(actor48.getId(), movie21.getId());
        moviesInformantStorage.addMovieToActor(actor49.getId(), movie21.getId());
        moviesInformantStorage.addMovieToActor(actor50.getId(), movie22.getId());
        moviesInformantStorage.addMovieToActor(actor51.getId(), movie22.getId());
        moviesInformantStorage.addMovieToActor(actor52.getId(), movie23.getId());
        moviesInformantStorage.addMovieToActor(actor53.getId(), movie23.getId());
        moviesInformantStorage.addMovieToActor(actor54.getId(), movie24.getId());
        moviesInformantStorage.addMovieToActor(actor55.getId(), movie24.getId());
        moviesInformantStorage.addMovieToActor(actor47.getId(), movie25.getId());
        moviesInformantStorage.addMovieToActor(actor56.getId(), movie25.getId());
        moviesInformantStorage.addMovieToActor(actor57.getId(), movie26.getId());
        moviesInformantStorage.addMovieToActor(actor58.getId(), movie26.getId());
        moviesInformantStorage.addMovieToActor(actor59.getId(), movie26.getId());
        moviesInformantStorage.addMovieToActor(actor60.getId(), movie27.getId());
        moviesInformantStorage.addMovieToActor(actor61.getId(), movie27.getId());
        moviesInformantStorage.addMovieToActor(actor62.getId(), movie27.getId());
        moviesInformantStorage.addMovieToActor(actor43.getId(), movie28.getId());
        moviesInformantStorage.addMovieToActor(actor63.getId(), movie28.getId());
        moviesInformantStorage.addMovieToActor(actor64.getId(), movie29.getId());
        moviesInformantStorage.addMovieToActor(actor65.getId(), movie29.getId());
        moviesInformantStorage.addMovieToActor(actor66.getId(), movie29.getId());
        moviesInformantStorage.addMovieToActor(actor67.getId(), movie29.getId());

        UserInformantStorage userInformantStorage = new UserInformantStorage();
        userInformantStorage.addUser(new User(1, "Jan Kowalski"));
        userInformantStorage.rentMovies(1, new int[]{8, 13, 24});
    }
}
