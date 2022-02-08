package customException;

public class InvalidAccount extends RuntimeException{

    public InvalidAccount() {
    }

    @Override
    public String toString() {
        return "Invalid Account";
    }
}
