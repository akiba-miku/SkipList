package SkipList;

import java.util.Random;
import java.util.Stack;

/**
 * Design a SkipList without using any built-in libraries.
 * SkipList class implements the skip list data structure.
 * A skip list is a probabilistic data structure that allows for fast search, insertion, and deletion operations, similar to a balanced tree.
 * Time complexity:
 * Insert(add) : O(logn) avg, O(n) worst case
 * Search(search): O(logn) avg, O(n) worst case
 * Delete(erase): O(logn) avg, O(n) worst case
 * Space complexity:
 * O(n) avg, O(nlogn) worst case
 * principle:
 * SkipList has multiple levels, each level is a sorted linked list with jump links from current level to the next level of elements with the same value
 * The Last level has every element.
 */
class SkipList {
    /**
     * Node class represents a node in the skip list.
     * Each node contains a value, a pointer to the next node in the same level (next), and a pointer to the node in the next level (down).
     */
    static class Node {
        int val;    // The value of the node
        Node next;  // Pointer to the next node in the same level
        Node down;  // Pointer to the node in the next level

        /**
         * Constructor to initialize the node with a value, next pointer, and down pointer.
         *
         * @param val The value of the node
         * @param next The pointer to the next node in the same level
         * @param down The pointer to the next level node
         */
        public Node(int val, Node next, Node down) {
            this.val = val;
            this.next = next;
            this.down = down;
        }
    }

    Node head = new Node(-1, null, null);  // The head node of the skip list, initialized with value -1 and no next or down
    Random rand = new Random();  // Random object for generating random numbers

    /**
     * Constructor to initialize the skip list.
     */
    public SkipList() {}

    /**
     * Search for a target value in the skip list.
     *
     * @param target The value to search for
     * @return {@code true} if the target value exists in the skip list, otherwise {@code false}
     */
    public boolean search(int target) {
        Node cur = head;  // Start from the top-left node(head)
        //Repeat step 1-3 until current nodes becomes null
        while (cur != null) {
            // Step1: Traverse the current level to the right until finding a value greater than or equal to the target
            while (cur.next != null && cur.next.val < target) {
                cur = cur.next;
            }
            // Step2: If the next node's value matches the target, return true
            if (cur.next != null && cur.next.val == target) {
                return true;
            }
            // Step3: Move down to the next level
            cur = cur.down;
        }
        return false;  // If the target is not found, return false
    }

    /**
     * Add a number to the skip list.
     *
     * @param num The number to add
     */
    public void add(int num) {
        Stack<Node> stack = new Stack<>();  // Stack to record the nodes at each level
        Node cur = head;

        // Traverse the skip list from top to bottom, searching for the correct position to insert
        while (cur != null) {
            // Traverse the current level to the right until finding a value greater than or equal to the target
            while (cur.next != null && cur.next.val < num) {
                cur = cur.next;
            }
            stack.push(cur);  // Record the current position
            cur = cur.down;   // Move down to the next level
        }

        boolean insert = true;  // Flag to determine if we need to continue inserting nodes
        Node down = null;  // Pointer to the down node in the next level
        // Insert the new node at each level
        while (insert && !stack.empty()) {
            cur = stack.pop();  // Pop the top element from the stack
            cur.next = new Node(num, cur.next, down);  // Insert a new node in the current level
            down = cur.next;  // Update the down node pointer for the next level
            insert = rand.nextDouble() < 0.5;  // With a 50% probability, decide if we need to create a higher level node
        }

        // If a new node was inserted at the top level, create a new head node
        if (insert) {
            head = new Node(-1, null, head);
        }
    }

    /**
     * Erase a number from the skip list.
     *
     * @param num The number to erase
     * @return true if the number was successfully erased, otherwise false
     */
    public boolean erase(int num) {
        Node cur = head;
        boolean found = false;  // Flag to indicate if the target number was found
        while (cur != null) {
            // Traverse the current level to the right until finding a value greater than or equal to the target
            while (cur.next != null && cur.next.val < num) {
                cur = cur.next;
            }
            // If the next node's value matches the target, remove it
            if (cur.next != null && cur.next.val == num) {
                cur.next = cur.next.next;  // Skip the node that needs to be removed
                found = true;  // Mark that the value was found and deleted
            }
            // Move down to the next level
            cur = cur.down;
        }
        return found;  // Return true if the number was found and deleted, otherwise false
    }
}

/*
 * Usage example of SkipList:
 * SkipList obj = new SkipList();
 * boolean param_1 = obj.search(target);  // Search for the target value
 * obj.add(num);  // Add a value to the skip list
 * boolean param_3 = obj.erase(num);  // Erase a value from the skip list
 */
