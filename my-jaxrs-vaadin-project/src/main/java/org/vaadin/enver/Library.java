
package org.vaadin.enver;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import java.util.*;

@ApplicationPath("/library")
@Path("/api/1")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class Library extends Application {
    @GET
    @Path("/registered-modules")
    public List<Map<String, String>> getRegisteredModuiles(){
        Map<String, String> foo = new HashMap<>();
        foo.put("eins", "one");
        Map<String, String> bar = new HashMap<>();
        bar.put("zwei", "two");
        return Arrays.asList(foo, bar);
    }
}
