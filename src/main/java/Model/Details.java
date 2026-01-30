package Model;
import Enums.Roles;
public class Details {
    private int id;
    private String name;
    private String gmail;
    private String password;
    private Roles role;
    public Details(String name, String gmail, String password, Roles role) {
        this.name = name;
        this.gmail = gmail;
        this.password = password;
        this.role = role;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public Details(){}
    public void setName(String name){
        this.name=name;
    }
    public void setGmail(String gmail){
        this.gmail=gmail;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setRole(Roles role){
        this.role=role;
    }

    public String getName() {
        return name;
    }
    public String getGmail() {
        return gmail;
    }
    public String getPassword() {
        return password;
    }
    public Roles getRole() {
        return role;
    }
}
