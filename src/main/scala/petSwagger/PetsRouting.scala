package petSwagger

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.{complete, get, path, _}
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{jackson, DefaultFormats}


class PetsRouting(petsRepository: PetsRepository)
  extends Json4sSupport {
  implicit val serialization = jackson.Serialization
  implicit val formats = DefaultFormats

  def all: Route = putPet ~ getById ~ getAll

  def putPet: Route = {
    put {
      path("pets") {
        entity(as[Pet]) { pet =>
          complete(petsRepository.put(pet))
        }
      }
    }
  }

  def getById: Route = {
    get {
      path("pets"/ Remaining ) { id =>
        complete(petsRepository.get(id))
      }
    }
  }

  def getAll: Route = {
    get {
      path("pets") {
        complete(petsRepository.getAll)
      }
    }
  }
}
