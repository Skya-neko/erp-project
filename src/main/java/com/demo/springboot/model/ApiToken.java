package com.demo.springboot.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiToken {

    private String clientId;

    private String access;

    private LocalDateTime issueTime;

    private LocalDateTime validTime;

}
