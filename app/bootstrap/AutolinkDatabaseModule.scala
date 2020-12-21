package bootstrap

import com.google.inject.AbstractModule

class AutolinkDatabaseModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[InitialData]).asEagerSingleton()
  }
}
