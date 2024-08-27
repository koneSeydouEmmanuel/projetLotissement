
package com.ilot.dao.repository.base;

import java.util.Date;
import java.util.List;
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
 * Repository customize : Fonctionnalite.
 *
 * @author Geo
 *
 */
@Repository
public interface _FonctionnaliteRepository {
	    /**
     * Finds Fonctionnalite by using id as a search criteria.
     *
     * @param id
     * @return An Object Fonctionnalite whose id is equals to the given id. If
     *         no Fonctionnalite is found, this method returns null.
     */
    @Query("select e from Fonctionnalite e where e.id = :id and e.isDeleted = :isDeleted")
    Fonctionnalite findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

    /**
     * Finds Fonctionnalite by using code as a search criteria.
     *
     * @param code
     * @return An Object Fonctionnalite whose code is equals to the given code. If
     *         no Fonctionnalite is found, this method returns null.
     */
    @Query("select e from Fonctionnalite e where e.code = :code and e.isDeleted = :isDeleted")
    Fonctionnalite findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Fonctionnalite by using createdAt as a search criteria.
     *
     * @param createdAt
     * @return An Object Fonctionnalite whose createdAt is equals to the given createdAt. If
     *         no Fonctionnalite is found, this method returns null.
     */
    @Query("select e from Fonctionnalite e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
    List<Fonctionnalite> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Fonctionnalite by using createdBy as a search criteria.
     *
     * @param createdBy
     * @return An Object Fonctionnalite whose createdBy is equals to the given createdBy. If
     *         no Fonctionnalite is found, this method returns null.
     */
    @Query("select e from Fonctionnalite e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
    List<Fonctionnalite> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Fonctionnalite by using isDeleted as a search criteria.
     *
     * @param isDeleted
     * @return An Object Fonctionnalite whose isDeleted is equals to the given isDeleted. If
     *         no Fonctionnalite is found, this method returns null.
     */
    @Query("select e from Fonctionnalite e where e.isDeleted = :isDeleted")
    List<Fonctionnalite> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Fonctionnalite by using libelle as a search criteria.
     *
     * @param libelle
     * @return An Object Fonctionnalite whose libelle is equals to the given libelle. If
     *         no Fonctionnalite is found, this method returns null.
     */
    @Query("select e from Fonctionnalite e where e.libelle = :libelle and e.isDeleted = :isDeleted")
    Fonctionnalite findByLibelle(@Param("libelle")String libelle, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Fonctionnalite by using updatedAt as a search criteria.
     *
     * @param updatedAt
     * @return An Object Fonctionnalite whose updatedAt is equals to the given updatedAt. If
     *         no Fonctionnalite is found, this method returns null.
     */
    @Query("select e from Fonctionnalite e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
    List<Fonctionnalite> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Fonctionnalite by using updatedBy as a search criteria.
     *
     * @param updatedBy
     * @return An Object Fonctionnalite whose updatedBy is equals to the given updatedBy. If
     *         no Fonctionnalite is found, this method returns null.
     */
    @Query("select e from Fonctionnalite e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
    List<Fonctionnalite> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

    /**
     * Finds Fonctionnalite by using parentId as a search criteria.
     *
     * @param parentId
     * @return An Object Fonctionnalite whose parentId is equals to the given parentId. If
     *         no Fonctionnalite is found, this method returns null.
     */
    @Query("select e from Fonctionnalite e where e.fonctionnalite.id = :parentId and e.isDeleted = :isDeleted")
    List<Fonctionnalite> findByParentId(@Param("parentId")Integer parentId, @Param("isDeleted")Boolean isDeleted);

  /**
   * Finds one Fonctionnalite by using parentId as a search criteria.
   *
   * @param parentId
   * @return An Object Fonctionnalite whose parentId is equals to the given parentId. If
   *         no Fonctionnalite is found, this method returns null.
   */
  @Query("select e from Fonctionnalite e where e.fonctionnalite.id = :parentId and e.isDeleted = :isDeleted")
  Fonctionnalite findFonctionnaliteByParentId(@Param("parentId")Integer parentId, @Param("isDeleted")Boolean isDeleted);




    /**
     * Finds List of Fonctionnalite by using fonctionnaliteDto as a search criteria.
     *
     * @param request, em
     * @return A List of Fonctionnalite
     * @throws DataAccessException,ParseException
     */
    public default List<Fonctionnalite> getByCriteria(Request<FonctionnaliteDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
        String req = "select e from Fonctionnalite e where e IS NOT NULL";
        HashMap<String, Object> param = new HashMap<String, Object>();
        req += getWhereExpression(request, param, locale);
                TypedQuery<Fonctionnalite> query = em.createQuery(req, Fonctionnalite.class);
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
     * Finds count of Fonctionnalite by using fonctionnaliteDto as a search criteria.
     *
     * @param request, em
     * @return Number of Fonctionnalite
     *
     */
    public default Long count(Request<FonctionnaliteDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
        String req = "select count(e.id) from Fonctionnalite e where e IS NOT NULL";
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
    default String getWhereExpression(Request<FonctionnaliteDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
        // main query
        FonctionnaliteDto dto = request.getData() != null ? request.getData() : new FonctionnaliteDto();
        dto.setIsDeleted(false);
        String mainReq = generateCriteria(dto, param, 0, locale);
        // others query
        String othersReq = "";
        if (request.getDatas() != null && !request.getDatas().isEmpty()) {
            Integer index = 1;
            for (FonctionnaliteDto elt : request.getDatas()) {
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
    default String generateCriteria(FonctionnaliteDto dto, HashMap<String, Object> param, Integer index,  Locale locale) throws Exception{
        List<String> listOfQuery = new ArrayList<String>();
        if (dto != null) {
            if (dto.getId() != null || Utilities.searchParamIsNotEmpty(dto.getIdParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getCode()) || Utilities.searchParamIsNotEmpty(dto.getCodeParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("code", dto.getCode(), "e.code", "String", dto.getCodeParam(), param, index, locale));
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
            if (Utilities.isNotBlank(dto.getLibelle()) || Utilities.searchParamIsNotEmpty(dto.getLibelleParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("libelle", dto.getLibelle(), "e.libelle", "String", dto.getLibelleParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getUpdatedAt()) || Utilities.searchParamIsNotEmpty(dto.getUpdatedAtParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
            }
            if (dto.getUpdatedBy() != null || Utilities.searchParamIsNotEmpty(dto.getUpdatedByParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
            }
                        if (dto.getParentId() != null || Utilities.searchParamIsNotEmpty(dto.getParentIdParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("parentId", dto.getParentId(), "e.fonctionnalite.id", "Integer", dto.getParentIdParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getFonctionnaliteCode()) || Utilities.searchParamIsNotEmpty(dto.getFonctionnaliteCodeParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("fonctionnaliteCode", dto.getFonctionnaliteCode(), "e.fonctionnalite.code", "String", dto.getFonctionnaliteCodeParam(), param, index, locale));
            }
            if (Utilities.isNotBlank(dto.getFonctionnaliteLibelle()) || Utilities.searchParamIsNotEmpty(dto.getFonctionnaliteLibelleParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("fonctionnaliteLibelle", dto.getFonctionnaliteLibelle(), "e.fonctionnalite.libelle", "String", dto.getFonctionnaliteLibelleParam(), param, index, locale));
            }

            /*List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
            if (Utilities.isNotEmpty(listOfCustomQuery)) {
                listOfQuery.addAll(listOfCustomQuery);
            }*/
        }
        return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
    }
}
