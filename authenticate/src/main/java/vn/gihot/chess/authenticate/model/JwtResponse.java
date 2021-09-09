package vn.gihot.chess.authenticate.model;

public class JwtResponse {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String id_user;
    private final String jwttoken;


    public JwtResponse(String id_user, String jwttoken) {
        this.id_user = id_user;
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public String getId_user() {
        return id_user;
    }
}
