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
	 * GET ENTRY DATE OF THE LAST NIKON ENTER BY A USER
	 * 
	 * @param lastDay
	 * @param idUser
	 * @return
	 */
	@Query(value = "SELECT * FROM nikoniko WHERE entry_date LIKE %:lastDay% AND user_id = :idUser", nativeQuery = true)
	public NikoNiko getNikoDate(@Param("lastDay") LocalDate lastDay,
			@Param("idUser") long idUser);

	/**
	 * GET NIKONIKO OF THE CURRENT DAY
	 * 
	 * @param today
	 * @param idUser
	 * @return
	 */
	@Query(value = "SELECT * FROM nikoniko WHERE entry_date LIKE %:today% AND user_id = :idUser", nativeQuery = true)
	public NikoNiko getTodayNikoNiko(@Param("today") LocalDate today,
			@Param("idUser") long idUser);

	/**
	 * GET NIKONIKO OF A PRECISE DATE
	 * 
	 * @param currentDate
	 * @param idUser
	 * @return
	 */
	@Query(value = "SELECT * FROM nikoniko WHERE entry_date LIKE %:currentDate% AND user_id = :idUser", nativeQuery = true)
	public NikoNiko getNikoByDate(@Param("currentDate") LocalDate currentDate,
			@Param("idUser") long idUser);

	@Query(value = "SELECT * FROM nikoniko INNER JOIN user ON user_id = user.id INNER JOIN user_has_team ON user.id=idLeft WHERE entry_date like %:date%  AND idRight = :idTeam AND mood != 0", nativeQuery = true)
	public List<NikoNiko> getNikoNikosOfATeamWithPreciseDate(
			@Param("date") LocalDate date, @Param("idTeam") long idTeam);

}
