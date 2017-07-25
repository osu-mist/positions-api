package edu.oregonstate.mist.positions

import edu.oregonstate.mist.api.Application
import edu.oregonstate.mist.api.Configuration
import edu.oregonstate.mist.positions.db.PositionDAO
import edu.oregonstate.mist.positions.db.PositionMockDAO
import edu.oregonstate.mist.positions.resources.PositionsResource
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

/**
 * Main application class.
 */
class PositionsApplication extends Application<PositionsConfiguration> {
    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(PositionsConfiguration configuration, Environment environment) {
        this.setup(configuration, environment)
        PositionDAO positionDAO = getPositionDAO(configuration, environment)

        environment.jersey().register(new PositionsResource(positionDAO))
        // @todo: register healthcheck
    }

    private PositionDAO getPositionDAO(PositionsConfiguration configuration,
                                       Environment environment) {
        PositionDAO positionDAO
        if(configuration.useTestDAO) {
            //@todo: change this to a config value
            positionDAO = new PositionMockDAO(1000)
        } else {
            DBIFactory factory = new DBIFactory()
            DBI jdbi = factory.build(environment, configuration.getDatabase(), "jdbi")
//            (AbstractPositionDAO) jdbi.onDemand(PositionsDAO.class)
        }

        positionDAO
    }

    /**
     * Instantiates the application class with command-line arguments.
     *
     * @param arguments
     * @throws Exception
     */
    public static void main(String[] arguments) throws Exception {
        new PositionsApplication().run(arguments)
    }
}
