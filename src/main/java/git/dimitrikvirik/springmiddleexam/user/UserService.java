package git.dimitrikvirik.springmiddleexam.user;


import git.dimitrikvirik.springmiddleexam.RecordAlreadyExistException;

public interface UserService {
    String registration(UserView userView) throws RecordAlreadyExistException;

    UserView login(String nickname, String password);

    UserView get(int id);

    void delete(int userId);

    void edit(int userId, UserView userView) throws RecordAlreadyExistException;
}
