
function open_delete(id) {
	
	$.ajax({
		url: '/admin/categoria/borrar/show/' + id,
		success: function(data) {
			$('#paraelmodal').html(data);
			$('#delete-modal').modal('show');
		}
	});
	
}
