
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
 * Repository customize : Statut.
 *
 * @author Geo
 *
 */
@Repository
public interface _StatutRepository {
	    /**
     * Finds Statut by using id as a search criteria.
     *
     * @param id
     * @return An Object Statut whose id is equals to the given id. If
     *         no Statut is found, this method returns null.
     */
    @Query("select e from Statut e where e.id = :id and e.isDeleted = :isDeleted")
    Statut findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

    /**
     * Finds Statut by using code as a search criteria.
     *
     * @param code
     * @return An Object Statut whose code is equals to the given code. If
     *         no Statut is found, this method returns null.
     */
    @Query("select e from Statut e where e.code = :code and e.isDeleted = :isDeleted")
    Statut findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Statut by using createdAt as a search criteria.
     *
     * @param createdAt
     * @return An Object Statut whose createdAt is equals to the given createdAt. If
     *         no Statut is found, this method returns null.
     */
    @Query("select e from Statut e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
    List<Statut> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Statut by using createdBy as a search criteria.
     *
     * @param createdBy
     * @return An Object Statut whose createdBy is equals to the given createdBy. If
     *         no Statut is found, this method returns null.
     */
    @Query("select e from Statut e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
    List<Statut> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Statut by using deletedAt as a search criteria.
     *
     * @param deletedAt
     * @return An Object Statut whose deletedAt is equals to the given deletedAt. If
     *         no Statut is found, this method returns null.
     */
    @Query("select e from Statut e where e.deletedAt = :deletedAt and e.isDeleted = :isDeleted")
    List<Statut> findByDeletedAt(@Param("deletedAt")Date deletedAt, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Statut by using isDeleted as a search criteria.
     *
     * @param isDeleted
     * @return An Object Statut whose isDeleted is equals to the given isDeleted. If
     *         no Statut is found, this method returns null.
     */
    @Query("select e from Statut e where e.isDeleted = :isDeleted")
    List<Statut> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Statut by using libelle as a search criteria.
     *
     * @param libelle
     * @return An Object Statut whose libelle is equals to the given libelle. If
     *         no Statut is found, this method returns null.
     */
    @Query("select e from Statut e where e.libelle = :libelle and e.isDeleted = :isDeleted")
    Statut findByLibelle(@Param("libelle")String libelle, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Statut by using updatedAt as a search criteria.
     *
     * @param updatedAt
     * @return An Object Statut whose updatedAt is equals to the given updatedAt. If
     *         no Statut is found, this method returns null.
     */
    @Query("select e from Statut e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
    List<Statut> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
    /**
     * Finds Statut by using updatedBy as a search criteria.
     *
     * @param updatedBy
     * @return An Object Statut whose updatedBy is equals to the given updatedBy. If
     *         no Statut is found, this method returns null.
     */
    @Query("select e from Statut e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
    List<Statut> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);



    /**
     * Finds List of Statut by using statutDto as a search criteria.
     *
     * @param request, em
     * @return A List of Statut
     * @throws DataAccessException,ParseException
     */
    public default List<Statut> getByCriteria(Request<StatutDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
        String req = "select e from Statut e where e IS NOT NULL";
        HashMap<String, Object> param = new HashMap<String, Object>();
        req += getWhereExpression(request, param, locale);
                TypedQuery<Statut> query = em.createQuery(req, Statut.class);
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
     * Finds count of Statut by using statutDto as a search criteria.
     *
     * @param request, em
     * @return Number of Statut
     *
     */
    public default Long count(Request<StatutDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
        String req = "select count(e.id) from Statut e where e IS NOT NULL";
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
    default String getWhereExpression(Request<StatutDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
        // main query
        StatutDto dto = request.getData() != null ? request.getData() : new StatutDto();
        dto.setIsDeleted(false);
        String mainReq = generateCriteria(dto, param, 0, locale);
        // others query
        String othersReq = "";
        if (request.getDatas() != null && !request.getDatas().isEmpty()) {
            Integer index = 1;
            for (StatutDto elt : request.getDatas()) {
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
    default String generateCriteria(StatutDto dto, HashMap<String, Object> param, Integer index,  Locale locale) throws Exception{
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
            if (Utilities.isNotBlank(dto.getDeletedAt()) || Utilities.searchParamIsNotEmpty(dto.getDeletedAtParam())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("deletedAt", dto.getDeletedAt(), "e.deletedAt", "Date", dto.getDeletedAtParam(), param, index, locale));
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

            /*List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
            if (Utilities.isNotEmpty(listOfCustomQuery)) {
                listOfQuery.addAll(listOfCustomQuery);
            }*/
        }
        return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
    }
}
