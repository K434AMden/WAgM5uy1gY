// 代码生成时间: 2025-10-15 03:04:27
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/defects")
public class DefectTrackingSystem {

    // A simple in-memory store for defects.
    // In a production environment, this would be replaced with a database.
    private ConcurrentHashMap<Integer, Defect> defectsStore = new ConcurrentHashMap<>();
    private AtomicInteger defectIdGenerator = new AtomicInteger(1);

    /**
     * Represents a defect in the system.
     */
    public static class Defect {
        private int id;
        private String summary;
        private String description;
        private String status;

        public Defect(int id, String summary, String description, String status) {
            this.id = id;
            this.summary = summary;
            this.description = description;
            this.status = status;
        }

        // Getters and setters for fields
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    /**
     * POST method to create a new defect.
     *
     * @param defect The defect to be created.
     * @return The created defect with its ID.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Defect createDefect(Defect defect) {
        if (defect == null || defect.getSummary() == null || defect.getDescription() == null || defect.getStatus() == null) {
            throw new BadRequestException("Defect data is incomplete.");
        }

        int id = defectIdGenerator.getAndIncrement();
        defect.setId(id);
        defectsStore.put(id, defect);
        return defect;
    }

    /**
     * GET method to retrieve a defect by ID.
     *
     * @param id The ID of the defect to retrieve.
     * @return The defect with the specified ID.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Defect getDefect(@PathParam("id") int id) {
        Defect defect = defectsStore.get(id);
        if (defect == null) {
            throw new NotFoundException("Defect with ID " + id + " not found.");
        }
        return defect;
    }

    /**
     * PUT method to update an existing defect.
     *
     * @param id The ID of the defect to update.
     * @param defect The updated defect data.
     * @return The updated defect.
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Defect updateDefect(@PathParam("id\) int id, Defect defect) {
        if (defect == null || defect.getId() != id) {
            throw new BadRequestException("Invalid defect data or ID mismatch.");
        }

        Defect existingDefect = defectsStore.get(id);
        if (existingDefect == null) {
            throw new NotFoundException("Defect with ID " + id + " not found.");
        }

        existingDefect.setSummary(defect.getSummary());
        existingDefect.setDescription(defect.getDescription());
        existingDefect.setStatus(defect.getStatus());
        return existingDefect;
    }

    /**
     * DELETE method to delete a defect by ID.
     *
     * @param id The ID of the defect to delete.
     * @return A success message if the deletion was successful.
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteDefect(@PathParam("id\) int id) {
        if (!defectsStore.remove(id).isPresent()) {
            throw new NotFoundException("Defect with ID " + id + " not found.");
        }
        return "Defect with ID " + id + " has been deleted.";
    }
}
