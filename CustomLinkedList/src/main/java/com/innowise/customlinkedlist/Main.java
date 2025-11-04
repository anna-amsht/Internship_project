package com.innowise.customlinkedlist;


public class Main {
    public static void main(String[] args) {
        Linked<Integer> list = new CustomList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        list.addLast(6);
        list.addLast(7);
        list.addLast(8);
        list.addLast(9);

        System.out.println(list.remove(8));

        list.add(2, 45);

        for (Integer i : list) {
            System.out.println(i);
        }

        System.out.println(list);

        System.out.println(list.getLast());
        System.out.println(list.get(6));
    }
}