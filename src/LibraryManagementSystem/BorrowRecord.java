package LibraryManagementSystem;

import java.time.LocalDate;
import java.time.LocalTime;

public class BorrowRecord {
    private String recordId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private Book borrowedBook;
    private User borrower;

    public BorrowRecord() {
    }

    public BorrowRecord(String recordId, LocalDate borrowDate, LocalDate dueDate, Book borrowedBook, User borrower) {
        this.recordId = recordId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.borrowedBook = borrowedBook;
        this.borrower = borrower;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Book getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(Book borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    //计算归还日期
    public void calculateOverdueDays(){

    }
}
