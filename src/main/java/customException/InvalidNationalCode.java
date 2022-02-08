package customException;

public class InvalidNationalCode extends RuntimeException{
    public InvalidNationalCode() {
    }

    @Override
    public String toString() {
        return "Invalid National Code";
    }
}
