package operations.operators;

import display.gui.GUI;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

/**
 *
 * @author Luis
 */
public class Crop extends GUI {

    private static RubberBandSelection rubberBandSelection;
    private static ImageView iv;

    public static void crop() {
        //Stage
        Stage cropStage = new Stage();

        //Pane
        StackPane sp = new StackPane();

        // Image layer: a group of images
        Group imageLayer = new Group();

        // Set ImageView
        iv = new ImageView(inputImage);

        // Add image to layer
        imageLayer.getChildren().add(iv);

        // Rubberband selection
        rubberBandSelection = new RubberBandSelection(imageLayer);

        // Create context menu and menu items
        ContextMenu contextMenu = new ContextMenu();

        MenuItem cropMenuItem = new MenuItem("Crop");
        cropMenuItem.setOnAction(e -> {
            // Get bounds for image crop
            Bounds selectionBounds = rubberBandSelection.getBounds();

            // Crop the image
            crop(selectionBounds, cropStage);
        });
        contextMenu.getItems().add(cropMenuItem);

        // Set context menu on image layer
        imageLayer.setOnMousePressed(e -> {
            if (e.isSecondaryButtonDown()) {
                contextMenu.show(imageLayer, e.getScreenX(), e.getScreenY());
            }
        });

        //Stackpane
        sp.getChildren().add(imageLayer);
        sp.setAlignment(Pos.CENTER);

        //ImageView
        iv.fitWidthProperty().bind(sp.widthProperty());
        iv.fitHeightProperty().bind(sp.heightProperty());

        //Stage
        cropStage.setMaximized(true);
        cropStage.setTitle("Crop");
        cropStage.setScene(new Scene(sp));
        cropStage.getIcons().add(ICON);
        cropStage.show();
    }

    private static void crop(Bounds bounds, Stage stage) {
        int width = (int) bounds.getWidth();
        int height = (int) bounds.getHeight();

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));

        WritableImage wi = new WritableImage(width, height);
        iv.snapshot(parameters, wi);

        bufferedImageC = SwingFXUtils.fromFXImage(wi, null);
        outputImage = wi;
        outputImageView.setImage(wi);
        stage.close();
    }

    /**
     * Drag rectangle with mouse cursor in order to get selection bounds
     */
    public static class RubberBandSelection {

        final DragContext dragContext = new DragContext();
        Rectangle rect = new Rectangle();

        Group group;

        public Bounds getBounds() {
            return rect.getBoundsInParent();
        }

        public RubberBandSelection(Group group) {

            this.group = group;

            rect = new Rectangle(0, 0, 0, 0);
            rect.setStroke(Color.BLUE);
            rect.setStrokeWidth(1);
            rect.setStrokeLineCap(StrokeLineCap.ROUND);
            rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));

            group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

        }

        EventHandler<MouseEvent> onMousePressedEventHandler = e -> {
            if (e.isSecondaryButtonDown()) {
                return;
            }

            // remove old rect
            rect.setX(0);
            rect.setY(0);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().remove(rect);

            // prepare new drag operation
            dragContext.mouseAnchorX = e.getX();
            dragContext.mouseAnchorY = e.getY();

            rect.setX(dragContext.mouseAnchorX);
            rect.setY(dragContext.mouseAnchorY);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().add(rect);
        };

        EventHandler<MouseEvent> onMouseDraggedEventHandler = e -> {
            if (e.isSecondaryButtonDown()) {
                return;
            }

            double offsetX = e.getX() - dragContext.mouseAnchorX;
            double offsetY = e.getY() - dragContext.mouseAnchorY;

            if (offsetX > 0) {
                rect.setWidth(offsetX);
            } else {
                rect.setX(e.getX());
                rect.setWidth(dragContext.mouseAnchorX - rect.getX());
            }

            if (offsetY > 0) {
                rect.setHeight(offsetY);
            } else {
                rect.setY(e.getY());
                rect.setHeight(dragContext.mouseAnchorY - rect.getY());
            }
        };

        EventHandler<MouseEvent> onMouseReleasedEventHandler = e -> {

            if (e.isSecondaryButtonDown()) {
            }

        };

        private static final class DragContext {

            public double mouseAnchorX;
            public double mouseAnchorY;

        }
    }
}
