import ch.qos.logback.classic.{Level, LoggerContext}
import org.slf4j.LoggerFactory

/**
 * Performance test for the Property resource.
 */
class PropertyGatlingTest extends Simulation with BaseConfig {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val scn = scenario("Test the Property entity")
        .repeat(2) {
            exec(http("Create new property")
            .post("/api/properties")
            .headers(headers_http)
            .body(StringBody("""{"id":null, "title": "SAMPLE_TEXT", "price": 300000, "description": "SAMPLE_TEXT", "x": 3, "y": 3, "beds": 3, "baths": 3, "squareMeters": 33}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_property_url"))).exitHereIfFailed
            .pause(5)
            .repeat(5) {
                exec(http("Get created property")
                .get("${new_property_url}")
                .headers(headers_http)
                .check(status.is(200))
                .check(jsonPath("$..provinces[0]").is("Scavy")))
                .pause(5)
            }
            .exec(http("Search for properties")
            .get("/api/properties?ax=1&ay=10&bx=10&by=1")
            .headers(headers_http)
            .check(status.is(200))
            .check(jsonPath("$..foundProperties").ofType[Int].not(0)))
            .pause(5)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(injection)
    ).protocols(httpConf)
}
