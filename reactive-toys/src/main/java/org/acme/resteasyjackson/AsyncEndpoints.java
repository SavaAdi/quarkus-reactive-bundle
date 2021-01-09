package org.acme.resteasyjackson;

import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
}
