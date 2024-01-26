package msk.extemulator.dao;

import msk.extemulator.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    Response getResponseByServiceAndMethodAndKey(String service, String method, String key);
}
