package javaprogramming.demo31librarymanagementsystem;

public class Books {
    private String ISBN;
    private String name;
    private String author;
    private int stock;//库存
    public Books(String ISBN, String name, String author, int stock) {
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
        this.stock = stock;
    }
    public Books() {
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Books{" +
                "ISBN='" + ISBN + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", stock=" + stock +
                '}';
    }
}
