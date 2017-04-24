package com.cgi.nikoniko.controllers;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cgi.nikoniko.controllers.PathClass.PathFinder;
import com.cgi.nikoniko.dao.INikoNikoCrudRepository;
import com.cgi.nikoniko.dao.IRoleCrudRepository;
import com.cgi.nikoniko.dao.IUserCrudRepository;
import com.cgi.nikoniko.dao.IUserHasRoleCrudRepository;
import com.cgi.nikoniko.utils.UtilsFunctions;

@Controller
public class MenuController  {

	public LocalDate TODAY_DATE = new LocalDate();

	public final static String BASE_URL = PathFinder.PATH + PathFinder.MENU_PATH;

	@Autowired
	IUserCrudRepository userCrud;

	@Autowired
	IUserHasRoleCrudRepository userRoleCrud;

	@Autowired
	IRoleCrudRepository roleCrud;

	@Autowired
	INikoNikoCrudRepository nikoCrud;

	/**
	 * SHOW MENU
	 * @param model
	 * @param login
	 * @return
	 */
	@Secured({"ROLE_ADMIN","ROLE_GESTIONNAIRE","ROLE_VP","ROLE_USER"})
	@RequestMapping(path = PathFinder.PATH + PathFinder.MENU_PATH, method = RequestMethod.GET)
	public String index(Model model, String login) {

		Long idUser = UtilsFunctions.getUserInformations(userCrud).getId();

		model.addAttribute("page","MENU");

		model.addAttribute("lastNiko",UtilsFunctions.getSTLNikoNikoMood(idUser, userCrud, nikoCrud));
		model.addAttribute("status", UtilsFunctions.checkDateNikoNiko(idUser, userCrud, nikoCrud));
		model.addAttribute("mood", UtilsFunctions.getUserLastMood(idUser, userCrud, nikoCrud));

		model.addAttribute("roles",UtilsFunctions.getConnectUserRole(userCrud, userRoleCrud, roleCrud));
		model.addAttribute("auth",UtilsFunctions.getUserInformations(userCrud).getFirstname());
		model.addAttribute("go_own_nikoniko", PathFinder.PATH + "user" + PathFinder.PATH + idUser + PathFinder.PATH + "link");
		model.addAttribute("add_nikoniko", PathFinder.PATH + "user" + PathFinder.PATH + idUser + PathFinder.PATH + "add");
		model.addAttribute("pie_chart", PathFinder.PATH + "graph" + PathFinder.PATH + PathFinder.SHOW_GRAPH);

		model.addAttribute("go_users", PathFinder.GO_USERS);
		model.addAttribute("go_nikonikos", PathFinder.GO_NIKOS);
		model.addAttribute("go_teams", PathFinder.GO_TEAMS);
		model.addAttribute("go_roles", PathFinder.GO_ROLES);
		model.addAttribute("go_functions", PathFinder.GO_FUNCTIONS);
		model.addAttribute("go_verticales", PathFinder.GO_VERTICALE);
		model.addAttribute("go_graphes", PathFinder.GO_GRAPHE);
		model.addAttribute("myCalendar", PathFinder.GO_CALENDAR);
		model.addAttribute(	"myVertCalendar", "/graph/nikonikovert/"
							+ UtilsFunctions.getUserInformations(userCrud).getVerticale().getId()
							+ "/month");

		model.addAttribute("go_user_has_team", PathFinder.GO_USERTEAM);
		model.addAttribute("go_user_has_role", PathFinder.GO_USERROLE);
		model.addAttribute("go_role_has_function", PathFinder.GO_ROLEFUNC);

		model.addAttribute("add_last", PathFinder.PATH + "user" + PathFinder.PATH + idUser + PathFinder.PATH + "addLast");

		model.addAttribute("id",idUser);


		return PathFinder.MENU_PATH + PathFinder.PATH + "mainMenu";
	}

}