package computerdatabase;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ScenarioDemo extends Simulation {

    HttpProtocolBuilder req = http
            .baseUrl("https://gatling.io");

    ScenarioBuilder myscenario = scenario("Home Page")        // Scenario Creation
            .exec(http("T00_Home").get("/"));                                // Attaching methods

    ScenarioBuilder multipleRequests = scenario("MultipleRequests")
            .exec(http("OpenSource").get("/open-source/"))            // Multiple methods
            .exec(http("Enterprices").get("/enterprise/"))
            .pause(3);

    ChainBuilder features = exec(http("FeaturesPage").get("/features/"))            // Composable requests
            .pause(3);

    ChainBuilder continueServices = exec(http("Services_page").get("/services/"))    // Composable requests
            .pause(3);

    ScenarioBuilder contactAndContinueScenario = scenario("Contact and Continue ") // Scenario  for composable reqs
            .exec(features, continueServices);

    {
        setUp(
                myscenario.injectOpen(atOnceUsers(1)),
                multipleRequests.injectOpen(atOnceUsers(1)),
                contactAndContinueScenario.injectOpen(atOnceUsers(1))
        ).protocols(req);
    }

}
