package logic;

import sweet.Sweets;
import sweet.Candy;
import sweet.Chocolate;
import sweet.Popsicles;

public class SweetsList {
    private Sweets [] sweetList;

    public SweetsList() {
        sweetList = new Sweets[]{
                new Candy(),
                new Chocolate(),
                new Popsicles()
        };
    }

    public Sweets[] getSweetList() {
        return sweetList;
    }
}
