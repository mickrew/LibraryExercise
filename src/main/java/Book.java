public class Book {
    private String title;
    private Integer ISBN;
    private Double price;
    private Integer pages;
    private String category;
    private Integer year;
    private Integer copies;

    public Book(String title, Integer ISBN, Double price, Integer pages, String category, Integer year, Integer copies){
        this.title=title;
        this.ISBN=ISBN;
        this.price=price;
        this.pages=pages;
        this.category=category;
        this.year=year;
        this.copies=copies;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getCopies() {
        return copies;
    }

    public Integer getISBN() {
        return ISBN;
    }

    public Integer getPages() {
        return pages;
    }

    public Integer getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
