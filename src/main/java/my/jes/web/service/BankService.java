package my.jes.web.service;

import my.jes.web.dao.KB_DAO;
import my.jes.web.dao.ShinHanDAO;
import my.jes.web.vo.KB_VO;
import my.jes.web.vo.ShinHanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {
    @Autowired
    KB_DAO kbDAO;
    @Autowired
    ShinHanDAO sDAO;

    @Transactional // 트랜잭션 사용하기 위해 servlet-context.xml에 import와 bean 추가 해야 함
    public String remit(KB_VO kbvo) throws Exception {
        long balance = kbDAO.selectBalance(kbvo);
        balance -= kbvo.getRemitAmount();
        if (balance > 0) {
            kbvo.setBalance(balance);
            kbDAO.updateBalance(kbvo);

            ShinHanVO svo = new ShinHanVO("1234567-123-12", "전은수", 0);
            svo.setAmount(sDAO.selectAmount(svo) + kbvo.getRemitAmount());
            sDAO.updateAmount(svo);
            return "ok";
        }
        return "fail";
    }
}
