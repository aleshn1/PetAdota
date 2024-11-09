package com.br.petadota.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.petadota.models.Animal;
import com.br.petadota.models.STATUSADOCAO;

@Repository
public interface AnimaisRepository extends JpaRepository<Animal, Long>{

	Optional<Animal> findByAdotado(STATUSADOCAO adotado);
	
}
