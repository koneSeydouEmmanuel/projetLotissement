
package com.ilot.dao.repository.base;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ilot.utils.*;
import com.ilot.utils.dto.*;
import com.ilot.utils.contract.*;
import com.ilot.utils.contract.Request;
import com.ilot.utils.contract.Response;
import com.ilot.dao.entity.*;

/**
 * Repository customize : User.
 *
 * @author Geo
 *
 */
@Repository
public interface _UserRepository {
	    /**
     * Finds User by using id as a search criteria.
     *
     * @param id
     * @return An Object User whose id is equals to the given id. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.id = :id and e.isDeleted = :isDeleted")
    User findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

    /**
     * Finds User by using contact as a search criteria.
     *
     * @param contact
     * @return An Object User whose contact is equals to the given contact. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.contact = :contact and e.isDeleted = :isDeleted")
    List<User> findByContact(@Param("contact")String contact, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using createdAt as a search criteria.
     *
     * @param createdAt
     * @return An Object User whose createdAt is equals to the given createdAt. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
    List<User> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using createdBy as a search criteria.
     *
     * @param createdBy
     * @return An Object User whose createdBy is equals to the given createdBy. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
    List<User> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using deletedAt as a search criteria.
     *
     * @param deletedAt
     * @return An Object User whose deletedAt is equals to the given deletedAt. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.deletedAt = :deletedAt and e.isDeleted = :isDeleted")
    List<User> findByDeletedAt(@Param("deletedAt")Date deletedAt, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using deletedBy as a search criteria.
     *
     * @param deletedBy
     * @return An Object User whose deletedBy is equals to the given deletedBy. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.deletedBy = :deletedBy and e.isDeleted = :isDeleted")
    List<User> findByDeletedBy(@Param("deletedBy")Integer deletedBy, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using email as a search criteria.
     *
     * @param email
     * @return An Object User whose email is equals to the given email. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.email = :email and e.isDeleted = :isDeleted")
    User findByEmail(@Param("email")String email, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using isDefaultPassword as a search criteria.
     *
     * @param isDefaultPassword
     * @return An Object User whose isDefaultPassword is equals to the given isDefaultPassword. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.isDefaultPassword = :isDefaultPassword and e.isDeleted = :isDeleted")
    List<User> findByIsDefaultPassword(@Param("isDefaultPassword")Boolean isDefaultPassword, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using isDeleted as a search criteria.
     *
     * @param isDeleted
     * @return An Object User whose isDeleted is equals to the given isDeleted. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.isDeleted = :isDeleted")
    List<User> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using isLocked as a search criteria.
     *
     * @param isLocked
     * @return An Object User whose isLocked is equals to the given isLocked. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.isLocked = :isLocked and e.isDeleted = :isDeleted")
    List<User> findByIsLocked(@Param("isLocked")Boolean isLocked, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using isSuperAdmin as a search criteria.
     *
     * @param isSuperAdmin
     * @return An Object User whose isSuperAdmin is equals to the given isSuperAdmin. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.isSuperAdmin = :isSuperAdmin and e.isDeleted = :isDeleted")
    List<User> findByIsSuperAdmin(@Param("isSuperAdmin")Boolean isSuperAdmin, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using login as a search criteria.
     *
     * @param login
     * @return An Object User whose login is equals to the given login. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.login = :login and e.isDeleted = :isDeleted")
    User findByLogin(@Param("login")String login, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using matricule as a search criteria.
     *
     * @param matricule
     * @return An Object User whose matricule is equals to the given matricule. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.matricule = :matricule and e.isDeleted = :isDeleted")
    User findByMatricule(@Param("matricule")String matricule, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using nom as a search criteria.
     *
     * @param nom
     * @return An Object User whose nom is equals to the given nom. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.nom = :nom and e.isDeleted = :isDeleted")
    List<User> findByNom(@Param("nom")String nom, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using password as a search criteria.
     *
     * @param password
     * @return An Object User whose password is equals to the given password. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.password = :password and e.isDeleted = :isDeleted")
    List<User> findByPassword(@Param("password")String password, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using prenom as a search criteria.
     *
     * @param prenom
     * @return An Object User whose prenom is equals to the given prenom. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.prenom = :prenom and e.isDeleted = :isDeleted")
    List<User> findByPrenom(@Param("prenom")String prenom, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using updatedAt as a search criteria.
     *
     * @param updatedAt
     * @return An Object User whose updatedAt is equals to the given updatedAt. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
    List<User> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds User by using updatedBy as a search criteria.
     *
     * @param updatedBy
     * @return An Object User whose updatedBy is equals to the given updatedBy. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
    List<User> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

    /**
     * Finds User by using profilId as a search criteria.
     *
     * @param profilId
     * @return An Object User whose profilId is equals to the given profilId. If
     *         no User is found, this method returns null.
     */
    @Query("select e from User e where e.profil.id = :profilId and e.isDeleted = :isDeleted")
    List<User> findByProfilId(@Param("profilId")Integer profilId, @Param("isDeleted")Boolean isDeleted);

