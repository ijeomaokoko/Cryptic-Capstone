package learn.crypticRadio.data;

import learn.crypticRadio.models.AppUser;
public interface UserRepository {

    AppUser findByUserId(String userId);

    AppUser add(AppUser appUser);

    boolean update(AppUser appUser);
}
