package learn.crypticRadio.data;

import learn.crypticRadio.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.NotExtensible;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserJdbcRepositoryTest {

    @Autowired
    UserJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findByUsername() {
        AppUser john = repository.findByUserId(1);
        assertEquals("john@smith.com",john.getUsername());
        assertEquals("$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",
                john.getPassword());

    }

    @Test
    void add() {
        AppUser user = makeJohn();
//        User actual = repository.add(user);
//        assertNotNull(actual);
       // assertEquals(, actual.getUsername());

        user= makeJohn();
        User actual = repository.add(user);
        assertNotNull(actual);
        assertEquals("john@smith.com", actual.getUsername());
    }

    @Test
    void update() {
        User user = makeJohn();
        user.getUsername();
        assertFalse(repository.update(makeJohn()));
        user.getUsername();
        assertFalse(repository.update(makeJohn()));

    }

    private AppUser makeJohn(){
        AppUser au = new AppUser(1,"","",false,null);
        return au;
    }
}