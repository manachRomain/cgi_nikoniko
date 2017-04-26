package com.cgi.nikoniko.dao;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cgi.nikoniko.dao.base.IBaseCrudRepository;
import com.cgi.nikoniko.models.tables.NikoNiko;
import com.cgi.nikoniko.models.tables.User;

public interface INikoNikoCrudRepository extends IBaseCrudRepository<NikoNiko> {

	/**
	 * FIND ALL NIKONIKO WITH SAME MOOD
	 * 
	 * @param mood
	 *            (range from 1 to 3)
	 * @return
	 */
	List<NikoNiko> findAllByMood(int mood);

	/**
	 * FIND USER WHERE NAME ENTER IN PARAMETER SOUNDS LIKE HIS REGISTRATION
	 * 
	 * @param name
	 * @return
	 */
	@Query(value = "SELECT * FROM user WHERE registration_number LIKE %:name%", nativeQuery = true)
	public ArrayList<User> getUsers(@Param("name") String name);

	/**
	 * FIND NIKONIKOS WHERE REGISTRATION USER SOUNDS LIKE NAME ENTER IN
	 * PARAMETER
	 * 
	 * @param name
	 * @return
	 */
	@Query(value = "SELECT * FROM nikoniko INNER JOIN user on nikoniko.user_id = user.id WHERE user.registration_number LIKE %:name%", nativeQuery = true)
	public ArrayList<NikoNiko> getNikoNiko(@Param("name") String name);

	/**
	 * GET NIKONIKO OF A PRECISE DATE
	 * 
	 * @param currentDate
	 * @param idUser
	 * @return
	 */
	@Query(value = "SELECT * FROM nikoniko WHERE entry_date LIKE %:currentDate% AND user_id = :idUser", nativeQuery = true)
	public NikoNiko getNikoByDate(@Param("currentDate") LocalDate currentDate, @Param("idUser") long idUser);

	/**
	 * GET ALL NIKONIKOS OF A TEAM WITH A PRECISE DATE AND MOOD NOT EQUALS TO 0
	 * @param date
	 * @param idTeam
	 * @return
	 */
	@Query(value = "SELECT * FROM nikoniko INNER JOIN user ON user_id = user.id INNER JOIN user_has_team ON user.id=idLeft WHERE entry_date like %:date%  AND idRight = :idTeam AND mood != 0", nativeQuery = true)
	public List<NikoNiko> getNikoNikosOfATeamWithPreciseDate(
			@Param("date") LocalDate date, @Param("idTeam") long idTeam);
	
	/**
	 * GET ALL NIKONIKOS OF A VERTICALE (EXCLUDE DATE)
	 * @param idVert
	 * @return
	 */
	@Query(value ="SELECT nikoniko.* FROM verticale INNER JOIN user ON  verticale.id = user.verticale_id INNER JOIN nikoniko ON user.id = nikoniko.user_id WHERE verticale.id = :idVert", nativeQuery = true)
	public List<NikoNiko> getAllNikoNikosOfAVerticale(@Param("idVert") long idVert);

	/**
	 * GET ALL NIKONIKOS OF A VERTICALE (INCLUDE PRECISE  DATE)
	 * @param idVert
	 * @param date
	 * @return
	 */
	@Query(value ="SELECT nikoniko.* FROM verticale INNER JOIN user ON  verticale.id = user.verticale_id INNER JOIN nikoniko ON user.id = nikoniko.user_id WHERE verticale.id = :idVert AND entry_date LIKE %:date%", nativeQuery = true)
	public List<NikoNiko> getAllNikoNikosOfVerticaleWithPreciseDate(@Param("idVert") long idVert, @Param("date") LocalDate date);

	/**
	 * GET ALL NIKONIKO OF A PRECISE DATE
	 * @param date
	 * @return
	 */
	@Query(value="SELECT * FROM nikoniko WHERE entry_date LIKE %:date%", nativeQuery=true)
	public List<NikoNiko> getAllNikoNikosOfAPreciseDate(@Param("date") LocalDate date);

	/**
	 * GET ALL NIKONIKOS OF A TEAM
	 * @param idTeam
	 * @return
	 */
	@Query(value ="SELECT nikoniko.* FROM team INNER JOIN user_has_team ON team.id = user_has_team.idRight INNER JOIN user ON user_has_team.idLeft = user.id INNER JOIN nikoniko ON user.id= nikoniko.user_id WHERE team.id = :idTeam", nativeQuery= true)
	public ArrayList<NikoNiko> getAllNikoNikosOfATeam(@Param("idTeam") long idTeam);

	/**
	 * FIND ALL NIKONIKOS OF A USER WITH PRECISE (DIFF OF getNikoByDate(), USE ARRAYLIST)
	 * PARAMETER
	 * 
	 * @param name
	 * @return
	 */
	@Query(value = "SELECT * FROM nikoniko INNER JOIN user on nikoniko.user_id = user.id WHERE user.id= :idUser AND nikoniko.entry_date LIKE %:date%", nativeQuery = true)
	public ArrayList<NikoNiko> getNikoNikoOfUserWithPreciseDate(@Param("idUser") long idUser, @Param("date") LocalDate date);

}
