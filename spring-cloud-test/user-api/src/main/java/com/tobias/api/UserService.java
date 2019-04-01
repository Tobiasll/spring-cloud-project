package com.tobias.api;

import com.tobias.domain.User;
import com.tobias.fallback.UserServiceFallback;
import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${user.service.name}", fallback = UserServiceFallback.class)
public interface UserService {

  @PostMapping("/user/save")
  boolean saveUser(User user);

  @GetMapping("user/findAll")
  List<User> findAll();


}
