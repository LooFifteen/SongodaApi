package sll.coding.songodaapi;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        SearchResults<Team> teams = Team.fromName("songoda", 1);
        Team team = teams.getResults().get(0);
        System.out.println(team.getName());
    }

}
