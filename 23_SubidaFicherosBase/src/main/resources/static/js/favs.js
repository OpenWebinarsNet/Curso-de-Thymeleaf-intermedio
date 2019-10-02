
$(document).ready(function(){
	
	// Favorite Button - Heart
	$('.favme').click(function() {
		var id = $(this).data('id');
		var button = $(this);
		
		var gestionSegunStatusCode = {
				400: function() {
					console.log('Error de petición')
				},
				204: function() {
					button.toggleClass('active');
					console.log("petición correcta");
				}
		}; 
		
		if ($(this).hasClass('active')) {
			// Eliminar
			$.ajax({			
				type: "POST",
				url: "/product/"+id+"/removeFav",
				statusCode: gestionSegunStatusCode 						
			});
		} else {
			// Añadir
			$.ajax({			
				type: "POST",
				url: "/product/"+id+"/addFav",
				statusCode: gestionSegunStatusCode 						
			});

		}
		
	});

	
});