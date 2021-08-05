package vn.gihot.chess.admin.service;

import org.springframework.security.core.Authentication;
import vn.gihot.chess.admin.core.IService;
import vn.gihot.chess.admin.model.AdminUser;

public interface IAdminUserService extends IService<AdminUser> {
    boolean isAuthenticated();
    Authentication autoLogin(String username, String password) throws Exception;
}
