package com.zhuangjy.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class BaseDao<T> {

    private Logger logger = LoggerFactory.getLogger(BaseDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public BaseDao() {
    }

    private void setQueryParam(Map<String, Object> hs, Query query) {
        if(hs!=null&&!hs.isEmpty()) {
            query.setProperties(hs);
        }
    }

    protected void setParam(Query query, Object[] params) {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
    }

    public T save(T obj) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(obj);
        return obj;
    }
    public void delete(T obj) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(obj);
    }
    public void add(T obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }
    public void update(T obj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }


    public List<T> query(String hql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        return query.list();
    }

    public List<T> query(String hql, Map<String, Object> hs) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setQueryParam(hs, query);
        return query.list();
    }


    public List<T> querySql(String hql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql);
        return query.list();
    }
    
    public List<T> querySqlWithLimit(String hql, int limit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql);
        query.setFetchSize(Math.max(1, limit));
        return query.list();
    }

    public List<T> querySql(String hql, Map<String, Object> hs) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql);
        setQueryParam(hs, query);
        return query.list();
    }

    public List<Map> queryHqlMap(String hql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public List<Map> queryHqlMap(String hql, Map<String, Object> hs) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setQueryParam(hs, query);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public List<List> queryHqlList(String hql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setResultTransformer(Transformers.TO_LIST);
        return query.list();
    }

    public List<Map> querySqlMap(String sql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public List<Map> querySqlMapByMap(String sql, Map<String, Object> hs) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql);
        setQueryParam(hs, query);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public List<Map> querySqlMap(String sql, Object... params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql);
        setParam(query, params);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public List<List> querySqlList(String sql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql);
        query.setResultTransformer(Transformers.TO_LIST);
        return query.list();
    }

    public Long queryHqlCount(String hql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        Object res = query.uniqueResult();
        return Long.valueOf(res == null ? "0" : res.toString());
    }

    public Long queryHqlCountByMap(String hql, Map<String, Object> params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setQueryParam(params, query);
        return Long.valueOf(query.uniqueResult().toString());
    }

    public Long queryHqlCount(String hql, Map params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setQueryParam(params, query);
        return Long.valueOf(query.uniqueResult().toString());
    }

    public Long querySqlCount(String hql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql);
        Object res = query.uniqueResult();
        return Long.valueOf(res == null ? "0" : res.toString());
    }

    public Long querySqlCountByMap(String hql, Map<String, Object> params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql);
        setQueryParam(params, query);
        return Long.valueOf(query.uniqueResult().toString());
    }

    public Object uniqueResult(String hql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        return query.uniqueResult();
    }

    public Object uniqueResult(String hql, Map<String, Object> hs) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setQueryParam(hs, query);
        return query.uniqueResult();
    }

    public int executeUpdateHql(String hql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        return query.executeUpdate();
    }

    public int executeUpdateHqlByMap(String hql, Map<String, Object> hs) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setQueryParam(hs, query);
        return query.executeUpdate();
    }

    public int executeUpdateHql(String hql, Object... params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        if (params != null && params.length > 0) {
            int i = 0;
            for (Object obj : params) {
                query.setParameter(i, obj);
                i++;
            }
//            for (int i = 0; i < params.length; i++) {
//                query.setParameter(i + 1, params[i]);
//            }
        }
        return query.executeUpdate();
    }


    public int executeUpdateSql(String sql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql);
        return query.executeUpdate();
    }

    public int executeUpdateSql(String sql,Object... params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql);
        setParam(query,params);
        return query.executeUpdate();
    }

    public int executeUpdateSql(String sql,Map<String,Object> params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql);
        setQueryParam(params, query);
        return query.executeUpdate();
    }

    public Object uniqueSqlResult(String sql) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql);
        return query.uniqueResult();
    }

    @Transactional
    public List<T> find(Class<T> clazz) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(clazz);
        return criteria.list();
    }

    @Transactional
    public List<T> find(Class<T> clazz, String sort, boolean ascending) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(clazz);
        if(ascending){
            criteria.addOrder(Order.asc(sort));
        }else {
            criteria.addOrder(Order.desc(sort));
        }
        return criteria.list();
    }

    public List<T> find(String hql, Object... params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setParam(query, params);
        return query.list();
    }

    public <T> Object loadById(Serializable id, Class<T> clazz) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(clazz);
        criteria.add(Restrictions.idEq(id));
