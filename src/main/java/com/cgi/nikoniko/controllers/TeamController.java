package com.cgi.nikoniko.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cgi.nikoniko.controllers.PathClass.PathFinder;
import com.cgi.nikoniko.controllers.base.view.ViewBaseController;
import com.cgi.nikoniko.dao.INikoNikoCrudRepository;
import com.cgi.nikoniko.dao.ITeamCrudRepository;
import com.cgi.nikoniko.dao.IUserCrudRepository;
import com.cgi.nikoniko.dao.IUserHasTeamCrudRepository;
import com.cgi.nikoniko.dao.IVerticaleCrudRepository;
import com.cgi.nikoniko.models.association.UserHasTeam;
import com.cgi.nikoniko.models.tables.NikoNiko;
import com.cgi.nikoniko.models.tables.Team;
import com.cgi.nikoniko.models.tables.User;
import com.cgi.nikoniko.models.tables.Verticale;
import com.cgi.nikoniko.models.association.base.AssociationItemId;
import com.cgi.nikoniko.utils.DumpFields;
import com.cgi.nikoniko.utils.UtilsFunctions;

@Controller
@RequestMapping(TeamController.BASE_URL)
public class TeamController extends ViewBaseController<Team> {

/////////////////// GLOBAL CONSTANT /////////////////////////////////

	public final static String BASE_TEAM = "team";
	public final static String BASE_URL = PathFinder.PATH + BASE_TEAM;
	
/////////////////// ALL CRUD /////////////////////////////////

	@Autowired
	IUserHasTeamCrudRepository userTeamCrud;

	@Autowired
	IUserCrudRepository userCrud;

	@Autowired
	ITeamCrudRepository teamCrud;

	@Autowired
	INikoNikoCrudRepository nikoCrud;

	@Autowired
	IVerticaleCrudRepository verticaleCrud;


/////////////////// FUNCTIONS RELATED WITH TEAM ONLY /////////////////////////////////

	/**
	 * SHOW ALL TEAM WITH A GIVEN NAME (NAME PARAMETER)
	 * @param model
	 * @param name
	 * @return
	 */
	@Secured({"ROLE_ADMIN","ROLE_GESTIONNAIRE"})
    @RequestMapping(path = {PathFinder.PATH, PathFinder.ROUTE_LIST}, method = RequestMethod.POST)
    public String showTeams(Model model,String name){

        model.addAttribute("model", "team");
        model.addAttribute("page",this.baseName);
        model.addAttribute("sortedFields",Team.FIELDS);
        model.addAttribute("items",DumpFields.listFielder(teamCrud.getTeams(name)));
        model.addAttribute("go_show", PathFinder.SHOW_ACTION);
        model.addAttribute("go_create", PathFinder.CREATE_ACTION);
        model.addAttribute("go_delete", PathFinder.DELETE_ACTION);

        return listView;
    }

	/**
	 *
	 * SHOW A TEAM WITH A GIVEN ID
	 */
	@Secured({"ROLE_ADMIN","ROLE_GESTIONNAIRE","ROLE_VP"})
	@RequestMapping(path = PathFinder.ROUTE_SHOW, method = RequestMethod.GET)
	public String showItemGet(Model model,@PathVariable Long id) {

		Long idverticale = null;

		Team teamBuffer = new Team();
		teamBuffer = teamCrud.findOne(id);

		if (teamBuffer.getVerticale() == null) {
			idverticale = 1L;

			teamBuffer.setVerticale(verticaleCrud.findOne(idverticale));
			teamCrud.save(teamBuffer);
			}

		model.addAttribute("page","Equipe : " + teamBuffer.getName());
		model.addAttribute("sortedFields",DumpFields.createContentsEmpty(super.getClazz()).fields);
		model.addAttribute("item",DumpFields.fielder(super.getItem(id)));
		model.addAttribute("show_users", PathFinder.DOT + PathFinder.PATH + PathFinder.SHOW_USERS);
		model.addAttribute("show_verticale", PathFinder.DOT + PathFinder.PATH + PathFinder.SHOW_VERTICAL);
		model.addAttribute("go_index", PathFinder.LIST_ACTION);
		model.addAttribute("go_delete", PathFinder.DELETE_ACTION);
		model.addAttribute("go_update", PathFinder.UPDATE_ACTION);

		return BASE_TEAM + PathFinder.PATH + PathFinder.SHOW_PATH;
	}


