package viser.user;

import viser.PasswordMismatchException;
import viser.UserNotFoundException;
import viser.db.Database;

public class User {

	private String userId;
	private String password;
	private String name;
	
	public User(String userId, String password, String name) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + "]";
	}

	public boolean matchPassword(String newPassword) {
		return this.password.equals(newPassword);
	}

	public static boolean login(String userId, String password) throws Exception {
		User user = Database.findByUserId(userId);
		if(user==null)
		{
			throw new UserNotFoundException();
		}
		
		if(!user.matchPassword(password)){
			throw new PasswordMismatchException();
		}
		return true;
	}
	
}
