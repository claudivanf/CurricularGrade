// @SOURCE:C:/Users/Claudivan/git/projeto/conf/routes
// @HASH:7389a6bd2184ecf927d902ec2f80b170c327cb3b
// @DATE:Wed Feb 26 13:54:04 BRT 2014

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:6
package controllers {

// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:6
class ReverseApplication {
    

// @LINE:12
// @LINE:11
def addPeriodo(): Call = {
   () match {
// @LINE:11
case () if true => Call("GET", _prefix + { _defaultPrefix } + "addPeriodo")
                                                        
// @LINE:12
case () if true => Call("POST", _prefix + { _defaultPrefix } + "addPeriodo")
                                                        
   }
}
                                                

// @LINE:13
def remPeriodo(periodo:Int): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "remPeriodo/" + implicitly[PathBindable[Int]].unbind("periodo", periodo))
}
                                                

// @LINE:14
def addCadeira(cadeira:String, periodo:Int): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "addCadeira/" + implicitly[PathBindable[String]].unbind("cadeira", dynamicString(cadeira)) + "/" + implicitly[PathBindable[Int]].unbind("periodo", periodo))
}
                                                

// @LINE:16
// @LINE:15
def remCadeira(cadeira:String): Call = {
   (cadeira: @unchecked) match {
// @LINE:15
case (cadeira) if true => Call("POST", _prefix + { _defaultPrefix } + "remCadeira/" + implicitly[PathBindable[String]].unbind("cadeira", dynamicString(cadeira)))
                                                        
// @LINE:16
case (cadeira) if true => Call("GET", _prefix + { _defaultPrefix } + "remCadeira/" + implicitly[PathBindable[String]].unbind("cadeira", dynamicString(cadeira)))
                                                        
   }
}
                                                

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                
    
}
                          
}
                  


// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:6
class ReverseApplication {
    

// @LINE:12
// @LINE:11
def addPeriodo : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.addPeriodo",
   """
      function() {
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "addPeriodo"})
      }
      if (true) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "addPeriodo"})
      }
      }
   """
)
                        

// @LINE:13
def remPeriodo : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.remPeriodo",
   """
      function(periodo) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "remPeriodo/" + (""" + implicitly[PathBindable[Int]].javascriptUnbind + """)("periodo", periodo)})
      }
   """
)
                        

// @LINE:14
def addCadeira : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.addCadeira",
   """
      function(cadeira,periodo) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "addCadeira/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("cadeira", encodeURIComponent(cadeira)) + "/" + (""" + implicitly[PathBindable[Int]].javascriptUnbind + """)("periodo", periodo)})
      }
   """
)
                        

// @LINE:16
// @LINE:15
def remCadeira : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.remCadeira",
   """
      function(cadeira) {
      if (true) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "remCadeira/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("cadeira", encodeURIComponent(cadeira))})
      }
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "remCadeira/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("cadeira", encodeURIComponent(cadeira))})
      }
      }
   """
)
                        

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        
    
}
              
}
        


// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:6
package controllers.ref {


// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:6
class ReverseApplication {
    

// @LINE:11
def addPeriodo(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.addPeriodo(), HandlerDef(this, "controllers.Application", "addPeriodo", Seq(), "GET", """""", _prefix + """addPeriodo""")
)
                      

// @LINE:13
def remPeriodo(periodo:Int): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.remPeriodo(periodo), HandlerDef(this, "controllers.Application", "remPeriodo", Seq(classOf[Int]), "GET", """""", _prefix + """remPeriodo/$periodo<[^/]+>""")
)
                      

// @LINE:14
def addCadeira(cadeira:String, periodo:Int): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.addCadeira(cadeira, periodo), HandlerDef(this, "controllers.Application", "addCadeira", Seq(classOf[String], classOf[Int]), "POST", """""", _prefix + """addCadeira/$cadeira<[^/]+>/$periodo<[^/]+>""")
)
                      

// @LINE:15
def remCadeira(cadeira:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.remCadeira(cadeira), HandlerDef(this, "controllers.Application", "remCadeira", Seq(classOf[String]), "POST", """""", _prefix + """remCadeira/$cadeira<[^/]+>""")
)
                      

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      
    
}
                          
}
        
    