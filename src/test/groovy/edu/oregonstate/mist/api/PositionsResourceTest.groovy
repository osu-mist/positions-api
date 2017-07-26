package edu.oregonstate.mist.api

import edu.oregonstate.mist.api.jsonapi.ResourceObject
import edu.oregonstate.mist.api.jsonapi.ResultObject
import edu.oregonstate.mist.positions.db.PositionDAO
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
        assertEquals(resultObject.data.size(), DATA_SIZE)

        resultObject.data.each {
            assertEquals(it.class, ResourceObject.class)
        }
    }

    @Test
    void shouldReturn200ForEmptyList() {
        Response response = positionsResource.getPositions("empty", "student")
        assertNotNull(response)
        assertEquals(response.getEntity().class, ResultObject.class)
        assertEquals(response.status, 200)

        ResultObject resultObject = response.getEntity()
        assertNotNull(resultObject.data)
        assertEquals(resultObject.data.class, ArrayList.class)
        assertEquals(resultObject.data.size(), 0)
    }

    @Test
    void shouldRequireBusinessCenter() {
        Response response = positionsResource.getPositions("", "student")
        assertNotNull(response)
        assertEquals(response.status, 400)
        assertEquals(response.getEntity().class, Error.class)
    }

    @Before
    void setup() {
        PositionDAO positionDAO = new PositionMockDAO(DATA_SIZE)
        positionsResource = new PositionsResource(positionDAO)
    }

}
