#Movies REST API

Movies REST API makes it easy to manage a collection of movies and actors. It stores information for each movie and actor and displays them to user in JSON format. This application has all required designed to facilitate the management of the collection, for example:

- displaying all actors and movies,
- displaying simple actor or movie by id,
- editing simple actor or movie by id,
- deleting simple actor or movie by id.

Movies REST API also has some additional benefits. For example it displays special information if you try to display empty list of actors/movies or you try to get informations about actors/movies which do not exist. Also you can not create new movie without any actor. And if you want to remove an actor, he/she will be removed from all movies where he/she was playing.


##Requirements:

 - Java JDK 8
 - IntelliJ IDEA Community
 - Any tool to send HTTP requests, for example: [HttpRequester](https://addons.mozilla.org/pl/firefox/addon/httprequester/) for
   Firefox, [Postman](https://chrome.google.com/webstore/detail/postman-rest-client/fhbjgbiflinjbdggehcddcbncdddomop) for Chrome


##Getting started:
To run the application you have firstly to import the project to IntelliJ. After that, you have to run "MoviesInformat", you can do it for example by pressing right button on "MoviesInformant" class and choosing "Run 'MoviesInformant.main()'".

![Starting application.](http://i.imgur.com/b3puGCI.png)

After that, you can use your tool to display simple welcome message. In your tool you need to write this URL: 

> http://localhost:8080/

And you have to choose "GET" as your HTTP method. If all went good, you should see welcome message.

![Welcome message.](http://i.imgur.com/w1NBe9P.png)

###Display actors/movies list:
To display actors list you have to write this adress: 

> http://localhost:8080/actors

And to display movies lists, you need to use this URL: 

> http://localhost:8080/movies

In both cases, you have to choose "GET" as your HTTP method. Result will be returned in JSON format. If some of lists is empty, you will get relevant information.

![Movies list.](http://i.imgur.com/TohHLJm.png)

###Display actor/movie:
To display simple actor you have to use this adress: 

> http://localhost:8080/actor/{id} 

As id you have to write id of actor. To get informations about movie, you have to write this URL: 

> http://localhost:8080/movie/{id}

As id you have to write id of movie. For example to display informations about actor with id number "1", you have to use this URL: 

> http://localhost:8080/actor/1

And the same for movie. If you want to get informations about movie with id number "1", you have to use this URL:

> http://localhost:8080/movie/1

In both cases, you have to choose "GET" as your HTTP method. Informations about actor/movie will be returned in JSON format. If actor/movie with this id does not exist, you will get relevant information.

![Simple actor.](http://i.imgur.com/W0NXeGI.png)

###Create actor/movie:
To create actor you have to use this adress:

> http://localhost:8080/newActor

Informations about actor you have to pass by using headers with following keys:

- id - id of the actor,
- name - full name of the actor.

To create movie you have to use this adress:

> http://localhost:8080/newMovie

As in the case of an actor, during creating new movie you have to pass informations about movie by using headers. Now you have to use the following keys in headers: 

- id - id of the movie,
- title - title of the movie,
- releaseDate - date when the movie was released,
- time - the duration time of the movie,
- type - type of the movie, for example "Comedy", "Action",
- director - full name of the director of the movie,
- actors - ids of actors which play in the movie, separated by commas.

In both cases, you have to choose "POST" as your HTTP method. After creating new actor/movie, they will be added to list of actors/movies and you will get informations about your creation in JSON format. If actor/movie with id which your try to use, already exists, you will get relevant information about it.

![New actor.](http://i.imgur.com/rrPPgjd.png)

![New movie.](http://i.imgur.com/0DtQ8dq.png)

###Edit actor/movie:
To edit actor you have to use this adress:

> http://localhost:8080/editActor/{id}

As id you have to write id of the actor. New informations about the actor you have to pass by using header with the following key:

- name - full name of the actor.

To edit movie you have to use this adress:

> http://localhost:8080/editMovie/{id}

As id you have to write id of the movie. You have to pass new informations about movie by using headers with the following keys:

- title - title of the movie,
- releaseDate - date when the movie was released,
- time - the duration time of the movie,
- type - type of the movie, for example "Comedy", "Action",
- director - full name of the director of the movie,
- actors - ids of actors which play in the movie, separated by commas.

In both cases, you have to choose "POST" as your HTTP method. During editing actor/movie, all headers are optional so you can for example only change name of the movie. After editing actor/movie you will get informations about new version of your object in JSON format. If actor/movie with this id does not exist, you will get relevant information.

![Edit movie.](http://i.imgur.com/aYdRq9Z.png)

###Remove actor/movie:
To remove actor you have to use this adress:

> http://localhost:8080/deleteActor/{id}

As id you have to write id of actor. To remove movie, you have to write this URL: 

> http://localhost:8080/deleteMovie/{id}

As id you have to write id of movie. For example to remove actor with id number "1", you have to use this URL: 

> http://localhost:8080/deleteActor/1

And the same for movie. If you want to remove movie with id number "1", you have to use this URL:

> http://localhost:8080/deleteMovie/1

In both cases, you have to choose "DELETE" as your HTTP method. After deletion, new list of actors/movies will be returned in JSON format. If actor/movie with this id was not existing during attempting to remove, you will get relevant information. 

![Remove actor.](http://i.imgur.com/YmV7MCK.png)