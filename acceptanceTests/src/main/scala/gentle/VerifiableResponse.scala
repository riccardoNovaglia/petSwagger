package gentle

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.stream.Materializer
import org.json4s.JValue
import org.json4s.jackson.JsonMethods
import org.scalatest.{Assertion, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration.{FiniteDuration, _}
import scala.language.postfixOps


class VerifiableResponse(httpResponse: HttpResponse)(implicit val actorSystem: ActorSystem, val materializer: Materializer)
  extends Matchers {
  import actorSystem.dispatcher
  private val timeout: FiniteDuration = 200 millis

  val statusCode      : Int     = httpResponse.status.intValue()
  val body            : String  = entityAsString(httpResponse)
  lazy val bodyAsJson : JValue  = JsonMethods.parse(body)

  def isSuccessful: Assertion = withClue(s"Was expecting successful response from service, but was $this") {
    statusCode should (equal(200) or equal(201))
  }

  def contains(aString: String): Assertion = {
    body should include(aString)
  }

  def contains(amount: Int = 1, path: String): Assertion = {
    val itemsAtPath = bodyAsJson \\ path
    withClue(s"Was expecting to find $amount occurrences of '$path' in response body '$body', but:") {
      itemsAtPath.children.length should be(amount)
    }
  }

  def containsAndGet(amount: Int = 1, path: String): (Assertion, JValue) = {
    val itemsAtPath = bodyAsJson \\ path
    val assertion = withClue(s"Was expecting to find $amount occurrences of '$path' in response body '$body', but:") {
      itemsAtPath.children.length should be(amount)
    }
    (assertion, itemsAtPath)
  }

  override def toString: String =
    s"""
       |StatusCode: $statusCode
       |Body      : $body
     """.stripMargin

  private def entityAsString(httpResponse: HttpResponse): String = {
    Await.result(httpResponse.entity.toStrict(timeout).map { _.data }.map(_.utf8String), 200 millis)
  }
}
