package customException;

public class RecordDoesNotExist extends RuntimeException{
    public RecordDoesNotExist() {
    }

    @Override
    public String toString() {
        return "record Does Not Exist";
    }
}
