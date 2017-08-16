package gentle

import com.github.tomakehurst.wiremock.http.RequestMethod

trait ActionsDefinition {
  def get(endpoint: String)(implicit baseUrl: String): ActionDefinition = {
    new ActionDefinition(RequestMethod.GET, baseUrl + endpoint)
  }

  def get(endpoint: String, actionName: String)(implicit baseUrl: String): ActionDefinition = {
    new ActionDefinition(RequestMethod.GET, baseUrl + endpoint)
  }
}

class ActionDefinition(val method: RequestMethod, val endpoint: String)
