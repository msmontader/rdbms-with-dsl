package db;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {
    // Various common constructs, simplifies parsing.
    private static final String REST = "\\s*(.*)\\s*",
            COMMA = "\\s*,\\s*",
            AND = "\\s+and\\s+";

    // Stage 1 syntax, contains the command name.
    private static final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
            LOAD_CMD = Pattern.compile("load " + REST),
            STORE_CMD = Pattern.compile("store " + REST),
            DROP_CMD = Pattern.compile("drop table " + REST),
            INSERT_CMD = Pattern.compile("insert into " + REST),
            PRINT_CMD = Pattern.compile("print " + REST),
            SELECT_CMD = Pattern.compile("select " + REST);

    // Stage 2 syntax, contains the clauses of commands.
    private static final Pattern CREATE_NEW = Pattern.compile("(\\S+)\\s+\\((\\S+\\s+\\S+\\s*" +
            "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)"),
            SELECT_CLS = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                    "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                    "([\\w\\s+\\-*/'<>=!]+?(?:\\s+and\\s+" +
                    "[\\w\\s+\\-*/'<>=!]+?)*))?"),
            CREATE_SEL = Pattern.compile("(\\S+)\\s+as select\\s+" +
                    SELECT_CLS.pattern()),
            INSERT_CLS = Pattern.compile("(\\S+)\\s+values\\s+(.+?" +
                    "\\s*(?:,\\s*.+?\\s*)*)");

    public static void main(String[] args, Database db) {
        if (args.length != 1) {
            System.err.println("Expected a single query argument");
            return;
        }

        eval(args[0], db);
    }

    public static String eval(String query, Database db) {
        Matcher m;
        if ((m = CREATE_CMD.matcher(query)).matches()) {
            return createTable(m.group(1), db);
        } else if ((m = LOAD_CMD.matcher(query)).matches()) {
            return loadTable(m.group(1), db);
        } else if ((m = STORE_CMD.matcher(query)).matches()) {
            return storeTable(m.group(1), db);
        } else if ((m = DROP_CMD.matcher(query)).matches()) {
            return dropTable(m.group(1), db);
        } else if ((m = INSERT_CMD.matcher(query)).matches()) {
            return insertRow(m.group(1), db);
        } else if ((m = PRINT_CMD.matcher(query)).matches()) {
            return printTable(m.group(1), db);
        } else if ((m = SELECT_CMD.matcher(query)).matches()) {
            return select(m.group(1), db);
        } else {
            return "Malformed query: " + query + "\n";
        }
    }

    private static String createTable(String expr, Database db) {
        Matcher m;
        if ((m = CREATE_NEW.matcher(expr)).matches()) {
            createNewTable(m.group(1), m.group(2).split(COMMA), db);
            return "";
        } else if ((m = CREATE_SEL.matcher(expr)).matches()) {
            return createSelectedTable(m.group(1), m.group(2), m.group(3), m.group(4), db);

        } else {
            return "ERROR: Malformed create: " + expr + "\n";
        }

    }

    private static String createNewTable(String name, String[] cols, Database db) {
        StringJoiner joiner = new StringJoiner(", ");
        Table newTable = new Table(name);

        for (int i = 0; i < cols.length - 1; i++) {
            joiner.add(cols[i]);
        }

        ArrayList<String> newArray = new ArrayList<>();
        for (String element : cols) {
            newArray.add(element);
        }

        newTable.insertColumns(newArray);
        db.tables.put(name, newTable);

        return "";
    }

    private static String createSelectedTable(String name, String exprs, String tables, String conds, Database db) {
        Table selected = Table.createTable(name);
        return "";
    }

    protected static String loadTable(String name, Database db) {
        if (FilesHandler.load(name) == null) {
            return "ERROR: No such table: " + name + "\n";
        }
        Table loaded = FilesHandler.load(name);
        db.tables.put(name, loaded);
        return "";
    }

    private static String storeTable(String name, Database db) {
        if (!tableExists(name, db)) {
            return "ERROR: No such table: " + name + "\n";
        } else {
            Table table = db.tables.get(name);
            FilesHandler.store(table);
            return "";
        }
    }

    private static String dropTable(String name, Database db) {
        if (!tableExists(name, db)) {
            return "ERROR: No such table: " + name + "\n";
        } else {
            db.tables.remove(name);
            return "";
        }
    }

    protected static String stringCopy(String str) {
        return new String(str);
    }

    private static String arrayToString(String[] arr) {
        String temp = "";
        for (String str : arr) {
            temp = temp + str;
        }
        return temp;
    }

    private static String getTableNameFromExpr(String expr) {
        String temp = stringCopy(expr);
        String[] exprParts = temp.split("\\s+");
        return exprParts[0];
    }

    private static String getRowStringFromExpr(String expr) {
        String temp = stringCopy(expr);
        String[] exprParts = temp.split(" (?=(([^'\"]*['\"]){2})*[^'\"]*$)");
        String[] rowContent = new String[exprParts.length - 2];
        System.arraycopy(exprParts, 2, rowContent, 0, rowContent.length);
        return arrayToString(rowContent);
    }

    private static int getColumnsLength(Table table) {
        return table.columnsInOrder.size();
    }

    private static void insertRowNoValue(String expr, Table table) {
        int tableSize = getColumnsLength(table);
        String[] rowContent = getRowStringFromExpr(expr).split(",");
        String[] newRowContent = new String[tableSize];
        System.arraycopy(rowContent, 0, newRowContent, 0, rowContent.length);
        for (int i = rowContent.length; i < table.columnsInOrder.size(); i++) {
            newRowContent[i] = ",NOVALUE";
        }
        ArrayList<Object> row = FilesHandler.convertLineToRow(arrayToString(newRowContent), table);
        table.insertRow(row);
    }

    private static String insertRow(String expr, Database db) {
        Matcher m = INSERT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed insert: ", expr + "\n");
            return "";
        }
        Table table = getTable(getTableNameFromExpr(expr), db);
        if (!(Table.checkRowColumnType(getRowStringFromExpr(expr), table))) {
            return "ERROR: Type values does not match column type \n";
        }
        int tableSize = getColumnsLength(table);
        String[] rowContent = getRowStringFromExpr(expr).split(",");
        if (rowContent.length < tableSize) {
            insertRowNoValue(expr, table);
            return "";
        } else if (rowContent.length > tableSize) {
            return "ERROR: row values are more than the columns number \n";
        } else {
            ArrayList<Object> row = FilesHandler.convertLineToRow(getRowStringFromExpr(expr), table);
            table.insertRow(row);
            return "";
        }

    }

    protected static String printTable(String name, Database db) {
//        System.out.printf("You are trying to print the table named %s\n", name);
        if (!tableExists(name, db)) {
            return "ERROR: No such table: " + name + "\n";
        } else {
            Table table = getTable(name, db);
            String tableOut = table.print();
            return tableOut;
        }
    }

    private static Table getTable(String name, Database db) {
        return db.tables.get(name);
    }

    private static boolean tableExists(String name, Database db) {
        if (db.tables.containsKey(name)) {
            return true;
        }
        return false;
    }

    private static String select(String expr, Database db) {
        Matcher m = SELECT_CLS.matcher(expr);
        if (!m.matches()) {
            return "Malformed select: " + expr + "\n";
        }

        return select(m.group(1), m.group(2), m.group(3), "selectedTable", false, db);
    }

    private static String select(String exprs, String tables, String conds, String selectedTable, boolean saveTable, Database db) {
        String temp = stringCopy(tables);
        String[] tablesArray = temp.replaceAll("\\s+", "").split(",");
        for (String table : tablesArray) {
            if (!tableExists(table, db)) {
                return "ERROR: No such table: " + table + "\n";
            }
        }

        if (exprs.equals("*") && tablesArray.length == 1) {
            Table table1 = getTable(tablesArray[0], db);
            db.tables.put(selectedTable, table1.join(table1, table1, selectedTable));
            Table table = getTable(selectedTable, db);
            return printTable(selectedTable, db);
        } else if (exprs.equals("*") && tablesArray.length > 1) {
            Table table1 = getTable(tablesArray[0], db);
            Table table2 = getTable(tablesArray[1], db);
            db.tables.put(selectedTable, table1.join(table1, table2, selectedTable));
            return printTable(selectedTable, db);
        }
        return "nothing";
    }
}