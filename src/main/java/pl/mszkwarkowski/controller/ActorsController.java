package pl.mszkwarkowski.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.mszkwarkowski.model.*;
import pl.mszkwarkowski.repository.*;
import springfox.documentation.annotations.ApiIgnore;
import pl.mszkwarkowski.other.Error;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@RestController
@EnableAutoConfiguration
@Api(description = "Actors management")
@RequestMapping(value = "/actors")
public class ActorsController {
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;

    /**
     * @return all actors.
     */
    @ApiOperation(value = "Return actors details.", notes = "Return a complete list of actors with their details. To get only one actor, it is needed to give actor's id as parameter.", response = Actor.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved details.", response = Actor.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Actor with this id can not be found.", response = Error.class)}
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Actor's id. Only one actor with the given id will be returned.", required = false, allowMultiple = false, paramType = "query", dataType = "integer")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = {MediaType.APPLICATION_JSON})
    public List<Actor> getActorsData() {
        return (List<Actor>) actorRepository.findAll();
    }

    /**
     * @param actor - Actor object, created from received JSON code.
     * @return Actor object.
     * @throws ForbiddenException and status code 403 if actor with this id already exists.
     */
    @ApiOperation(httpMethod = "POST", value = "Add new actor.", notes = "Add new actor and return his details.", response = Actor.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added new actor.", response = Actor.class),
            @ApiResponse(code = 403, message = "Tried to add actor with id which is already taken by other actor.", response = Error.class)}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON})
    public Actor addNewActor(@ApiParam(name = "actor", value = "New actor's data.", required = true) @RequestBody Actor actor) throws ForbiddenException {
        if (actorRepository.findOne(actor.getId()) != null) {
            throw new ForbiddenException("Actor with this id already exists");
        }
        actorRepository.save(actor);
        return actorRepository.findOne(actor.getId());
    }

    /**
     * @param id of actor.
     * @return Actor object.
     * @throws NotFoundException and status code 404 if actor with this id does not exist.
     */
    @ApiIgnore
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = {MediaType.APPLICATION_JSON}, params = "id")
    public Actor actorData(@RequestParam(value = "id", required = true) int id) throws NotFoundException {
        Actor actor = actorRepository.findOne(id);
        if (actor == null) {
            throw new NotFoundException("Actor with this id does not exist.");
        }
        return actor;
    }

    /**
     * @param id    of actor.
     * @param actor - Actor object, created from received JSON code.
     * @return Actor object.
     * @throws NotFoundException  and status code 404 if actor with this id does not exist.
     * @throws ForbiddenException and status code 403 if ids are not the same.
     */
    @ApiOperation(httpMethod = "PUT", value = "Edit actor.", notes = "Edit actor and return this actor with his details.", response = Actor.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited actor.", response = Actor.class),
            @ApiResponse(code = 404, message = "Tried to edit actor whom can not be found.", response = Error.class),
            @ApiResponse(code = 403, message = "Tried to edit actor with incompatible id.", response = Error.class)}
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON}, params = "id")
    public Actor editActor(@ApiParam(name = "id", value = "Id of actor whom has to be edited.", required = true) @RequestParam(value = "id", required = true) int id, @ApiParam(name = "actor", value = "Actor's data after edition.", required = true) @RequestBody Actor actor) throws NotFoundException, ForbiddenException {
        if (actorRepository.findOne(id) == null) {
            throw new NotFoundException("Actor with this id does not exist.");
        }
        if (id != actor.getId()) {
            throw new ForbiddenException("Wrong object id.");
        }
        actorRepository.save(actor);
        return actorRepository.findOne(actor.getId());
    }

    /**
     * @param id of actor
     * @return list of Actor objects.
     * @throws NotFoundException and status code 404 if actor can not be found before deletion.
     */
    @ApiOperation(httpMethod = "DELETE", value = "Remove actor.", notes = "Remove actor and return list of other actors with their details.", response = Actor.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed actor.", response = Actor.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Tried to remove actor whom can not be found.", response = Error.class)}
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(produces = {MediaType.APPLICATION_JSON}, params = "id")
    public List<Actor> deleteActor(@ApiParam(name = "id", value = "Id of actor whom has to be removed.", required = true) @RequestParam(value = "id", required = true) int id) throws NotFoundException {
        if (actorRepository.findOne(id) != null) {
            actorRepository.delete(id);
        } else {
            throw new NotFoundException("Actor with this id can not be found.");
        }
        return (List<Actor>) actorRepository.findAll();
    }
}