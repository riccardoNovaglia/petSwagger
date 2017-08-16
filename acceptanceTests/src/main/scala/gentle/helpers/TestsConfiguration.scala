package gentle.helpers

import com.typesafe.config.{Config, ConfigFactory}

trait TestsConfiguration {
  val config: Config = ConfigFactory.parseResources("acceptance-tests.conf").resolve()
}
