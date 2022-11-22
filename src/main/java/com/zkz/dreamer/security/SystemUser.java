package com.zkz.dreamer.security;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
@Data
public class SystemUser {

     private String id= UUID.fastUUID().toString();

     private Collection<? extends GrantedAuthority> authorities;
     private Object user;
}
