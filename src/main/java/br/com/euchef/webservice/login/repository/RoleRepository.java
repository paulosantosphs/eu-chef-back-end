package br.com.euchef.webservice.login.repository;

import br.com.euchef.webservice.login.model.user.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

}
