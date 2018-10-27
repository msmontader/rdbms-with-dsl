package db;
//
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the join method in the table class.
 *
 * @author Muntadher Inaya
 */

public class TestJoin {

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestJoin.class);
    }

    @Test
     public void test1(){
        Database data = new Database();
        data.transact("load fans");
        Table fans = data.tables.get("fans");
        data.transact("load monty");
        Table monty = data.tables.get("monty");
        Table join = Table.join(fans, monty, "join");
        System.out.print(join.print());

    }
//    @Test
//    public void test1(){
//        Table T1 = new Table("T1");
//        T1.addColumn("W");
//        T1.addColumn("B");
//        T1.addColumn("Z");
//        Row row1 = new Row();
//        row1.addRowItem(1);
//        row1.addRowItem(7);
//        row1.addRowItem(4);
//        Row row2 = new Row();
//        row2.addRowItem(7);
//        row2.addRowItem(7);
//        row2.addRowItem(3);
//        Row row3 = new Row();
//        row3.addRowItem(1);
//        row3.addRowItem(9);
//        row3.addRowItem(6);
//        Row row4 = new Row();
//        row4.addRowItem(1);
//        row4.addRowItem(11);
//        row4.addRowItem(9);
//        T1.addRow(row1);
//        T1.addRow(row2);
//        T1.addRow(row3);
//        T1.addRow(row4);
//        //////// Second table //////
//        Table T2 = new Table("T2");
//        T2.addColumn("x");
//        T2.addColumn("y");
//        T2.addColumn("Z");
//        T2.addColumn("W");
//        Row row5 = new Row();
//        row5.addRowItem(1);
//        row5.addRowItem(7);
//        row5.addRowItem(2);
//        row5.addRowItem(10);
//        Row row6 = new Row();
//        row6.addRowItem(7);
//        row6.addRowItem(7);
//        row6.addRowItem(4);
//        row6.addRowItem(1);
//        Row row7 = new Row();
//        row7.addRowItem(1);
//        row7.addRowItem(9);
//        row7.addRowItem(9);
//        row7.addRowItem(1);
//        T2.addRow(row5);
//        T2.addRow(row6);
//        T2.addRow(row7);
//        Table T3 = Table.join(T2, T1, null);
//        ArrayList<Object> secondRow = new ArrayList<>();
//        secondRow.add(9);
//        secondRow.add(1);
//        secondRow.add(1);
//        secondRow.add(9);
//        secondRow.add(11);
//        assertEquals(secondRow, T3.getRow(1).row);
//
//    }
//    @Test
//    public void test2(){
//        Table T2 = new Table("T2");
//        T2.addColumn("x");
//        T2.addColumn("z");
//        Row row1 = new Row();
//        row1.addRowItem(2);
//        row1.addRowItem(4);
//        Row row2 = new Row();
//        row2.addRowItem(8);
//        row2.addRowItem(9);
//        Row row3 = new Row();
//        row3.addRowItem(10);
//        row3.addRowItem(1);
//        Row row4 = new Row();
//        row4.addRowItem(11);
//        row4.addRowItem(1);
//        T2.addRow(row1);
//        T2.addRow(row2);
//        T2.addRow(row3);
//        T2.addRow(row4);
//        Table T1 = new Table("T1");
//        T1.addColumn("x");
//        T1.addColumn("y");
//        Row row5 = new Row();
//        row5.addRowItem(2);
//        row5.addRowItem(5);
//        Row row6 = new Row();
//        row6.addRowItem(8);
//        row6.addRowItem(3);
//        Row row7 = new Row();
//        row7.addRowItem(13);
//        row7.addRowItem(7);
//        T1.addRow(row5);
//        T1.addRow(row6);
//        T1.addRow(row7);
//        Table T3 = Table.join(T1, T2, null);
//        ArrayList<Object> secondRow = new ArrayList<>();
//        ArrayList<Object> firstRow = new ArrayList<>();
//        firstRow.add(2);
//        firstRow.add(5);
//        firstRow.add(4);
//        secondRow.add(8);
//        secondRow.add(3);
//        secondRow.add(9);
//        assertEquals(firstRow, T3.getRow(0).row);
//        assertEquals(secondRow, T3.getRow(1).row);

    }