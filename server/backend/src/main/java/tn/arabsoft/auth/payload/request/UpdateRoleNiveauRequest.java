package tn.arabsoft.auth.payload.request;

public class UpdateRoleNiveauRequest {
    private long user_id;
    private long role_id;
    private Integer niveau;

  
    public long getUser_id() { return user_id; }
    public void setUser_id(long user_id) { this.user_id = user_id; }
    public long getRole_id() { return role_id; }
    public void setRole_id(long role_id) { this.role_id = role_id; }
    public Integer getNiveau() { return niveau; }
    public void setNiveau(Integer niveau) { this.niveau = niveau; }
}
