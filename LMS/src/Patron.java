public class Patron extends User{
    public Patron(String userName, String role, long userID){
        super(userName, role, userID);
    }

    public boolean isPatron(){
        if(role.equals("Patron")) {
            return true;
        } else {
            return false;
        }
    }

}
