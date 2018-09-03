import io.gatling.core.Predef.{Simulation, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.{http, status}

import scala.concurrent.duration._

class SingleRequestSimulation extends Simulation {
  val qps: Int = sys.env.getOrElse("QPS", "100").toInt
  val duration: FiniteDuration = sys.env.getOrElse("DURATION", "1").toInt.minutes

  val scn: ScenarioBuilder = {
    val server = sys.env.getOrElse("URL", "http://localhost:8080")
    val request = sys.env.getOrElse("REQUEST", "/")

    scenario("SingleRequestSimulation").exec(
      http("app-request")
        .get(s"$server$request")
        .check(status.is(200))
    )
  }

  setUp(
    scn
      .inject(
        rampUsersPerSec(qps / 4) to qps during (duration / 4),
        constantUsersPerSec(qps) during (duration / 4),
        rampUsersPerSec(qps) to (qps * 1.5) during (duration / 4),
        constantUsersPerSec(qps) during (duration / 4),
      )
  ).protocols(
    http
      .connectionHeader("keep-alive")
      .shareConnections
  )
}
