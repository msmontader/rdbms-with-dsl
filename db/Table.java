package db;

import java.util.*;

/**
 * Created by muntadherinaya on 2/18/17.
 */

public class Table {
    protected String tableName;
    protected ArrayList<Object> rowsHolder;
    protected ArrayList<String> columnsInOrder;
    private HashMap<String, Integer> columnNames;
    private int columnCounter;

    // Main Table constructor. Takes a string argument which is the name of the table.
    public Table(String name) {
        this.tableName = name;
        this.columnCounter = 0;
        this.rowsHolder = new ArrayList<>();
        this.columnNames = new HashMap<>();
        this.columnsInOrder = new ArrayList<>();
    }

    protected static Table createTable(String name){
        Table newTable = new Table(name);
        return newTable;
    }
    /*
    Method that Check if two tables has any column matching. Returns a boolean
    @param t1 is the first table (left)
    @param t2 is the second table (right)
     */
    private static boolean isMatch(Table t1, Table t2) {
        for (String col : t1.columnNames.keySet()) {
            if (t2.columnNames.containsKey(col)) {
                return true;
            }
        }
        return false;
    }

    /*
    Method that returns the matching columns names between two tables as an ArrayList.
    @param leftTable is the first table.
    @param rightTable is the second table.
     */
    private static ArrayList<String> matchColumns(Table leftTable, Table rightTable) {
        ArrayList<String> commonCols = new ArrayList<>();
        for (String columnOfleftTable : leftTable.columnsInOrder) {
            for (String columnOfrightTable : rightTable.columnsInOrder) {
                if (columnOfleftTable.equals(columnOfrightTable)) {
                    commonCols.add(columnOfleftTable);
                }
            }
        }
        return commonCols;
    }

    /* Method that Deep copies an array. returns a new copied array
        @param arr is the array the is being copied
     */
    protected static ArrayList<Object> arrayCopy(ArrayList arr) {
        ArrayList<Object> newArray = new ArrayList<>();
        for (Object obj : arr) {
            newArray.add(obj);
        }
        return newArray;
    }

    /*
    Method that merges two columns together and returns a new ArrayList that contains the
    merged columns.
    @param leftTable is the first table that is being merged (left).
    @param rightTable is the second table that is being merged (right).
    <<--- This is a helper method !! --->>
     */
    private static ArrayList<String> mergeCommonColumns
    (Table leftTable, Table rightTable, ArrayList commoncols) {
        ArrayList<String> newColumns = new ArrayList<>();
        for (String str : leftTable.columnsInOrder) {
            if (commoncols.contains(str)) {
                newColumns.add(str);
            }
        }
        for (String str : leftTable.columnsInOrder) {
            if (commoncols.contains(str)) {
            } else {
                newColumns.add(str);
            }
        }
        for (String str : rightTable.columnsInOrder) {
            if (commoncols.contains(str)) {
            } else {
                newColumns.add(str);
            }
        }
        return newColumns;
    }

    /* This method merges two rows of different tables and returns one combined row.
        @param index1 is the number of the row in first table (left table).
        @param index2 is the number of the row in the second table (right table).
        @param commoncols is a set of the common columns between table1 and table 2.
     */
    // <<---- Open for Improvment !! This method is not very pretty. -->>>>
    private static Row mergeCommonRows(Table t1, int index1, Table t2, int index2, ArrayList<String> mergedCols) {
        Row newRow = new Row();
        Object currentRowVAlue;
        for (String str : mergedCols) {
            if (t1.columnsInOrder.contains(str)) {
                currentRowVAlue = t1.getRowValue(index1, str);
            } else {
                currentRowVAlue = t2.getRowValue(index2, str);
            }
            newRow.row.add(currentRowVAlue);

        }
        return newRow;
    }

    /*
    Method that returns a new table if we pass in a name for the table and a column arrayList
    (This method is a helper method is being used for computations purposes only !)
    @param name is the name of the table
    @param columns are the table columns names.
     */

    private static Table makeTable(String name, ArrayList<String> columns) {
        Table newTable = new Table(name);
        for (String str : columns) {
            newTable.addColumn(str);
        }
        return newTable;
    }