	// ///////////////////////////////////////////////////////////////////////////////////////////////////////

		/**
		 *
		 * ASSOCIATION TEAM --> USER
		 *
		 */

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * SHOW ALL USERS RELATED TO TEAM
	 * @param model
	 * @param id
	 * @return
	 */
	@Secured({"ROLE_ADMIN","ROLE_GESTIONNAIRE","ROLE_VP"})
	@RequestMapping(path = "{idTeam}" + PathFinder.PATH + PathFinder.SHOW_USERS, method = RequestMethod.GET)
	public String showLinksGet(Model model, @PathVariable Long idTeam) {

		Team teamBuffer = new Team();
		teamBuffer = teamCrud.findOne(idTeam);

		if (this.UserInTeam(idTeam).size() < LENGHT_VIEW) {
			model.addAttribute("items",this.UserInTeam(idTeam));
		}else{
			model.addAttribute("items",this.UserInTeam(idTeam).subList(0, LENGHT_VIEW));
		}
		
		//model.addAttribute("items",this.UserInTeam(idTeam));
		model.addAttribute("sortedFields",User.FIELDS);
		model.addAttribute("page", ((Team) teamBuffer).getName());
		model.addAttribute("go_show", PathFinder.SHOW_ACTION);
		model.addAttribute("go_create", PathFinder.CREATE_ACTION);
		model.addAttribute("go_delete", PathFinder.DELETE_ACTION);
		model.addAttribute("back", "./show");
		model.addAttribute("add", "addUsers");

		return BASE_TEAM + PathFinder.PATH + PathFinder.SHOW_USERS;
	}

	/**
	 *
	 * SHOW POST THAT UPDATE USER RELATION WITH TEAM WHEN A USER QUIT A TEAM
	 */
	@Secured({"ROLE_ADMIN","ROLE_GESTIONNAIRE","ROLE_VP"})
	@RequestMapping(path = "{idTeam}" + PathFinder.PATH + PathFinder.SHOW_USERS, method = RequestMethod.POST)
	public String showItemPost(Model model,@PathVariable Long idTeam, Long idUser) {
		return quitTeam(idUser, idTeam);
	}

	/**
	 *
	 * ADD USER FOR CURRENT TEAM
	 * @param model
	 * @param idTeam
	 * @return
	 */
	@Secured({"ROLE_ADMIN","ROLE_GESTIONNAIRE","ROLE_VP"})
	@RequestMapping(path = "{idTeam}" + PathFinder.PATH + PathFinder.ADD_USER, method = RequestMethod.GET)
	public String addUsersGet(Model model, @PathVariable Long idTeam) {

		Object teamBuffer = new Object();
		teamBuffer = teamCrud.findOne(idTeam);
		
		ArrayList<User> userList = userCrud.getAssociatedUsersForTeam(idTeam);
		
		if (userList.size() < LENGHT_VIEW) {
			model.addAttribute("items",(DumpFields.listFielder(userList)));
		}else{
			model.addAttribute("items",DumpFields.listFielder(userList.subList(0, LENGHT_VIEW)));
		}

		//model.addAttribute("items", DumpFields.listFielder(userList));
		model.addAttribute("sortedFields",User.FIELDS);
		model.addAttribute("page", ((Team) teamBuffer).getName());
		model.addAttribute("go_show", PathFinder.SHOW_ACTION);
		model.addAttribute("go_create", PathFinder.CREATE_ACTION);
		model.addAttribute("go_delete", PathFinder.DELETE_ACTION);
		model.addAttribute("back", "./showUser");
		model.addAttribute("add", PathFinder.ADD_USER);

		return BASE_TEAM + PathFinder.PATH + PathFinder.ADD_USER;
	}

