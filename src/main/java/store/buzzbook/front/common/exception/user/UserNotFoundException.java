package store.buzzbook.front.common.exception.user;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String userId) {
		super(String.format("User not found: %s",userId));
	}
}