    private static ArrayList<String> mergeColumns(Table leftTable, Table rightTable){
        ArrayList<String> newColumns = new ArrayList<>();
        for (String str : leftTable.columnsInOrder) {
                newColumns.add(str);
        }
        for (String str : rightTable.columnsInOrder) {
                newColumns.add(str);
        }
        return newColumns;
    }
    private static Row mergeRows(Table t1,int index1, Table t2, int index2) {
        ArrayList<Object> currentRow = t1.getRow(index1).row;
        ArrayList<Object> newRow = (ArrayList<Object>)currentRow.clone();
        for (Object obj : t2.getRow(index2).row) {
            newRow.add(obj);
        }
            Row mergedRow = new Row();
            mergedRow.row = newRow;
            return mergedRow;
        }
    protected static Table cartesianJoin(Table t1, Table t2, String name){
        Table newTable = new Table(name);
        ArrayList<String> mergeCols = mergeColumns(t1, t2);
        newTable.insertColumns(mergeCols);
        for(int i = 0; i < t1.rowsHolder.size(); i++){
            for(int k = 0; k < t2.rowsHolder.size(); k++){
                Row current = mergeRows(t1,i, t2, k);
                newTable.addRow(current);
            }
        }
        return newTable;
    }
    /*
    The most painful method. Joins two tables together.
    <<<---- Open for better code and more modifications !! ----->>>
    Returns a new joined table with title "joined" (Not sure how are we adding the name of
    the new joined table).
    @param t1 is the first table (left)
    @param t2 is the second table (right)
     */
    protected static Table join(Table t1, Table t2, String newName) {
        //loop through the rowHolder array to check each row
        if (isMatch(t1, t2)) {
        ArrayList<String> commoncols = matchColumns(t1, t2);
        ArrayList<String> mergedColumns = mergeCommonColumns(t1, t2, commoncols);
        Table joinTable = makeTable(newName, mergedColumns);
        Row tempRow;
            for (int i = 0; i < t1.rowsHolder.size(); i++) {
                HashSet<Object> table1 = new HashSet<>();
                for (String key : commoncols) {
                    table1.add((Object) t1.getRowValue(i, key));
                }
                for (int k = 0; k < t2.rowsHolder.size(); k++) {
                    HashSet<Object> table2 = new HashSet<>();
                    for (String key : commoncols) {
                        table2.add((Object) t2.getRowValue(k, key));
                    }
                    if (table1.equals(table2)) {
                        tempRow = mergeCommonRows(t1, i, t2, k, mergedColumns);
                        joinTable.addRow(tempRow);
                    }
                }

            }
            return joinTable;
        }
        else{
            Table cartesianTable = cartesianJoin(t1, t2, newName);
            return cartesianTable;
        }

    }

    /* Method that checks if a row length equals columns number.
       This method is used for inserting rows into a given existing table.
     */
    private static boolean checkRowColumnLength(ArrayList<Object> rowValues, Table table) {
            if (rowValues.size() > table.columnsInOrder.size()) {
            return false;
        }
        return true;
    }

    //Method that checks the column type with an intended row array
    protected static boolean checkRowColumnType(String line, Table table) {
        String copyLine = Parse.stringCopy(line);
        String[] rowValues = copyLine.split(",");
        int iterator;
        boolean match = true;
        if (rowValues.length < table.columnsInOrder.size()){
            iterator = rowValues.length;
        }
        else{
            iterator = table.columnsInOrder.size();
        }
        for (int i = 0; i < iterator; i++){
            Class givenValue = TypeCheck.getValuesType(rowValues[i]).getClass();
            Class columnType = table.getColumnType(i).getClass();
            if (!(givenValue.equals(columnType))){
                match = false;
            }
        }
        return match;
    }

    // Method that get the column type.
    protected Object getColumnType(int columnIndex) {
        String columnName = this.columnsInOrder.get(columnIndex);
        String[] parts = columnName.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
        String type = parts[1];
        if (type.equals("int")) {
            return new Integer(0);
        } else if (type.equals("float")) {
            return new Float(0.0);
        } else {
            return type;
        }

    }

    /*
    Method that adds a column to a specific table.
    @param name is the name of the column being added.
     */
    public void insertColumns(ArrayList<String> columns) {
        for (String str : columns) {
            this.columnNames.put(str, this.columnCounter);
            this.columnsInOrder.add(str);
            this.columnCounter += 1;
        }
    }

    public void addColumn(String name) {
        this.columnNames.put(name, this.columnCounter);
        this.columnsInOrder.add(name);
        this.columnCounter += 1;
    }

    private static String getStringFromArray(ArrayList<Object> arr){
        String line = "";
        for (Object obj : arr){
            line = line + obj.toString() + ",";
        }
        line = line.substring(0, line.length()-1);
        return line;
    }
    /*
    Table output method.
    Not very clean but it works
    <<-- open for improvment -->>
     */
    protected String print() {
        String line = "";
        for (String str : this.columnsInOrder){
            line = line + str + ",";
        }
        line = line.substring(0, line.length()-1);
        line = line +"\n";
        for (int i = 0; i < rowsHolder.size(); i++) {
            Row temp = (Row) rowsHolder.get(i);
            line = line +getStringFromArray(temp.row) + "\n";
        }
        return line;
    }

    /*
 Method that adds a Row object to specific table
 @param r is the Row object that is being passed.
  */
    private void addRow(Row r) {
        // Make sure later to have Row Length is equal to the column index !
        this.rowsHolder.add(r);
    }

    /*
    Method that returns a value (an Object) inside a specific row with a specific row index
    (getting an item with row index and column index)
    @param rowNumber is the number of the row in the table
    @param name is the name of the column (is being used to access the index)
     */
    private Object getRowValue(int rowNumber, String name) {
        int rowIndex = this.columnNames.get(name);
        Row targetRow = (Row) this.getRow(rowNumber);
        return targetRow.getRowItem(rowIndex);
    }

    /* Method that returns a Row object from the rowHolder array given a specific index
        @param rowIndex is the row number associated with specific table.
     */
    public Row getRow(int rowIndex) {
        return (Row) this.rowsHolder.get(rowIndex);
    }

    /* Method that takes an array of objects and stack them into a Row object that will
       be inserted into the table.
       This method does not handle the length match of the values of the row and the columns.
       It also does not check the Type of the values.
     */
    protected void insertRow(ArrayList<Object> arr) {
            Row newRow = new Row();
            for (int i = 0; i < arr.size(); i++) {
                newRow.row.add(arr.get(i));
            }
            this.addRow(newRow);
    }

}
