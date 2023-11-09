import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TableModel extends AbstractTableModel {
    private JTable table1;
    private JScrollPane scrollPane;
    private ArrayList<Book> collection;
    private Library library;
    private String[] columnNames = {"Barcode", "Title", "Author", "Genre"};

    public TableModel(ArrayList<Book> collection, Library library){
        this.collection = collection;
        this.library = library;
    }

    @Override
    public int getRowCount() {
        return collection.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Book book = collection.get(row);
        switch(column) {
            case 0:
                return book.getbarCode();
            case 1:
                return book.getTitle();
            case 2:
                return book.getAuthor();
            case 3:
                return book.getGenre();
            default:
                return null;

        }
    }


}
