package learn.crypticRadio.data;

import learn.crypticRadio.data.mappers.*;
import learn.crypticRadio.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public class UserJdbcRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AppUser findByUserId(String userId) {

        List<String> roles = getRolesByUsername(userId);

        final String sql = "select user_id, username, password_hash, disabled " +
                "from user " +
                "where username = ?";

        return jdbcTemplate.query(sql, new AppUserMapper(roles), userId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public AppUser add(AppUser appUser) {
        final String sql = "insert into user(username, password_hash, disabled) " +
                "values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appUser.getUsername());
            ps.setString(2, appUser.getPassword());
            ps.setBoolean(3, !appUser.isEnabled());
            return ps;
        }, keyHolder);

        if(rowsAffected <= 0) {
            return null;
        }
        appUser.setAppUserId(keyHolder.getKey().intValue());

        updateRoles(appUser);

        return appUser;
    }

    @Override
    public boolean update(AppUser appUser) {
        final String sql = "update user " +
                "set " +
                "username = ?, "+ "password_hash = ?, " + "disabled = ? " +
                "where user_id = ?;";
        return jdbcTemplate.update(sql,
                appUser.getUsername(),
                appUser.getPassword(),
                !appUser.isEnabled(),
                appUser.getAppUserId())> 0;
    }

//    @Override
//    public boolean deleteByUsername(String username) {
//        return  jdbcTemplate.update("delete from user where username= ?;", username) > 0;
//    }

    private void updateRoles(AppUser user) {
        // delete all roles then re add
        jdbcTemplate.update("delete from role_has_user where user_id = ?;", user.getAppUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if(authorities == null) {
            return;
        }

        for(String role : AppUser.convertAuthoritiesToRoles(authorities)) {
            String sql = "insert into role_has_user (user_id, role_id) "
                    + "select ?, role_id from role where `name` = ?;";
            jdbcTemplate.update(sql, user.getAppUserId(), role);
        }
    }

    private List<String> getRolesByUsername(String username) {
        final String sql = "select r.name "
                + "from role_has_user ur "
                + "inner join role r on ur.role_id = r.role_id "
                + "inner join user u on u.user_id = ur.user_id "
                + "where u.username = ?;";
        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("name"), username);
    }
}
