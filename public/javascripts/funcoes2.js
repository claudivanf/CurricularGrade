// funcao NECESSÁRIA para a inicialização dos popover[tooltip extendido]
$(document).ready(function(){
	$(".popover-top").popover({
		placement : 'top', html:"true", delay: { show: 100, hide: 100 }
	});
	$(".popover-right").popover({
		placement : 'right', html:"true", delay: { show: 100, hide: 100 }
	});
	$(".popover-bottom").popover({
		placement : 'bottom', html:"true", dela: { show: 100, hide: 100 }
	});
	$(".popover-left").popover({
		placement : 'left', html:"true", delay: { show: 100, hide: 100 }
	});

	$('.popover-right').on('shown.bs.popover', function () {
		$( ".popover-right" ).not($(this)).popover('hide');
	});
	
	$(".popover-bottom").hover(
	  function() {
		  	$(this).popover("show");
	  }, function() {
			 $(this).popover("hide");
	  }
	);
	
});


function resetPopovers(){
	$(".popover-top").popover('hide');
	$(".popover-right").popover('hide');
	$(".popover-bottom").popover('hide');
	$(".popover-left").popover('hide');
}

// Funcao que adiciona os spans dos periodos ao tooltip do botao mover/adiciona da cadeira
function contentPopover($elem, $periodo, $size_periodos){
	//resetPopovers();
	var botao = document.getElementById($elem.id);
	var cont = botao.getAttribute('data-content');
	var htmlForm = "Escolha o período: <br/>";
	for(var i=1; i <= $size_periodos; i++){
		if(i != $periodo+1) {
			htmlForm += "<a href='/addCadeira/"
			+ $elem.id.substr(5) + "/" + i + 
			" '><span class='badge'>" + i + "</span></a>";
		}
	}
	botao.setAttribute('data-content' , htmlForm);
}

function cadeiraVermelhaPopover($elem) {
	var divCadeira = document.getElementById($elem.id);
	var cont = divCadeira.getAttribute('data-content');
	var htmlForm = "Os seguintes requisitos estão inválidos: <br/>";
	for(var i=1; i <= $size_periodos; i++){
		if(i != $periodo+1) {
			htmlForm += "<a href='/addCadeira/"
			+ $elem.id.substr(5) + "/" + i + 
			" '><span class='badge'>" + i + "</span></a>";
		}
	}
	divCadeira.setAttribute('data-content' , htmlForm);	
}

// funcao que remove uma cadeira
function remCadeira(cadeira, flagRequisitos){
	var r = true;
	if (flagRequisitos == 'true'){
		r=confirm("A remoção dessa cadeira implicará na remoção de outra(s) cadeira(s), você realmente deseja removê-la?");
	}
	if (r==true){
	$.ajax({
		  type: "POST",
		  url: "/remCadeira/" + cadeira,
		  data: "",
		  success: function(){
		        window.location = "/home";
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown) {
			  alert("Erro durante a requisição, algum valor fornecido é inválido!");
		  }
		});
	}
}
