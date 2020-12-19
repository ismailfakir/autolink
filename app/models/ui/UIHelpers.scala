package models.ui

import views.html.common.customFieldConstructorTemplate

object UIHelpers {
  object MyHelpers {
    import views.html.helper.FieldConstructor
    implicit val myFields = FieldConstructor(customFieldConstructorTemplate.f)
  }

}
