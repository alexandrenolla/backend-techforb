package com.alexandrenolla.backendtechforb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrenolla.backendtechforb.models.User;
import com.alexandrenolla.backendtechforb.repositories.UserRepository;
import com.alexandrenolla.backendtechforb.services.exceptions.DataBindingViolationException;
import com.alexandrenolla.backendtechforb.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {

        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException(
            "User not found! Id:" + id + ", Type: " + User.class.getName()
        ));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User create(User obj) {

        obj.setId(null);
        obj = this.userRepository.save(obj);

        return obj;

    }

    @Transactional
    public User update(User obj) {

        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());

        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("There are entities related!");
        }
    }
}
