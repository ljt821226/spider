package com.jszx.cricket.platform.module.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/DemoService.svc")
public interface JaxrsODataService {

	@GET
    @Path("{id:.*}")
	public Response service(@Context HttpServletRequest req, @Context HttpServletResponse resp);

}
