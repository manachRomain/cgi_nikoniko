package com.cgi.nikoniko.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cgi.nikoniko.controllers.PathClass.PathFinder;
import com.cgi.nikoniko.dao.INikoNikoCrudRepository;
import com.cgi.nikoniko.dao.IRoleCrudRepository;
import com.cgi.nikoniko.dao.ITeamCrudRepository;
import com.cgi.nikoniko.dao.IUserCrudRepository;
import com.cgi.nikoniko.dao.IUserHasRoleCrudRepository;
import com.cgi.nikoniko.dao.IVerticaleCrudRepository;
import com.cgi.nikoniko.models.tables.NikoNiko;
import com.cgi.nikoniko.models.tables.RoleCGI;
import com.cgi.nikoniko.models.tables.Team;
import com.cgi.nikoniko.models.tables.User;
import com.cgi.nikoniko.models.tables.Verticale;

public abstract class UtilsFunctions {
	
	
public static LocalDate TODAY_DATE = new LocalDate();
	
	
/////////////////// UTILS FOR THE VOTE /////////////////////////////////
	
	
	/**
	 * FUNCTION FOR UPDATE THE PREVIOUS NIKONIKO VOTE BY USER
	 * @param idUser
	 * @param mood
	 * @param comment
	 * @return
	 */
	public static String updateLastNikoNiko(Long idUser, Integer mood, String comment, INikoNikoCrudRepository nikoCrud, IUserCrudRepository userCrud){
		
		if (UtilsFunctions.getSTLNikoNikoMood(idUser, userCrud, nikoCrud) == false) {
			
			return PathFinder.REDIRECT + PathFinder.PATH + PathFinder.MENU_PATH;
		}

		if (mood == null) {
			
			return PathFinder.REDIRECT + PathFinder.PATH + PathFinder.MENU_PATH;
		}

		else {
			
			NikoNiko niko = UtilsFunctions.getLastLastNikoNiko(idUser, userCrud, nikoCrud);
			
			niko.setMood(mood);
			niko.setComment(comment);
			
			nikoCrud.save(niko);
			
			return PathFinder.REDIRECT + PathFinder.PATH + PathFinder.MENU_PATH;

		}
	}
	
	/**
	 * FUNCTION THAT CHECK NIKONIKO DATE FOR UPDATE OR NEW. TRUE : UPDATE NIKONIKO, FALSE : NEW NIKONIKO
	 * @param idUser
	 * @return
	 */
	public static Boolean checkDateNikoNiko(Long idUser, IUserCrudRepository userCrud, INikoNikoCrudRepository nikoCrud){

		Boolean updateNiko = true;

		Long idMaxNiko = userCrud.getLastNikoNikoUser(idUser);

		if (idMaxNiko == null) {

			updateNiko = false;
		}

		else {

			NikoNiko lastNiko = nikoCrud.findOne(idMaxNiko);

			Date entryDate = lastNiko.getEntryDate();
			LocalDate dateEntry = new LocalDate(entryDate);

			if (entryDate == null || (TODAY_DATE.isAfter(dateEntry))) {
				updateNiko = false;
			}
		}

		return updateNiko;
	}

	/**
	 * GET LAST MOOD ENTER BY USER
	 * @param idUser
	 * @return
	 */
	public static int getUserLastMood(Long idUser, IUserCrudRepository userCrud, INikoNikoCrudRepository nikoCrud){

		int mood = 0;

		Long idMax = userCrud.getLastNikoNikoUser(idUser);

		if (idMax == null) {
			return mood;
		}

		else {
			mood = nikoCrud.findOne(idMax).getMood();
			return mood;
		}
	}

	/**
	 * GET SECOND TO LAST NIKONIKO USER AND CHECK IF THE MOOD IS NULL OR NOT.FALSE : CAN'T UPDATE, TRUE : UPDATE SECOND LAST
	 * @param idUser
	 * @param userCrud
	 * @return
	 */
	public static Boolean getSTLNikoNikoMood(Long idUser, IUserCrudRepository userCrud, INikoNikoCrudRepository nikoCrud){
		
		LocalDate lastDay = TODAY_DATE.minusDays(1);

		NikoNiko niko = nikoCrud.getNikoDate(lastDay, idUser);
		
		if (niko == null) {
			
			return false;
		}
		
		else{
			
			if (niko.getMood() != 0) {
				
				return false;
			}
			
			else {
				
				return true;
			}
		}
	}
	
