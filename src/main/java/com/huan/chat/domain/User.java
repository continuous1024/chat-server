package com.huan.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Writer;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    // 输出流
    private Writer writer;
}
