package my.jes.web.dao;

import my.jes.web.vo.OrderVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class OrderDAO {
    @Autowired
    SqlSession sqlSession;

    public long ordersInsert(ArrayList<OrderVO> list) {
        //System.out.println(list.size());
        long order_group_no = getOrder_group_no() + 1;
        for (OrderVO orderVO : list) {
            orderVO.setOrder_group_no(order_group_no);
            //System.out.println(">>>"+orderVO);
            sqlSession.insert("mapper.order.insert", orderVO);
        }
        return order_group_no;
    }

    public long getOrder_group_no() {
        //System.out.println(order_group_no);
        return sqlSession.selectOne("mapper.order.order_group_no");
    }
}
