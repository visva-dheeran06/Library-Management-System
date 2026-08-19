# Library Management System with Smart Reservations

A standalone Java desktop application that manages library operations using custom-built data structures, smart reservations, automated fines, and borrowing analytics.

The system goes beyond basic library management by implementing Queue, Stack, and Priority Queue data structures from scratch, making the core operations transparent and demonstrating practical applications of Data Structures and Object-Oriented Programming concepts.

## Overview

The Library Management System provides a complete interface for managing books, members, borrowing, returning, waiting lists, fines, borrowing history, and book popularity.

The system includes a smart reservation mechanism where members waiting for unavailable books are prioritized based on their membership type.

## Core Features

- Book catalog management
- Member management
- Book issue and return operations
- Smart priority-based waiting lists
- Automatic book reservation and issue
- Borrowing history tracking
- Automatic overdue fine calculation
- Most-borrowed books leaderboard
- Book search by title, author, or genre
- Book utilization analytics
- Java Swing graphical user interface
- Custom Queue, Stack, and Priority Queue implementations

## Key Highlights

### Custom Data Structures

Instead of relying entirely on Java's built-in collection implementations, the project implements the following data structures from scratch:

- **MyQueue** — FIFO queue
- **MyStack** — LIFO stack
- **MyPriorityQueue** — Priority-based queue with FIFO ordering for equal priorities

This makes the internal operations of the data structures visible and allows their time complexities and implementation details to be demonstrated clearly.

### Smart Reservations

When a book has no available copies, members can join its waiting list.

Members are prioritized according to their membership type:

- Premium — Priority 0
- Faculty — Priority 1
- Student — Priority 2

A lower priority number represents a higher priority.

If two members have the same priority, the member who joined the waiting list first is served first, preserving FIFO ordering within the same membership tier.

When the book is returned, the highest-priority member in the waiting list is automatically issued the available copy.

## System Architecture

    LibraryApp
    Java Swing GUI
         |
         v
    LibrarySystem
    Core Logic
         |
         +----------------+----------------+
         |                |                |
         v                v                v
       Models       Data Structures      Reports
         |                |                |
         v                v                v
    Book / Member    Queue / Stack    Leaderboard
                     Priority Queue   History
                                      Waiting List

## Data Structures

### HashMap

Java's `HashMap` is used for efficient lookup of books and members.

Used for:

- `books` — Book ID to Book object
- `members` — Member ID to Member object
- `waitingQueues` — Book ID to Priority Queue
- `history` — Member ID to Stack

Average time complexity:

- `get()` — O(1)
- `put()` — O(1)

This allows books and members to be retrieved efficiently using their unique IDs.

### MyQueue

A custom FIFO Queue implemented using an `ArrayList`.

Operations:

- `enqueue()` — adds an element to the end
- `dequeue()` — removes an element from the front
- `isEmpty()`
- `clear()`

Time complexity:

- Enqueue — O(1)
- Dequeue — O(n)

The dequeue operation is O(n) because removing the first element from an `ArrayList` shifts the remaining elements.

### MyStack

A custom LIFO Stack implemented using an `ArrayList`.

Used for member borrowing and returning history.

Operations:

- `push()` — O(1)
- `pop()` — O(1)
- `toList()`
- `clear()`

The `toList()` method reverses the internal list so that the most recent transaction appears first in the interface.

### MyPriorityQueue

A custom priority queue implemented using a sorted `ArrayList`.

Each entry stores:

- Data
- Priority
- Insertion order

Operations:

- `enqueue()` — O(n)
- `dequeue()` — O(1)
- `contains()`
- `isEmpty()`

The queue maintains sorted order during insertion.

If two members have the same priority, their insertion order acts as a tiebreaker, preserving FIFO behavior within the same membership category.

### Sorting

The leaderboard uses Java's built-in sorting functionality with a custom `Comparator`.

Books are sorted according to the number of times they have been borrowed.

Time complexity:

`O(n log n)`

## Application Modules

### 1. Catalog

The Catalog tab allows users to:

- View available books
- Search books
- Add new books
- View book availability
- View book utilization information

Search supports matching by:

- Title
- Author
- Genre

### 2. Members

The Members tab allows users to:

- View registered members
- Add new members
- Assign membership types
- Track currently borrowed books
- View fine balances

### 3. Issue / Return

The Issue/Return module handles the complete borrowing workflow.

When a book is issued:

1. Book and member are located using their IDs.
2. Availability is checked.
3. Available copies are decreased.
4. Borrow count is increased.
5. The issue date is recorded.
6. The transaction is added to the member's history.

If no copies are available, the member can be added to the waiting list.

### 4. Waiting List

The Waiting List module displays members waiting for unavailable books.

