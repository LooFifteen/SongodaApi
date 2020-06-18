package sll.coding.songodaapi;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Resource rs = Resource.fromSlug("admin-gui-premium");
        System.out.println(rs.getVersions().get(0).getChangelog(ChangelogType.HTML));
    }

}
