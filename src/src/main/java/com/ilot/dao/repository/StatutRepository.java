

package com.ilot.dao.repository;

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
import com.ilot.dao.repository.base._StatutRepository;

/**
 * Repository : Statut.
 *
 * @author Geo
 */
@Repository
public interface StatutRepository extends JpaRepository<Statut, Integer>, _StatutRepository {

}
