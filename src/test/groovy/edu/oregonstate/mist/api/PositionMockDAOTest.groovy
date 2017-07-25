package edu.oregonstate.mist.api

import edu.oregonstate.mist.positions.db.PositionMockDAO
import org.junit.Test

import static org.junit.Assert.*

class PositionMockDAOTest {
    @Test
    void shouldReturnEmptyPositionList() {
        assert !PositionMockDAO.generate(0, null)
    }

    @Test
    void shouldGenerateManyPositions() {
        (1..10).each {
            assertEquals(PositionMockDAO.generate(it, null).size(), it)
        }
    }

    @Test
    void shouldNotGenerateNegativePositions() {
        (-10..-1).each {
            assertEquals(PositionMockDAO.generate(it, null).size(), 0)
        }
    }

    @Test
    void shouldReturnPositionsSpecifiedInConstructor() {
        PositionMockDAO positionMockDAO
        (1..10).each {
            positionMockDAO = new PositionMockDAO(it)
            def positions = positionMockDAO.getPositions("abc")
            assertEquals(positions.size(), it)
            positions.each { assertEquals(it.businessCenter, "abc")}
        }
    }

    @Test
    void shouldReturnEmptyListForEmpty() {
        PositionMockDAO positionMockDAO
        (1..10).each {
            positionMockDAO = new PositionMockDAO(it)
            assertTrue(positionMockDAO.getPositions("empty").isEmpty())
        }
    }
}
