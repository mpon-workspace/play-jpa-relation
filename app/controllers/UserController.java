package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.db.jpa.Transactional;

import java.util.List;

import models.*;

import views.html.users.*;

public class UserController extends Controller {

	private static Form<User> userForm = Form.form(User.class);

	@Transactional
    public static Result users() {
    	List<User> userList = User.findAll();
        return ok(users.render(userList));
    }

    public static Result create() {
    	return ok(newUser.render(userForm));
    }

    @Transactional
    public static Result save() {
    	Form<User> newForm = userForm.bindFromRequest();
        if (newForm.hasErrors()) {
            return badRequest(newUser.render(newForm));
        }
    	User user = newForm.get();
    	user.save();
    	return redirect(routes.UserController.users());
    }

    @Transactional
    public static Result edit(Long id) {
        User user = User.findById(id);
        Form<User> editForm = userForm.fill(user);
        return ok(editUser.render(id, editForm));
    }

    @Transactional
    public static Result update(Long id) {
        Form<User> editForm = userForm.bindFromRequest();
        if(editForm.hasErrors()) {
            return badRequest(editUser.render(id, editForm));
        }
        User user = editForm.get();
        user.update(id);
        return redirect(routes.UserController.users());
    }

    @Transactional
    public static Result delete(Long id) {
        User user = User.findById(id);
        user.delete();
        return redirect(routes.UserController.users());
    }

}
