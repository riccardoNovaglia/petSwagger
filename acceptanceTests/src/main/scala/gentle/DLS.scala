package gentle

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.stream.Materializer
import com.github.tomakehurst.wiremock.client.{MappingBuilder, ResponseDefinitionBuilder}
import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, stubFor, urlEqualTo}
import com.github.tomakehurst.wiremock.http.RequestMethod
import gentle.helpers.CallsSugar

import scala.concurrent.Await
import scala.concurrent.duration._

class ServiceUnderTest(implicit val actorSystem: ActorSystem, val materializer: Materializer) {
  def execute(action: ActionDefinition): GivenKeyword = {
    new GivenKeyword(action.endpoint)
  }
  def apply(action: ActionDefinition): GivenKeyword = execute(action)
}

class GivenKeyword(url: String)(implicit val actorSystem: ActorSystem, val materializer: Materializer)
  extends CallsSugar {
  def Given(executionSteps: => Unit): ThenKeyword = {
    executionSteps
    val response: HttpResponse = Await.result(get(url), 10 seconds)
    new ThenKeyword(new VerifiableResponse(response))
  }

  def Given2(executionSteps: Dependency => Unit): ThenKeyword = {
    executionSteps
    val response: HttpResponse = Await.result(get(url), 10 seconds)
    new ThenKeyword(new VerifiableResponse(response))
  }
}

class ThenKeyword(response: VerifiableResponse) {
  def Then(verifications: VerifiableResponse => Unit): Unit = {
    verifications(response)
  }
}

class ReturnsKeyword(method: RequestMethod, endpoint: String) {
  def returnsJson(responseBody: String): Unit = {
    stubFor(new MappingBuilder(method, urlEqualTo(endpoint))
      .willReturn(aResponse()
        .withBody(responseBody).withHeader("Content-Type", "application/json")))
  }

  def returns(builder: ResponseDefinitionBuilder): Unit =
    stubFor(new MappingBuilder(method, urlEqualTo(endpoint))
      .willReturn(builder))
}
