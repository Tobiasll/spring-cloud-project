package com.tobias.fallback;

import com.tobias.api.UserService;
import com.tobias.domain.User;
import java.util.Collections;
import java.util.List;

public class UserServiceFallback implements UserService {

  @Override
  public boolean saveUser(User user) {
    return false;
  }

  @Override
  public List<User> findAll() {
    return Collections.emptyList();
  }
}
