package LibraryManagementSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntToDoubleFunction;

public class library {
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public library() {
        while (true) {
            System.out.println("-----欢迎来到图书管理系统-----\n" + "按对应数字选择功能，按【1】图书管理、按【2】用户管理" +
                    "按【3】借阅管理、按【4】数据统计");
            int choose1 = sc.nextInt();
            sc.nextLine();
            switch (choose1) {
                case 1:
                    libraryManagement();
                    break;
                case 2:
                    userManagement();
                    break;
                case 3:
                    borrowedManagement();
                    break;
                case 4:
                    generateReport();
                    break;
                default:
                    System.out.println("无效输入");
                    break;
            }

        }

    }

    //借阅管理
    private void borrowedManagement() {
        System.out.println("按对应数字选择功能，按【1】借书、按【2】还书" + "按【0】返回上一级");
        int choose4 = sc.nextInt();
        sc.nextLine();
        switch (choose4) {
            case 1:
                borrowedBook();
                break;
            case 2:
                returnBook();
                break;
            case 0:
                return;
            default:
                System.out.println("无效输入");
                break;
        }

    }

    //还书
    private void returnBook() {
        System.out.println("请输入所还书的id");
        String returnBookID = sc.nextLine();

        boolean bookFound = false; //标记书籍是否找到
        boolean recordFound = false; //标记借阅记录是否找到

        //查找数据
        for (Book book : books) {
            if (returnBookID.equals(book.getBookId())) {
                bookFound = true;
                if (!book.isBorrowed()) {
                    System.out.println("这本书已经是未借出的状态");
                    return; //如果书籍已经是未借出的状态，直接返回

                }

                //还书操作
                book.setBorrowed(false);
                System.out.println("归还成功！");

                //查找用户借阅记录并删除归还记录
                for (User user : users) {
                    //如果用户借阅了此书
                    for (BorrowRecord record : user.getRecords()) {
                        if (record.getBorrowedBook().getBookId().equals(returnBookID)) {
                            //删除借阅记录
                            user.getRecords().remove(record);
                            System.out.println("用户借阅记录已更新，已移除此书籍");
                            recordFound = true;
                            break;
                        }
                    }
                    if (recordFound) break;  //如果找到了并删除了记录，退出循环
                }
                if (!recordFound) {
                    System.out.println("未找到对应的借阅记录");
                }
                return; //结束还书操作
            }
        }
        if (!bookFound) {
            System.out.println("未找到该书，请确认输入id是否正确");
        }
    }

    //借书
    private void borrowedBook() {
        while (true) {
            System.out.println("请先登录，请输入用户id和手机号码");
            String userId = sc.nextLine();
            String userTel = sc.nextLine();

            boolean isLoggedIn = false; // 用来标记是否登录成功

            // 登录验证
            User loggedInUser=null;  //记录登陆成功的用户
            for (User user : users) {
                if (userId.equals(user.getUserId()) && userTel.equals(user.getPhone())) {
                    System.out.println("登录成功");
                    loggedInUser=user;
                    isLoggedIn = true;
                    break; // 登录成功，退出循环
                }
            }

            if (!isLoggedIn) {
                System.out.println("登录失败，请重新输入");
                continue; // 如果登录失败，继续循环，让用户重新输入
            }

            // 确保 loggedInUser 不为 null，再进行后续操作
            if (loggedInUser == null) {
                System.out.println("无法找到登录用户");
                continue; // 继续提示用户重新输入
            }

            // 登录成功后，开始借书操作
            System.out.println("请输入你要借阅的书id");
            String borrowId = sc.nextLine();

            boolean bookFound = false; // 用来标记书籍是否找到
            for (Book book : books) {
                if (borrowId.equals(book.getBookId())) {
                    bookFound = true;
                    if (!book.isBorrowed()) {
                        // 书籍未借出，更新借出状态
                        book.setBorrowed(true);
                        System.out.println("借阅成功！");

                        // 获取当前日期作为借书日期
                        LocalDate borrowDate = LocalDate.now();
                        // 借阅日期为14天
                        LocalDate dueDate = borrowDate.plusDays(14);


                        // 确保 loggedInUser 的借阅记录已初始化
                        if (loggedInUser.getRecords() == null) {
                            loggedInUser.setRecords(new ArrayList<>()); // 如果 records 为空，则初始化它
                        }

                        // 创建借阅记录并添加到用户记录
                        BorrowRecord newRecord = new BorrowRecord(generateRecordId(), borrowDate, dueDate, book, loggedInUser);
                        loggedInUser.getRecords().add(newRecord);
                    } else {
                        System.out.println("该书已经被借出！");
                    }
                    return;
                }
            }

            if (!bookFound) {
                System.out.println("未找到该书籍，请确认书籍id输入是否正确");
            }
        }
    }


    //生成借阅记录的id，确保每次借阅都有唯一的记录id

    private String generateRecordId() {
        return "RECORD" + System.currentTimeMillis(); //使用当前时间戳生成唯一id
    }

