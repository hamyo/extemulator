package msk.extemulator.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import msk.extemulator.domain.Response;
import msk.extemulator.dao.ResponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseRepository responseRepository;

    @SneakyThrows
    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public Response getResponse(String service, String method, String key) {
        Response response = responseRepository.getResponseByServiceAndMethodAndKey(service, method, key);
        if (response == null) {
            response = responseRepository.getResponseByServiceAndMethodAndKey(service, method, null);
        }
        if (response != null && response.getSettings().needPause()) {
            Thread.sleep(response.getSettings().getDelay());
        }
        return response;
    }
}
