package db;

import java.io.FileReader;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

/**
 * Created by muntadherinaya on 2/20/17.
 */

public class TableLauncher {
    public static void main(String[] args){
        String text = "  'hello  world',    9534.]853  ,  'hello workld'";
        String[] exprParts = text.split(" (?=(([^'\"]*['\"]){2})*[^'\"]*$)");

    }
}
