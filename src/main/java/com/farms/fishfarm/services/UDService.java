package com.farms.fishfarm.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.farms.fishfarm.entities.Firm;
import com.farms.fishfarm.entities.Role;
import com.farms.fishfarm.entities.User;
import com.farms.fishfarm.repo.FirmRepository;
import com.farms.fishfarm.repo.RoleRepository;
import com.farms.fishfarm.repo.UserRepository;

@Service
public class UDService implements UserDetailsService {

    

    private BCryptPasswordEncoder passEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private FirmRepository firmRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    public UDService(BCryptPasswordEncoder passEncoder, UserRepository userRepository, RoleRepository roleRepository, FirmRepository firmRepository) {
        this.passEncoder = passEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.firmRepository = firmRepository;
    }

    public boolean addRoleToUserById(String username, Long idRole) {
        User fuser = null;
        Role frole = null;
        fuser = userRepository.findByUsername(username).orElse(null);
        frole = roleRepository.findById(idRole).orElse(null);
        if (fuser == null || frole == null)
            return false;

        if (!fuser.getRoles().contains(frole)) {
            fuser.getRoles().add(frole);
            userRepository.save(fuser);
            return true;
        }

        return false;// role already exists
    }

  

    public boolean addRoleToUserByName(String username, String rolename) {
        User fuser = null;
        Role frole = null;
        fuser = userRepository.findByUsername(username).orElse(null);
        frole = roleRepository.findByName(rolename).orElse(null);
        if (fuser == null || frole == null)
            return false;

        if (!fuser.getRoles().contains(frole)) {
            fuser.getRoles().add(frole);
            userRepository.save(fuser);
            return true;
        }

        return false;// role already exists
    }


    public boolean addUser(String name, String pass, Long firm, String email, String description) {
        User u = new User();
        u.setUsername(name);
        u.setPassword(pass);
        u.setFirmId(firmRepository.findById(firm).orElse(null));
        u.setEnabled(true);
        u.setEmail(email);
        u.setComment(description);
        return addUser(u);
    }

    public boolean addUser(String name, String pass, Long firm) {
        User u = new User();
        u.setUsername(name);
        u.setPassword(pass);
        u.setFirmId(firmRepository.findById(firm).orElse(null));
        u.setEnabled(true);
        return addUser(u);
    }

    public boolean addUser(String name, String pass, String firmName) {
        Firm firm=firmRepository.findByShortname(firmName).orElse(null);
        if (firm==null) return false;
        User u = new User();
        u.setUsername(name);
        u.setPassword(pass);
        u.setFirmId(firm);
        u.setEnabled(true);
        return addUser(u);
    }

    public boolean addUser(User user) {
        User fuser = null;
        Long id = 0L;
        if (user.getId() != null && user.getId() > 0) {
            fuser = userRepository.findById(user.getId()).orElse(null);
            if (fuser != null)
                id = fuser.getId();
        }

        fuser = userRepository.findByUsername(user.getUsername()).orElse(null);

        if (fuser != null && id > 0 && !id.equals(fuser.getId()) || fuser != null && id == 0) {
            // throw new NameAlreadyBoundException()
            return false;
        } else {
            if (id == 0)
                user.setPassword(passEncoder.encode(user.getPassword()));
            userRepository.save(user);// insert-update user
        }
        return true;
    }

    public boolean addRole(String rolename) {
        Role frole = roleRepository.findByName(rolename).orElse(null);
        if (frole != null)
            return false;
        frole = new Role();
        frole.setName(rolename);
        roleRepository.save(frole);
        return true;
    }

    public String changePassword(String username, String oldpass, String newpass) {
        User u = userRepository.findByUsername(username).orElse(null);
        if (u == null) {
            return "Пользователь " + username + " не найден!";
        }
        if (!passEncoder.matches(oldpass, u.getPassword())) {
            return "Неверный старый пароль!";
        }
        if (newpass.equals(oldpass)) {
            return "Пароли совпадают!";
        }
        u.setPassword(passEncoder.encode(newpass));
        userRepository.save(u);
       return "";
    }

    public String resetPassword(String username, String newpass) {
        User u = userRepository.findByUsername(username).orElse(null);
        if (u == null) {
            return "Пользователь " + username + " не найден!";
         }
        u.setPassword(passEncoder.encode(newpass));
        userRepository.save(u);
        return "";
    }

    public List<User> getAll2firm(Long id) {
        Firm firm=firmRepository.findById(id).orElse(null);
        if (firm==null) return new ArrayList<>();
        return userRepository.findByFirmId(firm);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}
