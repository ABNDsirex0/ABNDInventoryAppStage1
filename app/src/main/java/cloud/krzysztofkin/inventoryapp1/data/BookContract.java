package cloud.krzysztofkin.inventoryapp1.data;

import android.provider.BaseColumns;

public final class BookContract {

    /**
     * Utility classes, which are collections of static members, are not meant to be instantiated.
     * private constructor to hide implicit public one
     */
    private BookContract() {
        throw new IllegalStateException("Utility class");
    }

    public static final class BookEntry implements BaseColumns {
        /**
         * Utility classes, which are collections of static members, are not meant to be instantiated.
         * private constructor to hide implicit public one
         */
        private BookEntry() {
            throw new IllegalStateException("Utility class");
        }

        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BOOK_NAME = "name";
        public static final String COLUMN_BOOK_PRICE = "price";
        public static final String COLUMN_BOOK_QUANTITY = "quantity";
        public static final String COLUMN_BOOK_SUPPLIER = "supplier";
        public static final String COLUMN_BOOK_PHONE = "phone";
    }
}
