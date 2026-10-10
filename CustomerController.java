package j930.controller;

import j930.entity.Customer;
import j930.entity.Preference;
import j930.service.CustomerService;
import j930.service.impl.CustomerServiceImpl;
import j930.util.JsonResult;
import j930.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户 Controller（普通 Servlet）。
 * web.xml 里配置映射：/customer/*
 */
public class CustomerController extends HttpServlet {

    private CustomerService customerService = new CustomerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        PrintWriter out = resp.getWriter();

        try {
            if (path == null || "/list".equals(path)) {
                String name = req.getParameter("name");
                if (name != null && !name.isEmpty()) {
                    out.print(JsonUtils.toJson(JsonResult.ok(customerService.findByName(name))));
                } else {
                    out.print(JsonUtils.toJson(JsonResult.ok(customerService.findAll())));
                }
            } else if ("/get".equals(path)) {
                Integer id = Integer.valueOf(req.getParameter("id"));
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.findOne(id))));
            } else if ("/byIdCard".equals(path)) {
                String idcard = req.getParameter("idcard");
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.findByIdCard(idcard))));
            } else if ("/byBed".equals(path)) {
                Integer bedId = Integer.valueOf(req.getParameter("bedId"));
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.findByBedId(bedId))));
            } else if ("/byRoom".equals(path)) {
                String roomNo = req.getParameter("roomNo");
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.findByRoomNo(roomNo))));
            } else if ("/byBuilding".equals(path)) {
                String buildingNo = req.getParameter("buildingNo");
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.findByBuildingNo(buildingNo))));
            } else if ("/byLevel".equals(path)) {
                Integer levelId = Integer.valueOf(req.getParameter("levelId"));
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.findByLevelId(levelId))));
            } else if ("/byUser".equals(path)) {
                Integer userId = Integer.valueOf(req.getParameter("userId"));
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.findByUserId(userId))));
            } else if ("/expiring".equals(path)) {
                LocalDate date = LocalDate.parse(req.getParameter("date"));
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.findExpiringBefore(date))));
            } else if ("/preference".equals(path)) {
                Integer customerId = Integer.valueOf(req.getParameter("customerId"));
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.findPreferenceByCustomerId(customerId))));
            } else if ("/page".equals(path)) {
                int pageNum = Integer.parseInt(req.getParameter("pageNum"));
                int pageSize = Integer.parseInt(req.getParameter("pageSize"));
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.findPage(pageNum, pageSize))));
            } else if ("/count".equals(path)) {
                out.print(JsonUtils.toJson(JsonResult.ok(customerService.count())));
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
                Customer customer = buildCustomer(req);
                boolean ok = customerService.save(customer);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("新增失败")));
            } else if ("/update".equals(path)) {
                Customer customer = buildCustomer(req);
                customer.setId(Integer.valueOf(req.getParameter("id")));
                boolean ok = customerService.update(customer);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("修改失败")));
            } else if ("/assignUser".equals(path)) {
                Integer customerId = Integer.valueOf(req.getParameter("customerId"));
                Integer userId = Integer.valueOf(req.getParameter("userId"));
                boolean ok = customerService.assignCustomerToUser(customerId, userId);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("分配失败")));
            } else if ("/removeUser".equals(path)) {
                Integer customerId = Integer.valueOf(req.getParameter("customerId"));
                boolean ok = customerService.removeCustomerFromUser(customerId);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("解除失败")));
            } else if ("/savePreference".equals(path)) {
                Integer customerId = Integer.valueOf(req.getParameter("customerId"));
                Preference p = new Preference();
                p.setPreferences(req.getParameter("preferences"));
                p.setAttention(req.getParameter("attention"));
                p.setRemark(req.getParameter("remark"));
                List<Preference> list = new ArrayList<>();
                list.add(p);
                boolean ok = customerService.savePreferences(customerId, list);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("保存失败")));
            } else if ("/deletePreference".equals(path)) {
                Integer customerId = Integer.valueOf(req.getParameter("customerId"));
                boolean ok = customerService.deletePreferences(customerId);
                out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("删除失败")));
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
            boolean ok = customerService.delete(id);
            out.print(JsonUtils.toJson(ok ? JsonResult.ok() : JsonResult.fail("删除失败")));
        } catch (Exception e) {
            out.print(JsonUtils.toJson(JsonResult.fail(e.getMessage())));
        }
    }

    private Customer buildCustomer(HttpServletRequest req) {
        Customer c = new Customer();
        c.setCustomerName(req.getParameter("customerName"));
        String age = req.getParameter("customerAge");
        if (age != null && !age.isEmpty()) c.setCustomerAge(Integer.valueOf(age));
        String sex = req.getParameter("customerSex");
        if (sex != null && !sex.isEmpty()) c.setCustomerSex(Integer.valueOf(sex));
        c.setIdcard(req.getParameter("idcard"));
        c.setRoomNo(req.getParameter("roomNo"));
        c.setBuildingNo(req.getParameter("buildingNo"));
        String checkin = req.getParameter("checkinDate");
        if (checkin != null && !checkin.isEmpty()) c.setCheckinDate(LocalDate.parse(checkin));
        String expiration = req.getParameter("expirationDate");
        if (expiration != null && !expiration.isEmpty()) c.setExpirationDate(LocalDate.parse(expiration));
        c.setContactTel(req.getParameter("contactTel"));
        String bedId = req.getParameter("bedId");
        if (bedId != null && !bedId.isEmpty()) c.setBedId(Integer.valueOf(bedId));
        c.setPsychosomaticState(req.getParameter("psychosomaticState"));
        c.setAttention(req.getParameter("attention"));
        String birthday = req.getParameter("birthday");
        if (birthday != null && !birthday.isEmpty()) c.setBirthday(LocalDate.parse(birthday));
        c.setHeight(req.getParameter("height"));
        c.setWeight(req.getParameter("weight"));
        c.setBloodType(req.getParameter("bloodType"));
        c.setFilepath(req.getParameter("filepath"));
        String userId = req.getParameter("userId");
        if (userId != null && !userId.isEmpty()) c.setUserId(Integer.valueOf(userId));
        String levelId = req.getParameter("levelId");
        if (levelId != null && !levelId.isEmpty()) c.setLevelId(Integer.valueOf(levelId));
        c.setFamilyNumber(req.getParameter("familyNumber"));
        return c;
    }
}