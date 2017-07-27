package edu.oregonstate.mist.positions.db

import edu.oregonstate.mist.positions.core.Position
import edu.oregonstate.mist.positions.mapper.PositionMapper
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import edu.oregonstate.mist.contrib.AbstractPositionDAO

@RegisterMapper(PositionMapper)
public interface PositionDAO extends Closeable {
      @SqlQuery("SELECT 1 FROM dual")
       Integer checkHealth()

      @SqlQuery(AbstractPositionDAO.getPositions)
      List<Position> getPositions(@Bind("businessCenter") String businessCenter)

      @SqlQuery(AbstractPositionDAO.validateBusinessCenter)
      boolean isValidBC(@Bind("businessCenter") String businessCenter)

        @Override
        void close()
    }