	/**
	 *
	 * ADD USER FOR CURRENT TEAM
	 * @param model
	 * @param idTeam
	 * @param idUser
	 * @return
	 */
	@Secured({"ROLE_ADMIN","ROLE_GESTIONNAIRE","ROLE_VP"})
	@RequestMapping(path = "{idTeam}" + PathFinder.PATH + PathFinder.ADD_USER, params = "idUser", method = RequestMethod.POST)
	public String addUsersPost(Model model, @PathVariable Long idTeam, @RequestParam Long idUser) {
		return setUsersForTeamPost(idTeam, idUser);
	}

	/**
	 * SHOW USERS TO ADD ON VERTICALE (SEARCH)
	 * @param model
	 * @param name
	 * @return
	 */
	@Secured({"ROLE_ADMIN","ROLE_GESTIONNAIRE"})
	@RequestMapping(path = "{idTeam}" + PathFinder.PATH + PathFinder.ADD_USER, params = "name", method = RequestMethod.POST)
	public String addVerticalForTeamPOST(Model model,@RequestParam String name , @PathVariable Long idTeam){

		Team teamBuffer = teamCrud.findOne(idTeam);
		
		if (name == "") {
			return PathFinder.REDIRECT + PathFinder.ADD_USER;
			}

		model.addAttribute("model", "team");
		model.addAttribute("page", teamBuffer.getName());
		model.addAttribute("sortedFields",User.FIELDS);
		model.addAttribute("items",DumpFields.listFielder(UtilsFunctions.findAny("user", name, userCrud, teamCrud, verticaleCrud, nikoCrud)));
		model.addAttribute("go_show", PathFinder.SHOW_ACTION);
		model.addAttribute("go_create", PathFinder.CREATE_ACTION);
		model.addAttribute("go_delete", PathFinder.DELETE_ACTION);
		model.addAttribute("back", PathFinder.DOT + PathFinder.PATH + PathFinder.SHOW_USERS);

		return BASE_TEAM + PathFinder.PATH + PathFinder.ADD_USER;

	}

	/**
	 * RETURN LIST OF ALL USERS IN A TEAM WITH A GIVEN ID
	 * @param teamId
	 * @return userList (list of user associated to a team)
	 */
	public ArrayList<User> setUsersForTeamGet(Long idValue) {

		List<Long> ids = new ArrayList<Long>();
		ArrayList<User> userList = new ArrayList<User>();

		List<BigInteger> idsBig = userTeamCrud.findAssociatedUser(idValue);

		if (!idsBig.isEmpty()) {//if no association => return empty list which can't be use with findAll(ids)
			for (BigInteger id : idsBig) {
				ids.add(id.longValue());

			}
			userList = (ArrayList<User>) userCrud.findAll(ids);
		}

		return userList;
	}

	/**
	 * FUNCTION THAT SET NEW USER IN TEAM (JUST AFFECT A USER ALREADY CREATE)
	 * @param idTeam
	 * @param idUser
	 * @return
	 */
	public String setUsersForTeamPost(Long idTeam,Long idUser){

		String redirect = PathFinder.REDIRECT + PathFinder.PATH + BASE_TEAM + PathFinder.PATH + idTeam + PathFinder.PATH + PathFinder.SHOW_USERS;

		Team team = new Team();
		team = teamCrud.findOne(idTeam);

		User user = new User();
		user = userCrud.findOne(idUser);

		UserHasTeam userHasTeamBuffer = new UserHasTeam(user, team, new Date());

		userTeamCrud.save(userHasTeamBuffer);

		return redirect;

	}

