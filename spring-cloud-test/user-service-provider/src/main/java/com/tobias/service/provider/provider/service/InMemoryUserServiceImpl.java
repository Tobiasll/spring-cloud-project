package com.tobias.service.provider.provider.service;

import com.tobias.api.UserService;
import com.tobias.domain.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("inMemoryUserServiceImpl")
public class InMemoryUserServiceImpl implements UserService {

  private Map<Long, User> map = new HashMap<>();


  @Override
  public boolean saveUser(User user) {
    return map.put(user.getId(), user) == null;
  }

  @Override
  public List<User> findAll() {
    return new ArrayList<>(map.values());
  }
}
