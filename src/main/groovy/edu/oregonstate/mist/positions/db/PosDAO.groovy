package edu.oregonstate.mist.positions.db

import edu.oregonstate.mist.positions.core.Position

abstract class PosDAO {
    List<Position> getPositions(String businessCenter) {
        new ArrayList<Position>()
    }
}