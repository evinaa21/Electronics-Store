/**
 * 
 */
/**
 * 
 */
module JavaProject {
	requires javafx.controls;
    requires javafx.graphics;

    opens main to javafx.graphics;
    opens view to javafx.graphics;
}