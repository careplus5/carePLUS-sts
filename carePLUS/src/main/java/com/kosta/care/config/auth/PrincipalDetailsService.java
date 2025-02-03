package com.kosta.care.config.auth;


import com.kosta.care.entity.Employee;
import com.kosta.care.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       System.out.println("loadUserByUsername:"+username+". right?");
        if(username != null && !username.isEmpty()) {
            System.out.println("username!!"+username);
            String identity = username.substring(0,2);
            Employee employee =  employeeRepository.identifyJob(username);
            System.out.println(employee.toString());
            return new PrincipalDetails(employee);
        }
        else throw new UsernameNotFoundException("Invalid username or password");
    }
}
