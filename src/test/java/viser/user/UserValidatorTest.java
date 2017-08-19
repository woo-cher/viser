package viser.user;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import viser.domain.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserValidatorTest {

  private static Validator validator;

  @BeforeClass
  public static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void UserIdIsNull() throws Exception {
    User user = new User(null, "password", "name", "25", "email@visermail.net", "Gender");

    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
    assertEquals(1, constraintViolations.size());
    System.out.println(constraintViolations.iterator().next().getMessage());
  }

  @Test
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

  @Test
  public void UserPasswordLength() throws Exception {
    User user = new User("userId", "pd", "name", "25", "email@visermail.net", "Man");
    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

    assertEquals(1, constraintViolations.size());
    System.out.println(constraintViolations.iterator().next().getMessage());

    user = new User("userId", "pdddddddddddd", "name", "25", "email@visermail.net", "Man");
    assertEquals(1, constraintViolations.size());
    System.out.println(constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void UserEmailCheck() throws Exception {
    User user = new User("userId", "password", "name", "25", "", "Man");
    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

    assertEquals(1, constraintViolations.size());
    System.out.println(constraintViolations.iterator().next().getMessage());
  }

}
