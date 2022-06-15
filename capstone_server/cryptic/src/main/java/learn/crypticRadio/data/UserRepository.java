package learn.crypticRadio.data;

import learn.crypticRadio.models.AppUser;
public interface UserRepository {

    AppUser findByUsername(String username);

    AppUser findByUserId(int userId);

    AppUser add(AppUser appUser);

    boolean update(AppUser appUser);
}
