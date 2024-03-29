package models.ui

case class Menu (name: String, href: String, icon: String)
case class MenuGroup(id: Int, name: String,icon: String, menuList: List[Menu])
case class Navbar(name: String, menuGroupList: List[MenuGroup])

object MenuGroup {
  val dashboard = MenuGroup(
    1,
    "Dashboard",
    "fas fa-fw fa-server",
    List(
      Menu("Users","/users","fas fa-fw fa-users"),
      Menu("connections","/connections","fas fa-arrow-circle-up"),
      Menu("Configuration","/dashboard","fas fa-fw fa-cogs"),
      Menu("Tools","/dashboard","fas fa-fw fa-wrench")
    )
  )

  val blank = MenuGroup(
    2,
    "blank", // will list only menu
    "fas fa-fw fa-tasks",
    List(
      Menu("Logs","/logs","fas fa-fw fa-tags")
    )
  )

  val sample = MenuGroup(
    3,
    "Sample",
    "fas fa-fw fa-table",
    List(
      Menu("Blank Page","/blank","fas fa-fw fa-file"),
      //Menu("Form","/sample/form","fas fa-fw fa-file"),
      Menu("Example Page","/example","fas fa-fw fa-file"),
      Menu("Vue Example Page","/vueexample","fas fa-fw fa-file"),
      Menu("Invoice","/invoice","fas fa-fw fa-book")
    )
  )

  val all = List(dashboard,blank,sample)
  val none = List.empty[MenuGroup]

}