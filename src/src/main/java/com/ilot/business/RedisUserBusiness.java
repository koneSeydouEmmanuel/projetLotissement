package com.ilot.business;

import com.ilot.utils.ExceptionUtils;
import com.ilot.utils.FunctionalError;
import com.ilot.utils.TechnicalError;
import com.ilot.utils.Utilities;
import com.ilot.utils.contract.Request;
import com.ilot.utils.contract.Response;
import com.ilot.utils.dto.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Component;

import java.util.*;

@Log4j2
@Component
public class RedisUserBusiness {

    private Response<UserDto> response;
    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private TechnicalError technicalError;
    @Autowired
    private ExceptionUtils exceptionUtils;
    @Autowired
    private UserBusiness userBusiness;

    public Response<UserDto> getRedisUserState(Request<UserDto> request, Locale locale) {
        log.info("----begin getRedisUserState-----");

        Response<UserDto> response = new Response<UserDto>();
        List<UserDto> items = new ArrayList<UserDto>();
        UserDto                            user                         = null;
        UserDto userDtos = null;
        List<Map<String,Object>> userDt = null;
        Response<UserDto> resp = null;
        Request<UserDto> req = null;
        UserDto userDto = null;
        UserDto userReturn = null;
        try {
           // usrDto = redisListUser.get("sky_qoe_user");

            // je récupère la date contenu dans la requête
            user = request.getData();

            // je recupère ma clé contenu dans le redis
            // userDtos = redisListUser.get("sky_qoe_user_"+user.getId());

            if (userDtos != null) {

                userReturn = userDtos;
            }

            if(userReturn == null){

                // je cré une instance de Request<UserDto>()
                req = new Request<UserDto>();

                // je cré une instance de UserDto()
                userDto = new UserDto();

                userDto.setId(user.getId());
                userDto.setIsDeleted(false);
                userDto.setIsLocked(false);

                // je l'objet userDto dans la requête req
                req.setData(userDto);

                // je fais ma recherche
              resp =  userBusiness.getByCriteria(req, Locale.FRANCE);

              if (resp != null && Utilities.isNotEmpty(resp.getItems())){

                  userReturn = resp.getItems().get(0);

                 // redisListUser.save("sky_qoe_user_"+userReturn.getId(), userReturn, true);
              }
            }

            response.setItems(Arrays.asList(userReturn));


        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (RuntimeException e) {
            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
        } catch (Exception e) {
            exceptionUtils.EXCEPTION(response, locale, e);
        } finally {
            log.info("----end getRedisUserState-----");
            if (response.isHasError() && response.getStatus() != null) {
                log.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
                        response.getStatus().getMessage());
                throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
            }
        }
        return response;
    }

}