  /**
   * Finds one User by using profilId as a search criteria.
   *
   * @param profilId
   * @return An Object User whose profilId is equals to the given profilId. If
   *         no User is found, this method returns null.
   */
  @Query("select e from User e where e.profil.id = :profilId and e.isDeleted = :isDeleted")
  User findUserByProfilId(@Param("profilId")Integer profilId, @Param("isDeleted")Boolean isDeleted);




    /**
     * Finds List of User by using userDto as a search criteria.
     *
     * @param request, em
     * @return A List of User
     * @throws DataAccessException,ParseException
     */
    public default List<User> getByCriteria(Request<UserDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
        String req = "select e from User e where e IS NOT NULL";
        HashMap<String, Object> param = new HashMap<String, Object>();
        req += getWhereExpression(request, param, locale);
                TypedQuery<User> query = em.createQuery(req, User.class);
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        if (request.getIndex() != null && request.getSize() != null) {
            query.setFirstResult(request.getIndex() * request.getSize());
            query.setMaxResults(request.getSize());
        }
        return query.getResultList();
    }

    /**
     * Finds count of User by using userDto as a search criteria.
     *
     * @param request, em
     * @return Number of User
     *
     */
    public default Long count(Request<UserDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
        String req = "select count(e.id) from User e where e IS NOT NULL";
        HashMap<String, Object> param = new HashMap<String, Object>();
        req += getWhereExpression(request, param, locale);
                javax.persistence.Query query = em.createQuery(req);
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        Long count = (Long) query.getResultList().get(0);
        return count;
    }

    /**
     * get where expression
     * @param request
     * @param param
     * @param locale
     * @return
     * @throws Exception
     */
    default String getWhereExpression(Request<UserDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
        // main query
        UserDto dto = request.getData() != null ? request.getData() : new UserDto();
        dto.setIsDeleted(false);
        String mainReq = generateCriteria(dto, param, 0, locale);
        // others query
        String othersReq = "";
        if (request.getDatas() != null && !request.getDatas().isEmpty()) {
            Integer index = 1;
            for (UserDto elt : request.getDatas()) {
                elt.setIsDeleted(false);
                String eltReq = generateCriteria(elt, param, index, locale);
                if (request.getIsAnd() != null && request.getIsAnd()) {
                    othersReq += "and (" + eltReq + ") ";
                } else {
                    othersReq += "or (" + eltReq + ") ";
                }
                index++;
            }
        }
        String req = "";
        if (!mainReq.isEmpty()) {
            req += " and (" + mainReq + ") ";
        }
        req += othersReq;

        //order
        if(Direction.fromOptionalString(dto.getOrderDirection()).orElse(null) != null && Utilities.notBlank(dto.getOrderField())) {
            req += " order by e."+dto.getOrderField()+" "+dto.getOrderDirection();
        }
        else {
            req += " order by  e.id desc";
        }
        return req;
    }

