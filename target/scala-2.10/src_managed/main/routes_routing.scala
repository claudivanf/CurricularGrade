// @SOURCE:C:/Users/Claudivan/git/projeto/conf/routes
// @HASH:7389a6bd2184ecf927d902ec2f80b170c327cb3b
// @DATE:Wed Feb 26 13:54:04 BRT 2014


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:9
private[this] lazy val controllers_Assets_at1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        

// @LINE:11
private[this] lazy val controllers_Application_addPeriodo2 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("addPeriodo"))))
        

// @LINE:12
private[this] lazy val controllers_Application_addPeriodo3 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("addPeriodo"))))
        

// @LINE:13
private[this] lazy val controllers_Application_remPeriodo4 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("remPeriodo/"),DynamicPart("periodo", """[^/]+""",true))))
        

// @LINE:14
private[this] lazy val controllers_Application_addCadeira5 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("addCadeira/"),DynamicPart("cadeira", """[^/]+""",true),StaticPart("/"),DynamicPart("periodo", """[^/]+""",true))))
        

// @LINE:15
private[this] lazy val controllers_Application_remCadeira6 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("remCadeira/"),DynamicPart("cadeira", """[^/]+""",true))))
        

// @LINE:16
private[this] lazy val controllers_Application_remCadeira7 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("remCadeira/"),DynamicPart("cadeira", """[^/]+""",true))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """addPeriodo""","""controllers.Application.addPeriodo()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """addPeriodo""","""controllers.Application.addPeriodo()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """remPeriodo/$periodo<[^/]+>""","""controllers.Application.remPeriodo(periodo:Int)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """addCadeira/$cadeira<[^/]+>/$periodo<[^/]+>""","""controllers.Application.addCadeira(cadeira:String, periodo:Int)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """remCadeira/$cadeira<[^/]+>""","""controllers.Application.remCadeira(cadeira:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """remCadeira/$cadeira<[^/]+>""","""controllers.Application.remCadeira(cadeira:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:9
case controllers_Assets_at1(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        

// @LINE:11
case controllers_Application_addPeriodo2(params) => {
   call { 
        invokeHandler(controllers.Application.addPeriodo(), HandlerDef(this, "controllers.Application", "addPeriodo", Nil,"GET", """""", Routes.prefix + """addPeriodo"""))
   }
}
        

// @LINE:12
case controllers_Application_addPeriodo3(params) => {
   call { 
        invokeHandler(controllers.Application.addPeriodo(), HandlerDef(this, "controllers.Application", "addPeriodo", Nil,"POST", """""", Routes.prefix + """addPeriodo"""))
   }
}
        

// @LINE:13
case controllers_Application_remPeriodo4(params) => {
   call(params.fromPath[Int]("periodo", None)) { (periodo) =>
        invokeHandler(controllers.Application.remPeriodo(periodo), HandlerDef(this, "controllers.Application", "remPeriodo", Seq(classOf[Int]),"GET", """""", Routes.prefix + """remPeriodo/$periodo<[^/]+>"""))
   }
}
        

// @LINE:14
case controllers_Application_addCadeira5(params) => {
   call(params.fromPath[String]("cadeira", None), params.fromPath[Int]("periodo", None)) { (cadeira, periodo) =>
        invokeHandler(controllers.Application.addCadeira(cadeira, periodo), HandlerDef(this, "controllers.Application", "addCadeira", Seq(classOf[String], classOf[Int]),"POST", """""", Routes.prefix + """addCadeira/$cadeira<[^/]+>/$periodo<[^/]+>"""))
   }
}
        

// @LINE:15
case controllers_Application_remCadeira6(params) => {
   call(params.fromPath[String]("cadeira", None)) { (cadeira) =>
        invokeHandler(controllers.Application.remCadeira(cadeira), HandlerDef(this, "controllers.Application", "remCadeira", Seq(classOf[String]),"POST", """""", Routes.prefix + """remCadeira/$cadeira<[^/]+>"""))
   }
}
        

// @LINE:16
case controllers_Application_remCadeira7(params) => {
   call(params.fromPath[String]("cadeira", None)) { (cadeira) =>
        invokeHandler(controllers.Application.remCadeira(cadeira), HandlerDef(this, "controllers.Application", "remCadeira", Seq(classOf[String]),"GET", """""", Routes.prefix + """remCadeira/$cadeira<[^/]+>"""))
   }
}
        
}

}
     