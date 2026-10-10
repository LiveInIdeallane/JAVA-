package j930.controller;

import j930.entity.Role;
import j930.service.RoleService;
import j930.service.impl.RoleServiceImpl;
import j930.util.JsonResult;
import j930.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色 Controller（普通 Servlet）。
 * web.xml 里配置映射：/role/*
 */
public class RoleController extends HttpServlet {

    private RoleService roleService = new RoleServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        PrintWriter out = resp.getWriter();

        try {
            if (path == null || "/list".equals(path)) {
                out.print(JsonUtils.toJson(JsonResult.ok(roleService.findAll())));
            } else if ("/get".equals(path)) {
                Integer id = Integer.valueOf(req.getParameter("id"));
                out.print(JsonUtils.toJson(JsonResult.ok(roleService.findOne(id))));
            } else if ("/byName".equals(path)) {
                String name = req.getParameter("name");
                out.print(JsonUtils.toJson(JsonResult.ok(roleService.findByName(name))));
            } else if ("/menus".equals(path)) {
                Integer roleId = Integer.valueOf(req.getParameter("roleId"));
                out.print(JsonUtils.toJson(JsonResult.ok(roleService.findMenusByRoleId(roleId))));
            } else if ("/menuIds".equals(path)) {
                Integer roleId = Integer.valueOf(req.getParameter("roleId"));
                out.print(JsonUtils.toJson(JsonResult.ok(roleService.findMenuIdsByRoleId(roleId))));
            } else if ("/page".equals(path)) {
                int pageNum = Integer.parseInt(req.getParameter("pageNum"));
                int pageSize = Integer.parseInt(req.getParameter("pageSize"));
                out.print(JsonUtils.toJson(JsonResult.ok(roleService.findPage(pageNum, pageSize))));
            } else if ("/count".equals(path)) {
                out.print(JsonUtils.toJson(JsonResult.ok(roleService.count())));
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
            if ("/save".equals(path)) {
                Role role = new Role();
                role.setName(req.getParameter("name"));
                boolean ok = roleService.save(role);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("新增失败")));
            } else if ("/update".equals(path)) {
                Role role = new Role();
                role.setId(Integer.valueOf(req.getParameter("id")));
                role.setName(req.getParameter("name"));
                boolean ok = roleService.update(role);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("修改失败")));
            } else if ("/assignMenus".equals(path)) {
                Integer roleId = Integer.valueOf(req.getParameter("roleId"));
                String[] menuIdArr = req.getParameterValues("menuIds");
                List<Integer> menuIds = new ArrayList<>();
                if (menuIdArr != null) {
                    for (String s : menuIdArr) menuIds.add(Integer.valueOf(s));
                }
                boolean ok = roleService.assignMenus(roleId, menuIds);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("分配失败")));
            } else if ("/removeMenu".equals(path)) {
                Integer roleId = Integer.valueOf(req.getParameter("roleId"));
                Integer menuId = Integer.valueOf(req.getParameter("menuId"));
                boolean ok = roleService.removeMenu(roleId, menuId);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("取消失败")));
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
            boolean ok = roleService.delete(id);
            out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("删除失败")));
        } catch (Exception e) {
            out.print(JsonUtils.toJson(JsonResult.fail(e.getMessage())));
        }
    }
}