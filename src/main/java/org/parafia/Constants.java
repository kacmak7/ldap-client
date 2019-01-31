package org.parafia;


/**
 * Constant values used throughout the application.
 * 
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class Constants {
    //~ Static fields/initializers =============================================
	
	/**
	 * Date Format for DateFormat classes with year only
	 */
	public static final String DATE_FORMAT_ONLY_YEAR = "yyyy";
	
	/**
	 * Date Format for DateFormat classes
	 */
	public static final String DATE_FORMAT_TEXT = "EEE MMM dd HH:mm:ss z yyyy";
	
	/**
	 * Date Format for DateFormat classes
	 */
	public static final String LONG_FORMAT_TEXT = "dd MMMM yyyy";
	
	/**
	 * User's Date Format
	 */
	public static final String DATE_FORMAT_USER = "dd-MM-yyyy";
	
	/**
	 * User's Time Format
	 */
	public static final String TIME_FORMAT_USER = "HH:mm";
	
	/**
	 * DB's Date Format
	 */
	public static final String DATE_FORMAT_DB = "yyyy-MM-dd";
	
    /**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /**
     * The name of the configuration properties file used in this application
     */
    public static final String CONFIG_PROPERTIES = "config.properties";
    
    public static final String FILE_SEPARATOR = "fileSeparator";
    
    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses, we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_KEY = "userForm";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";

    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    /**
     * The name of the User role, as specified in web.xml
     */
    public static final String USER_ROLE = "ROLE_USER";

    /**
     * The name of the user's role list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * The name of the CSS Theme setting.
     */
    public static final String CSS_THEME = "csstheme";
    
    public static final String MESSAGES_KEY = "successMessages";
    
    
    public static final String FILES_LIST = "filesList";
    
    public static final String FAMILIES_LIST = "familiesList";
    
    public static final String PERSONS_LIST = "personsList";
    
    public static final String MARRIAGE_STATUSES_LIST = "marriageStatusesList";
    
    public static final String TAKE_PART_LIST = "takePartList";
    
    public static final String FIANCEES_LIST = "fianceesList";
    
    public static final String GRAVE_DOUBLED_LIST = "graveDoubledList";
    
    public static final String GRAVE_OWNED_LIST = "graveOwnedList";
    
    public static final String GRAVE_GROUNDED_LIST = "graveGroundedList";
    
    public static final String SECTORS_LIST = "sectorsList";
    
    public static final String INTENTIONS_LIST = "intentionsList";
    
    /**
     * number of questions in canonical protocole
     */
    //jest String, bo z intem krzaczy sie appfuse:constants w header.jsp
    public final static String NUMBER_OF_QUESTIONS = "27";
    
    public final static String FAMILY_FILTER = "familyFilter";
    public final static String PERSON_FILTER = "personFilter";
    public final static String BAP_CON_DEA_FILTER = "bapConDeaFilter";
    public final static String INTENTION_FILTER = "intentionFilter";
    public final static String GRAVE_FILTER = "graveFilter";
    public final static String NICHE_FILTER = "nicheFilter";
    public final static String OFFERING_FILTER = "reportFilter";
    
    public final static String SORTING_FAMILY_FIELD = "sortingFamilySurname";
    public final static String SORTING_FAMILY_DIR = "sortingFamilyDir";
    
    public final static String PHOTO_URL = "photoUrl";
    
    public final static String FIRST_DATE = "firstDate";
    public final static String LAST_DATE = "lastDate";
    
    public final static String PERSON1_PARAM = "person1Id";
    public final static String PERSON2_PARAM = "person2Id";
    
    public final static String PERSON_ID = "personId";
    
    public final static String FIANCEE_PAIR_ID = "fianceePairId";
    
    public final static String INTENTION_ID = "intentionId";
    
    public final static String YEARS = "years";
    
    public final static String BURIAL_YEARS = "burialYears";
    
    public final static String YEAR = "year";
    
    public final static String INTENTION_DEFAULT_DATE = "intentionDefaultDate";
}