	/**
	 * UPDATE USER_HAS_TEAM (leaving_date) WHEN A TEAM DELETE AN USER FROM HIS OWN
	 * @param idUser
	 * @param idTeam
	 * @return
	 */
	public String quitTeam(Long idUser, Long idTeam){

		String redirect = PathFinder.REDIRECT + PathFinder.PATH + BASE_TEAM + PathFinder.PATH + idTeam + PathFinder.PATH + PathFinder.SHOW_USERS;

		Date date = new Date();

		UserHasTeam userHasTeamBuffer = userTeamCrud.findOne(new AssociationItemId(idUser, idTeam));
		userHasTeamBuffer.setLeavingDate(date);

		userTeamCrud.save(userHasTeamBuffer);

		return redirect;
	}

	/**
	 * FUNCTION RETURNING ALL TEAM RELATED WITH ONE USER WITH leaving_date = null
	 * @param idUser
	 * @return
	 */
	public ArrayList<Map<String, Object>> UserInTeam(Long idTeam){

		ArrayList<Long> ids = new ArrayList<Long>();
		ArrayList<User> userList = new ArrayList<User>();
		ArrayList<UserHasTeam> userHasTeamList = new ArrayList<UserHasTeam>();
		ArrayList<UserHasTeam> userHasTeamListClean = new ArrayList<UserHasTeam>();

		userList = setUsersForTeamGet(idTeam);

		for (int i = 0; i < userList.size(); i++) {
			userHasTeamList.add(userTeamCrud.findAssociatedUserTeamALL(userList.get(i).getId(), idTeam));

			if(userHasTeamList.get(i).getLeavingDate() == null){

				userHasTeamListClean.add(userHasTeamList.get(i));
				ids.add(userHasTeamList.get(i).getIdLeft());

				}
		}

		return DumpFields.listFielder((List<User>) userCrud.findAll(ids));
	}

	/**
	 * FIND ALL USERS RELATED TO A TEAM
	 * @param idValue
	 * @return
	 */
	public ArrayList<User> findUsersOfATeam(Long idValue) {

		List<Long> ids = new ArrayList<Long>();
		ArrayList<User> userList = new ArrayList<User>();
		List<BigInteger> idsBig = userTeamCrud.findAssociatedUser(idValue);

		if (!idsBig.isEmpty()) {//if no association => return empty list which can't be use with findAll(ids)
			for (BigInteger id : idsBig) {
				ids.add(id.longValue());
			}
			userList = (ArrayList<User>) userCrud.findAll(ids);
		}
		return userList;
	}


	// ///////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 *
	 * ASSOCIATION TEAM --> VERTICAL
	 *
	 */

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * RELATION USER HAS TEAM
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	// @Secured({"ROLE_ADMIN","ROLE_GESTIONNAIRE","ROLE_VP"})
	@RequestMapping(path = "{idTeam}" + PathFinder.PATH + PathFinder.SHOW_VERTICAL, method = RequestMethod.GET)
	public String showVerticalForUserGET(Model model, @PathVariable Long idTeam) {

		Team teamBuffer = new Team();
		teamBuffer = teamCrud.findOne(idTeam);

		model.addAttribute("page", teamBuffer.getName());
		model.addAttribute("sortedFields", Verticale.FIELDS);
		model.addAttribute("items", this.getVerticalForTeam(idTeam));
		model.addAttribute("show_verticale", PathFinder.DOT + PathFinder.PATH + PathFinder.SHOW_VERTICAL);
		model.addAttribute("back", PathFinder.DOT + PathFinder.PATH + PathFinder.SHOW_PATH);
		model.addAttribute("add", "addVerticale");

		return BASE_TEAM + PathFinder.PATH + "showVerticale";

	}

