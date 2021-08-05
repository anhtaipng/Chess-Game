package vn.gihot.chess.admin.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.gihot.chess.admin.model.AdminUser;

@Repository
public interface AdminUserRepos extends MongoRepository<AdminUser, String> {

}
