package gentle.helpers

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer

import scala.concurrent.Future


trait CallsSugar {
  implicit val actorSystem: ActorSystem
  implicit val materializer: Materializer

  def get(endpoint: String): Future[HttpResponse] =
    Http().singleRequest(HttpRequest(uri = endpoint))

  def post(endpoint: String): Future[HttpResponse] = ???
}
