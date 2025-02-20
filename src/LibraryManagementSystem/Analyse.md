    class Book {
    -String bookId
    -String title
    -String author
    -String isbn
    -boolean isBorrowed
    +getBookInfo()
    +updateStatus()
    }

    class User {
        -String userId
        -String name
        -String phone
        -List<BorrowRecord> records
        +borrowBook()
        +returnBook()
    }

    class BorrowRecord {
        -String recordId
        -LocalDate borrowDate
        -LocalDate dueDate
        -Book borrowedBook
        -User borrower
        +calculateOverdueDays()
    }

    class Library {
        -List<Book> books
        -List<User> users
        +addBook()
        +removeBook()
        +searchBook()
        +generateReport()
    }

    Book "1" -- "1..*" BorrowRecord
    User "1" -- "1..*" BorrowRecord
    Library --> Book
    Library --> User