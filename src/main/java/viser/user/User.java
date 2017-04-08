package viser.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import viser.PasswordMismatchException;
import viser.UserNotFoundException;

public class User {
	@NotNull(message = "아이디를 입력하세요.")
	@Size(min = 4, max = 12, message = "아이디는 4자 이상 12자 이하여야 합니다.")
	private String userId;
	
	@NotNull(message = "패스워드를 입력하세요.")
	@Size(min = 4, max = 12, message = "비밀번호는 4자 이상 12자 이하여야 합니다.")
	private String password;
	
	@NotNull(message = "이름을 입력하세요.")
	@Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하여야 합니다.")
	private String name;
	
	@NotNull(message = "나이를 입력하세요.")
	@Size(min = 1, max = 2, message = "나이는 1자 이상 2자 이하여야 합니다.")
	private String age;
	
	@NotNull(message = "이메일을 입력하세요.")
	@Email
	private String email;
	
	@NotNull(message = "성별을 골라주세요.")
	private String gender;

	public User() {

	}

	public User(String userId, String password, String name, String age, String email, String gender) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.age = age;
		this.email = email;
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", age=" + age + ", email="
				+ email + ", gender=" + gender +  "]";
	}

	public boolean matchPassword(String newPassword) {
		return this.password.equals(newPassword);
	}

	public static boolean login(String userId, String password) throws Exception {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.findByUserId(userId);
		if (user == null) {
			throw new UserNotFoundException();
		}

		if (!user.matchPassword(password)) {
			throw new PasswordMismatchException();
		}
		return true;
	}

}
