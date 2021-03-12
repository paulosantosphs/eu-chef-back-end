package br.com.euchef.webservice.login.repository;

import java.util.Optional;

import br.com.euchef.webservice.login.model.passport.Passport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PassportRepository extends CrudRepository<Passport, Long> {

	@Query("SELECT p FROM Passport p WHERE p.user.id = ?1 AND TYPE(p) = ?2")
	public Optional<Passport> findByUserIdAndType(Long userId, Class<? extends Passport> type);
}
