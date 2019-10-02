package com.openwebinars.proyecto.controladores;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openwebinars.proyecto.upload.StorageService;

@Controller
public class FicherosController {
	
	private StorageService storageService;
	
	
	public FicherosController(StorageService storageService) {
		this.storageService = storageService;
	}


	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().body(file);
	}

}