//        return session.load(clazz, id);
        return criteria.uniqueResult();
    }

    public <T> void deleteById(Serializable id, Class<T> clazz) {
        Session session = sessionFactory.getCurrentSession();
        Object obj = loadById(id, clazz);
        if(obj!=null) {
            session.delete(obj);
        }else {
            logger.error("delete empty obj by id [" + id + "] clazz [" + clazz.getSimpleName() + "]");
        }
    }

    public List<T> find(Class<?> clazz, List<Criterion> criterions) {
        return find(clazz, criterions, null);
    }


    public Long count(Class<?> clazz, List<Criterion> criterions) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria =session.createCriteria(clazz);
        if (criterions != null && !criterions.isEmpty()) {
            for (Criterion c : criterions) {
                criteria.add(c);
            }
        }
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public List<T> find(Class<?> clazz, List<Criterion> criterions, int start , int limit) {
        return find(clazz, criterions, start, limit, null);
    }

    public List<T> find(Class<?> clazz, List<Criterion> criterions, int start , int limit, List<Order> orders) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria =session.createCriteria(clazz);
        if (criterions != null && !criterions.isEmpty()) {
            for (Criterion c : criterions) {
                criteria.add(c);
            }
        }

        if (start < 0) {
            start = 0;
        }
        criteria.setFirstResult(start);
        if (limit <= 0) {
            limit = 100000;
        }
        criteria.setMaxResults(limit);

        if(orders!=null&&!orders.isEmpty()){
            for(Order order : orders) {
                criteria.addOrder(order);
            }
        }
        return criteria.list();
    }

    public List<T> find(Class<?> clazz, List<Criterion> criterions, List<Order> orders) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria =session.createCriteria(clazz);
        if (criterions != null && !criterions.isEmpty()) {
            for (Criterion c : criterions) {
                criteria.add(c);
            }
        }

        if(orders!=null&&!orders.isEmpty()){
            for(Order order : orders) {
                criteria.addOrder(order);
            }
        }
        return criteria.list();
    }

    public List<T> find(String propertyName, Object val, Class<?> clazz) {
        Assert.hasText(propertyName, "propertyName不能为空!");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria =session.createCriteria(clazz);
        criteria.add(Restrictions.eq(propertyName, val));
        return criteria.list();
    }

    public <T> Object findUnique(String propertyName, Object val, Class<?> clazz) {
        Assert.hasText(propertyName, "propertyName不能为空!");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria =session.createCriteria(clazz);
        criteria.add(Restrictions.eq(propertyName, val));
        return criteria.uniqueResult();
    }

    public List loadByIds(String idName, Serializable[] ids, Class<?> clazz) {
        Assert.notEmpty(ids, "ids不能为空!");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria =session.createCriteria(clazz);
        criteria.add(Restrictions.in(idName, ids));
        return criteria.list();
    }

    public List<T> findByOrder(Class<?> clazz, String property, String orderby) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria =session.createCriteria(clazz);
        if (StringUtils.isEmpty(orderby) || orderby.trim().equalsIgnoreCase("asc")) {
            criteria.addOrder(Order.asc(property));
        }else {
            criteria.addOrder(Order.desc(property));
        }
        return criteria.list();
    }

    public List<T> find(List<Criterion> criterias, Class<?> clazz) {
        return find(clazz, criterias, null);
    }

    public List<?> find(DetachedCriteria criteria) {
        Session session = sessionFactory.getCurrentSession();
        Criteria exec = criteria.getExecutableCriteria(session);
        return exec.list();
    }

    public List<T> query(String hql, Object... params) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setParam(query, params);
        return query.list();
    }

    public ClassMetadata getClassMetadata(String clz) {
        return sessionFactory.getClassMetadata(clz);
    }

    public List<T> findByMap(String hql, Map hs) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        setQueryParam(hs, query);
        return query.list();
    }
}
