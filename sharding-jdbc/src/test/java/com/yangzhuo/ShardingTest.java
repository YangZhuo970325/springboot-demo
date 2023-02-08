package com.yangzhuo;

import com.yangzhuo.sharding.utils.ShardingConnection;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShardingTest {

    @Test
    public void shardingTestByJava() throws SQLException {

        /**
         * 通过java代码进行sharding-jdbc的配置
         */
        ShardingConnection shardingConnection = new ShardingConnection();

        Connection conn = shardingConnection.getDataSource().getConnection();
        int row = 0;
        PreparedStatement pstmt = null;
        String sql = "insert into t_order(order_id, order_num, create_time, user_id, order_amount) values (?, ?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 3);
            pstmt.setInt(2, 1001);
            pstmt.setDate(3, null);
            pstmt.setInt(4, 2);
            pstmt.setDouble(5, 100.00);
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.printf("修改行数为：" + row);
    }

    @Test
    public void shardingTestByYml() throws SQLException, IOException {

        /**
         * 通过yaml文件进行sharding-jdbc的配置
         */
        String filePath = ShardingTest.class.getResource("/sharding.yaml").getFile();

        DataSource dataSource = YamlShardingDataSourceFactory.createDataSource(new File(filePath));

        Connection conn = dataSource.getConnection();
        int row = 0;
        PreparedStatement pstmt = null;
        String sql = "insert into t_order(order_id, order_num, create_time, user_id, order_amount) values (?, ?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(sql);
            // 设置order_id order_id%2 == 0 落表 t_order0, 落表 t_order1
            pstmt.setInt(1, 4);
            pstmt.setInt(2, 1001);
            pstmt.setDate(3, null);
            // 设置user_id user_id%2 == 0 落库 ds0, 落库 ds1
            pstmt.setInt(4, 2);
            pstmt.setDouble(5, 100.00);
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.printf("修改行数为：" + row);
    }

    @Test
    public void test() {
        Long x = new Long(100);
        if (x.longValue() > 80) {
            System.out.println("大于80");
        }
    }
}
