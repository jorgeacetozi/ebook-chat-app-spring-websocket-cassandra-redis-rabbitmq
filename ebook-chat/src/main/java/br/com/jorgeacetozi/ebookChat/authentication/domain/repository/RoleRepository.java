package br.com.jorgeacetozi.ebookChat.authentication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jorgeacetozi.ebookChat.authentication.domain.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Role findByName(String name);
}
