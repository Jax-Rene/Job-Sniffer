package com.zhuangjy.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class IBatisDao<T>  {

//	private static Logger logger = LoggerFactory.getLogger(IBatisDao.class);

    @Autowired
    @Qualifier("sqlMapClient")
    private SqlMapClient sqlMapClient;

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    /**
     * 对ID做预留处理。如可对namespace做抽象。
     * @param id
     * @return
     */
    private String getRealId(String id) {
        return id;
    }

    public <X> X queryForObject(String id) throws SQLException {
        return (X) this.sqlMapClient.queryForObject(getRealId(id));
    }

    public <X> X queryForObject(String id, Object param) throws SQLException {
        return (X) this.sqlMapClient.queryForObject(getRealId(id), param);
    }

    public <X> X queryForObject(String id, Object param, Object result) throws SQLException {
        return (X) this.sqlMapClient.queryForObject(getRealId(id), param, result);
    }

    public <X> List<X> queryForList(String id) throws SQLException {
        return this.sqlMapClient.queryForList(getRealId(id));
    }

    public <X> List<X> queryForList(String id, Object param) throws SQLException {
        return this.sqlMapClient.queryForList(getRealId(id), param);
    }

    public <X> X insert(String id, Object object) throws SQLException {
        return (X) this.sqlMapClient.insert(id, object);
    }

    public int update(String id, Object object)  throws SQLException {
        return this.sqlMapClient.update(id, object);
    }

    public int delete(String id, Object object)  throws SQLException {
        return this.sqlMapClient.delete(id, object);
    }

    public int delete(String id) throws SQLException{
        return this.sqlMapClient.delete(id);
    }

    public <X> List<X> queryForList(String id, Object param, int offset, int size)  throws SQLException {
        return this.sqlMapClient.queryForList(id, param, offset, size);
    }

    /**
     * 获取结果记录条数
     * @param id
     * @param param
     * @return
     */
    public Long queryForTotal(String id, Object param) throws SQLException {
        // prepare CountStatement
        String countStatementId = CountStatementUtil.getCountStatementId(id,
                ((SqlMapClientImpl) sqlMapClient).getDelegate());

        // do query
        return queryForObject(countStatementId, param);
    }

    public void update(String id) throws SQLException {
        this.sqlMapClient.update(id);
    }
}
