package com.zkz.dreamer.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;

public abstract class AbstractJwtTokenFilter  extends OncePerRequestFilter implements Filter {
}
