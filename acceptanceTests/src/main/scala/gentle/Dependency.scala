package gentle

trait Dependency {
  def execute(action: ActionDefinition): ReturnsKeyword = {
    new ReturnsKeyword(action.method, action.endpoint)
  }
  def apply(action: ActionDefinition): ReturnsKeyword = execute(action)
}


