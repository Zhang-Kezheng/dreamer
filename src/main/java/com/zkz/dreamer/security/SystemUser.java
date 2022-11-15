package com.zkz.dreamer.security;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public interface SystemUser extends UserDetails {

     String getId();

}
