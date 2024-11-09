package com.br.petadota.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.petadota.exceptions.RuntimeValidationException;
import com.br.petadota.models.Animal;
import com.br.petadota.models.STATUSADOCAO;
import com.br.petadota.repository.AnimaisRepository;

@Service
public class AnimaisService {

	private static final String ERRO_CADASTRAR_ANIMAL = "Ocorreu um erro ao tentar cadastrar o animal";
	private static final String ERRO_LISTAR_ANIMAL = "Ocorreu um erro ao tentar listar o animal";
	private static final String ERRO_ANIMAL_NAO_ENCONTRADO = "Animal não encontrado";
	private static final String ERRO_DELETAR_ANIMAL = "Ocorreu um erro ao tentar deletar o animal";
	
	@Autowired
	private AnimaisRepository repository;

	public List<Animal> listAll() {

		List<Animal> animal = new ArrayList<Animal>();

		try {
			animal = repository.findAll();
		} catch (Exception e) {
			System.out.println(ERRO_LISTAR_ANIMAL);
			throw new RuntimeValidationException(ERRO_LISTAR_ANIMAL);
		}
		return animal;
	}

	public Animal findById(Long id) {
	    if (id == null || id <= 0) {
	        throw new RuntimeValidationException("ID inválido: o ID do animal deve ser positivo e não nulo.");
	    }

	    return repository.findById(id)
	            .orElseThrow(() -> new RuntimeValidationException(
	                    String.format(ERRO_ANIMAL_NAO_ENCONTRADO + " para o ID: %d", id)
	            ));
	}

	public Animal save(Animal animal) {
		Animal animalcad = new Animal();
		try {
			 animalcad = repository.save(animal);
		} catch (Exception e) {
			throw new RuntimeValidationException(ERRO_CADASTRAR_ANIMAL);
		}
		
		return animalcad;
	}

	public void deleteById(Long id, Animal animal) {
		 try {
		        Optional<Animal> animalToDelete = repository.findById(id);
		        
		        if (animalToDelete.isPresent()) {
		            if (animalToDelete.get().getStatusAdocao().equals(STATUSADOCAO.ADOTADO)) {
		            	repository.deleteById(id);
		            }
		            repository.deleteById(id);
		        } else {
		            throw new RuntimeValidationException("Animal não encontrado.");
		        }
		        
		    } catch (Exception e) {
		        throw new RuntimeValidationException(ERRO_DELETAR_ANIMAL);
		    }
		}
}
