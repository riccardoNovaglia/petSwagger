package petSwagger

import org.json4s.DefaultFormats
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FreeSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures

class PetsRepositorySpec
  extends FreeSpec
          with Matchers
          with MockFactory
          with ScalaFutures
          with TestActorSystemAndMaterializer
{
  implicit val formats = DefaultFormats

  val itemsRepo = new PetsRepository()
  val failureCause = new RuntimeException("Something happened")

  "The Items Repository" - {

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
