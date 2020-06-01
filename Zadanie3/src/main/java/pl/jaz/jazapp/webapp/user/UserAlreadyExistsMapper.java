package pl.jaz.jazapp.webapp.user;

import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAlreadyExistsMapper implements ExceptionMapper<UserAlreadyExistsException> {

    @Override
    public Response toResponse(UserAlreadyExistsException exception) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash()
                .put("error-message", "Username or password id incorrect");

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
