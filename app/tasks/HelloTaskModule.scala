package tasks

import play.api.inject.{SimpleModule, _}
class HelloTaskModule extends SimpleModule(bind[HelloTask].toSelf.eagerly())
