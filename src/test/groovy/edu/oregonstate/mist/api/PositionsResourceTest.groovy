package edu.oregonstate.mist.api

import edu.oregonstate.mist.api.jsonapi.ResourceObject
import edu.oregonstate.mist.api.jsonapi.ResultObject
import edu.oregonstate.mist.positions.db.PosDAO
import edu.oregonstate.mist.positions.db.PositionMockDAO
import edu.oregonstate.mist.positions.resources.PositionsResource
import org.junit.Before
import org.junit.Test

import javax.ws.rs.core.Response

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class PositionsResourceTest {
    public static final int DATA_SIZE = 10
    PositionsResource positionsResource

    @Test
    void shouldRequireTypeAndOnlySupportStudents() {
        Response response = positionsResource.getPositions("", null)
        assertNotNull(response)
        assertEquals(response.status, 400)
        assertEquals(response.getEntity().class, Error.class)
    }
    @Test
    void shouldListAllPositions() {
        Response response = positionsResource.getPositions("bcName", "student")
        assertNotNull(response)
        assertEquals(response.getEntity().class, ResultObject.class)
        assertEquals(response.status, 200)

        ResultObject resultObject = response.getEntity()
        assertNotNull(resultObject.data)
        assertEquals(resultObject.data.class, ArrayList.class)
        assertEquals(resultObject.data.size(), DATA_SIZE * 2)

        resultObject.data.each {
            assertEquals(it.class, ResourceObject.class)
        }
    }

    @Test
    void shouldReturn400ForEmptyList() {
        Response response = positionsResource.getPositions("empty", "student")
        assertNotNull(response)
        assertEquals(response.getEntity().class, Error.class)
        assertEquals(response.status, 400)
    }

    @Test
    void shouldRequireBusinessCenter() {
        Response response = positionsResource.getPositions("", "student")
        assertNotNull(response)
        assertEquals(response.status, 400)
        assertEquals(response.getEntity().class, Error.class)
        assertEquals(response.getEntity()["developerMessage"],
                "businessCenter (query parameter) is required.")
    }

    @Before
    void setup() {
        PosDAO posDAO = new PositionMockDAO(DATA_SIZE)
        positionsResource = new PositionsResource(posDAO)
    }

    @Test
    void shouldValidateBusinessCenter() {
        Response response = positionsResource.getPositions("invalid-bc", "student")
        assertNotNull(response)
        assertEquals(response.status, 400)
        assertEquals(response.getEntity().class, Error.class)
        assertEquals(response.getEntity()["developerMessage"],
                "The value of businessCenter (query parameter) is invalid.")
    }

    @Test
    void shouldOnlyAcceptStudent() {
        Response response = positionsResource.getPositions("bcName", "faculty")
        assertNotNull(response)
        assertEquals(response.status, 400)
        assertEquals(response.getEntity().class, Error.class)
        assertEquals(response.getEntity()["developerMessage"],
                "type (query parameter) is required. " +
                        "'student' is currently the only supported type.")
    }
}
