package com.zkz.dreamer.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.util.AntPathMatcher;

public class PathMatherUtils {
    public static boolean matherPath(String[] urls,String path){
        if (StringUtils.isBlank(path) || urls==null||urls.length==0) {
            return false;
        }
        for (String url : urls) {
            if (isMatch(url, path)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isMatch(String url, String urlPath) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(url, urlPath);
    }
}
