package org.jug.jbpm.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jug.jbpm.model.ERespondType;
import org.jug.jbpm.model.ManagerResp;

@Path("management")
public class Management {

	/**
	 * http://localhost:8080/rest/management/decision/1/Tom/cancel
	 * http://localhost:8080/rest/management/decision/1/Tom/yes
	 * 
	 * @param decisionId
	 * @param manager
	 * @param decision
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	@Path("/decision/{id}/{manager}/{decision}")
	public ManagerResp decision(@PathParam("id") int decisionId, @PathParam("manager") String manager, @PathParam("decision") String decision) {
		
		ManagerResp managerResp = new ManagerResp();
		managerResp.setId(decisionId);
		managerResp.setManager(manager);
		managerResp.setRespond(ERespondType.valueOf(decision.toUpperCase()));
		
		System.out.println(managerResp);
		
		return managerResp;
	}
}
