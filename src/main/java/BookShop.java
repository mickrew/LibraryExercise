import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BookShop {
    private ArrayList<Book> Library = new ArrayList<Book>();
    private static Connection conn = null;

    public void readBooks() throws SQLException {​​​​

        Statement stmt = conn.createStatement();
        stmt.execute("SELECT * FROM Book");

        ResultSet rs = stmt.getResultSet();

        while (rs.next()){​​​​
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
        }​​​​

        rs.close();
        stmt.close();
    }​​​​

    public static void insertBook() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String info = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Book VALUES (?, ?, ?, ?, ?, ?, ?)");
        int i = 1;
        System.out.println("Title book: ");
        ps.setString(i++,info);
        System.out.println("ISBN: ");
        ps.setInt(i++,Integer.parseInt(info));
        System.out.println("Price: ");
        ps.setDouble(i++,Double.parseDouble(info));
        System.out.println("Pages: ");
        ps.setInt(i++,Integer.parseInt(info));
        System.out.println("Category: ");
        ps.setString(i++,info);
        System.out.println("Year: ");
        ps.setInt(i++,Integer.parseInt(info));
        System.out.println("Copies: ");
        ps.setInt(i++,Integer.parseInt(info));

        ps.execute();

    }

    public static void deleteBook() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String info = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Book WHERE ISBN = ? ");
        System.out.println("ISBN: ");
        ps.setInt(1,Integer.parseInt(info));

        ps.execute();
    }

    public static void updateQuantity() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String info = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("UPDATE Book SET Quantity = Quantity + ? WHERE ISBN = ? ");
        System.out.println("ISBN: ");
        ps.setInt(2,Integer.parseInt(info));
        System.out.println("Quantity: ");
        ps.setInt(1,Integer.parseInt(info));

        ps.execute();
    }

    public static void readAuthors() throws SQLException {​​​​

        Statement stmt = conn.createStatement();

        stmt.execute("SELECT * FROM Author");

        ResultSet rs = stmt.getResultSet();

        while (rs.next()){​​​​
            System.out.print(rs.getString("CF"));
            System.out.print(" ");
            System.out.print(rs.getString("Last Name"));
            System.out.print(" ");
            System.out.println(rs.getString("First Name"));
        }​​​​

        rs.close();
        stmt.close();
    }​​​​

    public static void insertAuthor() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String info = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Author VALUES (?, ?, ?)");
        int i = 1;
        System.out.println("Name: ");
        ps.setString(i++,info);
        System.out.println("Surname: ");
        ps.setString(i++,info);
        System.out.println("CF: ");
        ps.setString(i++,info);

        ps.execute();

    }

    public static void deleteAuthor() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String info = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Author WHERE  (?, ?, ?)");
        int i = 1;
        System.out.println("Name: ");
        ps.setString(i++,info);
        System.out.println("Surname: ");
        ps.setString(i++,info);
        System.out.println("CF: ");
        ps.setString(i++,info);

        ps.execute();
    }

    public static void readPublishers() throws SQLException {​​​​

        Statement stmt = conn.createStatement();
        stmt.execute("SELECT * FROM Publisher");

        ResultSet rs = stmt.getResultSet();

        while (rs.next()){​​​​
            System.out.print(rs.getString("Name"));
            System.out.print(" ");
            System.out.println(rs.getString("Location"));
        }​​​​

        rs.close();
        stmt.close();

    }​​​​

    public static void insertPublisher() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String info = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Publisher VALUES (?, ?)");
        int i = 1;
        System.out.println("Name: ");
        ps.setString(i++,info);
        System.out.println("Location: ");
        ps.setString(i++,info);

        ps.execute();

    }

    public static void deletePublisher() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String info = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Publisher WHERE Name = ?");
        int i = 1;
        System.out.println("Name: ");
        ps.setString(i++,info);

        ps.execute();
    }




    public static void main(String args[]) throws SQLException {

        Boolean quit = true;
        String connStr = "jdbc:mysql://localhost:3306/BookShop?user=root&password=root";
        while (quit) {

            System.out.println("1 Insert a new book\n" +
                    "\n" +
                    " •2 Remove a book\n" +
                    "\n" +
                    " •3 Update the quantity of a book\n" +
                    "\n" +
                    " •4 Read the list of the authors\n" +
                    "\n" +
                    " •5 Insert an author\n" +
                    "\n" +
                    " •6 Delete an author\n" +
                    "\n" +
                    " •7 Read the list of the publishers\n" +
                    "\n" +
                    " •8 Insert a publisher;\n" +
                    "\n" +
                    " •9 Delete a publisher" +
                    "\n" + " •0 Quit");


            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();

           try {
               conn = DriverManager.getConnection(connStr);
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
                default:
                    break;

            }

            conn.close();

        }

    }

}
