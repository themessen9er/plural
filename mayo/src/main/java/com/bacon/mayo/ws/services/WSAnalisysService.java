package com.bacon.mayo.ws.services;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bacon.core.BaconAnalysisUtil;
import com.bacon.mayo.ws.ServicePaths;
import com.google.inject.Injector;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value=ServicePaths.ANALISYS, description="The service used to analyze")
@Path(ServicePaths.ANALISYS)
public class WSAnalisysService {

	protected final Injector injector;
	
	@Inject
	public WSAnalisysService(
			Injector injector) {
		this.injector = injector;
	}

	@POST
	@ApiOperation(
			value = "Analize the source code")
	public synchronized Response analize() {
		if(BaconAnalysisUtil.isBusy()) {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
		new Thread() {
			public void run() {
				BaconAnalysisUtil.analize(injector);
			};
		}.start();
		return Response.ok("Analisys has started").build();		
	}
	
	@GET
	@Path("/progress")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get the analisys progress value")
	public synchronized Response getProgress() {
		return Response.ok(BaconAnalysisUtil.getProgress()).build();		
	}

}
