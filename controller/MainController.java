package j930.controller;

import j930.entity.*;
import j930.service.*;
import j930.service.impl.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 控制台主菜单入口。
 * 覆盖：用户、角色、菜单、客户 四个模块的菜单交互。
 * 关联功能：
 * - 用户-角色：新增/修改用户时选角色
 * - 角色-菜单：给角色分配菜单
 * - 用户-客户：给健康管家分配服务客户
 * - 客户-偏好：客户偏好维护
 */
public class MainController {

    private static final Scanner sc = new Scanner(System.in);

    private static final UserService userService = new UserServiceImpl();
    private static final RoleService roleService = new RoleServiceImpl();
    private static final MenuService menuService = new MenuServiceImpl();
    private static final CustomerService customerService = new CustomerServiceImpl();

    // ===================== 主菜单 =====================

    public static void mainMenu() {
        while (true) {
            System.out.println("\n===== 东软颐养中心管理系统 =====");
            System.out.println("1. 用户管理");
            System.out.println("2. 角色管理");
            System.out.println("3. 菜单管理");
            System.out.println("4. 客户管理");
            System.out.println("0. 退出");
            System.out.print("请选择服务类型(输入序号)：");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": userMenu(); break;
                case "2": roleMenu(); break;
                case "3": menuMenu(); break;
                case "4": customerMenu(); break;
                case "0":
                    System.out.println("已退出系统。");
                    return;
                default:
                    System.out.println("无效序号，请重新输入。");
            }
        }
    }

    // ===================== 1. 用户管理 =====================

    private static void userMenu() {
        while (true) {
            System.out.println("\n--- 用户管理 ---");
            System.out.println("1. 新增用户");
            System.out.println("2. 修改用户");
            System.out.println("3. 删除用户");
            System.out.println("4. 查询用户（按 id）");
            System.out.println("5. 查询所有用户");
            System.out.println("6. 按角色查用户");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");

            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addUser(); break;
                    case "2": updateUser(); break;
                    case "3": deleteUser(); break;
                    case "4": findUserById(); break;
                    case "5": listAllUsers(); break;
                    case "6": listUsersByRole(); break;
                    case "0": return;
                    default: System.out.println("无效序号。");
                }
            } catch (Exception e) {
                System.out.println("操作失败：" + e.getMessage());
            }
        }
    }

    private static void addUser() {
        User u = new User();
        System.out.print("真实姓名："); u.setNickname(sc.nextLine());
        System.out.print("系统账号："); u.setUsername(sc.nextLine());
        System.out.print("密码（留空默认手机号后6位）：");
        String pwd = sc.nextLine();
        if (!pwd.isEmpty()) u.setPassword(pwd);
        System.out.print("性别(0女 1男)："); u.setSex(Integer.parseInt(sc.nextLine()));
        System.out.print("邮箱："); u.setEmail(sc.nextLine());
        System.out.print("电话："); u.setPhoneNumber(sc.nextLine());

        System.out.println("可选角色：");
        for (Role r : roleService.findAll()) {
            System.out.println("  " + r.getId() + " - " + r.getName());
        }
        System.out.print("角色ID："); u.setRoleId(Integer.parseInt(sc.nextLine()));

        System.out.println(userService.save(u) ? "新增成功" : "新增失败");
    }

    private static void updateUser() {
        System.out.print("要修改的用户 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        User u = userService.findOne(id);
        if (u == null) { System.out.println("用户不存在"); return; }

        System.out.print("新真实姓名(" + u.getNickname() + ")：");
        String s = sc.nextLine(); if (!s.isEmpty()) u.setNickname(s);
        System.out.print("新账号(" + u.getUsername() + ")：");
        s = sc.nextLine(); if (!s.isEmpty()) u.setUsername(s);
        System.out.print("新性别(" + u.getSex() + ")：");
        s = sc.nextLine(); if (!s.isEmpty()) u.setSex(Integer.parseInt(s));
        System.out.print("新邮箱(" + u.getEmail() + ")：");
        s = sc.nextLine(); if (!s.isEmpty()) u.setEmail(s);
        System.out.print("新电话(" + u.getPhoneNumber() + ")：");
        s = sc.nextLine(); if (!s.isEmpty()) u.setPhoneNumber(s);
        System.out.print("新角色ID(" + u.getRoleId() + ")：");
        s = sc.nextLine(); if (!s.isEmpty()) u.setRoleId(Integer.parseInt(s));

        System.out.println(userService.update(u) ? "修改成功" : "修改失败");
    }

    private static void deleteUser() {
        System.out.print("要删除的用户 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        System.out.println(userService.delete(id) ? "删除成功" : "删除失败");
    }

    private static void findUserById() {
        System.out.print("用户 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        User u = userService.findOne(id);
        System.out.println(u == null ? "未找到" : u);
    }

    private static void listAllUsers() {
        List<User> list = userService.findAll();
        System.out.println("共 " + list.size() + " 条：");
        for (User u : list) System.out.println("  " + u);
    }

    private static void listUsersByRole() {
        System.out.println("可选角色：");
        for (Role r : roleService.findAll()) System.out.println("  " + r.getId() + " - " + r.getName());
        System.out.print("角色ID：");
        Integer roleId = Integer.parseInt(sc.nextLine());
        List<User> list = userService.findByRoleId(roleId);
        System.out.println("该角色下有 " + list.size() + " 个用户：");
        for (User u : list) System.out.println("  " + u);
    }

    // ===================== 2. 角色管理 =====================

    private static void roleMenu() {
        while (true) {
            System.out.println("\n--- 角色管理 ---");
            System.out.println("1. 新增角色");
            System.out.println("2. 修改角色");
            System.out.println("3. 删除角色");
            System.out.println("4. 查询角色（按 id）");
            System.out.println("5. 查询所有角色");
            System.out.println("6. 给角色分配菜单");
            System.out.println("7. 查看角色的菜单");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");

            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addRole(); break;
                    case "2": updateRole(); break;
                    case "3": deleteRole(); break;
                    case "4": findRoleById(); break;
                    case "5": listAllRoles(); break;
                    case "6": assignMenus(); break;
                    case "7": viewRoleMenus(); break;
                    case "0": return;
                    default: System.out.println("无效序号。");
                }
            } catch (Exception e) {
                System.out.println("操作失败：" + e.getMessage());
            }
        }
    }

    private static void addRole() {
        Role r = new Role();
        System.out.print("角色名："); r.setName(sc.nextLine());
        System.out.println(roleService.save(r) ? "新增成功，id=" + r.getId() : "新增失败");
    }

    private static void updateRole() {
        System.out.print("要修改的角色 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        Role r = roleService.findOne(id);
        if (r == null) { System.out.println("角色不存在"); return; }
        System.out.print("新角色名(" + r.getName() + ")：");
        String s = sc.nextLine(); if (!s.isEmpty()) r.setName(s);
        System.out.println(roleService.update(r) ? "修改成功" : "修改失败");
    }

    private static void deleteRole() {
        System.out.print("要删除的角色 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        System.out.println(roleService.delete(id) ? "删除成功" : "删除失败");
    }

    private static void findRoleById() {
        System.out.print("角色 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        Role r = roleService.findOne(id);
        System.out.println(r == null ? "未找到" : r);
    }

    private static void listAllRoles() {
        List<Role> list = roleService.findAll();
        System.out.println("共 " + list.size() + " 条：");
        for (Role r : list) System.out.println("  " + r);
    }

    private static void assignMenus() {
        System.out.print("角色 id：");
        Integer roleId = Integer.parseInt(sc.nextLine());

        List<Menu> allMenus = menuService.findAll();
        System.out.println("所有菜单：");
        for (Menu m : allMenus) System.out.println("  " + m.getId() + " - " + m.getTitle());

        System.out.print("要分配的菜单 id（逗号分隔，例如 1,2,3）：");
        String[] arr = sc.nextLine().split(",");
        List<Integer> menuIds = new ArrayList<>();
        for (String s : arr) {
            if (!s.trim().isEmpty()) menuIds.add(Integer.parseInt(s.trim()));
        }

        System.out.println(roleService.assignMenus(roleId, menuIds) ? "分配成功" : "分配失败");
    }

    private static void viewRoleMenus() {
        System.out.print("角色 id：");
        Integer roleId = Integer.parseInt(sc.nextLine());
        List<Menu> menus = roleService.findMenusByRoleId(roleId);
        System.out.println("该角色拥有 " + menus.size() + " 个菜单：");
        for (Menu m : menus) System.out.println("  " + m);
    }

    // ===================== 3. 菜单管理 =====================

    private static void menuMenu() {
        while (true) {
            System.out.println("\n--- 菜单管理 ---");
            System.out.println("1. 新增菜单");
            System.out.println("2. 修改菜单");
            System.out.println("3. 删除菜单");
            System.out.println("4. 查询菜单（按 id）");
            System.out.println("5. 查询所有菜单");
            System.out.println("6. 查询一级菜单");
            System.out.println("7. 按父级查子菜单");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");

            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addMenu(); break;
                    case "2": updateMenu(); break;
                    case "3": deleteMenu(); break;
                    case "4": findMenuById(); break;
                    case "5": listAllMenus(); break;
                    case "6": listTopMenus(); break;
                    case "7": listMenusByParent(); break;
                    case "0": return;
                    default: System.out.println("无效序号。");
                }
            } catch (Exception e) {
                System.out.println("操作失败：" + e.getMessage());
            }
        }
    }

    private static void addMenu() {
        Menu m = new Menu();
        System.out.print("一级菜单索引："); m.setMenusIndex(sc.nextLine());
        System.out.print("菜单标题："); m.setTitle(sc.nextLine());
        System.out.print("菜单图标："); m.setIcon(sc.nextLine());
        System.out.print("菜单路径："); m.setPath(sc.nextLine());
        System.out.print("父级ID（0 表示顶级）：");
        String s = sc.nextLine();
        if (!s.isEmpty()) m.setParentId(Integer.parseInt(s));
        System.out.println(menuService.save(m) ? "新增成功，id=" + m.getId() : "新增失败");
    }

    private static void updateMenu() {
        System.out.print("要修改的菜单 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        Menu m = menuService.findOne(id);
        if (m == null) { System.out.println("菜单不存在"); return; }
        System.out.print("新标题(" + m.getTitle() + ")：");
        String s = sc.nextLine(); if (!s.isEmpty()) m.setTitle(s);
        System.out.print("新路径(" + m.getPath() + ")：");
        s = sc.nextLine(); if (!s.isEmpty()) m.setPath(s);
        System.out.print("新父级ID(" + m.getParentId() + ")：");
        s = sc.nextLine(); if (!s.isEmpty()) m.setParentId(Integer.parseInt(s));
        System.out.println(menuService.update(m) ? "修改成功" : "修改失败");
    }

    private static void deleteMenu() {
        System.out.print("要删除的菜单 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        System.out.println(menuService.delete(id) ? "删除成功" : "删除失败");
    }

    private static void findMenuById() {
        System.out.print("菜单 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        Menu m = menuService.findOne(id);
        System.out.println(m == null ? "未找到" : m);
    }

    private static void listAllMenus() {
        List<Menu> list = menuService.findAll();
        System.out.println("共 " + list.size() + " 条：");
        for (Menu m : list) System.out.println("  " + m);
    }

    private static void listTopMenus() {
        List<Menu> list = menuService.findTopMenus();
        System.out.println("共 " + list.size() + " 个顶级菜单：");
        for (Menu m : list) System.out.println("  " + m);
    }

    private static void listMenusByParent() {
        System.out.print("父级 id：");
        Integer parentId = Integer.parseInt(sc.nextLine());
        List<Menu> list = menuService.findByParentId(parentId);
        System.out.println("共 " + list.size() + " 个子菜单：");
        for (Menu m : list) System.out.println("  " + m);
    }

    // ===================== 4. 客户管理 =====================

    private static void customerMenu() {
        while (true) {
            System.out.println("\n--- 客户管理 ---");
            System.out.println("1. 新增客户");
            System.out.println("2. 修改客户");
            System.out.println("3. 删除客户");
            System.out.println("4. 查询客户（按 id）");
            System.out.println("5. 按姓名查客户");
            System.out.println("6. 查询所有客户");
            System.out.println("7. 给健康管家分配客户");
            System.out.println("8. 解除健康管家的客户");
            System.out.println("9. 按健康管家查客户");
            System.out.println("10. 保存客户偏好");
            System.out.println("11. 查看客户偏好");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");

            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addCustomer(); break;
                    case "2": updateCustomer(); break;
                    case "3": deleteCustomer(); break;
                    case "4": findCustomerById(); break;
                    case "5": findCustomerByName(); break;
                    case "6": listAllCustomers(); break;
                    case "7": assignCustomerToUser(); break;
                    case "8": removeCustomerFromUser(); break;
                    case "9": listCustomersByUser(); break;
                    case "10": savePreference(); break;
                    case "11": viewPreference(); break;
                    case "0": return;
                    default: System.out.println("无效序号。");
                }
            } catch (Exception e) {
                System.out.println("操作失败：" + e.getMessage());
            }
        }
    }

    private static void addCustomer() {
        Customer c = new Customer();
        System.out.print("客户姓名："); c.setCustomerName(sc.nextLine());
        System.out.print("身份证号："); c.setIdcard(sc.nextLine());
        System.out.print("性别(0男 1女)："); c.setCustomerSex(Integer.parseInt(sc.nextLine()));
        System.out.print("出生日期(yyyy-MM-dd)："); c.setBirthday(LocalDate.parse(sc.nextLine()));
        System.out.print("联系电话："); c.setContactTel(sc.nextLine());
        System.out.print("房间号："); c.setRoomNo(sc.nextLine());
        System.out.print("床位号："); c.setBedId(Integer.parseInt(sc.nextLine()));
        System.out.print("血型："); c.setBloodType(sc.nextLine());
        System.out.print("家属："); c.setFamilyNumber(sc.nextLine());
        System.out.print("入住时间(yyyy-MM-dd)："); c.setCheckinDate(LocalDate.parse(sc.nextLine()));
        System.out.print("合同到期(yyyy-MM-dd)："); c.setExpirationDate(LocalDate.parse(sc.nextLine()));
        System.out.println(customerService.save(c) ? "新增成功，id=" + c.getId() : "新增失败");
    }

    private static void updateCustomer() {
        System.out.print("要修改的客户 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        Customer c = customerService.findOne(id);
        if (c == null) { System.out.println("客户不存在"); return; }
        System.out.print("新姓名(" + c.getCustomerName() + ")：");
        String s = sc.nextLine(); if (!s.isEmpty()) c.setCustomerName(s);
        System.out.print("新电话(" + c.getContactTel() + ")：");
        s = sc.nextLine(); if (!s.isEmpty()) c.setContactTel(s);
        System.out.print("新房间号(" + c.getRoomNo() + ")：");
        s = sc.nextLine(); if (!s.isEmpty()) c.setRoomNo(s);
        System.out.println(customerService.update(c) ? "修改成功" : "修改失败");
    }

    private static void deleteCustomer() {
        System.out.print("要删除的客户 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        System.out.println(customerService.delete(id) ? "删除成功" : "删除失败");
    }

    private static void findCustomerById() {
        System.out.print("客户 id：");
        Integer id = Integer.parseInt(sc.nextLine());
        Customer c = customerService.findOne(id);
        System.out.println(c == null ? "未找到" : c);
    }

    private static void findCustomerByName() {
        System.out.print("姓名关键字：");
        String name = sc.nextLine();
        List<Customer> list = customerService.findByName(name);
        System.out.println("共 " + list.size() + " 条：");
        for (Customer c : list) System.out.println("  " + c);
    }

    private static void listAllCustomers() {
        List<Customer> list = customerService.findAll();
        System.out.println("共 " + list.size() + " 条：");
        for (Customer c : list) System.out.println("  " + c);
    }

    private static void assignCustomerToUser() {
        System.out.print("客户 id：");
        Integer customerId = Integer.parseInt(sc.nextLine());
        System.out.println("可选健康管家：");
        List<User> users = userService.findByRoleId(2); // 2 = 健康管家
        for (User u : users) System.out.println("  " + u.getId() + " - " + u.getNickname());
        System.out.print("健康管家 id：");
        Integer userId = Integer.parseInt(sc.nextLine());
        System.out.println(customerService.assignCustomerToUser(customerId, userId) ? "分配成功" : "分配失败");
    }

    private static void removeCustomerFromUser() {
        System.out.print("客户 id：");
        Integer customerId = Integer.parseInt(sc.nextLine());
        System.out.println(customerService.removeCustomerFromUser(customerId) ? "解除成功" : "解除失败");
    }

    private static void listCustomersByUser() {
        System.out.print("健康管家 id：");
        Integer userId = Integer.parseInt(sc.nextLine());
        List<Customer> list = customerService.findByUserId(userId);
        System.out.println("该管家服务 " + list.size() + " 个客户：");
        for (Customer c : list) System.out.println("  " + c);
    }

    private static void savePreference() {
        System.out.print("客户 id：");
        Integer customerId = Integer.parseInt(sc.nextLine());
        Preference p = new Preference();
        System.out.print("饮食喜好："); p.setPreferences(sc.nextLine());
        System.out.print("注意事项："); p.setAttention(sc.nextLine());
        System.out.print("备注："); p.setRemark(sc.nextLine());
        List<Preference> list = new ArrayList<>();
        list.add(p);
        System.out.println(customerService.savePreferences(customerId, list) ? "保存成功" : "保存失败");
    }

    private static void viewPreference() {
        System.out.print("客户 id：");
        Integer customerId = Integer.parseInt(sc.nextLine());
        Preference p = customerService.findPreferenceByCustomerId(customerId);
        System.out.println(p == null ? "该客户暂无偏好" : p);
    }
}