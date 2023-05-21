package src.matachi.mapeditor.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import src.matachi.mapeditor.editor.checker.GameChecker;
import src.matachi.mapeditor.editor.checker.LevelCheck;
import src.matachi.mapeditor.editor.checker.LevelChecker;
import src.matachi.mapeditor.grid.Camera;
import src.matachi.mapeditor.grid.Grid;
import src.matachi.mapeditor.grid.GridCamera;
import src.matachi.mapeditor.grid.GridModel;
import src.matachi.mapeditor.grid.GridView;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Controller of the application.
 * 
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 * 
 */
public class Controller implements ActionListener, GUIInformation {

	/**
	 * The model of the map editor.
	 */
	private Grid model;

	private Tile selectedTile;
	private Camera camera;

	private List<Tile> tiles;

	private GridView grid;
	private View view;

	private int gridWith = Constants.MAP_WIDTH;
	private int gridHeight = Constants.MAP_HEIGHT;

	private Mode mode;

	private LevelChecker levelChecker = new LevelChecker();
	private GameChecker gameChecker = new GameChecker();

	/**
	 * Construct the controller.
	 */
	public Controller(String mode, String filePath) {
		//init(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
		if (Objects.equals(mode, "TEST")){
			this.mode = new TestMode(this, filePath);
			System.out.println("Test mode");
		} else if (Objects.equals(mode, "EDIT")){
			this.mode = new EditMode(this, filePath);
			System.out.println("Edit mode");
		}
	}

	public void init(int width, int height) {
		this.tiles = TileManager.getTilesFromFolder("data/");
		this.model = new GridModel(width, height, tiles.get(0).getCharacter());
		this.camera = new GridCamera(model, Constants.GRID_WIDTH,
				Constants.GRID_HEIGHT);

		grid = new GridView(this, camera, tiles); // Every tile is 30x30 pixels

		this.view = new View(this, camera, grid, tiles);
	}

