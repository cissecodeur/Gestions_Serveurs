package com.Gestion_Serveurs.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Gestion_Serveurs.entities.Server;

/**
 * Repository : Server.
 */
@Repository
public interface ServerRepository extends JpaRepository<Server, Integer>{
	/**
	 * Finds Server by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Server whose id is equals to the given id. If
	 *         no Server is found, this method returns null.
	 */
	
//	@Query("select e from Server e where e.isDeleted = :isDeleted")
//	List<Server >findByAll(@Param("isDeleted")Boolean isDeleted);
//	
//	@Query("select e from Server e where e.id = :id ")
//	Optional<Server> findById(@Param("id")Integer id);
////	
//	@Query("select e from Server e where e.statut.id = :statusId ")
//	Server findByStatusId(@Param("statusId")Integer statusId);
//	/**
	
	
//	@Query("select s from Server s where s.libelle like: mc and s.isDeleted = :isDeleted")
//	public Page<Server> Rechercher(@Param("mc") String mc ,  @Param("isDeleted")Boolean isDeleted,Pageable pageable);
//	
//	
//	@Query("select e from Server e where e.ip = :ip")
//	public  Server findByServerIp(@Param ("ip") String ip, @Param("isDeleted")Boolean isDeleted);
//
//	/**
//	 * Finds Server by using libelle as a search criteria.
//	 * 
//	 * @param libelle
//	 * @return An Object Server whose libelle is equals to the given libelle. If
//	 *         no Server is found, this method returns null.
//	 */
	@Query("select e from Server e where e.libelle = :libelle")
	Server findByLibelle(@Param("libelle")String libelle);
//	
//	
//	
//	@Query("select e from Server e where e.infoSupServer = :infoSupServer and e.isDeleted = :isDeleted")
//	List<Server> findByInfoSup(@Param("infoSupServer")String infoSupServer, @Param("isDeleted")Boolean isDeleted);
//	/**
//	 * Finds Server by using isDeleted as a search criteria.
//	 * 
//	 * @param isDeleted
//	 * @return An Object Server whose isDeleted is equals to the given isDeleted. If
//	 *         no Server is found, this method returns null.
//	 */
//	@Query("select e from Server e where e.isDeleted = :isDeleted")
//	List<Server> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
//	
//	
//	@Query("select e.libelle from Server e where e.isDeleted = :isDeleted")
//	ArrayList<String> findByString(@Param("isDeleted")Boolean isDeleted);
//	/**
//	 * Finds Server by using createdAt as a search criteria.
//	 * 
//	 * @param createdAt
//	 * @return An Object Server whose createdAt is equals to the given createdAt. If
//	 *         no Server is found, this method returns null.
//	 */
//	@Query("select e from Server e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
//	List<Server> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
//	/**
//	 * Finds Server by using createdBy as a search criteria.
//	 * 
//	 * @param createdBy
//	 * @return An Object Server whose createdBy is equals to the given createdBy. If
//	 *         no Server is found, this method returns null.
//	 */
//	@Query("select e from Server e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
//	List<Server> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
//	/**
//	 * Finds Server by using deleteAt as a search criteria.
//	 * 
//	 * @param deleteAt
//	 * @return An Object Server whose deleteAt is equals to the given deleteAt. If
//	 *         no Server is found, this method returns null.
//	 */
//	@Query("select e from Server e where e.deleteAt = :deleteAt and e.isDeleted = :isDeleted")
//	List<Server> findByDeleteAt(@Param("deleteAt")Date deleteAt, @Param("isDeleted")Boolean isDeleted);
//	/**
//	 * Finds Server by using deleteBy as a search criteria.
//	 * 
//	 * @param deleteBy
//	 * @return An Object Server whose deleteBy is equals to the given deleteBy. If
//	 *         no Server is found, this method returns null.
//	 */
//	@Query("select e from Server e where e.deleteBy = :deleteBy and e.isDeleted = :isDeleted")
//	List<Server> findByDeleteBy(@Param("deleteBy")Integer deleteBy, @Param("isDeleted")Boolean isDeleted);
//	/**
//	 * Finds Server by using updateAt as a search criteria.
//	 * 
//	 * @param updateAt
//	 * @return An Object Server whose updateAt is equals to the given updateAt. If
//	 *         no Server is found, this method returns null.
//	 */
//	@Query("select e from Server e where e.updateAt = :updateAt and e.isDeleted = :isDeleted")
//	List<Server> findByUpdateAt(@Param("updateAt")Date updateAt, @Param("isDeleted")Boolean isDeleted);
//	/**
//	 * Finds Server by using updateBy as a search criteria.
//	 * 
//	 * @param updateBy
//	 * @return An Object Server whose updateBy is equals to the given updateBy. If
//	 *         no Server is found, this method returns null.
//	 */
//	@Query("select e from Server e where e.updateBy = :updateBy and e.isDeleted = :isDeleted")
//	List<Server> findByUpdateBy(@Param("updateBy")Integer updateBy, @Param("isDeleted")Boolean isDeleted);
//
//	/**
//	 * Finds List of Server by using serverDto as a search criteria.
//	 * 
//	 * @param request, em
//	 * @return A List of Server
//	 * @throws DataAccessException,ParseException
//	 */
//	public default List<Server> getByCriteria(Request<ServerDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
//		String req = "select e from Server e where e IS NOT NULL";
//		HashMap<String, Object> param = new HashMap<>();
//		req += getWhereExpression(request, param, locale);
//		req += " order by e.id asc";
//		TypedQuery<Server> query = em.createQuery(req, Server.class);
//		for (Map.Entry<String, Object> entry : param.entrySet()) {
//			query.setParameter(entry.getKey(), entry.getValue());
//		}
//		if (request.getIndex() != null && request.getSize() != null) {
//			query.setFirstResult(request.getIndex() * request.getSize());
//			query.setMaxResults(request.getSize());
//		}
//		return query.getResultList();
//	}
//
//	/**
//	 * Finds count of Server by using serverDto as a search criteria.
//	 * 
//	 * @param request, em
//	 * @return Number of Server
//	 * 
//	 */
//	public default Long count(Request<ServerDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
//		String req = "select count(e.id) from Server e where e IS NOT NULL";
//		HashMap<String, Object> param = new HashMap<>();
//		req += getWhereExpression(request, param, locale);
//		req += " order by  e.id asc";
//		javax.persistence.Query query = em.createQuery(req);
//		for (Map.Entry<String, Object> entry : param.entrySet()) {
//			query.setParameter(entry.getKey(), entry.getValue());
//		}
//		Long count = (Long) query.getResultList().get(0);
//		return count;
//	}
//
//	/**
//	 * get where expression
//	 * @param request
//	 * @param param
//	 * @param locale
//	 * @return
//	 * @throws Exception
//	 */
//	default String getWhereExpression(Request<ServerDto> request, HashMap<String, Object> param, Locale locale) throws Exception {
//		// main query
//		ServerDto dto = request.getData() != null ? request.getData() : new ServerDto();
//		dto.setIsDeleted(false);
//		String mainReq = generateCriteria(dto, param, 0, locale);
//		// others query
//		String othersReq = "";
//		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
//			Integer index = 1;
//			for (ServerDto elt : request.getDatas()) {
//				elt.setIsDeleted(false);
//				String eltReq = generateCriteria(elt, param, index, locale);
//				if (request.getIsAnd() != null && request.getIsAnd()) {
//					othersReq += "and (" + eltReq + ") ";
//				} else {
//					othersReq += "or (" + eltReq + ") ";
//				}
//				index++;
//			}
//		}
//		String req = "";
//		if (!mainReq.isEmpty()) {
//			req += " and (" + mainReq + ") ";
//		}
//		req += othersReq;
//		return req;
//	}
//
//	/**
//	 * generate sql query for dto
//	 * @param dto
//	 * @param req
//	 * @param param
//	 * @param locale
//	 * @return
//	 * @throws Exception
//	 */
//	default String generateCriteria(ServerDto dto, HashMap<String, Object> param, Integer index,  Locale locale) throws Exception{
//		List<String> listOfQuery = new ArrayList<String>();
//		if (dto != null) {
//			if (dto.getId()!= null && dto.getId() > 0) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
//			}
//			if (dto.getLibelle()!= null && !dto.getLibelle().isEmpty()) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("libelle", dto.getLibelle(), "e.libelle", "String", dto.getLibelleParam(), param, index, locale));
//			}
//			if (dto.getIsDeleted()!= null) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
//			}
//			if (dto.getCreatedAt()!= null && !dto.getCreatedAt().isEmpty()) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
//			}
//			if (dto.getCreatedBy()!= null && dto.getCreatedBy() > 0) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
//			}
//			if (dto.getDeleteAt()!= null && !dto.getDeleteAt().isEmpty()) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("deleteAt", dto.getDeleteAt(), "e.deleteAt", "Date", dto.getDeleteAtParam(), param, index, locale));
//			}
//			if (dto.getDeleteBy()!= null && dto.getDeleteBy() > 0) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("deleteBy", dto.getDeleteBy(), "e.deleteBy", "Integer", dto.getDeleteByParam(), param, index, locale));
//			}
//			if (dto.getUpdateAt()!= null && !dto.getUpdateAt().isEmpty()) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("updateAt", dto.getUpdateAt(), "e.updateAt", "Date", dto.getUpdateAtParam(), param, index, locale));
//			}
//			if (dto.getUpdateBy()!= null && dto.getUpdateBy() > 0) {
//				listOfQuery.add(CriteriaUtils.generateCriteria("updateBy", dto.getUpdateBy(), "e.updateBy", "Integer", dto.getUpdateByParam(), param, index, locale));
//			}
//		}
//		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
//	}
}
