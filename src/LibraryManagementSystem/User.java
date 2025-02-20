package LibraryManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String name;
    private String phone;
    List<BorrowRecord> records;

    public User() {
    }

    public User(String userId, String name, String phone, List<BorrowRecord> records) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.records = records;
        this.records=new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<BorrowRecord> getRecords() {
        return records;
    }

    public void setRecords(List<BorrowRecord> records) {
        this.records = records;
    }

    //用户借书
    public void borrowedBook(){

    }

    //用户还书
    public void returnBook(){

    }
}
