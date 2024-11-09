package com.br.petadota.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.petadota.exceptions.RuntimeValidationException;
import com.br.petadota.models.Animal;
import com.br.petadota.service.AnimaisService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "api/v1")
public class AnimaisController {

	@Autowired
	private AnimaisService service;

	private static final String SUCCESS = "sucesso";
	private static final String MESSAGE = "mensagem";
	
		
	@GetMapping("list/animais")
	public ResponseEntity<HashMap<String, Object>> listarTodos() {
		HashMap<String, Object> response = new HashMap<>();
		List<Animal> animais =  new ArrayList<Animal>();
		try {
			animais = service.listAll();
		}catch(EntityNotFoundException e) {
			response.put(SUCCESS, false);
			response.put(MESSAGE, e.getMessage());
			return ResponseEntity.status((HttpStatus) HttpStatus.NOT_FOUND).body(response);
		}
		response.put(SUCCESS, true);
		response.put("Animais cadastrados: ", animais);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "getAnimalById/animal/{id}")
	public ResponseEntity<HashMap<String, Object>> findById(@PathVariable Long id) {
	    try {
	        Animal animal = service.findById(id);
	        return createResponse(HttpStatus.FOUND, true, MESSAGE, animal);
	    } catch (RuntimeValidationException e) {
	        return createResponse(HttpStatus.NOT_FOUND, false, e.getMessage());
	    } catch (Exception e) {
	        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, MESSAGE, e.getMessage());
	    }
	}


	@PostMapping("save/animal")
	public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) {
		Animal saveAnimal = service.save(animal);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveAnimal);
	}

	@PutMapping("update/animal/{id}")
	public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal animal) {
		if (service.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		animal.setId(id);
		return ResponseEntity.ok(service.save(animal));
	}

	@DeleteMapping("delete/animal/{id}")
	public ResponseEntity<Void> deleteAnimal(@PathVariable Long id, Animal animal) {
		if (service.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		service.deleteById(id, animal);
		return ResponseEntity.noContent().build();
	}
	
	 private ResponseEntity<HashMap<String, Object>> createResponse(HttpStatus status, boolean success, String message, Object data) {
	        HashMap<String, Object> response = new HashMap<>();
	        response.put(SUCCESS, success);
	        response.put(MESSAGE, message);
	        response.put("data", data);
	        return ResponseEntity.status(status).body(response);
	    }
	 
	 private ResponseEntity<HashMap<String, Object>> createResponse(HttpStatus status, boolean success, Object data) {
	        HashMap<String, Object> response = new HashMap<>();
	        response.put(SUCCESS, success);
	        response.put("data", data);
	        return ResponseEntity.status(status).body(response);
	    }

}
