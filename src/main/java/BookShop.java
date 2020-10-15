package main.java;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class BookShop {
    private static Connection conn = null;

    public static void readBooks() throws SQLException {

        Statement stmt = conn.createStatement();
        stmt.execute("SELECT * FROM book");

        ResultSet rs = stmt.getResultSet();
        int i=1;
        while (rs.next()){
            System.out.println(i++ + ") ");
            System.out.print("ISBN: ");
            System.out.print(rs.getInt("ISBN"));
            System.out.print("\t\tTitle: ");
            System.out.print(rs.getString("Title"));
            System.out.print("\t\tPrice: ");
            System.out.print(rs.getDouble("Price"));
            System.out.print("\t\tPages: ");
            System.out.print(rs.getInt("Pages"));
            System.out.print("\t\tPages: ");
            System.out.print(rs.getString("Category"));
            System.out.print("\t\tYear: ");
            System.out.print(rs.getInt("Year"));
            System.out.print("\t\tCopies: ");
            System.out.println(rs.getInt("Copies"));
        }

        rs.close();
        stmt.close();
        waitResult();
    }

    private static boolean checkPresent(String a, String table, String column) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("SELECT * FROM "+ table +" where "+ column +" = " + "'" +a+"'");
        ResultSet rs = stmt.getResultSet();

        while (rs.next()) {
            rs.close();
            stmt.close();
            return true;
        }
        rs.close();
        stmt.close();
        return false;
    }

    private static void waitResult(){
        System.out.println("Press a button to continue");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public static void insertBook() throws SQLException {
        Book book = new Book();
        Scanner sc1 = new Scanner(System.in);
        System.out.println("How many authors have written the book: ");
        int nAuthors = Integer.parseInt(sc1.nextLine());
        ArrayList<Author> authors = new ArrayList<Author>();
        for(int i=0; i<nAuthors; i++){
            Author a= new Author();
            System.out.println("Insert CF of the author");
            a.setCF(sc1.nextLine());
            while(!checkPresent(a.getCF(), "Author", "CF")){
                System.out.println("The author: " + a.getCF() + " is not present. Please insert the author first");
                insertAuthor();
            }
            authors.add(i,a);
        }

        PreparedStatement ps = conn.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        int i = 1;
        System.out.println("ISBN: ");
        Integer isbn =Integer.parseInt(sc1.nextLine());
        ps.setInt(i++,isbn);
        System.out.println("Title book: ");
        ps.setString(i++,sc1.nextLine());
        System.out.println("Price: ");
        ps.setDouble(i++,Math.abs(Double.parseDouble(sc1.nextLine())));
        System.out.println("Pages: ");
        ps.setInt(i++,Math.abs(Integer.parseInt(sc1.nextLine())));
        System.out.println("Category: ");
        ps.setString(i++,sc1.nextLine());

        System.out.println("Year: ");
        int year;
        int Currentyear = Calendar.getInstance().get(Calendar.YEAR);
        do{
            System.out.println("The current year is the max value possible");
            year=Integer.parseInt(sc1.nextLine());
        } while(year > Currentyear);
        ps.setInt(i++,year);

        System.out.println("Copies: ");
        ps.setInt(i++,Math.abs(Integer.parseInt(sc1.nextLine())));
        System.out.println("Publisher: ");
        String publisher = sc1.nextLine();
        ps.setString(i++,publisher);
        while(!checkPresent(publisher, "Publisher", "name")){
            System.out.println("The publisher: " + publisher + " is not present. Please insert the publisher first");
            insertPublisher();
        }

        ps.execute();

        PreparedStatement ps1= conn.prepareStatement("INSERT INTO bookhasauthor VALUES (?, ?)");
        for(int j=0; j<nAuthors; j++) {
            ps1.setString(1, authors.get(j).getCF());
            ps1.setInt(2, isbn);
            ps1.execute();
        }
        readBooks();
    }

    public static void deleteBook() throws SQLException {
        readBooks();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps = conn.prepareStatement("DELETE FROM book WHERE ISBN = ? ");
        int isbn;
        do {
            System.out.println("Insert a valid ISBN");
            isbn = Integer.parseInt(sc.nextLine());
        }while(!checkPresent(String.valueOf(isbn), "Book", "isbn"));
        ps.setInt(1,isbn);
        ps.execute();
    }

    public static void updateQuantity() throws SQLException {
        readBooks();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps = conn.prepareStatement("UPDATE book SET copies = copies + ? WHERE ISBN = ? ");
        int isbn;
        do {
            System.out.println("Insert a valid ISBN");
            isbn = Integer.parseInt(sc.nextLine());
        }while(!checkPresent(String.valueOf(isbn), "Book", "isbn"));
        ps.setInt(2,isbn);
        System.out.println("Quantity: ");

        Statement stmt = conn.createStatement();
        stmt.execute("SELECT copies FROM book where isbn = "+ isbn);

        ResultSet rs = stmt.getResultSet();
        int actualCopies = 0;
        while (rs.next()){
            actualCopies = rs.getInt("copies");
        }
        int copies = Integer.parseInt(sc.nextLine());
        if((-actualCopies) > copies)
                copies = -actualCopies;
        ps.setInt(1,copies);

        ps.execute();
    }

    public static void readAuthors() throws SQLException {

        Statement stmt = conn.createStatement();

        stmt.execute("SELECT * FROM author");

        ResultSet rs = stmt.getResultSet();

        while (rs.next()){
            System.out.print(rs.getString("CF"));
            System.out.print(" ");
            System.out.print(rs.getString("name"));
            System.out.print(" ");
            System.out.println(rs.getString("surname"));
        }
        waitResult();
        rs.close();
        stmt.close();
    }

    public static void insertAuthor() throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO author VALUES (?, ?, ?)");
        int i = 1;
        System.out.println("Name: ");
        ps.setString(2,sc.nextLine());
        System.out.println("Surname: ");
        ps.setString(3,sc.nextLine());
        System.out.println("CF: ");
        ps.setString(1,sc.nextLine());

        ps.execute();

    }

    public static void deleteAuthor() throws SQLException {
        readAuthors();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps = conn.prepareStatement("DELETE FROM author WHERE  cf = ?");

        String cf;
        do {
            System.out.println("Insert a valid CF: ");
            cf = sc.nextLine();
        }while(!checkPresent(cf, "Author", "CF"));

        ps.setString(1,cf);
        ps.execute();
    }

    public static void readPublishers() throws SQLException {

        Statement stmt = conn.createStatement();
        stmt.execute("SELECT * FROM publisher");

        ResultSet rs = stmt.getResultSet();

        while (rs.next()){
            System.out.print(rs.getString("Name"));
            System.out.print(" ");
            System.out.println(rs.getString("Location"));
        }
        waitResult();
        rs.close();
        stmt.close();

    }

    public static void insertPublisher() throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Publisher VALUES (?, ?)");
        int i = 1;
        System.out.println("Name: ");
        ps.setString(i++,sc.nextLine());
        System.out.println("Location: ");
        ps.setString(i++,sc.nextLine());

        ps.execute();

    }

    public static void deletePublisher() throws SQLException {
        readPublishers();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Publisher WHERE Name = ?");
        String name;
        do {
            System.out.println("Insert a valid Name: ");
            name = sc.nextLine();
        }while(!checkPresent(name, "Publisher", "name"));

        ps.setString(1,name);

        ps.execute();
    }




    public static void main(String args[]) throws SQLException {

        Boolean quit = true;
        String connStr = "jdbc:mysql://localhost:3306/BookShop?user=root&password=root";
        while (quit) {

            System.out.println(
                    " \n •1 Insert a new book\n" +
                    " •2 Remove a book\n" +
                    " •3 Update the quantity of a book (- in case you want to remove, + in case you want to add)\n" +
                    " •4 Read the list of the authors\n" +
                    " •5 Insert an author\n" +
                    " •6 Delete an author\n" +
                    " •7 Read the list of the publishers\n" +
                    " •8 Insert a publisher\n" +
                    " •9 Delete a publisher\n" +
                    " •10 View all books\n" +
                    " •0 Quit" );


            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();

           try {
               conn = DriverManager.getConnection("jdbc:mysql://localhost/BookShop?" + "zeroDateTimeBehavior =CONVERT_TO_NULL&serverTimezone=CET", "root", "studenti" );
           } catch (SQLException ex) {
               System.out.println("SQLException: " + ex.getMessage());
               System.out.println("SQLState: " + ex.getSQLState());
               System.out.println("VendorError: " + ex.getErrorCode());
           }

            switch (i) {
                case 1:
                    BookShop.insertBook();
                    break;
                case 2:
                    BookShop.deleteBook();
                    break;
                case 3:
                    BookShop.updateQuantity();
                    break;
                case 4:
                    BookShop.readAuthors();
                    break;
                case 5:
                    BookShop.insertAuthor();
                    break;
                case 6:
                    BookShop.deleteAuthor();
                    break;
                case 7:
                    BookShop.readPublishers();
                    break;
                case 8:
                    BookShop.insertPublisher();
                    break;
                case 9:
                    BookShop.deletePublisher();
                    break;
                case 0:
                    quit=false;
                    break;
                case 10:
                    BookShop.readBooks();
                    break;
                default:
                    break;
            }
            conn.close();

        }

    }

}
