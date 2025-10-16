
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomList<E> implements Linked<E>{
    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(int index, E el) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index is out of bounds");
        if (index == 0) {
            addFirst(el);

        }
        else if (index == size) {
            addLast(el);
        }
        else {
            Node<E> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            Node<E> prevNode = current.prev;
            Node<E> node = new Node<>(prevNode, el, current);
            prevNode.next = node;
            current.prev = node;

            size++;
        }

    }

    @Override
    public void addFirst(E el) {
        Node<E> node = new Node<>(null, el, head);
        if(head != null){
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
        size++;
    }

    @Override
    public void addLast(E el) {
        Node<E> node = new Node<>(tail, el, null);
        if (tail != null) {
            tail.next = node;
        }
        tail = node;
        if (head == null) {
            head = node;
        }
        size++;
    }

    @Override
    public E get(int index) {
        if (head == null) throw new NoSuchElementException("List is empty");
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index is out of bounds");
        Node<E> current = head;
        for( int i = 0; i < index; i++ ) {
            current= current.next;
        }
        return current.getValue();
    }

    @Override
    public E getFirst() {
        if (head == null) throw new NoSuchElementException("List is empty");
        return head.getValue();
    }

    @Override
    public E getLast() {
        if (tail == null) throw new NoSuchElementException("List is empty");
        return tail.getValue();
    }

    @Override
    public E remove(int index) {
        if (head == null) throw new NoSuchElementException("List is empty");
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index is out of bounds");
        Node<E> removed;

        if(index == 0) {
            return removeFirst();
        }

        if(index == size - 1) {
            return removeLast();
        }
        Node<E> current = head;
        for(int i = 0; i < index; i++) {
            current = current.next;
        }
        removed = current;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return removed.getValue();
    }

    @Override
    public E removeFirst() {
        if (head == null) throw new NoSuchElementException("List is empty");
        Node<E> removed = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size--;
        return removed.getValue();
    }

    @Override
    public E removeLast() {
        if (tail == null) throw new NoSuchElementException("List is empty");
        Node<E> removed = tail;
        tail = removed.prev;
        if(tail != null) {
            tail.next = null;
        }
        else {
            head = null;
        }
        size--;
        return removed.getValue();
    }

    @Override
    public String toString() {
        return "CustomList{" +
                "head=" + head +
                ", tail=" + tail +
                ", size=" + size +
                '}';
    }
    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }


    private class CustomIterator implements Iterator<E> {
        private Node<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (current == null) throw new NoSuchElementException();

            E value = current.value;
            current = current.next;
            return value;
        }
    }

    private static class Node<E>{
        E value;
        Node<E> next;
        Node<E> prev;

        private Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public final void setValue(E value){
            this.value = value;
        }
        public final E getValue(){
            return value;
        }

        @Override
        public String toString() {    return value.toString();     }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value) && Objects.equals(next, node.next) && Objects.equals(prev, node.prev);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, next, prev);
        }
    }
}

