package com.zkz.dreamer.response;

import cn.hutool.core.util.CharsetUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkz.dreamer.exception.BaseException;
import com.zkz.dreamer.util.GsonUtils;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Data
public class ResponseData {
    private int code;
    private String message;
    private Object data;

    public static ResponseData success(){
        return success(null);
    }
    public static ResponseData success(Object data){
        return getInstance(200,"success",data);
    }
    private static ResponseData getInstance(int code,String message,Object data){
        ResponseData responseData=new ResponseData();
        responseData.setCode(code);
        responseData.setMessage(message);
        responseData.setData(data);
        return responseData;
    }

    public static ResponseData failed(int code,String message){
        return getInstance(code,message,null);
    }

    public static ResponseData failed(BaseException baseException){
        return getInstance(baseException.getCode(),baseException.getMessage(),null);
    }

    public static ResponseData failed(String message){
        return failed(500,message);
    }

    /**
     * 适用于mybatis-plus
     * @param page 分页对象
     * @param <T>实体类
     * @return 统一响应对象
     */
    public static <T>ResponseData page(Page<T> page){
        PageResult<T> pageResult=new PageResult<>(page.getCurrent(),page.getSize(),page.getTotal(),page.getRecords());
        return success(pageResult);
    }

    /**
     * 手动构造分页
     * @param pageNum 当前页码
     * @param pageSize 分页大小
     * @param total 总数据
     * @param rows 数据列表
     * @param <T> 实体对象
     * @return 统一响应对象
     */
    public static <T>ResponseData page(long pageNum,long pageSize,long total,Iterable<T> rows){
        PageResult<T> pageResult=new PageResult<>(pageNum,pageSize,total,rows);
        return success(pageResult);
    }

    public static void responseExceptionError(HttpServletResponse response,BaseException baseException) throws IOException {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType("application/json");
        ResponseData failed = ResponseData.failed(baseException);
        response.getOutputStream().write(GsonUtils.toJson(failed).getBytes());
    }

}
