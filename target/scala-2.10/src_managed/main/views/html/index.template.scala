
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[PlanoDeCurso,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(plano: PlanoDeCurso):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._


Seq[Any](format.raw/*1.23*/("""

"""),format.raw/*4.1*/("""
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type='text/javascript' src=""""),_display_(Seq[Any](/*6.38*/routes/*6.44*/.Assets.at("javascripts/funcoes.js"))),format.raw/*6.80*/(""""></script>
<link rel="stylesheet" href=""""),_display_(Seq[Any](/*7.31*/routes/*7.37*/.Assets.at("stylesheets/main.css"))),format.raw/*7.71*/("""">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<body style='background-image:url("""),_display_(Seq[Any](/*10.36*/routes/*10.42*/.Assets.at("images/bg-1.jpg"))),format.raw/*10.71*/(""");'>
"""),_display_(Seq[Any](/*11.2*/main("Plano de Curso")/*11.24*/ {_display_(Seq[Any](format.raw/*11.26*/("""
	<center><h2>"""),_display_(Seq[Any](/*12.15*/Messages("planoDeCurso"))),format.raw/*12.39*/("""</h2></center>
	
	<!-- BOTAO ADD PERIODO 
    <span id="addPeriodo">
		<a href="/addPeriodo">"""),_display_(Seq[Any](/*16.26*/Messages("addPeriodo"))),format.raw/*16.48*/("""</a>
	</span>
	 -->
	<h3 style="position:absolute;left:60%;top:50px;">Períodos Do Curso</h3>
	<div id="periodos">
	"""),_display_(Seq[Any](/*21.3*/for(periodo <- plano.getPeriodos()) yield /*21.38*/ {_display_(Seq[Any](format.raw/*21.40*/("""
		<div id=""""),_display_(Seq[Any](/*22.13*/periodo/*22.20*/.getNumero())),format.raw/*22.32*/("""" class="periodo" ondrop="drop(event, this)"
			ondragover="allowDrop(event,this)" ondragleave="leave(event, this)">
		<!--  BOTAO FECHAR DO PERIODO 
		"""),_display_(Seq[Any](/*25.4*/if(periodo.getNumero() != 1)/*25.32*/ {_display_(Seq[Any](format.raw/*25.34*/("""
			<span><a class="close" href="remPeriodo/"""),_display_(Seq[Any](/*26.45*/periodo/*26.52*/.getNumero())),format.raw/*26.64*/("""">X</a></span>
		""")))})),format.raw/*27.4*/("""
		 -->
		
		<ul style="list-style:none;">
			<span class="label label-info" style="width:190px;height:30px;font-size:20px;margin-bottom:10px;margin-left:-40px;">"""),_display_(Seq[Any](/*31.121*/periodo/*31.128*/.getNumero())),format.raw/*31.140*/(""" """),_display_(Seq[Any](/*31.142*/Messages("nPeriodo"))),format.raw/*31.162*/(""" </span>
			"""),_display_(Seq[Any](/*32.5*/for(cadeira <- periodo.getListaCadeiras()) yield /*32.47*/ {_display_(Seq[Any](format.raw/*32.49*/("""
			
		  	<!--  <div class="alocadas" id=""""),_display_(Seq[Any](/*34.39*/cadeira/*34.46*/.getNome())),format.raw/*34.56*/("""" title="remover" 
		  	onclick="remCadeira('"""),_display_(Seq[Any](/*35.28*/cadeira/*35.35*/.getNome())),format.raw/*35.45*/("""', '"""),_display_(Seq[Any](/*35.50*/plano/*35.55*/.isPreRequisito(cadeira.getNome()))),format.raw/*35.89*/("""')">  -->
		  	
		  	"""),_display_(Seq[Any](/*37.7*/if(plano.verificaPrerequisito(cadeira.getNome()))/*37.56*/{_display_(Seq[Any](format.raw/*37.57*/("""
		  			<div class="alocadas-vermelho" id=""""),_display_(Seq[Any](/*38.44*/cadeira/*38.51*/.getNome())),format.raw/*38.61*/("""" title="remover" draggable="true" 
		ondragstart="drag(event)" ondragend="dragEnd()">
		  	""")))}/*40.7*/else/*40.11*/{_display_(Seq[Any](format.raw/*40.12*/("""
		  		<div class="alocadas" id=""""),_display_(Seq[Any](/*41.34*/cadeira/*41.41*/.getNome())),format.raw/*41.51*/("""" title="remover" draggable="true" 
		ondragstart="drag(event)" ondragend="dragEnd()">
		  	""")))})),format.raw/*43.7*/("""		  	
		  		<center><label class="nome"> <span>"""),_display_(Seq[Any](/*44.43*/cadeira/*44.50*/.getNome())),format.raw/*44.60*/("""</span></label></center>
		  		<label class="creditos">"""),_display_(Seq[Any](/*45.32*/cadeira/*45.39*/.getCreditos())),format.raw/*45.53*/(""" """),_display_(Seq[Any](/*45.55*/Messages("creditos"))),format.raw/*45.75*/("""</label>
		  		<label class="dificuldade"> dificuldade: """),_display_(Seq[Any](/*46.49*/cadeira/*46.56*/.getDificuldade())),format.raw/*46.73*/("""</label>
		  		<span style="margin-top: 5px;margin-left:150px;" 
		  		onclick="remCadeira('"""),_display_(Seq[Any](/*48.29*/cadeira/*48.36*/.getNome())),format.raw/*48.46*/("""', '"""),_display_(Seq[Any](/*48.51*/plano/*48.56*/.isPreRequisito(cadeira.getNome()))),format.raw/*48.90*/("""')"></span>	
		  	</div>
		""")))})),format.raw/*50.4*/(""" 
		</ul>
		<span class="periodoCreditos">"""),_display_(Seq[Any](/*52.34*/periodo/*52.41*/.getCreditos())),format.raw/*52.55*/(""" """),_display_(Seq[Any](/*52.57*/Messages("creditos"))),format.raw/*52.77*/("""</span>
		<span class="periodoDificuldade">Dificuldade: """),_display_(Seq[Any](/*53.50*/periodo/*53.57*/.getDificuldadeTotal())),format.raw/*53.79*/("""</span>
		</div>
	""")))})),format.raw/*55.3*/("""
	</div>
	<h5 style="float:right;position:absolute;top:630px;left:760px;margin:0;color:#600;">
		Clique sobre uma cadeira para removê-la.
	</h5>
	
	<br/>
	<center><div class="separator"> </div> </center>
	
	<h3 style="float:left;position:relative;left:50px;">"""),_display_(Seq[Any](/*64.55*/Messages("cadeirasDisponiveis"))),format.raw/*64.86*/("""</h3>
	<h5 style="float:right;position:absolute;top:630px;left:60px;margin:0;color:#600;">
		Arraste a cadeira para o período que você quer alocá-la
	</h5>
	<div style="width:35%;height:500px;overflow-y:auto;margin-top:20px;margin-left:30px;">
	<ul style="list-style:none;overflow-y:auto;margin-top:20px;"> 
		"""),_display_(Seq[Any](/*70.4*/for(cadeira <- plano.getCadeiraDispniveisOrdenadas()) yield /*70.57*/ {_display_(Seq[Any](format.raw/*70.59*/("""
		<div class="disponiveis" id=""""),_display_(Seq[Any](/*71.33*/cadeira/*71.40*/.getNome())),format.raw/*71.50*/(""""	draggable="true" 
		ondragstart="drag(event)" ondragend="dragEnd()">
	  		<center><label class="nome">"""),_display_(Seq[Any](/*73.35*/cadeira/*73.42*/.getNome())),format.raw/*73.52*/("""</label></center>
		  	<label class="creditos">"""),_display_(Seq[Any](/*74.31*/cadeira/*74.38*/.getCreditos())),format.raw/*74.52*/(""" """),_display_(Seq[Any](/*74.54*/Messages("creditos"))),format.raw/*74.74*/("""</label>
		  	<label class="dificuldade"> dificuldade: """),_display_(Seq[Any](/*75.48*/cadeira/*75.55*/.getDificuldade())),format.raw/*75.72*/("""</label>
	  	</div>
	""")))})),format.raw/*77.3*/(""" 
	</ul>
	</div>	
""")))})),format.raw/*80.2*/("""
</body>
"""))}
    }
    
    def render(plano:PlanoDeCurso): play.api.templates.HtmlFormat.Appendable = apply(plano)
    
    def f:((PlanoDeCurso) => play.api.templates.HtmlFormat.Appendable) = (plano) => apply(plano)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Feb 26 13:54:06 BRT 2014
                    SOURCE: C:/Users/Claudivan/git/projeto/app/views/index.scala.html
                    HASH: a57a16f8f0faede912a19d8bb3877d65debb4a18
                    MATRIX: 780->1|912->22|942->44|1080->147|1094->153|1151->189|1229->232|1243->238|1298->272|1471->409|1486->415|1537->444|1579->451|1610->473|1650->475|1702->491|1748->515|1882->613|1926->635|2082->756|2133->791|2173->793|2223->807|2239->814|2273->826|2464->982|2501->1010|2541->1012|2623->1058|2639->1065|2673->1077|2723->1096|2927->1263|2944->1270|2979->1282|3018->1284|3061->1304|3110->1318|3168->1360|3208->1362|3289->1407|3305->1414|3337->1424|3420->1471|3436->1478|3468->1488|3509->1493|3523->1498|3579->1532|3638->1556|3696->1605|3735->1606|3816->1651|3832->1658|3864->1668|3977->1763|3990->1767|4029->1768|4100->1803|4116->1810|4148->1820|4274->1915|4359->1964|4375->1971|4407->1981|4500->2038|4516->2045|4552->2059|4590->2061|4632->2081|4726->2139|4742->2146|4781->2163|4912->2258|4928->2265|4960->2275|5001->2280|5015->2285|5071->2319|5132->2349|5213->2394|5229->2401|5265->2415|5303->2417|5345->2437|5439->2495|5455->2502|5499->2524|5551->2545|5856->2814|5909->2845|6261->3162|6330->3215|6370->3217|6440->3251|6456->3258|6488->3268|6631->3375|6647->3382|6679->3392|6764->3441|6780->3448|6816->3462|6854->3464|6896->3484|6989->3541|7005->3548|7044->3565|7099->3589|7152->3611
                    LINES: 26->1|30->1|32->4|34->6|34->6|34->6|35->7|35->7|35->7|38->10|38->10|38->10|39->11|39->11|39->11|40->12|40->12|44->16|44->16|49->21|49->21|49->21|50->22|50->22|50->22|53->25|53->25|53->25|54->26|54->26|54->26|55->27|59->31|59->31|59->31|59->31|59->31|60->32|60->32|60->32|62->34|62->34|62->34|63->35|63->35|63->35|63->35|63->35|63->35|65->37|65->37|65->37|66->38|66->38|66->38|68->40|68->40|68->40|69->41|69->41|69->41|71->43|72->44|72->44|72->44|73->45|73->45|73->45|73->45|73->45|74->46|74->46|74->46|76->48|76->48|76->48|76->48|76->48|76->48|78->50|80->52|80->52|80->52|80->52|80->52|81->53|81->53|81->53|83->55|92->64|92->64|98->70|98->70|98->70|99->71|99->71|99->71|101->73|101->73|101->73|102->74|102->74|102->74|102->74|102->74|103->75|103->75|103->75|105->77|108->80
                    -- GENERATED --
                */
            