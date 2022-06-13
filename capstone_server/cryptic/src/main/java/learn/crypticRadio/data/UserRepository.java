package learn.crypticRadio.data;

import learn.crypticRadio.models.AppUser;
public interface UserRepository {

    AppUser findByUsername(String username);

    AppUser add(AppUser appUser);

    boolean update(AppUser appUser);
}
