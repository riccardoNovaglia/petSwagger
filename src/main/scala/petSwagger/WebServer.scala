package petSwagger

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext
import scala.io.StdIn
import scala.language.{implicitConversions, postfixOps}


object WebServer {

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
  implicit val actors = new Actors(system, materializer, executionContext)

  private val petRepository: PetsRepository = new PetsRepository()
  private val petsRouting: PetsRouting = new PetsRouting(petRepository)

  def main(args: Array[String]) {
    val route: Route = petsRouting.all

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
