public class StaffMember extends User{
    public StaffMember(String userName, String role, long userID){
        super(userName, role, userID);
    }
    public boolean isStaff(){
        if(role.equals("Staff")) {
            return true;
        } else {
            return false;
        }
    }

    public void staffAction(int choice){
        switch(choice) {

        }
    }


}
