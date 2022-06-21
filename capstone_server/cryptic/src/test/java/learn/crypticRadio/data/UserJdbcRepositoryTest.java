package learn.crypticRadio.data;

import learn.crypticRadio.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.NotExtensible;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
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
        AppUser goodUser= makeGoodUser();
        User actual = repository.add(goodUser);
        assertNotNull(actual);
        assertEquals("Test", actual.getUsername());
        assertEquals("Pass",actual.getPassword());
    }

    @Test
    void update() {


        AppUser badUser = makeBadUser();
        assertFalse(repository.update(badUser));

        assertTrue(repository.update(makeGoodUser()));

        AppUser actual = repository.findByUserId(2);
        assertEquals("Test", actual.getUsername());
        assertEquals("Pass",actual.getPassword());
    }

    private AppUser makeBadUser(){
        AppUser au = new AppUser(-1,"john@smith.com"," ",false, List.of("USER"));
        return au;
    }
    private AppUser makeGoodUser(){

        AppUser au = new AppUser(2,"Test","Pass",false,List.of("USER"));
        return au;
    }

}