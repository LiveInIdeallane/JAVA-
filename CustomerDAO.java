package j930.dao.impl;

import j930.entity.Customer;

import java.time.LocalDate;
import java.util.List;

public interface CustomerDAO {
    /** 新增 */
    int save(Customer customer);

    /** 修改 */
    int update(Customer customer);

    /** 根据主键删除（逻辑删除） */
    int delete(Integer id);

    /** 根据主键查询单条 */
    Customer findOne(Integer id);

    /** 根据客户姓名模糊查询 */
    List<Customer> findByName(String name);

    /** 根据联系电话查询 */
    Customer findByPhone(String phone);

    /** 根据房间号查询该房间所有客户 */
    List<Customer> findByRoomNo(String roomNo);

    /** 根据楼栋查询 */
    List<Customer> findByBuildingNo(String buildingNo);

    /** 根据护理等级查询 */
    List<Customer> findByLevelId(Integer levelId);

    /** 根据健康管家查询（查某个护工负责的所有客户） */
    List<Customer> findByUserId(Integer userId);

    /** 查询所有在住客户 */
    List<Customer> findAll();

    /** 分页查询 */
    List<Customer> findPage(int offset, int limit);

    /** 统计总数 */
    int count();

    /** 查询合同即将到期的客户（到期日在指定日期之前） */
    List<Customer> findExpiringBefore(LocalDate date);

    /** 根据身份证号查询（前台登记查重 / 医保关联用） */
    Customer findByIdCard(String idcard);

    /** 根据床位号反查客户（调床时用） */
    Customer findByBedId(Integer bedId);

}
