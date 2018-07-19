package com.jszx.cricket.platform.module.service.impl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.springframework.stereotype.Service;

import com.jszx.cricket.platform.module.service.JaxrsODataService;

@Service("com.jszx.cricket.platform.module.service.JaxrsODataService")
public class JaxrsODataServiceImpl implements JaxrsODataService {

	@Override
	public Response service(HttpServletRequest req, HttpServletResponse resp) {
		String requestMapping = req.getContextPath() + req.getServletPath() + "/DemoService.svc";
		req.setAttribute("requestMapping", requestMapping);
		// create odata handler and configure it with EdmProvider and Processor
		OData odata = OData.newInstance();
		ServiceMetadata edm = odata.createServiceMetadata(new DemoEdmProvider(), new ArrayList<EdmxReference>());
		ODataHttpHandler handler = odata.createHandler(edm);
		handler.register(new DemoEntityCollectionProcessor());

		// let the handler do the work
		handler.process(req, resp);
		return Response.ok().build();
	}

}
