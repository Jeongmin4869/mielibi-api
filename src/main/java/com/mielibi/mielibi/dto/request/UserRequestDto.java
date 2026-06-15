package com.mielibi.mielibi.dto.request;

import com.mielibi.mielibi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
    private String username;
    private String password;

    public User toEntity(){
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        return user;
    }

}
