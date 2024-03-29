package org.tckry.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.tckry.shortlink.admin.common.convention.result.Result;
import org.tckry.shortlink.admin.common.convention.result.Results;
import org.tckry.shortlink.admin.common.enums.UserErrorCodeEnum;
import org.tckry.shortlink.admin.dto.req.UserLoginReqDTO;
import org.tckry.shortlink.admin.dto.req.UserRegisterReqDTO;
import org.tckry.shortlink.admin.dto.req.UserUpdateReqDTO;
import org.tckry.shortlink.admin.dto.resp.UserActualRespDTO;
import org.tckry.shortlink.admin.dto.resp.UserLoginRespDTO;
import org.tckry.shortlink.admin.dto.resp.UserRespDTO;
import org.tckry.shortlink.admin.service.UserService;

/**
 * 用户管理控制层
 */
@RestController
@RequiredArgsConstructor    // 采用lombok的构造器方式注入
public class UserController {
//    不采用下面的方式，需要两行代码，且不太美观。或者采用@Resource注解替换Autowired自动注入
//    @Autowired
//    private UserService userService;

    private final UserService userService;  // 配合上面RequiredArgsConstructor以构造器的方式注入

    /**
     * 根据用户名查询应用户信息
     */
    @GetMapping("/api/short-link/admin/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {

        // 查出空的话报错
        // 1、一般不太可能在result里面做各种封装   2、controller里禁止写业务代码，
        // 所以采用统一封装Result
        UserRespDTO result = userService.getUserByUsername(username);
//        if(result==null){
//            // return new Result<UserRespDTO>().setCode(UserErrorCodeEnum.USER_NULL.code()).setMessage(UserErrorCodeEnum.USER_NULL.message());
//            return new Result<UserRespDTO>().setCode(UserErrorCodeEnum.USER_NULL.code()).setMessage(UserErrorCodeEnum.USER_NULL.message());
//        }else {
//            // return new Result<UserRespDTO>().setCode("0").setData(result);  // Results封装Result，避免每次new
//            return Results.success(result);
//        }

        // 失败的情况已经被全局异常拦截器处理了
        return Results.success(result);

    }

    /**
    * 根据用户名查询应用户无脱敏信息
    */

    @GetMapping("/api/short-link/admin/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username) {
        // 获取无脱敏的真实数据
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username),UserActualRespDTO.class));

    }

    /** 
    * 查询用户名是否已存在
    */
    @GetMapping("/api/short-link/admin/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
        return Results.success(userService.hasUsername(username));
    }

    /**
    * 注册用户
    * @Param: [requestParam]
    * @return: org.tckry.shortlink.admin.common.convention.result.Result<T>
    * @Date: 2023/12/17
    */
    @PostMapping("/api/short-link/admin/v1/user") // Restful风格，前面POST代表插入，不需要在url中定义url的saveUser
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam){ //  Results.success();必须要返回值，所以方法声明T为大写Voidpublic Result<Void>
        userService.register(requestParam);
        return Results.success();
    }

    /**
    * 根据用户名修改用户
    * @Param: [requestParam]
    * @return: org.tckry.shortlink.admin.common.convention.result.Result<java.lang.Void>
    * @Date: 2023/12/18
    */
    @PutMapping("/api/short-link/admin/v1/user")  // Restful风格，通过语义判断修改用户，url中不用添加额外内容
    public Result<Void> update(@RequestBody UserUpdateReqDTO requestParam) {
        userService.update(requestParam);
        return Results.success();
    }

    /**
     * 用户登录
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/admin/v1/user/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam) {  // 登录返回一个String 的token，但是为了后期的扩展，这里把token封装到类里了
        return Results.success(userService.login(requestParam));
    }

    /**
    * 检查用户是否登录
    * @Param: [token]
    * @return: org.tckry.shortlink.admin.common.convention.result.Result<java.lang.Boolean>
    * @Date: 2023/12/18
    */
    @GetMapping("/api/short-link/admin/v1/user/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username") String username,@RequestParam("token") String token){
        return Results.success(userService.checkLogin(username,token));
    }

    /**
     * 用户退出登录
     * @param username
     * @param token
     * @return
     */
    @DeleteMapping("/api/short-link/admin/v1/user/logout")    // 涉及到数据的删除，使用DeleteMapping，删redis的数据
    public Result<Void> logout(@RequestParam("username") String username,@RequestParam("token") String token){
        userService.logout(username,token);
        return Results.success();
    }
}