	/**
	 * SHOW VERTICAL TO ON TEAM
	 * @param idTeam
	 * @return
	 */
	public ArrayList<Verticale> getVerticalForTeam(Long idTeam) {
		ArrayList<Verticale> verticaleList = new ArrayList<Verticale>();
		Long idVerticale = teamCrud.getTeamVertical(idTeam);
		verticaleList.add(verticaleCrud.findOne(idVerticale));
		return verticaleList;
	}

	/**
	 * SHOW VERTICAL TO ADD TEAM
	 *
	 * @param model
	 * @param idUser
	 * @return
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_GESTIONNAIRE" })
	@RequestMapping(path = "{idTeam}" + PathFinder.PATH + PathFinder.ADD_VERTICAL, method = RequestMethod.GET)
	public String addVerticalForUserGET(Model model, @PathVariable Long idTeam) {

		Object teamBuffer = new Object();
		teamBuffer = teamCrud.findOne(idTeam);
		model.addAttribute("items", DumpFields
				.listFielder((ArrayList<Verticale>) verticaleCrud.findAll()));
		model.addAttribute("sortedFields", Verticale.FIELDS);
		model.addAttribute("page", ((Team) teamBuffer).getName());
		model.addAttribute("go_show", PathFinder.SHOW_ACTION);
		model.addAttribute("go_create", PathFinder.CREATE_ACTION);
		model.addAttribute("go_delete", PathFinder.DELETE_ACTION);
		model.addAttribute("back", PathFinder.DOT + PathFinder.PATH + PathFinder.SHOW_VERTICAL);
		model.addAttribute("add", PathFinder.ADD_VERTICAL);

		return BASE_TEAM + PathFinder.PATH + PathFinder.ADD_VERTICAL;
	}

	/**
	 * ADD ONE VERTICALE TO USER
	 *
	 * @param model
	 * @param idUser
	 * @param idTeam
	 * @return
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_GESTIONNAIRE" })
	@RequestMapping(path = "{idTeam}" + PathFinder.PATH + PathFinder.ADD_VERTICAL, method = RequestMethod.POST)
	public String addVerticalForUserPOST(Model model, @PathVariable Long idTeam, Long idVertical) {
		return setVerticalForTeam(idTeam, idVertical);
	}

	/**
	 * ADD ONE VERTICAL TO A TEAM
	 * @param idTeam
	 * @param idVertical
	 * @return
	 */
	private String setVerticalForTeam(Long idTeam, Long idVertical) {

		String redirect = PathFinder.REDIRECT + PathFinder.PATH + BASE_TEAM + PathFinder.PATH
						+ idTeam + PathFinder.PATH + PathFinder.SHOW_VERTICAL;

		Team teamBuffer = new Team();
		Verticale verticaleBuffer = new Verticale();

		teamBuffer = teamCrud.findOne(idTeam);
		verticaleBuffer = verticaleCrud.findOne(idVertical);

		teamBuffer.setVerticale(verticaleBuffer);
		teamCrud.save(teamBuffer);

		return redirect;
	}


	// ///////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	*
	* ASSOCIATION TEAM --> NIKONIKO
	*
	*/

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * SELECTION NIKONIKO PAR RAPPORT A UN ENSEMBLE (TEAM, VERTICALE, ETC...)
	 * @param idTeam
	 * @return
	 */
	public ArrayList<NikoNiko> findNikoNikosOfATeam(Long idTeam){

		ArrayList<User> usersOfTeam = findUsersOfATeam(idTeam);

		ArrayList<NikoNiko> nikonikos = new ArrayList<NikoNiko>();
		//Partie a externaliser en fonction findAllNikoNikoForAUser(idUser) => probablement deja existante
		if (!usersOfTeam.isEmpty()) {
			for (User user : usersOfTeam) {
				if (!user.getNikoNikos().isEmpty()) {
					nikonikos.addAll(user.getNikoNikos());
				}
			}
		}
		//fin de partie a externaliser

		return nikonikos;
	}


	/////////////////// CONSTRUCTORS /////////////////////////////////


	public TeamController() {
		super(Team.class, BASE_URL);
	}


}
