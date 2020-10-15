package main.java;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BookShop {
    private static Connection conn = null;

    public static void readBooks() throws SQLException {

        Statement stmt = conn.createStatement();
        stmt.execute("SELECT * FROM book");

        ResultSet rs = stmt.getResultSet();

        while (rs.next()){
            System.out.print(rs.getInt("ISBN"));
            System.out.print(" ");
            System.out.print(rs.getString("Title"));
            System.out.print(" ");
            System.out.print(rs.getDouble("Price"));
            System.out.print(" ");
            System.out.print(rs.getInt("Pages"));
            System.out.print(" ");
            System.out.print(rs.getString("Category"));
            System.out.print(" ");
            System.out.print(rs.getInt("Year"));
            System.out.print(" ");
            System.out.println(rs.getInt("Copies"));
        }

        rs.close();
        stmt.close();
    }

    public static void insertBook() throws SQLException {
        Book book = new Book();
        Scanner sc1 = new Scanner(System.in);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        int i = 1;
        System.out.println("ISBN: ");
        ps.setInt(i++,Integer.parseInt(sc1.nextLine()));
        System.out.println("Title book: ");
        ps.setString(i++,sc1.nextLine());
        System.out.println("Price: ");
        ps.setDouble(i++,Double.parseDouble(sc1.nextLine()));
        System.out.println("Pages: ");
        ps.setInt(i++,Integer.parseInt(sc1.nextLine()));
        System.out.println("Category: ");
        ps.setString(i++,sc1.nextLine());
        System.out.println("Year: ");
        ps.setInt(i++,Integer.parseInt(sc1.nextLine()));
        System.out.println("Copies: ");
        ps.setInt(i++,Integer.parseInt(sc1.nextLine()));
        System.out.println("Publisher: ");
        ps.setString(i++,sc1.nextLine());
        ps.execute();

    }

    public static void deleteBook() throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps = conn.prepareStatement("DELETE FROM book WHERE ISBN = ? ");
        System.out.println("ISBN: ");
        ps.setInt(1,Integer.parseInt(sc.nextLine()));

        ps.execute();
    }

    public static void updateQuantity() throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps = conn.prepareStatement("UPDATE book SET copies = copies + ? WHERE ISBN = ? ");
        System.out.println("ISBN: ");
        ps.setInt(2,Integer.parseInt(sc.nextLine()));
        System.out.println("Quantity: ");
        ps.setInt(1,Integer.parseInt(sc.nextLine()));

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

        rs.close();
        stmt.close();

        System.out.println("Premi un pulsante per continuare");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
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
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps = conn.prepareStatement("DELETE FROM author WHERE  (?, ?, ?)");
        int i = 1;
        System.out.println("Name: ");
        ps.setString(i++,sc.nextLine());
        System.out.println("Surname: ");
        ps.setString(i++,sc.nextLine());
        System.out.println("CF: ");
        ps.setString(i++,sc.nextLine());

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
        System.out.println("publisherName: ");
        Scanner sc0 = new Scanner(System.in);
        PreparedStatement ps0 = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0; ");
        int i = 1;
        ps0.execute();
        String name = sc0.nextLine();
        PreparedStatement ps1 = conn.prepareStatement("DELETE FROM Book WHERE publisherName = ? ");
        ps1.setString(i,name);
        ps1.execute();

        PreparedStatement ps = conn.prepareStatement("DELETE FROM Publisher WHERE name = ? ");
        ps.setString(i,name);
        ps.execute();
    }




    public static void main(String args[]) throws SQLException {

        Boolean quit = true;
        String connStr = "jdbc:mysql://localhost:3306/BookShop?user=root&password=studenti";
        while (quit) {

            System.out.println(
                    " \n •1 Insert a new book\n" +
                    " •2 Remove a book\n" +
                    " •3 Update the quantity of a book\n" +
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
