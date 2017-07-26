package edu.oregonstate.mist.positions.resources

import com.codahale.metrics.annotation.Timed
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.jsonapi.ResultObject
import edu.oregonstate.mist.positions.db.PositionDAO
import groovy.transform.TypeChecked
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.security.PermitAll
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("positions")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
@TypeChecked
class PositionsResource extends Resource {
    Logger logger = LoggerFactory.getLogger(PositionsResource.class)
    private PositionDAO positionDAO

    PositionsResource(PositionDAO positionDAO) {
        this.positionDAO = positionDAO
    }

    @Timed
    @GET
    Response getPositions(@QueryParam('businessCenter') String businessCenter,
                          @QueryParam('type') String type) {
        if (!businessCenter?.trim()) {
            return badRequest("businessCenter is required").build()
        }

        if (!type?.trim() || !type.equalsIgnoreCase("student")) {
            return badRequest("type is required. Student is currently the only supported type.")
                    .build()
        }

        ok(new ResultObject(
                data: positionDAO.getPositions(businessCenter).collect { it.toResourceObject() }
        )).build()
    }

}
