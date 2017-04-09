package viser.user;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.*;

public class UserValidatorTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test // ID 가 Null 일 경우
	public void UserIdIsNull() throws Exception{
		User user = new User(null, "password", "name", "25", "email@visermail.net", "Gender");

		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getMessage());
	}

	@Test // ID 가 제한 범위를 초과한 경우
	public void UserIdLength() throws Exception {
		User user = new User("us", "password", "name", "25", "email@visermail.net", "Man");

		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getMessage());

		user = new User("userIddddddddddd", "password", "name", "25", "email@visermail.net", "Man");
		constraintViolations = validator.validate(user);
		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getMessage());
	}
	
	@Test // 비밀번호 유효성 체크
	public void UserPasswordLength() throws Exception {
		User user = new User("userId", "pd", "name", "25", "email@visermail.net", "Man");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		
		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getMessage());
		
		user = new User("userId", "pdddddddddddd", "name", "25", "email@visermail.net", "Man");
		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getMessage());
	}
	
	@Test // 이메일 유효성 체크
	public void UserEmailCheck() throws Exception {
		User user = new User("userId", "password", "name", "25", "", "Man");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		
		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getMessage());
	}
	
}