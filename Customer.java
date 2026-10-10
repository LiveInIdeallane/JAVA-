package j930.entity;

import lombok.Data;

import java.time.LocalDate;


@Data
public class Customer {

    private Integer id;
    private Integer isDeleted;          // 逻辑删除(0显示1隐藏)

    private String customerName;        // customer_name 客户姓名
    private Integer customerAge;        // customer_age 年龄
    private Integer customerSex;        // customer_sex 性别(0男1女)
    private String idcard;              // 身份证号
    private String roomNo;              // room_no 房间号
    private String buildingNo;          // building_no 所属楼房
    private LocalDate checkinDate;  // checkin_date 入住时间
    private LocalDate expirationDate;// expiration_date 合同到期时间
    private String contactTel;          // contact_tel 联系电话
    private Integer bedId;              // bed_id 床号

    private String psychosomaticState;  // psychosomatic_state 身心状况
    private String attention;           // 注意事项
    private LocalDate birthday;     // 出生日期
    private String height;              // 身高
    private String weight;              // 体重
    private String bloodType;           // blood_type 血型
    private String filepath;            // 头像路径

    private Integer userId;             // user_id 关联健康管家(护工),无管家为-1
    private Integer levelId;            // level_id 护理等级
    private String familyNumber;        // family_number 家属

}
