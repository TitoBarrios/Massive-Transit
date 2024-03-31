package error;

public class UserNotFoundException extends Exception {
    
    public UserNotFoundException(String message){
        super(message);
    }

}
