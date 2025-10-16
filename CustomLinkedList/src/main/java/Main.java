public class Main {
    public static void main(String[] args) {
        Linked<Integer> list = new CustomList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        for (Integer i : list) {
            System.out.println(i);
        }
        list.removeFirst();
        System.out.println(list);

        System.out.println(list.getLast());
    }
}