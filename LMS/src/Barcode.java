import java.util.ArrayList;
/*
 * Kiara Howard, Software Dev I, 10/08/23
 * Class Name: Barcode
 * This class creates, checks for available, and
 * reassigns barcodes for each book that is entered.
 * @param nextID Current number to check against.
 * @param currentbarCode The final assigned barCode.
 * @param checkTaken String if given barcode is already assigned
 *          to a book.
 * @param idList An ArrayList for storing the assigned barCodes.
 */

public class Barcode {
    int nextID = 0;
    int currentbarCode;
    String checkTaken;
    boolean found = true;
    public ArrayList<Integer> idList;
    //ArrayList<Integer> idList = new ArrayList<>();

    public Barcode(ArrayList<Integer> idList) {
        this.idList = idList;
    }
    /*
     * textbarCode(int barCode)
     * checks if the given or current barCode number is already in
     * use or free to be selected. If it's already taken, the
     * generator will continue onto the next and check each one.
     * Once an available barCode is found, then it's added to that ArrayList
     * and returned as the barCode for that book.
     *
     * @param barCode If an barCode number is given
     */
    public void textbarCode(int barCode) {
        nextID = barCode;
        while (found) {
            found = false;
            while (idList.contains(nextID)) {
                found = true;
                nextID++;
                if (!found) {
                    break;
                }
            }
        }
            if (!found) {
                idList.add(nextID);
                currentbarCode = nextID;
                found = true;
            }
        }

    /*
     * Receives the current available barCode number
     * and assigns it to be returned
     *
     * @return currentbarCode The current available barCode for a book.
     */
    public int getCurrentbarCode() {
        currentbarCode = nextID++;
        return currentbarCode;
    }
    public void setCurrentbarCode(int currentbarCode){
        this.currentbarCode = currentbarCode;
    }

    public String getCheckTaken(){
        return checkTaken;
    }

    public void setCheckTaken(String checkTaken){
        this.checkTaken = checkTaken;
    }

}
