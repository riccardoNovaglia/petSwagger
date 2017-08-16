package pets

import com.typesafe.config.Config
import gentle.{ActionDefinition, ActionsDefinition, ServiceUnderTest}
import gentle.helpers.{SystemAndMaterializer, TestsConfiguration}
import org.scalatest.FreeSpec

import scala.language.{implicitConversions, postfixOps}

class PetsSpec extends FreeSpec with MyActions with SystemAndMaterializer {

  val myService = new ServiceUnderTest()

  "The app" - {
    "Should return all pets created" in {
      myService(getAllPets) Given {
        // TODO: now what?
        // could be myService(createPet) which maps to -> calling createPet on the service before this call
      } Then { result =>
        result.isSuccessful
        // TODO: define pet response
      }
    }
  }
}


trait MyActions
  extends ActionsDefinition
    with TestsConfiguration { this: SystemAndMaterializer =>
  val serviceConfiguration = new ServiceConfiguration(config)
  implicit val baseUrl: String = serviceConfiguration.baseUrl
  val getAllPets: ActionDefinition = get("pets", "get all pets")
}

class ServiceConfiguration(config: Config) {
  val baseUrl: String = config.getString("service.baseUrl") + "/"
}
