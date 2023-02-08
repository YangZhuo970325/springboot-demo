package com.yangzhuo.service.impl;

import com.yangzhuo.entity.Order;
import com.yangzhuo.service.ShardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class ShardingServiceImpl implements ShardingService {

    @Autowired
    private DataSource dataSource;

    @Override
    public int insertOrder(Order order)  {

        int row = 0;
        PreparedStatement pstmt = null;
        String sql = "insert into t_order(order_id, order_num, create_time, user_id, order_amount) values (?, ?, ?, ?, ?)";

        try {
            Connection conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            // 设置order_id order_id%2 == 0 落表 t_order0, 落表 t_order1
            pstmt.setInt(1, order.getOrderId());
            pstmt.setInt(2, order.getOrderNum());
            pstmt.setDate(3, order.getCreateTime());
            // 设置user_id user_id%2 == 0 落库 ds0, 落库 ds1
            pstmt.setInt(4, order.getUserId());
            pstmt.setDouble(5, order.getOrderAmount());
            row = pstmt.executeUpdate();
            return row;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Order queryOrder(int orderId) {
        PreparedStatement pstmt = null;
        String sql = "select * from t_order where order_id = " + orderId;

        try {
            Connection conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            // 设置order_id order_id%2 == 0 落表 t_order0, 落表 t_order1
            ResultSet rs = pstmt.executeQuery();
            Order order = new Order();
            while (rs.next()) {
                order.setOrderId(rs.getInt(1));
                order.setOrderNum(rs.getInt(2));
                order.setUserId(rs.getInt(4));
                order.setOrderAmount(rs.getDouble(5));
            }
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
