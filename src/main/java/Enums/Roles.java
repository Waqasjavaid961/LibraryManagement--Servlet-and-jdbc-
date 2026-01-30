package Enums;

public enum Roles {
    superadmin,
    user,
    admin;

    public static Roles fromString(String input) {
        if (input == null) {
            return null;
        }
        
        // Loop ke zariye check karein taake exact match ka darr na rahe
        for (Roles role : Roles.values()) {
            if (role.name().equalsIgnoreCase(input.trim())) {
                return role;
            }
        }
        
        System.out.println("Galti: Role '" + input + "' database mein sahi nahi hai.");
        return null;
    }
}