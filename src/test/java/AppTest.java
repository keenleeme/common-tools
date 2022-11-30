import com.alibaba.fastjson.JSONObject;
import com.latrell.service.impl.OperationImpl;
import com.latrell.service.impl.SpiOperationImpl;
import com.latrell.test.domain.SysUser;
import com.latrell.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * 测试类
 *
 * @author liz
 * @date 2022/10/24-10:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Test
    public void test_consumer() {
        // 函数式接口，可执行方法有两个：accept、andThen
        Consumer<Integer> consumer1 = x -> System.out.println("x * x = " + (x * x));
        Consumer<Integer> consumer2 = x -> System.out.println("x + x = " + (x + x));
        consumer1.andThen(consumer2).accept(2);
    }

    @Test
    public void test_spiDemo() {
        SpiOperationImpl spiOperation = new SpiOperationImpl();
        spiOperation.operate();
        OperationImpl operation = new OperationImpl();
        operation.operate();
    }

    @Test
    public void test_clickhouse() throws SQLException, ClassNotFoundException {
        // 加载驱动
        String driverClass = "ru.yandex.clickhouse.ClickHouseDriver";
        Class.forName(driverClass);
        String url = "jdbc:clickhouse://play.clickhouse.com:9440/";
        String user = "explorer";
        String password = "";

        Connection connection = DriverManager.getConnection(url, user, password);
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT 'Play ClickHouse'");
        System.out.println(resultSet.next());
    }

    @Resource
    private UserService userService;

    @Test
    public void test_selectList() {
        List<SysUser> list = userService.list();
        System.out.println(JSONObject.toJSONString(list));
    }

}
