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
 - [curl](https://curl.haxx.se/)

##Getting started:
To run the application you have firstly to import the project to IntelliJ. In next step you have to build the project, you can do it for example by choosing "Build" in menu and later by choosing "Build project".

![Building project.](http://i.imgur.com/i0eE33b.png)

After that, you have to run "MoviesInformat", you can do it for example by pressing right button on "MoviesInformant" class and choosing "Run 'MoviesInformant.main()'".

![Starting application.](http://i.imgur.com/b3puGCI.png)

After that, you can use curl to display simple welcome message. You need to use this URL: 

> http://localhost:8080/

You have to choose "GET" as your HTTP method. This is how your code should look and the result:

> curl http://localhost:8080/

If all went good, you should see welcome message.

    Welcome in Movies REST API!

###Display actors/movies list:
To display actors list you have to write this adress: 

> http://localhost:8080/actors

And to display movies lists, you need to use this URL: 

> http://localhost:8080/movies

In both cases, you have to choose "GET" as your HTTP method. Result will be returned in JSON format. 

Use example with result:

    curl http://localhost:8080/actors
    [{"id":1,"name":"Cezary Pazura"},{"id":2,"name":"Malgorzata Kozuchowska"},{"id":3,"name":"Jerzy Stuhr"},...]

###Display actor/movie:
To display simple actor you have to use this adress: 

> http://localhost:8080/actor/{id} 

As id you have to write id of actor. To get informations about movie, you have to write this URL: 

> http://localhost:8080/movie/{id}

As id you have to write id of movie. For example to display informations about actor with id number "1", you have to use this URL: 

> http://localhost:8080/actor/1

And the same for movie. If you want to get informations about movie with id number "1", you have to use this URL:

> http://localhost:8080/movie/1

In both cases, you have to choose "GET" as your HTTP method. Informations about actor/movie will be returned in JSON format. 

	curl http://localhost:8080/movie/1
	{"id":1,"title":"Kiler","releaseDate":"17-10-1997","time":104,"type":"Comedy","director":"Juliusz Machulski","actorList":[{"id":1,"name":"Cezary Pazura"},{"id":2,"name":"Malgorzata Kozuchowska"},{"id":3,"name":"Jerzy Stuhr"},{"id":4,"name":"Janusz Rewinski"}],"available":true,"category":"HIT"}

Remember, that if you are using terminal, you have to write this code to get unicode characters:
> chcp 65001

###Create actor/movie:
To create actor you have to use this adress:

> http://localhost:8080/newActor

Informations about actor you have to pass by using JSON code. Your JSON code should look like this:

	{
      "id": ...,
      "name": "..."
    }

To create movie you have to use this adress:

> http://localhost:8080/newMovie

As in the case of an actor, during creating new movie you have to pass informations about movie by using JSON code. Your JSON code should look like this:


	{
	  "id": ...,
	  "title": "...",
	  "releaseDate": "...",
	  "time": ...,
	  "type": "...",
	  "director": "...",
	  "actorList": [
		{
		  "id": ...,
		  "name": "..."
		}
	  ],
	  "category": "..."
	}

In both cases, you have to choose "POST" as your HTTP method. You have to remember that you have to choose "application json" as "content type". After creating new actor/movie, they will be added to list of actors/movies and you will get informations about your creation in JSON format. If actor/movie with id which your try to use, already exists, null will be returned.

Use example with result for actor:

	curl -H "Content-Type: application/json" -X POST -d "{\"id\":70,\"name\":\"Some Actor\"}" http://localhost:8080/newActor
	{"id":70,"name":"Some Actor"}

Use example with result for movie:

	curl -H "Content-Type: application/json" -X POST -d "{\"id\": 31,\"title\": \"Some Title\",\"releaseDate\": \"06-01-2017\",\"time\": 122,\"typ
	e\": \"Comedy\",\"director\": \"Some Director\",\"actorList\": [{\"id\": 70,\"name\": \"Some Actor\"}],\"category\": \"OTHER\"}" http://localhost:8080/newMovie
	{"id":31,"title":"Some Title","releaseDate":"06-01-2017","time":122,"type":"Comedy","director":"Some Director","actorList":[{"id":70,"name":"Some Actor"}],"available":true,"category"
	:"OTHER"}

###Edit actor/movie:
To edit actor you have to use this adress:

> http://localhost:8080/editActor/{id}

As id you have to write id of the actor. New informations about the actor you have to pass by using JSON code the same like during creating new Actor [Create-actor/movie](#Create-actor/movie). To edit movie you have to use this adress:

> http://localhost:8080/editMovie/{id}

As id you have to write id of the movie. You have to pass new informations about movie by using JSON code the same like during creating new Movie [Create-actor/movie](#Create-actor/movie). In both cases, you have to choose "PUT" as your HTTP method. After editing actor/movie you will get informations about new version of your object in JSON format.

Use example with result for actor:

	curl -H "Content-Type: application/json" -X PUT -d "{\"id\":70,\"name\":\"Some Edited Actor\"}" http://localhost:8080/editActor/70
	{"id":70,"name":"Some Edited Actor"}

Use example with result for movie:

	curl -H "Content-Type: application/json" -X PUT -d "{\"id\": 31,\"title\": \"Some Edited Title\",\"releaseDate\": \"06-01-2017\",\"time\": 95,
	\"type\": \"Action\",\"director\": \"Some Director\",\"actorList\": [{\"id\": 70,\"name\": \"Some Edited Actor\"}],\"category\": \"HIT\"}" http://localhost:8080/editMovie/31
	{"id":31,"title":"Some Edited Title","releaseDate":"06-01-2017","time":95,"type":"Action","director":"Some Director","actorList":[{"id":70,"name":"Some Edited Actor"}],"available":tr
	ue,"category":"HIT"}

###Remove actor/movie:
To remove actor you have to use this adress:

> http://localhost:8080/deleteActor/{id}

As id you have to write id of actor. To remove movie, you have to write this URL: 

> http://localhost:8080/deleteMovie/{id}

As id you have to write id of movie. In both cases, you have to choose "DELETE" as your HTTP method. After deletion, new list of actors/movies will be returned in JSON format.

Use example with result for actor:

	curl -X DELETE http://localhost:8080/deleteActor/2
	[{"id":1,"name":"Cezary Pazura"},{"id":3,"name":"Jerzy Stuhr"},...]

Use example with result for movie:

	curl -X DELETE http://localhost:8080/deleteMovie/2
	[{"id":1,"title":"Kiler","releaseDate":"17-10-1997","time":104,"type":"Comedy","director":"Juliusz Machulski","actorList":[{"id":1,"name":"Cezary Pazura"},{"id":3,"name":"Jerzy Stuhr"},{"id":4,"name":"Janusz Rewinski"}],"available":true,"category":"HIT"},{"id":3,"title":"Chlopaki nie placza", ...}, ...]









