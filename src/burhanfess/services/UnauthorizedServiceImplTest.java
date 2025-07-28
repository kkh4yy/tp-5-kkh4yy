// File: src/test/java/burhanfess/services/UnauthorizedServiceImplTest.java
package burhanfess.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import burhanfess.exceptions.UsernameAlreadyExistsException;
import burhanfess.exceptions.InvalidCredentialsException;

class UnauthorizedServiceImplTest {
    private UnauthorizedService svc;

    @BeforeEach
    void setUp() {
        svc = new UnauthorizedServiceImpl();
    }

    @Test
    void testRegisterAndDuplicate() {
        svc.register("newu", "pw");
        assertThrows(UsernameAlreadyExistsException.class,
            () -> svc.register("newu", "other")
        );
    }

    @Test
    void testLoginSuccessAndFailure() {
        svc.register("u2", "p2");
        assertDoesNotThrow(() -> svc.login("u2", "p2"));
        assertThrows(InvalidCredentialsException.class,
            () -> svc.login("u2", "wrong")
        );
        assertThrows(InvalidCredentialsException.class,
            () -> svc.login("nope", "pw")
        );
    }
}
