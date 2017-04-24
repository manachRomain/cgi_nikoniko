package com.cgi.nikoniko.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cgi.nikoniko.dao.base.IBaseCrudRepository;
import com.cgi.nikoniko.models.tables.Verticale;

public interface IVerticaleCrudRepository extends IBaseCrudRepository<Verticale>{

	@Query(value = "SELECT nikoniko.id FROM nikoniko INNER JOIN user ON nikoniko.user_id = user.id "
			+ "INNER JOIN verticale ON user.verticale_id = verticale.id WHERE verticale.id = :idVerticale",nativeQuery=true)
	public List<BigInteger> getNikoNikoFromVerticale(@Param("idVerticale") long idVerticale);

	/**
	 * FIND VERTICALE BY HIS NAME
	 * @param login
	 * @return
	 */
	List<Verticale> findAllByName(String name);

	/**
	 * SELECT ALL VERTICALES WHERE VERTICALE'S NAME APPROX NAME PARAMETER
	 * @param name
	 * @return
	 */
	@Query(value = "SELECT * FROM verticale WHERE name LIKE %:name%", nativeQuery=true)
	public ArrayList<Verticale> getVerticales(@Param("name") String name);

}