The system uses the custom `MyPriorityQueue` to determine who receives the book first.

Duplicate waiting-list entries are prevented using the queue's `contains()` method.

### 5. Smart Auto-Issue

When a book is returned:

1. The book's available copies are updated.
2. The return transaction is recorded.
3. Any applicable fine is calculated.
4. The system checks the waiting list.
5. The highest-priority member is removed from the queue.
6. The book is automatically issued to that member.
7. The new issue is added to their borrowing history.

This happens automatically without requiring another manual issue operation.

### 6. Leaderboard

The Leaderboard displays the most-borrowed books.

Books are sorted by their `timesBorrowed` value in descending order.

The system can display the top N most-borrowed books.

### 7. History

Each member has an individual borrowing and return history.

The custom Stack stores transactions, with the most recent action displayed first.

## Fine Calculation

The system uses a 14-day loan period.

If a book is returned after the 14-day limit:

    Fine = (Days Kept - 14) × ₹5

For example:

    Days kept = 20
    Overdue days = 20 - 14
                   = 6

    Fine = 6 × ₹5
         = ₹30

The calculated fine is added to the member's fine balance.

## Book Utilization

The system calculates how heavily a book's available copies are being used.

    Utilization Rate =
    (Total Copies - Available Copies)
    ---------------------------------- × 100
              Total Copies

For example:

    Total copies = 4
    Available copies = 1

    Utilization =
    (4 - 1) / 4 × 100
    = 75%

This provides an indication of which books are currently in high demand.

## Graphical User Interface

The application is built using Java Swing.

The interface contains six main tabs:

1. Catalog
2. Members
3. Issue / Return
4. Waiting List
5. Leaderboard
6. History

The GUI uses components such as:

- `JFrame`
- `JTabbedPane`
- `JTable`
- `DefaultTableModel`
- `JTextField`
- `JTextArea`
- `JComboBox`
- `JButton`

`SwingUtilities.invokeLater()` is used to create the GUI on Java's Event Dispatch Thread.

## Project Structure

    LibraryManagementSystem/
    |
    +-- DataStructures.java
    |   +-- MyQueue
    |   +-- MyStack
    |   +-- MyPriorityQueue
    |
    +-- Models.java
    |   +-- Book
    |   +-- Member
    |
    +-- LibrarySystem.java
    |   +-- Core library operations
    |
    +-- LibraryApp.java
    |   +-- Java Swing GUI
    |
    +-- LibraryDemo.java
    |   +-- Demo data initialization
    |
    +-- Reports.java
    |   +-- Leaderboard
    |   +-- History
    |   +-- Waiting-list formatting
    |
    +-- README.md

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming
- Data Structures and Algorithms
- HashMap
- ArrayList
- Custom Queue
- Custom Stack
- Custom Priority Queue
- Java Comparator and Sorting
- Java Date and Time API
- Git and GitHub

## Team

This project was developed as a team of five at Lovely Professional University.

Team members:

- B Visva Dheeran — DataStructures.java / LibraryDemo.java
- S Harsavarthan — Models.java
- R Hrishikesh — LibrarySystem.java
- N Santosh — LibraryApp.java
- V Prethiyangaran — Reports.java

Mentor: Mr. Ravi Kant Sahu

## My Contribution

My primary contributions to the project were:

- Implemented the custom `MyQueue`
- Implemented the custom `MyStack`
- Implemented the custom `MyPriorityQueue`
- Developed the `DataStructuresTest` class
- Developed `LibraryDemo.java` for initializing demonstration data
- Worked with the team using GitHub for collaborative development

## Complexity Summary

- Book lookup using HashMap — O(1) average
- Member lookup using HashMap — O(1) average
- MyQueue enqueue — O(1)
- MyQueue dequeue — O(n)
- MyStack push — O(1)
- MyStack pop — O(1)
- MyPriorityQueue enqueue — O(n)
- MyPriorityQueue dequeue — O(1)
- Leaderboard sorting — O(n log n)

## Running the Data Structure Tests

Compile the data structure test:

    javac DataStructures.java

Run the test:

    java DataStructuresTest

## Project Highlights

The project demonstrates practical application of:

- Data Structures
- Object-Oriented Programming
- Java GUI development
- Algorithmic thinking
- Time complexity analysis
- Modular software design
- Git-based team collaboration
- Real-world problem modelling

The main focus of the project is not simply managing library records, but demonstrating how different data structures can be selected and implemented according to the requirements of real-world operations.

## Future Improvements

Potential future improvements include:

- Persistent database storage
- User authentication
- Enhanced reporting and analytics
- Online access through a web-based interface
- Email or notification support for reservations and overdue books
- More advanced search and recommendation functionality
