// 代码生成时间: 2025-09-23 15:56:38
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/theme")
public class ThemeSwitchingService {

    // This is a simple in-memory store for the theme. In a real-world scenario, 
    // this might be a database or a distributed cache.
    private String currentTheme = "default";

    /**
     * GET method to get the current theme.
     *
     * @return The current theme as a string.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getCurrentTheme() {
        return currentTheme;
    }

    /**
     * PUT method to switch the theme.
     *
     * @param themeName The name of the theme to switch to.
     * @return A response indicating success or failure.
     */
    @PUT
    @Path("/{themeName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response switchTheme(@PathParam("themeName") String themeName) {
        try {
            // Validate the theme name.
            if (themeName == null || themeName.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity(
                    "{"error": "Invalid theme name."}").build();
            }

            // Switch the theme.
            currentTheme = themeName;
            return Response.status(Response.Status.OK).entity(
                "{"message": "Theme switched to " + currentTheme}.build();
        } catch (Exception e) {
            // Handle any unexpected errors.
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                "{"error": "Internal server error."}").build();
        }
    }
}