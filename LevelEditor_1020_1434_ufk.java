// 代码生成时间: 2025-10-20 14:34:30
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// 定义关卡编辑器REST API的路径
@Path("/level-editor")
public class LevelEditor {

    // 存储关卡数据的Map，键为关卡ID，值为关卡信息
    private Map<String, String> levels = new HashMap<>();

    /**
     * 获取所有关卡列表
     * @return 返回所有关卡的列表
     */
    @GET
    @Path("/levels")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLevels() {
        return Response.ok(levels).build();
    }

    /**
     * 添加新关卡
     * @param levelData 新关卡的数据
     * @return 返回添加的关卡ID和状态信息
     */
    @POST
    @Path("/add-level")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addLevel(String levelData) {
        try {
            String levelId = UUID.randomUUID().toString();
            levels.put(levelId, levelData);

            return Response.ok("Level added with ID: " + levelId).build();
        } catch (Exception e) {
            return Response.serverError().entity("Error adding level: " + e.getMessage()).build();
        }
    }

    /**
     * 获取特定关卡信息
     * @param levelId 关卡ID
     * @return 返回特定关卡的信息
     */
    @GET
    @Path("/levels/{levelId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getLevel(@PathParam("levelId") String levelId) {
        if (levels.containsKey(levelId)) {
            return Response.ok(levels.get(levelId)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Level not found").build();
        }
    }

    /**
     * 更新特定关卡信息
     * @param levelId 关卡ID
     * @param levelData 新的关卡数据
     * @return 返回更新状态信息
     */
    @POST
    @Path("/levels/{levelId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateLevel(@PathParam("levelId\) String levelId, String levelData) {
        if (levels.containsKey(levelId)) {
            levels.put(levelId, levelData);
            return Response.ok("Level updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Level not found").build();
        }
    }

    /**
     * 删除特定关卡
     * @param levelId 关卡ID
     * @return 返回删除状态信息
     */
    @POST
    @Path("/remove-level/{levelId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeLevel(@PathParam("levelId\) String levelId) {
        if (levels.remove(levelId) != null) {
            return Response.ok("Level removed successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Level not found").build();
        }
    }
}
