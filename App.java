package j930;

import j930.controller.MainController;

/**
 * 程序入口：启动主菜单。
 * 和 g921.App / f920.App 一样，入口只负责"启动"，具体交互都在 controller 层。2
 */
public class App {
    public static void main(String[] args) {
        MainController.mainMenu();
    }
}
