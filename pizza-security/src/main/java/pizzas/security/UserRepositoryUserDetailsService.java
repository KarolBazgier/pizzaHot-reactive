package pizzas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pizzas.User;
import pizzas.data.UserRepository;
@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
   private UserRepository userRepo;
    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepository){
        this.userRepo = userRepository;
    }
   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).block();
        if (user != null){
            return user;
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }


}
