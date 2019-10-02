$(document).ready(function(){
	
	$('button[name="addInputFileImage"]').click(function(event){
		event.preventDefault();
		console.log('pulsado');
		var clonado = $('#images-container #imagen').clone()
		clonado.val('');
		var cantidad = $('#images-container input[name="file"]').length;
		clonado.attr('id','imagen-'+cantidad);
		clonado.appendTo('#images-container');
	});
	
});