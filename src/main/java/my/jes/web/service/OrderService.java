package my.jes.web.service;

import my.jes.web.dao.OrderDAO;
import my.jes.web.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDAO;

    public long ordersInsert(ArrayList<OrderVO> list) throws Exception {
        return orderDAO.ordersInsert(list);
    }

}
