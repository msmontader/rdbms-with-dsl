package db;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
public class TestInsert {

        public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestJoin.class);
    }

    @Test
    public void test1(){
            Database data = new Database();
//            String result = data.transact("select * from fans, records");
            data.transact("create table monty (%#%#% string   ,  %$%$%$ float)");
            data.transact("insert into monty values 'hello'");
            data.transact("insert into monty values  ' my name is monty ',    9");
            data.transact("insert into monty values  ' my name is monty ',    9");
            data.transact("insert into monty values  ' my name is monty ',    9");
            data.transact("insert into monty values  ' my name is monty ',    9");
            data.transact("store monty");
            data.transact("select * from hello , monty");
            System.out.println(data.transact("select *   from , records"));
    }
}
