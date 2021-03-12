package br.com.euchef.webservice.login.repository;

import java.util.Optional;

import br.com.euchef.webservice.login.model.user.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
	public Optional<User> findByEmail(String email);
}
