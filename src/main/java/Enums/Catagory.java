package Enums;

public enum Catagory {
    computer,
    science,
    mathematics,
    fiction,
    biography;
    public static Catagory fromString(String input){
        if(input==null){
            System.out.println("input is null");
            return null;
        }
        try{
            return Catagory.valueOf(input.trim().toLowerCase());
        }catch (IllegalArgumentException e){
            System.out.println("wrong catagory");
            return null;
        }
    }
}


