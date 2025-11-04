package com.innowise.customlinkedlist;

public interface Linked<E> extends Iterable<E>{
    int size();
    void add(int index, E el);
    void  addFirst(E el);
    void addLast(E el);

    E get(int index);
    E getFirst();
    E getLast();

    E remove(int index);
    E removeFirst();
    E removeLast();

    boolean isEmpty();

}