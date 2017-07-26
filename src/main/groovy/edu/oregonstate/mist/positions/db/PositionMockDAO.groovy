package edu.oregonstate.mist.positions.db

import edu.oregonstate.mist.positions.core.Position

class PositionMockDAO implements PositionDAO {
    private static List<String> titles = ["Office Manager", "Retail Food Service",
                                          "Mock Dept", "Science Lab"]
    public int positionSize = 0

    PositionMockDAO(int positionSize) {
        this.positionSize = positionSize
    }

    @Override
    List<Position> getPositions(String businessCenter) {
        if (businessCenter == "empty") {
            return generate(0, null)
        }

        generate(positionSize, businessCenter)
    }

    static List<Position> generate(int size, String businessCenter) {
        List<Position> result = new ArrayList<>()
        def random = new Random()

        if (size) {
            size.times {
                result += new Position(
                        title: chooseTitle(),
                        businessCenter: businessCenter,
                        positionNumber: "C5" + random.nextInt(9999),
                        organizationCode: getOrganizationCode()
                )
            }
        }
        result
    }

    private static int getOrganizationCode() {
        new Random().nextInt(20) + 1111
    }

    private static String chooseTitle() {
        def random = new Random()
        titles[random.nextInt(titles.size())] + " " +
                random.nextInt(111)
    }
}