	/**
	 * Different commands that comes from the view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (Tile t : tiles) {
			if (e.getActionCommand().equals(
					Character.toString(t.getCharacter()))) {
				selectedTile = t;
				break;
			}
		}
		if (e.getActionCommand().equals("flipGrid")) {
			// view.flipGrid();
		} else if (e.getActionCommand().equals("save")) {
			saveFile();
		} else if (e.getActionCommand().equals("load")) {
			loadFile();
		} else if (e.getActionCommand().equals("update")) {
			updateGrid(gridWith, gridHeight);
		}
	}

	public void updateGrid(int width, int height) {
		view.close();
		init(width, height);
		view.setSize(width, height);
	}

	DocumentListener updateSizeFields = new DocumentListener() {

		public void changedUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}

		public void removeUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}

		public void insertUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}
	};

	private void saveFile() {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"xml files", "xml");
		chooser.setFileFilter(filter);
		File workingDirectory = new File(System.getProperty("user.dir"));
		chooser.setCurrentDirectory(workingDirectory);

		int returnVal = chooser.showSaveDialog(null);
		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// Apply level checking
				levelChecker.performChecks(model, chooser.getSelectedFile().getName());
				//System.out.println(chooser.getSelectedFile().getName());

				Element level = new Element("level");
				Document doc = new Document(level);
				doc.setRootElement(level);

				Element size = new Element("size");
				int height = model.getHeight();
				int width = model.getWidth();
				size.addContent(new Element("width").setText(width + ""));
				size.addContent(new Element("height").setText(height + ""));
				doc.getRootElement().addContent(size);

				for (int y = 0; y < height; y++) {
					Element row = new Element("row");
					for (int x = 0; x < width; x++) {
						char tileChar = model.getTile(x,y);
						String type = "PathTile";

						if (tileChar == Constants.WALL_TILE_CHAR)
							type = "WallTile";
						else if (tileChar == Constants.PILL_TILE_CHAR)
							type = "PillTile";
						else if (tileChar == Constants.GOLD_TILE_CHAR)
							type = "GoldTile";
						else if (tileChar == Constants.ICE_TILE_CHAR)
							type = "IceTile";
						else if (tileChar == Constants.PAC_TILE_CHAR)
							type = "PacTile";
						else if (tileChar == Constants.TROLL_TILE_CHAR)
							type = "TrollTile";
						else if (tileChar == Constants.TX5_TILE_CHAR)
							type = "TX5Tile";
						else if (tileChar == Constants.PORTAL_WHITE_TILE_CHAR)
							type = "PortalWhiteTile";
						else if (tileChar == Constants.PORTAL_YELLOW_TILE_CHAR)
							type = "PortalYellowTile";
						else if (tileChar == Constants.PORTAL_DARK_GOLD_TILE_CHAR)
							type = "PortalDarkGoldTile";
						else if (tileChar == Constants.PORTAL_DARK_GRAY_TILE_CHAR)
							type = "PortalDarkGrayTile";

						Element e = new Element("cell");
						row.addContent(e.setText(type));
					}
					doc.getRootElement().addContent(row);
				}
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				xmlOutput
						.output(doc, new FileWriter(chooser.getSelectedFile()));
			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Invalid file!", "error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
		}
	}

	public void loadFile() {
		SAXBuilder builder = new SAXBuilder();
		try {
			JFileChooser chooser = new JFileChooser();
			File selectedFile;
			BufferedReader in;
			FileReader reader = null;
			File workingDirectory = new File(System.getProperty("user.dir"));
			chooser.setCurrentDirectory(workingDirectory);

			int returnVal = chooser.showOpenDialog(null);
			Document document;
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
				if (selectedFile.canRead() && selectedFile.exists()) {
					document = (Document) builder.build(selectedFile);

					Element rootNode = document.getRootElement();

					List sizeList = rootNode.getChildren("size");
					Element sizeElem = (Element) sizeList.get(0);
					int height = Integer.parseInt(sizeElem
							.getChildText("height"));
					int width = Integer
							.parseInt(sizeElem.getChildText("width"));
					updateGrid(width, height);

					List rows = rootNode.getChildren("row");
					for (int y = 0; y < rows.size(); y++) {
						Element cellsElem = (Element) rows.get(y);
						List cells = cellsElem.getChildren("cell");

						for (int x = 0; x < cells.size(); x++) {
							Element cell = (Element) cells.get(x);
							String cellValue = cell.getText();

							char tileNr;
							if (cellValue.equals("PathTile"))
								tileNr = Constants.PATH_TILE_CHAR;
							else if (cellValue.equals("WallTile"))
								tileNr = Constants.WALL_TILE_CHAR;
							else if (cellValue.equals("PillTile"))
								tileNr = Constants.PILL_TILE_CHAR;
							else if (cellValue.equals("GoldTile"))
								tileNr = Constants.GOLD_TILE_CHAR;
							else if (cellValue.equals("IceTile"))
								tileNr = Constants.ICE_TILE_CHAR;
							else if (cellValue.equals("PacTile"))
								tileNr = Constants.PAC_TILE_CHAR;
							else if (cellValue.equals("TrollTile"))
								tileNr = Constants.TROLL_TILE_CHAR;
							else if (cellValue.equals("TX5Tile"))
								tileNr = Constants.TX5_TILE_CHAR;
							else if (cellValue.equals("PortalWhiteTile"))
								tileNr = Constants.PORTAL_WHITE_TILE_CHAR;
							else if (cellValue.equals("PortalYellowTile"))
								tileNr = Constants.PORTAL_YELLOW_TILE_CHAR;
							else if (cellValue.equals("PortalDarkGoldTile"))
								tileNr = Constants.PORTAL_DARK_GOLD_TILE_CHAR;
							else if (cellValue.equals("PortalDarkGrayTile"))
								tileNr = Constants.PORTAL_DARK_GRAY_TILE_CHAR;
							else
								tileNr = '0';

							model.setTile(x, y, tileNr);
						}
					}

					// Apply level checking
					levelChecker.performChecks(model, selectedFile.getName());
					//System.out.println(chooser.getSelectedFile().getName());

					String mapString = model.getMapAsString();
					grid.redrawGrid();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile getSelectedTile() {
		return selectedTile;
	}
}
