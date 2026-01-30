package Model;

import Enums.Roles;

public class Users extends Details {

    public Users(String name, String gmail, String password, Roles role) {
        super(name, gmail, password, role);
    }public Users(){}
    private boolean isactive;
    public void setIsactive(boolean isactive){
        this.isactive=isactive;
    }
    public boolean isIsactive(){
        return isactive;
    }
}
