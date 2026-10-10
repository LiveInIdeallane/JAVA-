package j930.controller;

import j930.entity.Menu;
import j930.service.MenuService;
import j930.service.impl.MenuServiceImpl;
import j930.util.JsonResult;
import j930.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 菜单 Controller（普通 Servlet）。
 * web.xml 里配置映射：/menu/*
 */
public class MenuController extends HttpServlet {

    private MenuService menuService = new MenuServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        PrintWriter out = resp.getWriter();

        try {
            if (path == null || "/list".equals(path)) {
                out.print(JsonUtils.toJson(JsonResult.ok(menuService.findAll())));
            } else if ("/get".equals(path)) {
                Integer id = Integer.valueOf(req.getParameter("id"));
                out.print(JsonUtils.toJson(JsonResult.ok(menuService.findOne(id))));
            } else if ("/byPath".equals(path)) {
                String p = req.getParameter("path");
                out.print(JsonUtils.toJson(JsonResult.ok(menuService.findByPath(p))));
            } else if ("/top".equals(path)) {
                out.print(JsonUtils.toJson(JsonResult.ok(menuService.findTopMenus())));
            } else if ("/byParent".equals(path)) {
                Integer parentId = Integer.valueOf(req.getParameter("parentId"));
                out.print(JsonUtils.toJson(JsonResult.ok(menuService.findByParentId(parentId))));
            } else if ("/byRole".equals(path)) {
                Integer roleId = Integer.valueOf(req.getParameter("roleId"));
                out.print(JsonUtils.toJson(JsonResult.ok(menuService.findMenusByRoleId(roleId))));
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
                Menu menu = buildMenu(req);
                boolean ok = menuService.save(menu);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("新增失败")));
            } else if ("/update".equals(path)) {
                Menu menu = buildMenu(req);
                menu.setId(Integer.valueOf(req.getParameter("id")));
                boolean ok = menuService.update(menu);
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
            boolean ok = menuService.delete(id);
            out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("删除失败")));
        } catch (Exception e) {
            out.print(JsonUtils.toJson(JsonResult.fail(e.getMessage())));
        }
    }

    private Menu buildMenu(HttpServletRequest req) {
        Menu menu = new Menu();
        menu.setMenusIndex(req.getParameter("menusIndex"));
        menu.setTitle(req.getParameter("title"));
        menu.setIcon(req.getParameter("icon"));
        menu.setPath(req.getParameter("path"));
        String parentId = req.getParameter("parentId");
        if (parentId != null && !parentId.isEmpty()) menu.setParentId(Integer.valueOf(parentId));
        return menu;
    }
}