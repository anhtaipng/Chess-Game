package vn.gihot.chess.admin.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gihot.chess.admin.core.PBKDF2Encoder;
import vn.gihot.chess.admin.form.SignupForm;
import vn.gihot.chess.admin.model.AdminUser;
import vn.gihot.chess.admin.service.AdminUserService;

@RestController
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    PBKDF2Encoder passwordEncoder;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AdminUser> signUp(@RequestBody SignupForm form){
        ObjectMapper mapper = new ObjectMapper();
        form.password = passwordEncoder.encode(form.password);
        AdminUser admUser = mapper.convertValue(form, AdminUser.class);
        AdminUser adminUser = adminUserService.create(admUser);

        return new ResponseEntity<>(adminUser, HttpStatus.CREATED);
    }

}
