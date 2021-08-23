package iva.lesperance.api.tutorial.userApp;

import java.util.List;
import java.util.Optional;

public interface UserAppRepository {
    Optional<UserApp> findUserByUsername(String username);
    boolean saveUser(UserApp userApp);
    boolean updateUser(int userID, UserApp userApp);
    boolean deleteUser(String userId);
    List<UserApp> getUsers();
    boolean saveRole(UserAppRole role);
    Optional<UserAppRole> findRole(String roleName);
    boolean addRole2User(String username, String roleName);
    List<UserAppRole> getRoles4User(String username);
    boolean enableUserApp(String username);

}
