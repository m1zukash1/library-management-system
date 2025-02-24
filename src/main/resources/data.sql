-- =====================================
-- Categories
-- =====================================
INSERT INTO category (category_id, category_name) VALUES (1, 'Fiction');
INSERT INTO category (category_id, category_name) VALUES (2, 'Science Fiction');
INSERT INTO category (category_id, category_name) VALUES (3, 'Non-Fiction');
INSERT INTO category (category_id, category_name) VALUES (4, 'Biography');
INSERT INTO category (category_id, category_name) VALUES (5, 'History');

-- =====================================
-- Publishers
-- =====================================
INSERT INTO publisher (publisher_id, publisher_name) VALUES (1, 'Penguin Random House');
INSERT INTO publisher (publisher_id, publisher_name) VALUES (2, 'HarperCollins');
INSERT INTO publisher (publisher_id, publisher_name) VALUES (3, 'Simon & Schuster');
INSERT INTO publisher (publisher_id, publisher_name) VALUES (4, 'Hachette Book Group');
INSERT INTO publisher (publisher_id, publisher_name) VALUES (5, 'Macmillan Publishers');

-- =====================================
-- Authors
-- =====================================
INSERT INTO author (author_id, first_name, last_name) VALUES (1, 'George', 'Orwell');
INSERT INTO author (author_id, first_name, last_name) VALUES (2, 'J.K.', 'Rowling');
INSERT INTO author (author_id, first_name, last_name) VALUES (3, 'Isaac', 'Asimov');
INSERT INTO author (author_id, first_name, last_name) VALUES (4, 'Stephen', 'King');
INSERT INTO author (author_id, first_name, last_name) VALUES (5, 'Yuval Noah', 'Harari');

-- =====================================
-- Books
-- =====================================
INSERT INTO book (book_id, title, isbn, publication_year, publisher_id, category_id)
VALUES
    (1, '1984', '9780451524935', 1949, 1, 1),
    (2, 'Animal Farm', '9780451526342', 1945, 1, 1),
    (3, 'Harry Potter and the Sorcerer''s Stone', '9780590353427', 1997, 2, 2),
    (4, 'Harry Potter and the Chamber of Secrets', '9780439064873', 1998, 2, 2),
    (5, 'Foundation', '9780553293357', 1951, 3, 3),
    (6, 'It', '9781501142970', 1986, 4, 1),
    (7, 'The Shining', '9780307743657', 1977, 4, 1),
    (8, 'Sapiens: A Brief History of Humankind', '9780062316097', 2011, 5, 5),
    (9, 'Homo Deus: A Brief History of Tomorrow', '9780062464316', 2015, 5, 5),
    (10, 'The Institute', '9781982110566', 2019, 4, 1);

-- =====================================
-- Book Authors (Many-to-Many)
-- =====================================
-- 1984 and Animal Farm by George Orwell
INSERT INTO book_author (book_id, author_id) VALUES (1, 1);
INSERT INTO book_author (book_id, author_id) VALUES (2, 1);
-- Harry Potter books by J.K. Rowling
INSERT INTO book_author (book_id, author_id) VALUES (3, 2);
INSERT INTO book_author (book_id, author_id) VALUES (4, 2);
-- Foundation by Isaac Asimov
INSERT INTO book_author (book_id, author_id) VALUES (5, 3);
-- It, The Shining, and The Institute by Stephen King
INSERT INTO book_author (book_id, author_id) VALUES (6, 4);
INSERT INTO book_author (book_id, author_id) VALUES (7, 4);
INSERT INTO book_author (book_id, author_id) VALUES (10, 4);
-- Sapiens and Homo Deus by Yuval Noah Harari
INSERT INTO book_author (book_id, author_id) VALUES (8, 5);
INSERT INTO book_author (book_id, author_id) VALUES (9, 5);

-- =====================================
-- Members
-- =====================================
INSERT INTO member (member_id, first_name, last_name, email, membership_date)
VALUES
    (1, 'Alice', 'Johnson', 'alice.johnson@example.com', '2024-01-10'),
    (2, 'Bob', 'Smith', 'bob.smith@example.com', '2024-01-12'),
    (3, 'Charlie', 'Brown', 'charlie.brown@example.com', '2024-01-15'),
    (4, 'Diana', 'Prince', 'diana.prince@example.com', '2024-01-20'),
    (5, 'Evan', 'Wright', 'evan.wright@example.com', '2024-01-25'),
    (6, 'Fiona', 'Lee', 'fiona.lee@example.com', '2024-02-01');

-- =====================================
-- Book Copies (Multiple Copies per Book)
-- =====================================
INSERT INTO book_copy (copy_id, book_id, status) VALUES (1, 1, 'Available');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (2, 1, 'Loaned');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (3, 2, 'Available');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (4, 3, 'Available');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (5, 3, 'Available');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (6, 4, 'Loaned');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (7, 5, 'Available');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (8, 6, 'Loaned');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (9, 7, 'Available');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (10, 8, 'Available');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (11, 9, 'Available');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (12, 10, 'Loaned');
INSERT INTO book_copy (copy_id, book_id, status) VALUES (13, 10, 'Available');

-- =====================================
-- Loans
-- =====================================
INSERT INTO loan (loan_id, copy_id, member_id, loan_date, due_date, return_date)
VALUES
    (1, 2, 1, '2024-02-01', '2024-02-15', NULL),
    (2, 6, 2, '2024-02-03', '2024-02-17', NULL),
    (3, 8, 3, '2024-02-05', '2024-02-19', NULL),
    (4, 12, 4, '2024-02-07', '2024-02-21', NULL),
    (5, 13, 5, '2024-02-10', '2024-02-24', NULL);

-- =====================================
-- Reservations
-- =====================================
INSERT INTO reservation (reservation_id, book_id, member_id, reservation_date, status)
VALUES
    (1, 1, 6, '2024-02-10', 'Pending'),
    (2, 3, 4, '2024-02-11', 'Confirmed'),
    (3, 7, 2, '2024-02-12', 'Pending'),
    (4, 8, 1, '2024-02-13', 'Confirmed'),
    (5, 5, 3, '2024-02-14', 'Pending');
