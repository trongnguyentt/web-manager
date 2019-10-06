package blog.repository.Imp;

import blog.domain.Mannager;
import blog.repository.customer.MannagerRepositoryCustom;
import blog.service.dto.MannagerDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MannagerRepositoryImpl implements MannagerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
   @Override
    public List<Mannager> search(Pageable pageable, MultiValueMap<String,String> multiValueMap){
       String sql = "select T from Mannager T where T.status <> 0";

       Map<String, Object> values = new HashMap<>();
       sql += creatQuery(multiValueMap, values);

       Query query = entityManager.createQuery(sql, Mannager.class);

       values.forEach(query::setParameter);

       query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
       query.setMaxResults(pageable.getPageSize());
       return query.getResultList();
    }
    @Override
    public Long countMannager(MultiValueMap<String, String> queryParams) {
        String sql = "select count(M) from Mannager M where M.status <> 0";

        Map<String, Object> values = new HashMap<>();
        sql += creatQuery(queryParams, values);

        Query query = entityManager.createQuery(sql, Long.class);

        values.forEach(query::setParameter);

        return (Long) query.getSingleResult();
    }

    @Override
    public MannagerDTO doC() {
        String sql = "select SUM(T.spentMoney) from Mannager T where T.status <> 0 and T.createdBy='admin'";
        Map<String, Object> values = new HashMap<>();
        MannagerDTO mannagerDTO=new MannagerDTO();
        Query query=entityManager.createQuery(sql,Long.class);
        values.forEach(query::setParameter);
        String sql2 = "select SUM(T.spentMoney) from Mannager T where T.status <> 0 and T.createdBy='xnxx'";
        Map<String, Object> values2 = new HashMap<>();
        Query query2=entityManager.createQuery(sql2,Long.class);
        values2.forEach(query2::setParameter);


        mannagerDTO.setSpentMoney(((Long) query.getSingleResult()-(Long) query2.getSingleResult())/2);
       return mannagerDTO;
    }

    public String creatQuery(MultiValueMap<String,String> multiValueMap,Map<String,Object> map){
        String query="";

        return query;
    }

}
