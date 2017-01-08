#Movies REST API

Movies REST API makes it easy to manage a collection of movies and actors. It stores information for each movie and actor and displays them to user in JSON format. This application has all required designed to facilitate the management of the collection, for example:

- displaying all actors and movies,
- displaying simple actor or movie by id,
- editing simple actor or movie by id,
- deleting simple actor or movie by id,
- displaying user movies,
- renting movies by user,
- returning movies by user.

Movies REST API also has some additional benefits. For example you can not create new movie without any actor. And if you want to remove an actor, he/she will be removed from all movies where he/she was playing.


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

Use example with result for actor:

	curl http://localhost:8080/actor/13
	{"id":13,"name":"Marlon Brando"}

Use example with result for movie:

	curl http://localhost:8080/movie/1
	{"id":1,"title":"Kiler","releaseDate":"17-10-1997","time":104,"type":"Comedy","director":"Juliusz Machulski","actorList":[{"id":1,"name":"Cezary Pazura"},{"id":2,"name":"Malgorzata Kozuchowska"},{"id":3,"name":"Jerzy Stuhr"},{"id":4,"name":"Janusz Rewinski"}],"available":true,"category":"HIT"}

Remember, that if you are using terminal, you have to write this code to get unicode characters:
> chcp 65001

###Create actor/movie:
To create actor you have to use this adress:

> http://localhost:8080/newActor

Informations about actor have to be passed by using JSON code. Your JSON code should look like this:

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

In both cases, you have to choose "POST" as your HTTP method. You have to remember that you have to choose "application json" as "content type". After creating new actor/movie, they will be added to list of actors/movies and you will get informations about your creation in JSON format. If actor/movie with id which you try to use, already exists, null will be returned.

Use example with result for actor:

	curl -H "Content-Type: application/json" -X POST -d "{\"id\":70,\"name\":\"Some Actor\"}" http://localhost:8080/newActor
	{"id":70,"name":"Some Actor"}

Use example with result for movie:

	curl -H "Content-Type: application/json" -X POST -d "{\"id\": 31,\"title\": \"Some Title\",\"releaseDate\": \"06-01-2017\",\"time\": 122,\"type\": \"Comedy\",\"director\": \"Some Director\",\"actorList\": [{\"id\": 70,\"name\": \"Some Actor\"}],\"category\": \"OTHER\"}" http://localhost:8080/newMovie
	{"id":31,"title":"Some Title","releaseDate":"06-01-2017","time":122,"type":"Comedy","director":"Some Director","actorList":[{"id":70,"name":"Some Actor"}],"available":true,"category":"OTHER"}

###Edit actor/movie:
To edit actor you have to use this adress:

> http://localhost:8080/editActor/{id}

