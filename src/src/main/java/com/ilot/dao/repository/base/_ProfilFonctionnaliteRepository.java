
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
 * Repository customize : ProfilFonctionnalite.
 *
 * @author Geo
 *
 */
@Repository
public interface _ProfilFonctionnaliteRepository {
	    /**
     * Finds ProfilFonctionnalite by using id as a search criteria.
     *
     * @param id
     * @return An Object ProfilFonctionnalite whose id is equals to the given id. If
     *         no ProfilFonctionnalite is found, this method returns null.
     */
    @Query("select e from ProfilFonctionnalite e where e.id = :id and e.isDeleted = :isDeleted")
    ProfilFonctionnalite findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

    /**
     * Finds ProfilFonctionnalite by using createdAt as a search criteria.
     *
     * @param createdAt
     * @return An Object ProfilFonctionnalite whose createdAt is equals to the given createdAt. If
     *         no ProfilFonctionnalite is found, this method returns null.
     */
    @Query("select e from ProfilFonctionnalite e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
    List<ProfilFonctionnalite> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds ProfilFonctionnalite by using createdBy as a search criteria.
     *
     * @param createdBy
     * @return An Object ProfilFonctionnalite whose createdBy is equals to the given createdBy. If
     *         no ProfilFonctionnalite is found, this method returns null.
     */
    @Query("select e from ProfilFonctionnalite e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
    List<ProfilFonctionnalite> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds ProfilFonctionnalite by using isDeleted as a search criteria.
     *
     * @param isDeleted
     * @return An Object ProfilFonctionnalite whose isDeleted is equals to the given isDeleted. If
     *         no ProfilFonctionnalite is found, this method returns null.
     */
    @Query("select e from ProfilFonctionnalite e where e.isDeleted = :isDeleted")
    List<ProfilFonctionnalite> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
    /**
     * Finds ProfilFonctionnalite by using updatedAt as a search criteria.
     *
     * @param updatedAt
     * @return An Object ProfilFonctionnalite whose updatedAt is equals to the given updatedAt. If
     *         no ProfilFonctionnalite is found, this method returns null.
     */
    @Query("select e from ProfilFonctionnalite e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
    List<ProfilFonctionnalite> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds ProfilFonctionnalite by using updatedBy as a search criteria.
     *
     * @param updatedBy
     * @return An Object ProfilFonctionnalite whose updatedBy is equals to the given updatedBy. If
     *         no ProfilFonctionnalite is found, this method returns null.
     */
    @Query("select e from ProfilFonctionnalite e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
    List<ProfilFonctionnalite> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

    /**
     * Finds ProfilFonctionnalite by using fonctionnaliteId as a search criteria.
     *
     * @param fonctionnaliteId
     * @return An Object ProfilFonctionnalite whose fonctionnaliteId is equals to the given fonctionnaliteId. If
     *         no ProfilFonctionnalite is found, this method returns null.
     */
    @Query("select e from ProfilFonctionnalite e where e.fonctionnalite.id = :fonctionnaliteId and e.isDeleted = :isDeleted")
    List<ProfilFonctionnalite> findByFonctionnaliteId(@Param("fonctionnaliteId")Integer fonctionnaliteId, @Param("isDeleted")Boolean isDeleted);

  /**
   * Finds one ProfilFonctionnalite by using fonctionnaliteId as a search criteria.
   *
   * @param fonctionnaliteId
   * @return An Object ProfilFonctionnalite whose fonctionnaliteId is equals to the given fonctionnaliteId. If
   *         no ProfilFonctionnalite is found, this method returns null.
   */
  @Query("select e from ProfilFonctionnalite e where e.fonctionnalite.id = :fonctionnaliteId and e.isDeleted = :isDeleted")
  ProfilFonctionnalite findProfilFonctionnaliteByFonctionnaliteId(@Param("fonctionnaliteId")Integer fonctionnaliteId, @Param("isDeleted")Boolean isDeleted);


    /**
     * Finds ProfilFonctionnalite by using profilId as a search criteria.
     *
     * @param profilId
     * @return An Object ProfilFonctionnalite whose profilId is equals to the given profilId. If
     *         no ProfilFonctionnalite is found, this method returns null.
     */
    @Query("select e from ProfilFonctionnalite e where e.profil.id = :profilId and e.isDeleted = :isDeleted")
    List<ProfilFonctionnalite> findByProfilId(@Param("profilId")Integer profilId, @Param("isDeleted")Boolean isDeleted);

  /**
   * Finds one ProfilFonctionnalite by using profilId as a search criteria.
   *
   * @param profilId
   * @return An Object ProfilFonctionnalite whose profilId is equals to the given profilId. If
   *         no ProfilFonctionnalite is found, this method returns null.
   */
  @Query("select e from ProfilFonctionnalite e where e.profil.id = :profilId and e.isDeleted = :isDeleted")
  ProfilFonctionnalite findProfilFonctionnaliteByProfilId(@Param("profilId")Integer profilId, @Param("isDeleted")Boolean isDeleted);




    /**
     * Finds List of ProfilFonctionnalite by using profilFonctionnaliteDto as a search criteria.
     *
     * @param request, em
     * @return A List of ProfilFonctionnalite
     * @throws DataAccessException,ParseException
     */
    public default List<ProfilFonctionnalite> getByCriteria(Request<ProfilFonctionnaliteDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
        String req = "select e from ProfilFonctionnalite e where e IS NOT NULL";
        HashMap<String, Object> param = new HashMap<String, Object>();
        req += getWhereExpression(request, param, locale);
                TypedQuery<ProfilFonctionnalite> query = em.createQuery(req, ProfilFonctionnalite.class);
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
     * Finds count of ProfilFonctionnalite by using profilFonctionnaliteDto as a search criteria.
     *
     * @param request, em
     * @return Number of ProfilFonctionnalite
     *
     */
    public default Long count(Request<ProfilFonctionnaliteDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
        String req = "select count(e.id) from ProfilFonctionnalite e where e IS NOT NULL";
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
    default String getWhereExpression(Request<ProfilFonctionnaliteDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
        // main query
        ProfilFonctionnaliteDto dto = request.getData() != null ? request.getData() : new ProfilFonctionnaliteDto();
        dto.setIsDeleted(false);
        String mainReq = generateCriteria(dto, param, 0, locale);
        // others query
        String othersReq = "";
        if (request.getDatas() != null && !request.getDatas().isEmpty()) {
            Integer index = 1;
            for (ProfilFonctionnaliteDto elt : request.getDatas()) {
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
    default String generateCriteria(ProfilFonctionnaliteDto dto, HashMap<String, Object> param, Integer index,  Locale locale) throws Exception{
        List<String> listOfQuery = new ArrayList<String>();
        if (dto != null) {
            if (dto.getId() != null || Utilities.searchParamIsNotEmpty(dto.getIdParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getCreatedAt()) || Utilities.searchParamIsNotEmpty(dto.getCreatedAtParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
            }
            if (dto.getCreatedBy() != null || Utilities.searchParamIsNotEmpty(dto.getCreatedByParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
            }
            if (dto.getIsDeleted() != null || Utilities.searchParamIsNotEmpty(dto.getIsDeletedParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getUpdatedAt()) || Utilities.searchParamIsNotEmpty(dto.getUpdatedAtParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
            }
            if (dto.getUpdatedBy() != null || Utilities.searchParamIsNotEmpty(dto.getUpdatedByParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
            }
                        if (dto.getFonctionnaliteId() != null || Utilities.searchParamIsNotEmpty(dto.getFonctionnaliteIdParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("fonctionnaliteId", dto.getFonctionnaliteId(), "e.fonctionnalite.id", "Integer", dto.getFonctionnaliteIdParam(), param, index, locale));
            }
                        if (dto.getProfilId() != null || Utilities.searchParamIsNotEmpty(dto.getProfilIdParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("profilId", dto.getProfilId(), "e.profil.id", "Integer", dto.getProfilIdParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getFonctionnaliteCode()) || Utilities.searchParamIsNotEmpty(dto.getFonctionnaliteCodeParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("fonctionnaliteCode", dto.getFonctionnaliteCode(), "e.fonctionnalite.code", "String", dto.getFonctionnaliteCodeParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getFonctionnaliteLibelle()) || Utilities.searchParamIsNotEmpty(dto.getFonctionnaliteLibelleParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("fonctionnaliteLibelle", dto.getFonctionnaliteLibelle(), "e.fonctionnalite.libelle", "String", dto.getFonctionnaliteLibelleParam(), param, index, locale));
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
