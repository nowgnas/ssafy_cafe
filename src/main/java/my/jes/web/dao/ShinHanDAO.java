package my.jes.web.dao;

import my.jes.web.vo.ShinHanVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShinHanDAO {
    @Autowired
    SqlSession sqlSession;

    public long selectAmount(ShinHanVO vo) {
        return sqlSession.selectOne("mapper.shinhan.selectAmount", vo);
    }

    public void updateAmount(ShinHanVO vo) {
        sqlSession.update("mapper.shinhan.updateAmount", vo);
    }

}