As id you have to write id of the actor. New informations about the actor you have to pass by using JSON code the same while creating new Actor [Create actor/movie](#create-actormovie). To edit movie you have to use this adress:

> http://localhost:8080/editMovie/{id}

As id you have to write id of the movie. You have to pass new informations about movie by using JSON code the same while creating new Movie [Create actor/movie](#create-actormovie). In both cases, you have to choose "PUT" as your HTTP method. You have to remember that you have to choose "application json" as "content type". After editing actor/movie you will get informations about new version of your object in JSON format.

Use example with result for actor:

	curl -H "Content-Type: application/json" -X PUT -d "{\"id\":70,\"name\":\"Some Edited Actor\"}" http://localhost:8080/editActor/70
	{"id":70,"name":"Some Edited Actor"}

Use example with result for movie:

	curl -H "Content-Type: application/json" -X PUT -d "{\"id\": 31,\"title\": \"Some Edited Title\",\"releaseDate\": \"06-01-2017\",\"time\": 95,\"type\": \"Action\",\"director\": \"Some Director\",\"actorList\": [{\"id\": 70,\"name\": \"Some Edited Actor\"}],\"category\": \"HIT\"}"  http://localhost:8080/editMovie/31
	{"id":31,"title":"Some Edited Title","releaseDate":"06-01-2017","time":95,"type":"Action","director":"Some Director","actorList":[{"id":70,"name":"Some Edited Actor"}],"available":true,"category":"HIT"}

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
	
###Create user:
To create user you have to use this adress:

> http://localhost:8080/newUser

Informations about user have to be passed by using JSON code. Your JSON code should look like this:

	{
      "id": ...,
      "name": "..."
    }

You have to choose "POST" as your HTTP method. After creating new user, he will be added to list of users and you will get informations about your creation in JSON format. If user with id which you try to use, already exists, null will be returned. 

Use example with result:

	curl -H "Content-Type: application/json" -X POST -d "{\"id\":2,\"name\":\"New User\"}" http://localhost:8080/newUser
	{"id":2,"name":"New User","debt":0}

###Get user movies:
To get list of movies rented by user you have to use this adress:

> http://localhost:8080/userMovies/{id}

As id you have to write id of user. You have to choose "GET" as your HTTP method. After that, you will get information about all movies rented by user in JSON format.

Use example with result:

	curl http://localhost:8080/userMovies/1
	[{"id":8,"title":"The Departed","releaseDate":"26-09-2006","time":152,"type":"Criminal","director":"Martin Scorsese","actorList":[{"id":20,"name":"Leonardo DiCaprio"},{"id":21,"name":"Matt Damon"},{"id":22,"name":"Jack Nicholson"},{"id":23,"name":"Mark Wahlberg"}],"available":false,"category":"HIT"},{"id":13,"title":"Once Upon a Time in America","releaseDate":"17-02-1984","time":229,"type":"Drama","director":"Sergio Leone","actorList":[{"id":32,"name":"James Wood"},{"id":17,"name":"Robert De Niro"},{"id":19,"name":"Joe Pesci"}],"available":false,"category":"OTHER"},{"id":24,"title":"Doctor Strange","releaseDate":"20-10-2016","time":115,"type":"Action","director":"Scott Derrickson","actorList":[{"id":53,"name":"Benedict Cumberbatch"},{"id":54,"name":"Rachel McAdams"}],"available":false,"category":"NEW"}]

###Rent movies:
To rent movies by user you have to use this adress:

> http://localhost:8080/rentMovies/{userId}/{moviesId}

As "userId" you have to write id of user. As "moviesId" you have to write movies id, separated by commas. You have to choose "PUT" as your HTTP method. After that, you will get information about just rented movies in JSON format.

Use example with result:

	curl -X PUT http://localhost:8080/rentMovies/1/3,25
	[{"id":3,"title":"Chlopaki nie placza","releaseDate":"25-02-2000","time":96,"type":"Comedy","director":"Olaf Lubaszenko","actorList":[{"id":5,"name":"Maciej Stuhr"},{"id":7,"name":"Michal Milowicz"},{"id":1,"name":"Cezary Pazura"}],"available":false,"category":"HIT"},{"id":25,"title":"The Accountant","releaseDate":"06-10-2016","time":128,"type":"Drama","director":"Gavin O'Connor","actorList":[{"id":55,"name":"Anna Kendrick"},{"id":56,"name":"Ben Affleck"}],"available":false,"category":"NEW"}]

###Return movies:
To return movies by user you have to use this adress:

> http://localhost:8080/returnMovies/{userId}/{moviesId}

As "userId" you have to write id of user. As "moviesId" you have to write movies id, separated by commas. You have to choose "DELETE" as your HTTP method. After that, you will get information about movies, which are still rented by user, in JSON format.

Use example with result:

	curl -X DELETE http://localhost:8080/returnMovies/1/3,8,13
	[{"id":24,"title":"Doctor Strange","releaseDate":"20-10-2016","time":115,"type":"Action","director":"Scott Derrickson","actorList":[{"id":53,"name":"Benedict Cumberbatch"},{"id":54,"name":"Rachel McAdams"}],"available":false,"category":"NEW"},{"id":25,"title":"The Accountant","releaseDate":"06-10-2016","time":128,"type":"Drama","director":"Gavin O'Connor","actorList":[{"id":55,"name":"Anna Kendrick"},{"id":56,"name":"Ben Affleck"}],"available":false,"category":"NEW"}]
