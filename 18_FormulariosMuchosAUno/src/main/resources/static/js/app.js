function open_delete(id, tipo) {

	var url = "";
	var doNothing = false;

	if (tipo === "C") {
		url = '/admin/categoria/borrar/show/' + id;
	} else if (tipo === "P") {
		url = '/admin/producto/borrar/show/' + id;
	} else {
		doNothing = true;
	}

	if (!doNothing) {
		$.ajax({
			url : url,
			success : function(data) {
				$('#paraelmodal').html(data);
				$('#delete-modal').modal('show');
			}
		});
	}

}

$(document).ready(
		function() {

			// Controlador genérico de toggle, por si tuviéramos alguno más
			$('.btn-toggle').click(function() {
				$(this).find('.btn').toggleClass('active');

				if ($(this).find('.btn-primary').length > 0) {
					$(this).find('.btn').toggleClass('btn-primary');
				}
				
				$(this).find('.btn').toggleClass('btn-default');
			});
			
			// Controlador del toggle de disponibilidad
			$('.disponibilidad-toggle').click(function() {
				if ($('#disponibilidad').val() == 'DISPONIBLE') {
					$('#disponibilidad').val('SIN_STOCK');
				} else {
					$('#disponibilidad').val('DISPONIBLE');
				}
			})

			$('#form-producto :submit').click(
					function(event) {
						// Evitamos que se envíe el formulario
						event.preventDefault();

						// Tomamos el número de categoría
						// y lo asignamos al campo oculto para enviarlo
						var selectedAsText = $('#categoria-datalist').val();
						var valueAsLong = $(
								'#categoria-list [value="' + selectedAsText
										+ '"]').data('value');
						$('#categoria').val(valueAsLong);
						
						$('#form-producto').submit();
					});
		});
