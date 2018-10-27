package db;
import java.util.HashMap;

public class Database {
    protected static HashMap<String, Table> tables;
    private Parse input;

    public Database() {
        tables = new HashMap<>();
        input = new Parse();
    }

    public String transact(String query) {
        String results = input.eval(query, this);
        return results;
    }
}