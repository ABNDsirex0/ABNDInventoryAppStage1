package cloud.krzysztofkin.inventoryapp1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cloud.krzysztofkin.inventoryapp1.data.BookContract.BookEntry;
import cloud.krzysztofkin.inventoryapp1.data.BookDbHelper;

public class MainActivity extends AppCompatActivity {
    BookDbHelper mDbHelper;
    TextView tableTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new BookDbHelper(this);
        tableTextView = findViewById(R.id.table_text_view);
        showTable();
    }

    /**
     * Read table from database and show it on TextView
     */
    void showTable() {
        String tableText = readTable();
        tableTextView.setText(tableText);
    }

    /**
     * Run on click button "read from database"
     * show data in Log and update TextView
     *
     * @param view current view
     */
    public void readFromDatabaseClick(View view) {
        String tableText = readTable();
        Log.v("MainActivity", "Book table :\n" + tableText);
        showTable();
    }

    /**
     * read all table from database
     *
     * @return formated table
     */
    private String readTable() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String tableHeader = BookEntry._ID + " - " +
                BookEntry.COLUMN_BOOK_NAME + " - " +
                BookEntry.COLUMN_BOOK_PRICE + " - " +
                BookEntry.COLUMN_BOOK_QUANTITY + " - " +
                BookEntry.COLUMN_BOOK_SUPPLIER + " - " +
                BookEntry.COLUMN_BOOK_PHONE + "\n";

        String[] projection = {BookEntry._ID,
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_SUPPLIER,
                BookEntry.COLUMN_BOOK_PHONE};
        Cursor cursor = db.query(BookEntry.TABLE_NAME, projection, null, null, null, null, null);
        String tableBody = "";
        try {
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER);
            int phoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PHONE);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplier = cursor.getString(supplierColumnIndex);
                String currentPhone = cursor.getString(phoneColumnIndex);

                //due to just a few operations I give up using stringbuilder
                tableBody = tableBody + "\n" +
                        currentId + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplier + " - " +
                        currentPhone;
            }

        } finally {
            //always close cursor
            cursor.close();
        }
        return (tableHeader + tableBody);
    }

    /**
     * Add book to the database and show result in log
     *
     * @param name     Name of book
     * @param price    Price of book
     * @param quantity Quantity
     * @param supplier Supplier name
     * @param phone    Supplier phone
     */
    private void addBook(String name, int price, int quantity, String supplier, String phone) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME, name);
        values.put(BookEntry.COLUMN_BOOK_PRICE, price);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER, supplier);
        values.put(BookEntry.COLUMN_BOOK_PHONE, phone);
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Log.e("Main.Activity", "Error with saving Book");
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Log.v("MainActivity", "Book saved with row id: " + newRowId);
        }
    }

    /**
     * On clic add sample data button.
     * Add sample data and update TextView.
     *
     * @param view
     */
    public void addSampleDataClick(View view) {
        addBook("Sample Book", 100, 10, "No name", "+1000000001");
        showTable();
    }
}
