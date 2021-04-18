package git.dimitrikvirik.springmiddleexam.user;


import git.dimitrikvirik.springmiddleexam.RecordAlreadyExistException;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    String registration(UserView userView, HttpServletRequest request) throws RecordAlreadyExistException;

    UserView login(String nickname, String password, HttpServletRequest request);

    UserView getLoggedUser(HttpServletRequest request);

    UserView get(int id);

    String getUsername(int id);


    void logout(HttpServletRequest request);

   void delete(UserView loggedUser, HttpServletRequest request);

   void edit(UserView loggedUser, UserView userView) throws RecordAlreadyExistException;
}
