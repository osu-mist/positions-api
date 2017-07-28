package edu.oregonstate.mist.positions.db

import edu.oregonstate.mist.positions.core.Position

interface PositionDAO {
    List<Position> getPositions(String businessCenter)
}