package com.demo.springboot.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Permission {

    private Long id;
    @NonNull
    private String name;

    @NonNull
    private String desc;

    @NonNull
    private String operator;

    @NonNull
    private String url;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @Builder.Default
    LocalDateTime updateDate = LocalDateTime.now();
}
