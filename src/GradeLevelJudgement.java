import java.util.Scanner;

public class GradeLevelJudgement {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        //用户输入成绩,可以重复输入，按0退出
        while (true) {
            System.out.println("请输入你的成绩，按（0）退出");
            int grade = sc.nextInt();

            if (grade == 0) {
                break;
            }

            if (grade < 0 || grade > 100) {
                System.out.println("你输入的成绩不规范，请重新输入");
                continue;
            }
            LevelJudgement(grade);
        }
        sc.close();

    }

    //创建一个方法来判断成绩等级
    private static void LevelJudgement(int grade) {
        String level;
        if (grade <= 60) {
            level= "D";
        } else if (grade <= 70) {
            level = "C";
        } else if (grade <= 80) {
            level = "B";
        } else {
            level = "A";
        }
        System.out.println("你的等级为："+level);
    }


}
