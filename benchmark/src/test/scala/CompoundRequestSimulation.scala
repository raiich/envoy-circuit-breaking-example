import io.gatling.core.Predef.{Simulation, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.{http, status}

import scala.concurrent.duration._

class CompoundRequestSimulation extends Simulation {
  val qps: Int = sys.env.getOrElse("QPS", "100").toInt
  val duration: FiniteDuration = sys.env.getOrElse("DURATION", "1").toInt.minutes

  val scn1: ScenarioBuilder = {
    val server = sys.env.getOrElse("URL", "http://localhost:8080")
    val request = sys.env.getOrElse("REQUEST_1", "/envoy")

    scenario("CompoundRequestSimulation1").exec(
      http("app-request-1")
        .get(s"$server$request")
        .check(status.is(200))
    )
  }
  val scn2: ScenarioBuilder = {
    val server = sys.env.getOrElse("URL", "http://localhost:8080")
    val request = sys.env.getOrElse("REQUEST_2", "/local")

    scenario("CompoundRequestSimulation2").exec(
      http("app-request-2")
        .get(s"$server$request")
        .check(status.is(200))
    )
  }

  setUp(
    scn1
      .inject(
        rampUsersPerSec(qps / 4) to qps during (duration / 4),
        constantUsersPerSec(qps) during (duration / 4),
        rampUsersPerSec(qps) to (qps * 1.5) during (duration / 4),
        constantUsersPerSec(qps) during (duration / 4),
      ),
    scn2
      .inject(
        constantUsersPerSec(qps) during duration,
      ),
  ).protocols(
    http
      .connectionHeader("keep-alive")
      .shareConnections
  )
}
