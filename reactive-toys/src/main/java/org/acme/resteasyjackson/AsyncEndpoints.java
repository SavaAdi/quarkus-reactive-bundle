package org.acme.resteasyjackson;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.MissingResourceException;
import java.util.concurrent.CompletionStage;

@Path("/")
public class AsyncEndpoints {

    @GET
    @Path("reactive")
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> reactiveHello() {
        return ReactiveStreams.of("h", "e", "l", "l", "o")
                .map(String::toUpperCase)
                .toList()
                .run()
                .thenApply(Object::toString);
    }

    @GET
    @Path("/{name:[a-zA-Z]*}/scramble-mutiny")
    public Uni<Response> getMutinyAnagram(final @PathParam("name") String name) {
        System.out.println("Got this name: " + name);
        return Uni.createFrom().item(name).onItem()
                .transform(toScramble -> {
                            if (toScramble.length() < 3) {
                                throw new MissingResourceException("I", "don't", "care");
                            } else if(toScramble.length() >= 10) {
                                throw new IllegalArgumentException("Too many letters");
                            }
                            else {
                                return Response.ok(name).
                                        type(MediaType.TEXT_PLAIN).build();
                            }
                        })
                .onFailure(MissingResourceException.class)
                    .recoverWithItem(() -> Response.ok(name + " FAILEEED").
                            type(MediaType.TEXT_PLAIN).build())
                .onFailure(IllegalArgumentException.class)
                    .recoverWithItem(() -> Response.ok(name + " FAILEEED IO").
                            type(MediaType.TEXT_PLAIN).build());
    }
}
