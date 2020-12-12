package models.ui

case class Menu (name: String, href: String, icon: String)
case class MenuGroup(name: String,icon: String, menuList: List[Menu])
case class Navbar(name: String, menuGroupList: List[MenuGroup])

object MenuGroup {
  val dashboard = MenuGroup(
    "Dashboard",
    "fas fa-fw fa-server",
    List(
      Menu("Users","/dashboard","fas fa-fw fa-users"),
      Menu("Configuration","/dashboard","fas fa-fw fa-cogs"),
      Menu("Tools","/dashboard","fas fa-fw fa-wrench")
    )
  )

  val blank = MenuGroup(
    "blank", // will list only menu
    "fas fa-fw fa-tasks",
    List(
      Menu("Blank Page","/blank","fas fa-fw fa-file"),
      Menu("Logs","/blank","fas fa-fw fa-tags")
    )
  )

  val all = List(dashboard,blank)

}