
Dropzone.autoDiscover=false;

$(document).ready(function(){
	
	$(".file-dropzone").on('dragover', handleDragEnter);
	$(".file-dropzone").on('dragleave', handleDragLeave);
	$(".file-dropzone").on('drop', handleDragLeave);

	function handleDragEnter(e) {
		this.classList.add('drag-over');
	}

	function handleDragLeave(e) {
		this.classList.remove('drag-over');
	}
	
	$('form#form-producto').dropzone({

		url : "/admin/producto/upload/images",
		autoProcessQueue : true,
		uploadMultiple : true,
		maxFilesize : 5, // MB
		parallelUploads : 1,
		maxFiles : 10,
		addRemoveLinks : true,
		acceptedFiles: "image/*",
		previewsContainer : ".dropzone-previews",

		// The setting up of the dropzone
		init : function() {

			var myDropzone = this;

			// customizing the default progress bar
			this.on("uploadprogress", function(file, progress) {

				progress = parseFloat(progress).toFixed(0);

				var progressBar = file.previewElement.getElementsByClassName("dz-upload")[0];
				progressBar.innerHTML = progress + "%";
			});

			
			this.on("success", function(file, serverResponse) {
				var i = $('input[name*="imagen"').length;
				$('<input>').attr({
					type: 'hidden',
					id: 'producto-imagen-'+i,
					name: 'imagen['+i+']',
					value: serverResponse
				}).appendTo('form#form-producto');
				
			});
			
			
		}
	
		
	});
	
	
	
});

	
	