package com.app.devrah.Network;


public class End_Points {

    public static final String BASE_URL = "http://m1.cybussolutions.com/devrah/Api_service/";

    public static final String LOGIN = BASE_URL + "login";
    public static final String FORGOT_PASSWORD = BASE_URL + "forgot";
    public static final String SIGN_UP = BASE_URL + "addUserSignUp";
    public static final String SIGN_UP_GOOGLE = BASE_URL + "addGoogleSignUp";
    public static final String GET_MEMBER_PROJECTS = BASE_URL + "getProjectsToMembers";
    public static final String UPDATE_PROJECT_NAME = BASE_URL + "updateProjectName";
    public static final String UPDATE_BOARD_NAME = BASE_URL + "updateBoardName";
    public static final String GET_NOTIFICATIONS = BASE_URL + "getMyMessages";
    public static final String GETMYCARDS = BASE_URL + "getMyCards/";
    public static final String SENT_MESSAGES = BASE_URL + "getSentMessagesById";
    public static final String SEND_NEW_MESSAGE = BASE_URL + "sendMessage";
    public static final String INSERT_TOKEN_ID = BASE_URL + "insertTokenid";
    public static final String GET_TEAMS = BASE_URL + "getAllTeams";
    public static final String GET_MEMBER_TEAMS = BASE_URL + "getMemberTeams";
    public static final String GET_TEAM_MEMBERS_BY_ID = BASE_URL + "teamDetailById";
    public static final String ADD_SINGLE_TEAM_MEMBER = BASE_URL + "addSingleTeamMember";
    public static final String ADD_BULK_TEAM_MEMBERS = BASE_URL + "bulkAddMembers";
    public static final String SEARCH_USER = BASE_URL + "searchuser";
    public static final String DELETE_USER_FROM_TEAM = BASE_URL + "deleteUserFromTeam";
    public static final String ADD_NEW_TRAM = BASE_URL + "addNewTeam";


    public static final String ADD_NEW_PROJECT = BASE_URL + "addNewProject";

    public static final String ADD_NEW_PROJECT_ET = BASE_URL + "addNewGroup";


    public static final String GET_SPINNER_GROUP_PROJECTS = BASE_URL + "getSpinnerGroupData";


    public static final String GET_GROUP_PROJECTS = BASE_URL + "projectGroupsAndProjectsByGroups";
    public static final String GET_WORK_BOARD = BASE_URL + "getWorkBoardsByProjects";
    public static final String GET_REFRENCE_BOARD = BASE_URL + "getRefrenceBoardsByProjects";
    public static final String ADD_WORK_BOARD = BASE_URL + "addNewWorkBoard";
    public static final String  GET_ALL_BOARD_LIST = BASE_URL + "getBoardAllLists";
    public static final String  GET_CARDS_FOR_LIST = BASE_URL + "cardsAssociationList";

