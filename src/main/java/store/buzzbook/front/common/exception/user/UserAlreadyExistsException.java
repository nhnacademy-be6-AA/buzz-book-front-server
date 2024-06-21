package store.buzzbook.front.common.exception.user;

public class UserAlreadyExistsException extends RuntimeException  {
    public UserAlreadyExistsException(String userId) {
        super(String.format("User not found: %s",userId));
    }
}
