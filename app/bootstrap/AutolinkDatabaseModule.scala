package bootstrap

import com.google.inject.AbstractModule

class AutolinkDatabaseModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[InitialData]).asEagerSingleton()
    bind(classOf[models.db.EmployeeDAO]).asEagerSingleton()
    bind(classOf[models.db.RecipesDao]).to(classOf[models.db.RecipesDaoSlickPlainSql])
  }
}
