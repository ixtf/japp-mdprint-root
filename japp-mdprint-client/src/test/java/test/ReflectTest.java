package test;

import com.google.common.reflect.ClassPath;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ReflectTest {

    @Test
    public void test() throws IOException {
        String uuid = UUID.nameUUIDFromBytes("3000".getBytes()).toString();
        System.out.println(TimeUnit.DAYS.toHours(300));
        System.out.println(uuid);// e93028bd-c1aa-3dfb-b687-181f2031765d

        ClassPath cp = ClassPath.from(Thread.currentThread()
                .getContextClassLoader());
        cp.getTopLevelClasses("com.hengyi.japp.print.domain").forEach(
                clazz -> {
                    System.out.println(clazz);
                });

        // LoginOrient login = new LoginOrient();
        // OrientPK t = login.getClass().getAnnotation(OrientPK.class);
        // Class<?>[] cla = login.getClass().getInterfaces();
        // login.getClass().getInterfaces();
        // System.out.println(cla);
    }
}