	/**
	 * GET SECOND TO LAST NIKONIKO IF EXIST AND MOOD != 0
	 * @param idUser
	 * @param userCrud
	 * @param nikoCrud
	 * @return
	 */
	public static NikoNiko getLastLastNikoNiko(Long idUser, IUserCrudRepository userCrud, INikoNikoCrudRepository nikoCrud){
		
		if (UtilsFunctions.getSTLNikoNikoMood(idUser, userCrud, nikoCrud) == true) {
			
			LocalDate lastDay = TODAY_DATE.minusDays(1);
			
			NikoNiko niko = nikoCrud.getNikoDate(lastDay, idUser);
			
			return niko;	
		}
		
		return null;	
	}
	
	
/////////////////// UTILS TO SEARCH /////////////////////////////////
	
	
	/**
	 * FIND A SPECIFIC MODEL
	 * @param models
	 * @param name
	 * @param userCrud
	 * @param teamCrud
	 * @param verticaleCrud
	 * @return
	 */
	public static <T> ArrayList<T> findAny(String modelName, String name, IUserCrudRepository userCrud,ITeamCrudRepository teamCrud, IVerticaleCrudRepository verticaleCrud, INikoNikoCrudRepository nikoCrud){
		
		switch (modelName) {
		case PathFinder.TEAM_NAME:
			ArrayList<Team> teamList = new ArrayList<Team>();
			teamList = teamCrud.getTeams(name);
			return (ArrayList<T>) teamList;
			
		case PathFinder.USER_NAME:
			ArrayList<User> userList = new ArrayList<User>();
			userList = userCrud.getUsers(name);
			return (ArrayList<T>) userList;
			
		case PathFinder.VERTICALE_NAME:
			ArrayList<Verticale> verticaleList = new ArrayList<Verticale>();
			verticaleList = verticaleCrud.getVerticales(name);
			return (ArrayList<T>) verticaleList;
		case PathFinder.NIKO_NAME:
			
			ArrayList<NikoNiko> nikoList = new ArrayList<NikoNiko>();
			nikoList = nikoCrud.getNikoNiko(name);
			return (ArrayList<T>) nikoList;
		}
		return null;
		
	}
	
	
/////////////////// UTILS TO GET ROLES /////////////////////////////////

	
	/**
	 * HAVE ALL ROLES ASSOCIATED TO A USER
	 * @param idUser
	 * @return
	 */
	public static ArrayList<RoleCGI> setRolesForUserGet(Long idUser, IUserHasRoleCrudRepository userRoleCrud, IRoleCrudRepository roleCrud) {

		List<Long> ids = new ArrayList<Long>();
		ArrayList<RoleCGI> roleList = new ArrayList<RoleCGI>();

		List<BigInteger> idsBig = userRoleCrud.findAssociatedRole(idUser);

		if (!idsBig.isEmpty()) {
			for (BigInteger id : idsBig) {
				ids.add(id.longValue());

			}
			roleList = (ArrayList<RoleCGI>) roleCrud.findAll(ids);
		}

		return roleList;
	}
	
	/**
	 * RETURN THE HIGHEST ROLE FROM A USER
	 * @return
	 */
	public static String getConnectUserRole(IUserCrudRepository userCrud, IUserHasRoleCrudRepository userRoleCrud, IRoleCrudRepository roleCrud){

		String login = "";
		String role = "";
		User user = new User();
		ArrayList<RoleCGI> roleList = new ArrayList<RoleCGI>();
		ArrayList<String> roleNames = new ArrayList<String>();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		login = auth.getName();
		user = userCrud.findByLogin(login);
		roleList = UtilsFunctions.setRolesForUserGet(user.getId(), userRoleCrud, roleCrud);
		roleNames = UtilsFunctions.convertObjectToString(roleList);

		if (roleNames.contains("ROLE_ADMIN")) {
			role = "admin";
		}
		else if (roleNames.contains("ROLE_VP")) {
			role = "vp";
		}
		else if (roleNames.contains("ROLE_CHEF_PROJET")) {
			role = "chefProjet";
		}
		else if (roleNames.contains("ROLE_GESTIONNAIRE")) {
			role = "gestionTeam";
		}
		else {
			role = "employee";
		}

		return role;
	}
	
	/**
	 * GET USER ROLE
	 * @param user
	 * @param userRoleCrud
	 * @param roleCrud
	 * @return
	 */
	public static String getRoleForUser(User user, IUserHasRoleCrudRepository userRoleCrud, IRoleCrudRepository roleCrud){
		
		String role = "";
		
		ArrayList<RoleCGI> roleList = new ArrayList<RoleCGI>();
		ArrayList<String> roleNames = new ArrayList<String>();
		
		roleList = UtilsFunctions.setRolesForUserGet(user.getId(), userRoleCrud, roleCrud);
		roleNames = UtilsFunctions.convertObjectToString(roleList);
		
		if (roleNames.contains("ROLE_ADMIN")) {
			role = "admin";
		}
		else if (roleNames.contains("ROLE_VP")) {
			role = "vp";
		}
		else if (roleNames.contains("ROLE_CHEF_PROJET")) {
			role = "chefProjet";
		}
		else if (roleNames.contains("ROLE_GESTIONNAIRE")) {
			role = "gestionTeam";
		}
		else {
			role = "employee";
		}

		return role;
		
	}
	
	/**
	 * GET ROLE NAME FROM ROLE OBJECT
	 * @param roleList
	 * @return
	 */
	public static ArrayList<String> convertObjectToString(ArrayList<RoleCGI> roleList){

		ArrayList<String> roleNames = new ArrayList<String>();
		for (int i = 0; i <roleList.size(); i++) {
			roleNames.add(roleList.get(i).getName());
		}
		return roleNames;
	}
	
/////////////////// UTILS TO GET USER'S INFORMATION /////////////////////////////////

	
	/**
	 * RETURN USER FROM AUTHENTIFICATION
	 * @return
	 */
	public static User getUserInformations(IUserCrudRepository userCrud){

		String login = "";
		User user = new User();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		login = auth.getName();
		user = userCrud.findByLogin(login);

		return user;
	}
}
