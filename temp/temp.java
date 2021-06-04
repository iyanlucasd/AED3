public class temp{
    public static void main(String[] args) {
        int x = 1;
        int y;
        int total = 0;

        while (x<5) {
            y = x*x;
            System.out.println(y);
            total+= y;
            ++x;
        }
        System.out.println("Total = "+ total);
    }

}