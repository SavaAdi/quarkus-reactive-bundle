package org.acme.resteasyjackson;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        var responseItem = new SomeItem();
        responseItem.property = name;
        System.out.println(responseItem);
        return Uni.createFrom().item(name).onItem().transform(toScramble ->
            Response.ok(responseItem).
                    type(MediaType.APPLICATION_JSON).build()
        );
    }
}