    /**
     * generate sql query for dto
     * @param dto
     * @param param
     * @param index
     * @param locale
     * @return
     * @throws Exception
     */
    default String generateCriteria(UserDto dto, HashMap<String, Object> param, Integer index,  Locale locale) throws Exception{
        List<String> listOfQuery = new ArrayList<String>();
        if (dto != null) {
            if (dto.getId() != null || Utilities.searchParamIsNotEmpty(dto.getIdParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getContact()) || Utilities.searchParamIsNotEmpty(dto.getContactParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("contact", dto.getContact(), "e.contact", "String", dto.getContactParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getCreatedAt()) || Utilities.searchParamIsNotEmpty(dto.getCreatedAtParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
            }
            if (dto.getCreatedBy() != null || Utilities.searchParamIsNotEmpty(dto.getCreatedByParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getDeletedAt()) || Utilities.searchParamIsNotEmpty(dto.getDeletedAtParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("deletedAt", dto.getDeletedAt(), "e.deletedAt", "Date", dto.getDeletedAtParam(), param, index, locale));
            }
            if (dto.getDeletedBy() != null || Utilities.searchParamIsNotEmpty(dto.getDeletedByParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("deletedBy", dto.getDeletedBy(), "e.deletedBy", "Integer", dto.getDeletedByParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getEmail()) || Utilities.searchParamIsNotEmpty(dto.getEmailParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("email", dto.getEmail(), "e.email", "String", dto.getEmailParam(), param, index, locale));
            }
            if (dto.getIsDefaultPassword() != null || Utilities.searchParamIsNotEmpty(dto.getIsDefaultPasswordParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("isDefaultPassword", dto.getIsDefaultPassword(), "e.isDefaultPassword", "Boolean", dto.getIsDefaultPasswordParam(), param, index, locale));
            }
            if (dto.getIsDeleted() != null || Utilities.searchParamIsNotEmpty(dto.getIsDeletedParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
            }
            if (dto.getIsLocked() != null || Utilities.searchParamIsNotEmpty(dto.getIsLockedParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("isLocked", dto.getIsLocked(), "e.isLocked", "Boolean", dto.getIsLockedParam(), param, index, locale));
            }
            if (dto.getIsSuperAdmin() != null || Utilities.searchParamIsNotEmpty(dto.getIsSuperAdminParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("isSuperAdmin", dto.getIsSuperAdmin(), "e.isSuperAdmin", "Boolean", dto.getIsSuperAdminParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getLogin()) || Utilities.searchParamIsNotEmpty(dto.getLoginParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("login", dto.getLogin(), "e.login", "String", dto.getLoginParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getMatricule()) || Utilities.searchParamIsNotEmpty(dto.getMatriculeParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("matricule", dto.getMatricule(), "e.matricule", "String", dto.getMatriculeParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getNom()) || Utilities.searchParamIsNotEmpty(dto.getNomParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("nom", dto.getNom(), "e.nom", "String", dto.getNomParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getPassword()) || Utilities.searchParamIsNotEmpty(dto.getPasswordParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("password", dto.getPassword(), "e.password", "String", dto.getPasswordParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getPrenom()) || Utilities.searchParamIsNotEmpty(dto.getPrenomParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("prenom", dto.getPrenom(), "e.prenom", "String", dto.getPrenomParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getUpdatedAt()) || Utilities.searchParamIsNotEmpty(dto.getUpdatedAtParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
            }
            if (dto.getUpdatedBy() != null || Utilities.searchParamIsNotEmpty(dto.getUpdatedByParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
            }
                        if (dto.getProfilId() != null || Utilities.searchParamIsNotEmpty(dto.getProfilIdParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("profilId", dto.getProfilId(), "e.profil.id", "Integer", dto.getProfilIdParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getProfilCode()) || Utilities.searchParamIsNotEmpty(dto.getProfilCodeParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("profilCode", dto.getProfilCode(), "e.profil.code", "String", dto.getProfilCodeParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getProfilLibelle()) || Utilities.searchParamIsNotEmpty(dto.getProfilLibelleParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("profilLibelle", dto.getProfilLibelle(), "e.profil.libelle", "String", dto.getProfilLibelleParam(), param, index, locale));
            }

            /*List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
            if (Utilities.isNotEmpty(listOfCustomQuery)) {
                listOfQuery.addAll(listOfCustomQuery);
            }*/
        }
        return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
    }
}
