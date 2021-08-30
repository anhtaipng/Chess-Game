package vn.gihot.chess.authenticate.repo;

import org.springframework.data.repository.CrudRepository;
import vn.gihot.chess.authenticate.model.EntityUser;

// This will be AUTO IMPLEMENTED by Spring into a Bean called UserRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<EntityUser, String> {

    EntityUser findByUsername(String name);
}
