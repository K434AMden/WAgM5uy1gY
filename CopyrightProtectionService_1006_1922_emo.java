// 代码生成时间: 2025-10-06 19:22:04
// CopyrightProtectionService.java

/*
 * 简单版版权保护系统，使用Java和Jersey框架实现。
 * 提供版权信息的存储和验证功能。
 */

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

@Path("/copyright")
public class CopyrightProtectionService {

    // 使用线程安全的Map存储版权信息
    private ConcurrentHashMap<String, String> copyrightInfoMap = new ConcurrentHashMap<>();

    // POST方法，用于注册版权信息
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerCopyright(CopyrightInfo copyrightInfo) {
        try {
            if (copyrightInfo == null || copyrightInfo.getCopyrightId() == null ||
                copyrightInfo.getCopyrightHolder() == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(
                        new ErrorResponse("Invalid copyright information provided")).build();
            }

            copyrightInfoMap.putIfAbsent(copyrightInfo.getCopyrightId(), copyrightInfo.getCopyrightHolder());
            return Response.ok().entity(
                    new SuccessResponse("Copyright registered successfully")).build();
        } catch (Exception e) {
            return Response.serverError().entity(
                    new ErrorResponse("Error registering copyright: " + e.getMessage())).build();
        }
    }

    // GET方法，用于验证版权信息
    @Path("/validate/{copyrightId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateCopyright(@PathParam("copyrightId") String copyrightId) {
        try {
            String copyrightHolder = copyrightInfoMap.get(copyrightId);
            if (copyrightHolder == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(
                        new ErrorResponse("Copyright not found")).build();
            }
            return Response.ok().entity(
                    new SuccessResponse("Copyright validated", copyrightHolder)).build();
        } catch (Exception e) {
            return Response.serverError().entity(
                    new ErrorResponse("Error validating copyright: " + e.getMessage())).build();
        }
    }

    // 内部类，用于返回成功响应
    private static class SuccessResponse {
        private String message;
        private String copyrightHolder;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public SuccessResponse(String message, String copyrightHolder) {
            this.message = message;
            this.copyrightHolder = copyrightHolder;
        }

        // getter和setter方法
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCopyrightHolder() {
            return copyrightHolder;
        }

        public void setCopyrightHolder(String copyrightHolder) {
            this.copyrightHolder = copyrightHolder;
        }
    }

    // 内部类，用于返回错误响应
    private static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        // getter和setter方法
        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    // 内部类，用于封装版权信息
    public static class CopyrightInfo {
        private String copyrightId;
        private String copyrightHolder;

        public CopyrightInfo() {
        }

        public CopyrightInfo(String copyrightId, String copyrightHolder) {
            this.copyrightId = copyrightId;
            this.copyrightHolder = copyrightHolder;
        }

        // getter和setter方法
        public String getCopyrightId() {
            return copyrightId;
        }

        public void setCopyrightId(String copyrightId) {
            this.copyrightId = copyrightId;
        }

        public String getCopyrightHolder() {
            return copyrightHolder;
        }

        public void setCopyrightHolder(String copyrightHolder) {
            this.copyrightHolder = copyrightHolder;
        }
    }
}
