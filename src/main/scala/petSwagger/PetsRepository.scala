package petSwagger

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.concurrent.Future.{failed, successful}


class PetsRepository()(implicit private val actors: Actors) {

  val buf: ArrayBuffer[Pet] = ArrayBuffer.empty[Pet]

  def get(id: String): Future[Pet] = buf.find(_.id == id) match {
    case Some(pet)  => successful(pet)
    case None       => failed(new RuntimeException(s"No pets with id $id"))
  }

  def getAll: Future[Seq[Pet]] = successful(buf)

  def put(pet: Pet): Future[Pet] = successful({
    buf += pet
    pet
  })
}

case class Pet(id: String, name: String)
