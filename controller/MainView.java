package slh.controller;

import java.util.Scanner;

public class MainView {

    private static Scanner scanner = new Scanner(System.in);

    public static void menu() {
        while (true) {
            System.out.println("********************************");
            System.out.println("      东软颐养中心管理系统");
            System.out.println("********************************");
            System.out.println("1、用户管理");
            System.out.println("2、客户管理");
            System.out.println("3、床位管理");
            System.out.println("4、护理管理");
            System.out.println("5、食物管理");
            System.out.println("0、退出系统");
            System.out.println("********************************");
            System.out.print("请输入序号：");

            int select = scanner.nextInt();
            switch (select) {
                case 1:
                    System.out.println("【用户管理模块】开发中...");
                    break;
                case 2:
                    System.out.println("【客户管理模块】开发中...");
                    break;
                case 3:
                    BedView.menu();          // ← 床位管理，正确调用
                    break;
                case 4:
                    System.out.println("【护理管理模块】开发中...");
                    break;
                case 5:
                    System.out.println("【食物管理模块】开发中...");
                    break;
                case 0:
                    System.out.println("感谢使用，再见！");
                    System.exit(0);
                    break;
                default:
                    System.out.println("输入有误，请重新输入！");
            }
        }
    }
}