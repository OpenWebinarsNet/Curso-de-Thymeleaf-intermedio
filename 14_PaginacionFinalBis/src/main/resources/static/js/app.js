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
