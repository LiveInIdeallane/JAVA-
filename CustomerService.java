package j930.service;

import j930.entity.Customer;
import j930.entity.Preference;

import java.time.LocalDate;
import java.util.List;

/**
 * 客户业务接口。
 * 关联功能：
 * - 用户-客户：给健康管家分配服务客户、按管家查客户
 * - 客户-偏好：客户偏好的增删改查
 */
public interface CustomerService {

    /** 入住登记：新增客户（校验身份证不重复；自动计算年龄） */
    boolean save(Customer customer);

    /** 修改客户信息 */
    boolean update(Customer customer);

    /** 逻辑删除客户 */
    boolean delete(Integer id);

    /** 按主键查 */
    Customer findOne(Integer id);

    /** 按姓名模糊查 */
    List<Customer> findByName(String name);

    /** 按身份证查（查重） */
    Customer findByIdCard(String idcard);

    /** 按床位反查客户 */
    Customer findByBedId(Integer bedId);

    /** 按房间查 */
    List<Customer> findByRoomNo(String roomNo);

    /** 按楼栋查 */
    List<Customer> findByBuildingNo(String buildingNo);

    /** 按护理等级查 */
    List<Customer> findByLevelId(Integer levelId);

    /** 查所有在住客户 */
    List<Customer> findAll();

    /** 分页查 */
    List<Customer> findPage(int pageNum, int pageSize);

    /** 统计总数 */
    int count();

    /** 合同到期预警 */
    List<Customer> findExpiringBefore(LocalDate date);

    // ========== 用户-客户关联（健康管家） ==========

    /** 查某个健康管家服务的所有客户 */
    List<Customer> findByUserId(Integer userId);

    /** 给健康管家分配服务客户（一个客户只能有一个管家） */
    boolean assignCustomerToUser(Integer customerId, Integer userId);

    /** 解除健康管家与客户的关联 */
    boolean removeCustomerFromUser(Integer customerId);

    // ========== 客户-偏好关联 ==========

    /** 查客户的偏好 */
    Preference findPreferenceByCustomerId(Integer customerId);

    /** 保存客户偏好（先删旧偏好，再批量插入新偏好） */
    boolean savePreferences(Integer customerId, List<Preference> preferences);

    /** 删除客户偏好 */
    boolean deletePreferences(Integer customerId);
}