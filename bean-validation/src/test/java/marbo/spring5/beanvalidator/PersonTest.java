package marbo.spring5.beanvalidator;

import marbo.spring5.beanvalidator.model.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void firstNameIsNull() {

        Person person = new Person(null, "Doe", "a@a.com", 10);

        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate( person );

        constraintViolations.forEach(error -> System.err.println(error.getPropertyPath() + " " + error.getMessage()));

        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "must not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void lastNameIsNull() {

        Person person = new Person("John", null, "a@a.com", 10);

        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate( person );

        constraintViolations.forEach(error -> System.err.println(error.getPropertyPath() + " " + error.getMessage()));

        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "must not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void emailNotWellFormed() {

        Person person = new Person("John", "Doe", "aa", 10);

        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate( person );

        constraintViolations.forEach(error -> System.err.println(error.getPropertyPath() + " " + error.getMessage()));

        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "must be a well-formed email address",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void ageIsLessEqualOne() {

        Person person = new Person("John", "Doe", "aa@aa.com", 0);

        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate( person );

        constraintViolations.forEach(error -> System.err.println(error.getPropertyPath() + " " + error.getMessage()));

        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "must be greater than or equal to 1",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void personIsValid() {
        Person person = new Person("Jonh", "Doe", "a@a", 10);

        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate( person );

        assertEquals( 0, constraintViolations.size() );
    }
}
