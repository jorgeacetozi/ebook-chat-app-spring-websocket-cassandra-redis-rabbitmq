package br.com.jorgeacetozi.ebookChat.authentication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jorgeacetozi.ebookChat.authentication.domain.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
