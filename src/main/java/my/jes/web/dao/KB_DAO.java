package my.jes.web.dao;

import my.jes.web.vo.KB_VO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KB_DAO {
    @Autowired
    SqlSession sqlSession;

    public long selectBalance(KB_VO vo) {
        return sqlSession.selectOne("mapper.kb.selectBalance", vo);
    }

    public void updateBalance(KB_VO vo) {
        sqlSession.update("mapper.kb.updateBalance", vo);
    }

}
