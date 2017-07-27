package edu.oregonstate.mist.positions.mapper

import edu.oregonstate.mist.contrib.AbstractPositionDAO
import edu.oregonstate.mist.positions.core.Position
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

class PositionMapper implements ResultSetMapper<Position> {
    public Position map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        new Position(
                title: rs.getString(AbstractPositionDAO.mapperColumnTitle),
                businessCenter: rs.getString("businessCenter"),
                positionNumber: rs.getString(AbstractPositionDAO.mapperColumnPositionNumber),
                organizationCode: rs.getString(AbstractPositionDAO.mapperColumnPositionReports)
        )
    }

}