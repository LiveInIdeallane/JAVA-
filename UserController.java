package j930.controller;

import j930.entity.User;
import j930.service.UserService;
import j930.service.impl.UserServiceImpl;
import j930.util.JsonResult;
import j930.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户 Controller（普通 Servlet，不用 @WebServlet 注解）。
 * web.xml 里配置映射：/user/*
 */
public class UserController extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        PrintWriter out = resp.getWriter();

        try {
            if (path == null || "/list".equals(path)) {
                String nickname = req.getParameter("nickname");
                if (nickname != null && !nickname.isEmpty()) {
                    out.print(JsonUtils.toJson(JsonResult.ok(userService.findByNickname(nickname))));
                } else {
                    out.print(JsonUtils.toJson(JsonResult.ok(userService.findAll())));
                }
            } else if ("/get".equals(path)) {
                Integer id = Integer.valueOf(req.getParameter("id"));
                out.print(JsonUtils.toJson(JsonResult.ok(userService.findOne(id))));
            } else if ("/byRole".equals(path)) {
                Integer roleId = Integer.valueOf(req.getParameter("roleId"));
                out.print(JsonUtils.toJson(JsonResult.ok(userService.findByRoleId(roleId))));
            } else if ("/byUsername".equals(path)) {
                String username = req.getParameter("username");
                out.print(JsonUtils.toJson(JsonResult.ok(userService.findByUsername(username))));
            } else if ("/page".equals(path)) {
                int pageNum = Integer.parseInt(req.getParameter("pageNum"));
                int pageSize = Integer.parseInt(req.getParameter("pageSize"));
                out.print(JsonUtils.toJson(JsonResult.ok(userService.findPage(pageNum, pageSize))));
            } else if ("/count".equals(path)) {
                out.print(JsonUtils.toJson(JsonResult.ok(userService.count())));
            } else {
                out.print(JsonUtils.toJson(JsonResult.fail("未知路径：" + path)));
            }
        } catch (Exception e) {
            out.print(JsonUtils.toJson(JsonResult.fail(e.getMessage())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        PrintWriter out = resp.getWriter();

        try {
            if ("/login".equals(path)) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                User user = userService.login(username, password);
                out.print(JsonUtils.toJson(JsonResult.ok(user)));
            } else if ("/save".equals(path)) {
                User user = buildUser(req);
                boolean ok = userService.save(user);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("新增失败")));
            } else if ("/update".equals(path)) {
                User user = buildUser(req);
                user.setId(Integer.valueOf(req.getParameter("id")));
                boolean ok = userService.update(user);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("修改失败")));
            } else {
                out.print(JsonUtils.toJson(JsonResult.fail("未知路径：" + path)));
            }
        } catch (Exception e) {
            out.print(JsonUtils.toJson(JsonResult.fail(e.getMessage())));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            Integer id = Integer.valueOf(req.getParameter("id"));
            boolean ok = userService.delete(id);
            out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("删除失败")));
        } catch (Exception e) {
            out.print(JsonUtils.toJson(JsonResult.fail(e.getMessage())));
        }
    }

    private User buildUser(HttpServletRequest req) {
        User user = new User();
        user.setNickname(req.getParameter("nickname"));
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        String sex = req.getParameter("sex");
        if (sex != null && !sex.isEmpty()) user.setSex(Integer.valueOf(sex));
        user.setEmail(req.getParameter("email"));
        user.setPhoneNumber(req.getParameter("phoneNumber"));
        String roleId = req.getParameter("roleId");
        if (roleId != null && !roleId.isEmpty()) user.setRoleId(Integer.valueOf(roleId));
        return user;
    }
}