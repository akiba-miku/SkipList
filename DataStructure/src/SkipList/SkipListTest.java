package SkipList;

public class SkipListTest {

    public static void main(String[] args) {
        SkipList skiplist = new SkipList();

        // Test 1: Add and search
        System.out.println("Test 1: Add and Search");
        skiplist.add(1);
        skiplist.add(3);
        skiplist.add(2);

        // Test search functionality
        System.out.println("Searching for 1: " + skiplist.search(1));  // Expected: true
        System.out.println("Searching for 2: " + skiplist.search(2));  // Expected: true
        System.out.println("Searching for 3: " + skiplist.search(3));  // Expected: true
        System.out.println("Searching for 4: " + skiplist.search(4));  // Expected: false

        // Test 2: Erase functionality
        System.out.println("\nTest 2: Erase");
        System.out.println("Erasing 2: " + skiplist.erase(2));  // Expected: true
        System.out.println("Erasing 2 again: " + skiplist.erase(2));  // Expected: false

        // Test search after erase
        System.out.println("Searching for 2 after erase: " + skiplist.search(2));  // Expected: false
        System.out.println("Searching for 1 after erase: " + skiplist.search(1));  // Expected: true
        System.out.println("Searching for 3 after erase: " + skiplist.search(3));  // Expected: true

        // Test 3: Add more elements
        System.out.println("\nTest 3: Add More Elements");
        skiplist.add(4);
        skiplist.add(5);
        skiplist.add(6);

        // Test search for new elements
        System.out.println("Searching for 4: " + skiplist.search(4));  // Expected: true
        System.out.println("Searching for 5: " + skiplist.search(5));  // Expected: true
        System.out.println("Searching for 6: " + skiplist.search(6));  // Expected: true

        // Test 4: Erase multiple elements
        System.out.println("\nTest 4: Erase Multiple Elements");
        System.out.println("Erasing 3: " + skiplist.erase(3));  // Expected: true
        System.out.println("Erasing 5: " + skiplist.erase(5));  // Expected: true

        // Test search after multiple erasures
        System.out.println("Searching for 3 after erase: " + skiplist.search(3));  // Expected: false
        System.out.println("Searching for 5 after erase: " + skiplist.search(5));  // Expected: false
        System.out.println("Searching for 4 after erasure: " + skiplist.search(4));  // Expected: true
        System.out.println("Searching for 6 after erasure: " + skiplist.search(6));  // Expected: true
    }
}
