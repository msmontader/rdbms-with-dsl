package db;

import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by muntadherinaya on 2/25/17.
 * This file takes care of the load and store command.
 * In this class, all of the file handling business is being done here.
 * HORA !
 */
public class FilesHandler {
    /* Method that load a tableName file from the current directory.
        @param tableName is a String that represents the tableName name.
        This method return a new tableName if the file already exists.
        Otherwise it returns a null.
        <<--- Make sure to take care of the null when you have no tableName file. -->>
     */
    public static Table load(String tableName) {
        String line;
        ArrayList<String> columns;
        ArrayList<Object> row;
        Table newTable = new Table(tableName);
        String path = "./db/"+tableName + ".tbl";
        File file = new File(path);
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                line = reader.readLine();
                columns = convertStringToArray(line);
                newTable.insertColumns(columns);
                while ((line = reader.readLine()) != null) {
                    row = convertLineToRow(line, newTable);
                    newTable.insertRow(row);
                }
                reader.close();
            } catch (IOException e) {
                System.err.println(e);
            }
            return newTable;
        }
        return null;
    }
    protected static boolean isNoValue(String value){
        if (value.equals("NOVALUE")){
            return true;
        }
        return false;
    }
    /*
        Method that convert a string line to a row array.
        This method also checks for each type of the string and converts it to
        the appropriate type.
        @param line is a string line from a table file.
        @param table is a Table instance
        This method returns an Arraylist of Objects that contains a row.
     */
    protected static ArrayList<Object> convertLineToRow(String line, Table table) {
        ArrayList<Object> row = new ArrayList<>();
        String copyLine = new String(line);
        String[] parts = copyLine.split(",");
        Object type;
        for (int i = 0; i < table.columnsInOrder.size(); i++) {
            if (isNoValue(parts[i])) {
                row.add(parts[i]);
            } else {
                type = table.getColumnType(i);
                if (type instanceof Integer) {
                    int x = Integer.parseInt(parts[i]);
                    row.add(x);
                } else if (type instanceof Float) {
                    float x = Float.parseFloat(parts[i]);
                    x = (float) (Math.floor(x * 1000) / 1000);
                    row.add(x);
                } else {
                    row.add(parts[i]);
                }
            }
        }
        return row;
    }

    /*
     Method that converts a string line to an Array List.
     @param line is a string line from a table file.
     returns a String Array List that contains the columns.
     This method is used as a helper method for the load function.
     */
    private static ArrayList<String> convertStringToArray(String line) {
        ArrayList<String> stringArray = new ArrayList<>();
        String[] parts;
        String copyLine = new String(line);
        parts = copyLine.split(",");
        for (String str : parts) {
            stringArray.add(str);
        }
        return stringArray;
    }

    /* Method that store a Table instance in a file.
        @param table is a Table instance that represents the table.
        This method Does not return anything. Will raise an error if no
        such table exists.
        <<--- Make sure to take care of the null when you have no table file. -->>
     */
    protected static void store(Table table) {
        try {
            File file = new File(table.tableName + ".tbl");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write((columnToString(table.columnsInOrder)));
            writer.newLine();
            for (int i = 0; i < table.rowsHolder.size(); i++) {
                Row temp = table.getRow(i);
                String row = rowToString(temp.row);
                writer.write(row);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("ERROR: No such table: " + table.tableName);
        }
    }

    /*
        Method that takes in an arrayList (single row) and convert it to a string.
        this method returns a single string line that will be used for storing.
        @param arr is an array list of strings that represents a single row.
        <<--- Helper method for the store function. --->>
     */
    private static String rowToString(ArrayList<Object> row) {
        ArrayList<Object> copyRow = Table.arrayCopy(row);
        String str = "";
        for (Object obj : row) {
            str = str + obj.toString() + ",";
        }
        str = str.substring(0, str.length()-1);
        return str;
    }

    /*
        Method that takes in an arrayList (columns) and convert it to a string.
        this method returns a single string line that will be used for storing.
        @param arr is an array list of strings that represents columns.
        <<--- Helper method for the store function. --->>
     */
    private static String columnToString(ArrayList<String> arr) {
        ArrayList<Object> copyArr = Table.arrayCopy(arr);
        String str = "";
        for (Object obj : copyArr) {
            str = str + obj.toString() + ",";
        }
        str = str.substring(0, str.length()-1);
        return str;
    }

}
