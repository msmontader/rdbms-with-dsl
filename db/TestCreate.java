package db;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestCreate {
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestCreate.class);
    }

    @Test
    public void test1(){
        Database data = new Database();
        data.transact("load fans");
        data.transact("load monty");
//        System.out.print(data.transact("create table    monty   (name string  ,     numbers float )   "));
        System.out.print(data.transact("print monty"));
        System.out.print(data.transact("create table t3 as select * from fans, monty"));
        System.out.print(data.transact("select * from fans, monty"));

    }
}
