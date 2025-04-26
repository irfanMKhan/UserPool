package com.secured.userpool.service.implementation;

import com.secured.userpool.exception.CommonException;
import com.secured.userpool.model.dao.Password;
import com.secured.userpool.model.dao.User;
import com.secured.userpool.model.dto.UserDTO;
import com.secured.userpool.model.payload.UserRequest;
import com.secured.userpool.repository.UserRepository;
import com.secured.userpool.service.UserService;
import com.secured.userpool.utility.PasswordEncoderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    public List<UserDTO> get(String sort, String order) {
        List<User> userList = userRepository.get(sort, order);
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : userList) {
            UserDTO userDTO = new UserDTO();
            ModelMapper mapper = new ModelMapper();
            System.out.println(user);

            mapper.map(user, userDTO);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public UserDTO save(UserRequest request) {

        Optional<User> checkUser = findByUsername(request.getUsername());

        if (checkUser.isPresent()) {
            throw new CommonException("username already present");
        } else {
            ModelMapper mapper = new ModelMapper();
            User user = new User();
            mapper.map(request, user);

            user = userRepository.save(user);
            savePassword(request.getPassword(), user);

            UserDTO userDTO = new UserDTO();
            mapper.map(user, userDTO);

            return userDTO;
        }
    }

    private Password savePassword(String raw, User user) {
        Password password = Password.builder()
                .hashed(new PasswordEncoderService(
                                PasswordEncoderService.EncodeAlgorithm.sha512
                        ).encode(raw)
                ).user(user)
                .build();

        return userRepository.savePassword(password);
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


}
