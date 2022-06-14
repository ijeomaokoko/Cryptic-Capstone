package learn.crypticRadio.data;

import learn.crypticRadio.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        AppUser john = repository.findByUsername("john@smith.com");
        assertEquals("john@smith.com",john.getUsername());
        assertEquals("$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",
                john.getPassword());
        
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    private AppUser makeJohn(){
        AppUser au = new AppUser(1,"","",false,null);
        return au;
    }
}