    public static final String UPDATE_USER_PROFILE= BASE_URL + "updateUserProfile";
    public static final String ADD_NEW_REFERENCE_BOARD= BASE_URL + "addNewReferenceBoard";
    public static final String SAVE_NEW_LIST_BY_BOARD_ID= BASE_URL + "saveNewListByBoardId";
    public static final String GET_ALL_PROJECS= BASE_URL + "getAllMyProjects";
    public static final String GET_ALL_CARDS= BASE_URL + "getCardsByBoards";
    public static final String SAVE_CARD_BY_LIST_ID= BASE_URL + "saveCardByListId";
    public static final String GET_MEMBERS_PROJECT= BASE_URL + "getAllProjectMembers";
    public static final String LEAVE_PROJECT= BASE_URL + "leave_project";
    public static final String PROJECT_STATUS= BASE_URL + "updateProjectStatus";
    public static final String COPY_PROJECT= BASE_URL + "copyInGroup";
    public static final String MOVE_PROJECT= BASE_URL + "moveInGroup";
    public static final String GET_CARD_ASSOC_LABELS= BASE_URL + "getBoardAssociatedCardLabels";
    public static final String ASSOSIATE_USET_TO_PROJECT= BASE_URL + "associateUserWithProject";
    public static final String DELETE_PROJECT_MEMBER= BASE_URL + "deleteProjectMember";
    public static final String GET_ALL_BOARD_LISTS_FOR_CARD= BASE_URL + "getAllBoardListsForCard";
    public static final String FAVOURITES= BASE_URL + "myBoardsByFavourite";
    public static final String COPY_BORAD_TO_OTHER= BASE_URL + "copyBoardFromOneProjectToAnOther";
    public static final String MOVE_BORAD_TO_OTHER= BASE_URL + "moveBoardFromOneProjectToAnOther";
    public static final String GETPOSITION= BASE_URL + "getPostionofBoard";
    public static final String GET_BOARD_MEMBERS= BASE_URL + "getAllBoardMembers";
    public static final String ADD_BOARD_MEMBERS= BASE_URL + "associateUserWithBoard";
    public static final String DELETE_BOARD_MEMBERS= BASE_URL + "deleteBoardMember";
    public static final String NOTIFICATIONS= BASE_URL + "system_notifications";
    public static final String ACITITIES_DATA= BASE_URL + "activity_logs_data";
    public static final String DELETE_LIST= BASE_URL + "deleteList";
    public static final String UPDATE_COLOR_BG_LIST= BASE_URL + "updateBgList";
    public static final String LEAVE_BOARD= BASE_URL + "leave_board";
    public static final String COPY_LIST= BASE_URL + "copyListFromOneBoardToAnOther";
    public static final String MOVE_LIST= BASE_URL + "moveListFromOneBoardToAnOther";
    public static final String GET_LABELS= BASE_URL + "getLables";
    public static final String SAVE_NEW_LABELS_CARD= BASE_URL + "saveNewLabelForCard";
    public static final String SAVE_NEW_ATTACHMENT_BY_CARD_ID= BASE_URL + "saveUploadedAttachmentFromComputerByCardId";
    public static final String UPDATE_CARD_DUE_TIME= BASE_URL + "updateCardDueTime";
    public static final String DELETE_ATTACHMENT_BY_ID= BASE_URL + "deleteAttachmentFileById";
    public static final String MAKE_COVER= BASE_URL + "makeCover";
    public static final String UPDATE_LABLE= BASE_URL + "updateLabelById";
    public static final String DELETE_LABLE= BASE_URL + "deleteCardLabelById";
    public static final String ASSOSIATE_USER_CARD= BASE_URL + "associateUserWithCard";
    public static final String DELETE_USER_CARD= BASE_URL + "deleteUserFromCard";
    public static final String GET_CHECKLIST_DATA= BASE_URL + "getCheckListData";
    public static final String UPDATE_DUE_DATES_BY_ID= BASE_URL + "updateDueDatesById";
    public static final String UPDATE_START_DATES_BY_ID= BASE_URL + "updateStartDatesById";
    public static final String UPDATE_CARD_COMPLETED= BASE_URL + "updateCardCompleted";
    public static final String UPDATE_CARD_START_TIME= BASE_URL + "updateCardStartTime";
    public static final String UN_CHECK_CHECKBOX= BASE_URL + "uncheckCheckBox";
    public static final String CHECK_CHECKBOX= BASE_URL + "checkCheckBOx";
    public static final String SET_CARD_DESCRIPTION_BY_ID= BASE_URL + "setCardDescriptionByCardId";
    public static final String GET_POSITION_OF_LIST= BASE_URL + "getAllBoardListPositionsForCard";
    public static final String GET_ALL_BOARDS_FOR_CARD= BASE_URL + "getAllBoardsForCards";
    public static final String MOVE_NEW_CARD_BY_CARD_ID= BASE_URL + "moveNewCardByCardId";
    public static final String COPY_NEW_CARD_BY_CARD_ID= BASE_URL + "copyNewCardByCardId";
    public static final String LOCK_CARD_BY_CARD_ID= BASE_URL + "lockCardById";
    public static final String UNLOCK_CARD_BY_CARD_ID= BASE_URL + "unlockCardById";
    public static final String LEAVE_CARD= BASE_URL + "leaveCard";
    public static final String DELETE_CARD= BASE_URL + "deleteCard";
    public static final String SUBSCRIBE_CARD= BASE_URL + "subscribeUserByCardNuSERiD";
    public static final String UNSUBSCRIBE_CARD= BASE_URL + "unsubscribeUserByCardNuSERiD";
    public static final String UPDATE_CARD_NAME= BASE_URL + "updateCardName";
    public static final String EDIT_CHECKITEM= BASE_URL + "updateCheckBox";
    public static final String DELETE_CHECKITEM= BASE_URL + "deleteCheckBox";
    public static final String ADD_CHECKITEM= BASE_URL + "addNewCheckBox";
    public static final String ADD_NEW_CHECKLIST= BASE_URL + "addNewChecklist";
    public static final String AUPDATE_CHECKLIST_NAME= BASE_URL + "updatechecklistNameChecklist";
    public static final String MARDK_FAVOURITE= BASE_URL + "markFavourite";
    public static final String MARK_READ= BASE_URL + "markRead";
    public static final String DELETE_CHECKLIST= BASE_URL + "deleteCheckList";
    public static final String UPDATE_LIST_NAME= BASE_URL + "updateListName";
    public static final String DELETE_BOARD= BASE_URL + "deleteBoard";
    public static final String GET_CARD_MEMBERS= BASE_URL + "getCardMembers";
    public static final String DELETE_CARD_MEMBER= BASE_URL + "deleteCardMember";
    public static final String GET_WORK_BOARD_TO_MEMBERS= BASE_URL + "getWorkBoardToMembers";
    public static final String GET_REFERENCE_BOARD_TO_MEMBERS= BASE_URL + "getReferenceBoardToMembers";
    public static final String GET_POSITION_LIST= BASE_URL + "getPostionofList";
    public static final String GET_DUE_DATE= BASE_URL + "getDueDate";
    public static final String GETMYCARDSBYDUEDATE= BASE_URL + "getCardsByDueDateCalendarView";
    public static final String GETCHECKLISTCOMMENTS= BASE_URL + "getCheckListComments";
    public static final String DELETECOMMENT= BASE_URL + "deleteComment";
    public static final String ADDCOMMENTS= BASE_URL + "addNewComments";
    public static final String EDITCOMMENTS= BASE_URL + "editComment";
    public static final String REPLYCOMMENT= BASE_URL + "replyComment";
    public static final String ADDIMAGECOMMENTS= BASE_URL + "addImageComment";
    public static final String GETCHILDCOMMENTS= BASE_URL + "getCheckListChildComments";
    public static final String ADDCHILDIMAGECOMMENTS= BASE_URL + "addChildImageComment";
    public static final String CEHCK_PASS = BASE_URL + "checkpas";
    public static final String UPDATE_PASS = BASE_URL + "updatePass";
    public static final String SEARCH_USER_TO_ADD_MEMBER = BASE_URL + "searchusertoaddmember";
    public static final String GET_BOARD_ASSIGNED_LABELS = BASE_URL + "getBoardAssignedLabels";
    public static final String UN_ASSIGN_LABEL_BY_ID = BASE_URL + "unAssignLabelById";
    public static final String ASSIGN_LABEL_BY_ID = BASE_URL + "reAssignLabelById";
    public static final String SEARCH_USER_TEAM = BASE_URL + "searchUserTeam";

    public static final String DELETE_PROJECT= BASE_URL + "delete_project";
    public static final String GET_ALL_BOARDS_BY_PROJECT= BASE_URL + "getBoardsByProjects";
    public static final String ARCHIVE_BOARD= BASE_URL + "boardCardsCheck";
    public static final String GET_DUE_DATE_PROJECT= BASE_URL + "getDueDateProject";
    public static final String GET_PROJECT_CREATED_BY = BASE_URL + "getProjectCreatedBy";


}
