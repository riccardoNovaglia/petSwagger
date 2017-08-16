package petSwagger

import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{jackson, DefaultFormats}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FreeSpec, Matchers}

class ItemsRoutingSpec
  extends FreeSpec
          with Matchers
          with MockFactory
          with ScalatestRouteTest
          with Json4sSupport {

  val itemsRepository: PetsRepository = stub[PetsRepository]
  val routingService: PetsRouting = new PetsRouting(itemsRepository)

  implicit val formats = DefaultFormats
  implicit val serialization = jackson.Serialization

  "The Routing Service" - {
    "put" in {
      ???
    }

    "get" in {
      ???
    }

    "getAll" in {
      ???
    }
  }
}
