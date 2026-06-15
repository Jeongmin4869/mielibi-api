package com.mielibi.mielibi.repository;

import com.mielibi.mielibi.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    /*
    Repository
    - 목적: DB CRUD, 쿼리 검증
     */

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private List<User> getSavedUsers(){
        User user1 = User.builder()
                .username("jeong@gmail.com")
                .password("12345678")
                .build();

        User user2 = User.builder()
                .username("joon@gmail.com")
                .password("12340000")
                .build();

        entityManager.persist(user1);
        entityManager.persist(user2);

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        return list;
    }

    private User getSaved(){
        User user = User.builder()
                .username("jeong@gmail.com")
                .password("12345678")
                .build();
        return entityManager.persist(user);
    }

    @Test
    @DisplayName("모든 사용자 조회")
    public void findAll(){
        // given
        List<User> list = getSavedUsers();
        System.out.println("======================");
        for(User user : list) {
            System.out.println(user.getUserId());
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            System.out.println();
        }
        System.out.println("======================");

        // when
        List<User> allUser = userRepository.findAll();

        // then
        assertThat(allUser).hasSize(2)
                .extracting(User::getUsername)
                .containsExactlyInAnyOrder(
                        "jeong@gmail.com"
                        ,"joon@gmail.com"
                );

    }

    @Test
    @DisplayName("Id로 사용자 조회")
    public void findByUserId(){
        // given
        User user = getSaved();
        System.out.println("======================");
        System.out.println(user.getUserId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println("======================");

        // when
        Long id = user.getUserId();
        User savedUser = userRepository.findById(id).orElse(null);

        // then
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("사용자 추가")
    public void createUser(){
        // given
        User newUser = User.builder()
                .username("noa@gmail.com")
                .password("11112222")
                .build();

        // when
        User savedUser = userRepository.save(newUser);

        // then
        assertThat(savedUser.getUserId()).isNotNull(); // DB에 저장되면 PK가 생기므로
        assertThat(savedUser.getUsername()).isEqualTo(newUser.getUsername());
    }

    @Test
    @DisplayName("사용자 삭제")
    public void deleteUser(){
        // given
        User user = User.builder()
                .username("noa@gmail.com")
                .password("11112222")
                .build();

        User savedUser = userRepository.save(user);
        Long id = savedUser.getUserId();

        // when
        userRepository.deleteById(id);

        // then
        Optional<User> deletedUser = userRepository.findById(id);
        assertThat(deletedUser).isEmpty();

    }


}