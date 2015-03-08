import java.awt.EventQueue;

/**
 * <h2>The App Class</h2>
 * <br>
 * This class has the main entry point starts the OpenWeatherApp.
 * 
 * @author	Group10 (James V, Li G, Curtis V, Alec W, Jeremy A)
 * @version	1.0
 * @since 	2015-02-01
 *  *
 */
public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				AppWindow window = new AppWindow();
				window.setVisible(true);
			}
		}); }
}