    //用户管理
    private void userManagement() {
        while (true) {
            System.out.println("按对应数字选择功能，按【1】注册用户、按【2】注销用户" +
                    "按【3】查询用户、按【0】返回上一级");
            int choose3 = sc.nextInt();
            sc.nextLine();  //清除换行符
            switch (choose3) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    removeUser();
                    break;
                case 3:
                    searchUser();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("无效输入");
                    break;
            }
        }
    }

    //查找
    private void searchUser() {
        System.out.println("请输入你要查找的用户id");
        String searchUserId = sc.nextLine();
        boolean found = false;
        for (User user : users) {
            if (searchUserId.equals(user.getUserId())) {
                System.out.println("你要查找的书籍是：" + "用户id为：" + user.getUserId() + "姓名为："
                        + user.getName() + ",手机号码为：" + user.getPhone() + "借阅书id为：" + user.getRecords()
                );
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("没有找到该用户，请重新输入");
        }
    }

    //注销
    private void removeUser() {
        System.out.println("请输入你要注销的id");
        String deleteUserId = sc.nextLine();
        for (User user : users) {
            if (deleteUserId.equals(user.getUserId())) {
                users.remove(user);
                System.out.println("删除成功!");
                return;
            }
        }
        System.out.println("未找到该用户");
    }

    //注册
    private void registerUser() {
        System.out.println("请分别输入你的id，姓名，联系方式");
        String id = sc.nextLine();
        String name = sc.nextLine();
        String tel = sc.nextLine();
        User newUser = new User(id, name, tel, null);
        users.add(newUser);
        System.out.println("注册用户成功！");
    }

    //图书管理
    private void libraryManagement() {
        while (true) {
            System.out.println("按对应数字选择功能，按【1】添加图书、按【2】删除图书" +
                    "按【3】查找图书、按【4】修改图书、按【0】返回上一级");
            int choose2 = sc.nextInt();
            sc.nextLine();  //清除换行符
            switch (choose2) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    generateReport();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("无效输入");
                    break;
            }
        }
    }


    //添加书籍
    public void addBook() {
        System.out.println("请输入你要添加的图书Id,书名，作者，isbn");
        String bookId = sc.nextLine();
        String title = sc.nextLine();
        String author = sc.nextLine();
        String isbn = sc.nextLine();
        Book newBook = new Book(bookId, title, author, isbn, false);
        books.add(newBook);
        System.out.println("添加成功！");
    }

    //删除书籍
    public void removeBook() {
        System.out.println("请输入你要删除的书id");
        String deleteBook = sc.nextLine();
        for (Book book : books) {
            if (deleteBook.equals(book.getBookId())) {
                books.remove(book);
                System.out.println("删除成功！");
                return;
            }
        }
        System.out.println("未找到该书籍");
    }

    //查找书籍
    public void searchBook() {
        System.out.println("请输入你要查找的图书id");
        String searchBook = sc.nextLine();
        boolean found = false;
        for (Book book : books) {
            if (searchBook.equals(book.getBookId())) {
                System.out.println("你要查找的书籍是：" + "书籍id为：" + book.getBookId() + ","
                        + book.getAuthor() + "的" + book.getTitle() + "isbn为：" + book.getIsbn()
                        + "是否借出：" + book.isBorrowed());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("没有找到该书籍，请重新输入");
        }
    }

    //生成报表文件
    public void generateReport() {
        String reportFilename = "LibraryReport.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilename))) {
            //写入报表头部
            writer.write("---- 图书管理系统报表 ----");
            writer.write("书籍信息：\n");

            //写入所有图书的信息
            for (Book book : books) {
                writer.write("书籍ID：" + book.getBookId() + ", ");
                writer.write("书名：" + book.getTitle() + ", ");
                writer.write("作者：" + book.getAuthor() + ", ");
                writer.write("ISBN：" + book.getIsbn() + ", ");
                writer.write("是否借出：" + (book.isBorrowed() ? "是" : "否") + "\n");
            }
            writer.write("\n\n用户借阅记录:\n");

            //写入所有用户的借阅记录
            for (User user : users) {
                writer.write("用户ID:" + user.getUserId() + ", ");
                writer.write("姓名:" + user.getName() + ", ");
                writer.write("手机号码:" + user.getPhone() + "\n");

                //如果用户有借阅记录
                if (user.getRecords() != null && !user.getRecords().isEmpty()) {
                    for (BorrowRecord record : user.getRecords()) {
                        writer.write(" 借阅书籍ID：" + record.getBorrowedBook().getBookId() + ", ");
                        writer.write("书名为：" + record.getBorrowedBook().getTitle() + ", ");
                        writer.write("借阅日期:" + record.getBorrowDate() + ", ");
                        writer.write("归还日期:" + record.getDueDate() + "\n");
                    }
                } else {
                    writer.write("无借阅记录\n");
                }
                writer.write("\n");
            }
            System.out.println("报表已成功生成，“文件名：" + reportFilename);
        } catch (IOException e) {
            System.out.println("生成报表时发生错误:" + e.getMessage());
        }
    }
}
