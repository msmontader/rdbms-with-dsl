package db;

import java.util.ArrayList;

/**
 * Created by muntadherinaya on 2/18/17.
 */

public class Row {

    public ArrayList<Object> row;
    public Row(){
        this.row = new ArrayList<>();
    }

    public Object getRowItem(int index){
        return this.row.get(index);
    }

}
