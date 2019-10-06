package blog.repository.customer;

import blog.domain.Mannager;
import blog.service.dto.MannagerDTO;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;


import java.util.List;

public interface MannagerRepositoryCustom {
    List<Mannager> search(Pageable pageable, MultiValueMap<String,String> multiValueMap);
    Long countMannager(MultiValueMap<String, String> queryParams);
    MannagerDTO doC();
}
