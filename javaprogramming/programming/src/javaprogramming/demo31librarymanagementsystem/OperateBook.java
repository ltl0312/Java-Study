package javaprogramming.demo31librarymanagementsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OperateBook {
    List<Books> books = new ArrayList<>();
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("programming\\src\\javaprogramming\\demo31librarymanagementsystem\\books.txt"));
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                // 添加长度检查以避免数组越界
                if (split.length >= 4) {
                    Books book = new Books(split[0], split[1], split[2], Integer.parseInt(split[3]));
                    books.add(book);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBook(Books book) {
        books.add(book);
    }
    public void showAllBooks() {
        for (Books book : books) {
            System.out.println(book);
        }
    }
    public void borrowBook(String ISBN) {
        for (Books book : books) {
            if (book.getISBN().equals(ISBN)) {
                if (book.getStock() > 0) {
                    book.setStock(book.getStock() - 1);
                    System.out.println("借书成功！");
                } else {
                    System.out.println("库存不足！");
                }
                return;
            }
        }
    }
    public void returnBook(String ISBN) {
        for (Books book : books) {
            if (book.getISBN().equals(ISBN)) {
                book.setStock(book.getStock() + 1);
                System.out.println("还书成功！");
                return;
            }
        }
    }
    public void saveBooks() {
        try {
            FileWriter fw = new FileWriter("programming\\src\\javaprogramming\\demo31librarymanagementsystem\\books.txt");
            for (Books book : books){
                fw.write(book.getISBN() + "," + book.getName() + "," + book.getAuthor() + "," + book.getStock());
                fw.write("\n");
                fw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sortBooks() {
        books.sort(Comparator.comparing(Books::getISBN));
    }
}
