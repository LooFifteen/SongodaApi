package sll.coding.songodaapi;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(Resource.fromId(22).getOwner().getName());
    }

}
