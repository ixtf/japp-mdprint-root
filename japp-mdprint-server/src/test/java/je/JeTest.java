package je;

/**
 * Created by jzb on 16-3-3.
 */
public class JeTest {
    public static void main(String[] args) throws Exception {
        long _start = System.currentTimeMillis();
        MyServiceJe.sapService.syncData("3000");
        long _end = System.currentTimeMillis();
        System.out.println("结束:" + ((_end - _start) / 1000));
    }
}
