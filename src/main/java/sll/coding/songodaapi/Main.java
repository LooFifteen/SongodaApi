package sll.coding.songodaapi;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Resources rs = Resource.fromName("ultimate", -1);
        List<Resource> resources = rs.getResults();
        for (Resource resource : resources) {
            System.out.println(resource.getName());
        }
        System.out.println("Current page: " + rs.getCurrentPage());
        System.out.println("Last page: " + rs.getLastPage());
    }

}
