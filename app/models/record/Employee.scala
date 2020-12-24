package models.record

import java.lang.annotation.Documented
import java.time.LocalDate
import play.api.libs.json._

@Documented
case class Employee(_id: String, name: String, dateOfBirth: LocalDate)
case class EmployeeList(employees: List[Employee])
