// 代码生成时间: 2025-09-30 02:57:21
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * ImageLazyLoader class is a REST service that provides lazy loading functionality for images.
 * It allows users to pass an image URL and returns a resized or converted image.
 */
@Path("/image")
public class ImageLazyLoader {

    /**
     * Resizes an image from a given URL and returns it as a response.
     *
     * @param imageUrl The URL of the image to be resized.
     * @return A response containing the resized image.
     */
    @GET
    @Path("/resize")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response resizeImage(@FormDataParam("imageUrl") String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            BufferedImage image = ImageIO.read(inputStream);
            BufferedImage resizedImage = resizeImage(image, 300, 300); // Resize to 300x300 for example

            ByteArrayOutputStream baous = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", baous);
            byte[] imageBytes = baous.toByteArray();

            return Response.ok(imageBytes).build();
        } catch (IOException e) {
            // Log error and return a bad request response
            return Response.status(Response.Status.BAD_REQUEST).entity("Error resizing image: " + e.getMessage()).build();
        }
    }

    /**
     * Resizes an image to the specified width and height.
     *
     * @param originalImage The original image to be resized.
     * @param width The new width.
     * @param height The new height.
     * @return The resized image.
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return resizedImage;
    }
}
