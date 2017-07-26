package edu.oregonstate.mist.api

import edu.oregonstate.mist.positions.core.Position
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
            assertEquals(PositionMockDAO.generate(it, null).size(), it * 2)
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
            assertEquals(positions.size(), it * 2)
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

    @Test
    void shouldGenerateOrganizationCodesInLimitedRange() {
        PositionMockDAO.generate(100, null).each {
            def difference = Math.abs(Integer.valueOf(it.organizationCode) - 1111)
            assertTrue(difference <= 100)
        }
    }

    @Test
    void positionNumberShouldBeUnique() {
        def positions = PositionMockDAO.generate(100, "abc")

        def positionNumbers =  positions.positionNumber
        def uniquePositionNumbers = positionNumbers.unique()

        assertEquals(positionNumbers, uniquePositionNumbers)
    }
}
