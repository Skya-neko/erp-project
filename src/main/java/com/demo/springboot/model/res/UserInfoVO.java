package com.demo.springboot.model.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class UserInfoVO {

    private String userId;
    private String userName;
    private List<PermissionVo> menu;

}
