package Project4;

/**
 * Name: Daniel Le
 * Section: CS143
 * <p>
 * This class manages a tag ring game by creating and manipulating a linked list
 * of TagNode objects. The constructor takes a list of player names and creates
 * TagNode objects, linking them together, where each node represents a player and
 * contains the player's name. The printTagRing method prints out the players
 * in the ring and who they are trying to tag. The printOutList method prints
 * out the players who have been tagged and by whom. The TagRingContains and
 * outListContains methods check if a player is in the tag ring or the out list,
 * respectively. The isGameOver method checks if the game is over, which happens
 * when there is only one player left in the tag ring. The winner method returns
 * the name of the winner if the game is over, otherwise it returns null. The tag
 * method tags a player by moving them from the tag ring to the out list and
 * recording the name of the player who tagged them. The method also updates the
 * links between the nodes in the tag ring and the out list.
 */

import java.util.List;

public class TagManager {
    private TagNode frontIn;
    private TagNode frontOut;

    public TagManager(List<String> names) {
        if (names.isEmpty()) {
            throw new IllegalArgumentException();
        }
        TagNode last = null;
        for (int i = 0; i < names.size(); i++) {
            TagNode tagger = new TagNode(names.get(i));
            if (frontIn == null) {
                frontIn = tagger;
                last = frontIn;
            } else {
                TagNode curr = last;
                curr.next = tagger;
                last = curr.next;
            }
        }
    }

    /**
     * Prints out a list of tags from the current frontIn node
     * to the last node in the list.
     * <p>
     * Preconditions:
     * frontIn must not be null.
     */
    public void printTagRing() {
        TagNode curr = frontIn;
        while (curr != null) {
            if (curr.next == null) {
                System.out.println("  " + curr.name + " " +
                        "is trying to tag " + frontIn.name);
            } else {
                System.out.println("  " + curr.name + " " +
                        "is trying to tag " + curr.next.name);
            }
            curr = curr.next;
        }
    }

    /**
     * Prints out a list of tags from the current frontOut node
     * to the last node in the list.
     * <p>
     * Preconditions:
     * frontOut must not be null.
     */
    public void printOutList() {
        TagNode curr = frontOut;
        while (curr != null) {
            if (curr.next != null) {
                System.out.println("  " + curr.name + " " +
                        "was tagged by " + curr.tagger);
            } else {
                System.out.println("  " + curr.name + " " +
                        "was tagged by " + curr.tagger);
            }
            curr = curr.next;
        }

    }

    /**
     * Checks if the TagRing contains a TagNode with the specified name.
     *
     * @param name the name of the TagNode to search for
     * @return true if the TagRing contains a TagNode with the
     * specified name, false otherwise
     * @throws NullPointerException if the name is null
     */
    public boolean tagRingContains(String name) {
        TagNode curr = frontIn;
        while (curr != null) {
            if (curr.name.equalsIgnoreCase(name)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Determines if a given name is present in the outList.
     *
     * @param name a String representing the name to search for
     * @return true if the name is present in the outList, false otherwise
     * <p>
     * Precondition: The outList is not null.
     */
    public boolean outListContains(String name) {
        TagNode curr = frontOut;
        while (curr != null) {
            if (curr.name.equalsIgnoreCase(name)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Checks if the game is over, i.e., if there is only one
     * TagNode left in the frontIn list.
     *
     * @return true if the game is over, false otherwise.
     * @throws NullPointerException if frontIn is null.
     */
    public boolean isGameOver() {
        if (frontIn.next == null) {
            return true;
        }
        return false;
    }

    /**
     * Returns the name of the winner of the game.
     * Preconditions:
     * The game must be over (i.e., the linked list has only one node)
     * The frontIn node must not be null.
     *
     * @return The name of the winner or null if the game is
     * not over or the frontIn node is null.
     */
    public String winner() {
        if (isGameOver() && frontIn != null) {
            return frontIn.name;
        }
        return null;
    }

    /**
     * Preconditions:
     * The game must not be over (i.e., there must be at least two players in the game).
     * The given name must belong to a player in the game.
     * <p>
     * This method tags the player with the given name and updates the game state accordingly.
     *
     * @param name the name of the player to tag
     * @throws IllegalArgumentException if the given name is not a player in the game
     * @throws IllegalStateException    if the game is already over
     */
    public void tag(String name) {
        if (isGameOver()) {
            throw new IllegalStateException();
        }
        if (!tagRingContains(name)) {
            throw new IllegalArgumentException();
        }

        TagNode tagged = null;
        if (frontIn.name.equalsIgnoreCase(name)) {
            TagNode curr = frontIn;
            while (curr.next != null) {
                curr = curr.next;
            }
            tagged = frontIn;
            tagged.tagger = curr.name;
            frontIn = frontIn.next;
        } else {
            TagNode curr = frontIn;
            while (!curr.next.name.equalsIgnoreCase(name)) {
                curr = curr.next;
            }
            tagged = curr.next;
            if (curr.next.next != null) {
                curr.next = curr.next.next;
            } else {
                curr.next = null;
            }
            tagged.tagger = curr.name;
        }
        if (frontOut != null) {
            tagged.next = frontOut;
        } else {
            tagged.next = null;
        }
        frontOut = tagged;
    }
}
