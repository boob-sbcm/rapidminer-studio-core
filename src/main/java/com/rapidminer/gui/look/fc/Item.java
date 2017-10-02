/**
 * Copyright (C) 2001-2017 by RapidMiner and the contributors
 *
 * Complete list of developers available at our web site:
 *
 * http://rapidminer.com
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.gui.look.fc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.ColorUIResource;

import com.rapidminer.gui.look.Colors;
import com.rapidminer.gui.tools.ResourceAction;
import com.rapidminer.gui.tools.SwingTools;
import com.rapidminer.gui.tools.dialogs.ConfirmDialog;
import com.rapidminer.io.remote.RemoteFileSystemView;
import com.rapidminer.tools.FontTools;


/**
 * An item for the item panel or the file list (might be a file or directory or an image...)
 *
 * @author Ingo Mierswa, Tobias Malbrecht
 */
public class Item extends JComponent implements Comparable<Item>, MouseListener {

	private static final long serialVersionUID = 2227494244271451068L;

	private class ItemMouseMotionAdapter extends MouseMotionAdapter {

		@Override
		public void mouseDragged(MouseEvent e) {
			requestFocusInWindow();
			if (getSelectionMode()) {
				getParentPane().selectedComponentMouseDragged(e.getPoint());
			}
		}
	}

	private transient final Action ADD_TO_BOOKMARKS_ACTION = new ResourceAction("file_chooser.add_to_bookmarks") {

		private static final long serialVersionUID = 6397058648283021931L;

		@Override
		public void actionPerformed(ActionEvent e) {
			getParentPane().getFilePane().addToBookmarks(Item.this.file);
		}
	};

	private transient final Action RENAME_ACTION = new ResourceAction("file_chooser.rename") {

		private static final long serialVersionUID = -415784022947681215L;

		@Override
		public void actionPerformed(ActionEvent e) {
			renameFile();
		}
	};

	private transient final Action SELECT_ACTION = new ResourceAction("file_chooser.select") {

		private static final long serialVersionUID = 2103094536883537758L;

		@Override
		public void actionPerformed(ActionEvent e) {
			getFileChooser().setSelectedFile(getFile());
			getParentPane().getFilePane().filechooserUI.getApproveSelectionAction().actionPerformed(null);
		}
	};

	private transient final Action DELETE_ACTION = new ResourceAction("file_chooser.delete") {

		private static final long serialVersionUID = 435288965907486522L;

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean res = false;
			String itemString = (isDirectory() ? "directory" : "file") + " " + getItemName();
			int resInt = SwingTools.showConfirmDialog("file_chooser.delete", ConfirmDialog.YES_NO_CANCEL_OPTION, itemString);
			if (resInt == ConfirmDialog.YES_OPTION) {
				try {
					res = delete();
				} catch (Exception exp) {
					// do nothing
				}
				if (!res) {
					SwingTools.showVerySimpleErrorMessage("file_chooser.delete.error", itemString);
				} else {
					getParentPane().getFilePane().rescanDirectory();
				}
			}
		}

	};

	private transient final Action OPEN_ACTION = new ResourceAction("file_chooser.open") {

		private static final long serialVersionUID = -5651411399479644689L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Item.this.parentPane.filePane.filechooserUI.setCurrentDirectoryOfFileChooser(getFile());
		}
	};

	private ItemMouseMotionAdapter motionListener = new ItemMouseMotionAdapter();

    /**
     * The Name label.
     */
    protected MultipleLinesLabel nameLabel = new MultipleLinesLabel("");

    /**
     * The Image label.
     */
    protected JLabel imageLabel = new JLabel("");

	private ItemPanel parentPane;

	private int tx, ty;

	private String fileName = "";

	private long fileSize = 0;

	private long lastModification;

	private File file;

	private boolean isDirectory = false, isFloppyDrive = false, isDrive = false;

	private JMenuItem addToBookmarksMenuItem;

	private static Border selectedThumbBorder;

    /**
     * The constant grayImageBorder.
     */
    public static Border grayImageBorder = BorderFactory.createLineBorder(Color.lightGray);

    /**
     * The constant emptyImageBorder.
     */
    public static Border emptyImageBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);

    /**
     * The constant defaultThumbBorder.
     */
    public static Border defaultThumbBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);

	private JPopupMenu popup;

	private JMenuItem pasteMenuItem;

	private String response;

	private String compareType = FileList.ORDER_BY_FILE_NAME;

	private ImageIcon thumbIcon, bigSystemIcon, smallSystemIcon;

	private String fileType;

	private FileSystemView fileSystemView;

	private Point initPosition;

	private boolean selectionStatus = false;

	private Dimension bestSize = new Dimension(10, 10);

    /**
     * The constant menuFont.
     */
    public static Font menuFont = FontTools.getFont(Font.SANS_SERIF, Font.PLAIN, 12);

    /**
     * Update virtual item for theme.
     *
     * @param currentTheme the current theme
     */
    public static void updateVirtualItemForTheme(Colors currentTheme) {
		if (currentTheme == null) {
			selectedThumbBorder = BorderFactory.createLineBorder(new ColorUIResource(122, 170, 233), 2);
		} else {
			selectedThumbBorder = BorderFactory.createLineBorder(currentTheme.getFileChooserColors()[1], 2);
		}
	}

    /**
     * Sets best size.
     *
     * @param w the w
     * @param h the h
     */
    public void setBestSize(int w, int h) {
		this.bestSize = new Dimension(w, h);
	}

    /**
     * Is directory boolean.
     *
     * @return the boolean
     */
    public boolean isDirectory() {
		return this.isDirectory;
	}

    /**
     * Gets best size.
     *
     * @return the best size
     */
    public Dimension getBestSize() {
		return this.bestSize;
	}

    /**
     * Sets compare type.
     *
     * @param compare_type the compare type
     */
    public void setCompare_type(String compare_type) {
		this.compareType = compare_type;
	}

    /**
     * Gets parent pane.
     *
     * @return the parent pane
     */
    public ItemPanel getParentPane() {
		return this.parentPane;
	}

    /**
     * Gets file.
     *
     * @return the file
     */
    public File getFile() {
		return this.file;
	}

    /**
     * Gets last modification time.
     *
     * @return the last modification time
     */
    public long getLastModificationTime() {
		if (this.fileSystemView.isFloppyDrive(this.file)) {
			return 0;
		}

		if (this.lastModification == 0) {
			this.lastModification = this.file.lastModified();
		}
		return this.lastModification;
	}

    /**
     * Gets file name.
     *
     * @return the file name
     */
    public String getFileName() {
		return this.fileName;
	}

    /**
     * Gets file size.
     *
     * @return the file size
     */
    public long getFileSize() {
		if (this.fileSystemView.isFloppyDrive(this.file) || this.file.isDirectory()) {
			return 0;
		}

		if (this.fileSize == 0) {
			this.fileSize = this.file.length();
		}
		return this.fileSize;
	}

    /**
     * Gets compare type.
     *
     * @return the compare type
     */
    public String getCompareType() {
		return this.compareType;
	}

    /**
     * Instantiates a new Item.
     *
     * @param parent the parent
     * @param f      the f
     */
    public Item(ItemPanel parent, File f) {
		if (selectedThumbBorder == null) {
			updateVirtualItemForTheme(null);
		}

		try {
			this.fileSystemView = parent.getFilePane().getFSV();
			this.file = f;
			this.parentPane = parent;

			getFileData();
			addListeners();

			this.setOpaque(false);
			this.setBorder(defaultThumbBorder);
			this.setFocusable(true);

			this.imageLabel.setFocusable(false);
			this.nameLabel.setFocusable(false);
		} catch (Exception e) {
		}
	}

	private void getFileData() {
		this.fileName = this.fileSystemView.getSystemDisplayName(this.file);

		try {
			this.isDirectory = this.file.isDirectory();
		} catch (Exception exp) {
			this.isDirectory = false;
		}

		try {
			this.isFloppyDrive = this.fileSystemView.isFloppyDrive(this.file);
		} catch (Exception exp) {
			this.isFloppyDrive = false;
		}

		try {
			this.isDrive = this.fileSystemView.isDrive(this.file);
		} catch (Exception exp) {
			this.isDrive = false;
		}

		this.nameLabel.setText(this.fileName);

		this.setToolTipText("");

		if (this.isDirectory && !this.isDrive) {
			this.fileType = "File Folder";
		} else {
			this.fileType = this.parentPane.getFilePane().cachSystemDetails(this.file, this.fileName)[1].toString();
		}
		if (this.isFloppyDrive) {
			getFloppyData();
		}
	}

    /**
     * Finalize all.
     */
    public void finalizeAll() {
		try {
			this.smallSystemIcon = null;
			this.bigSystemIcon = null;
			this.imageLabel.setIcon(null);
			this.thumbIcon = null;
			this.removeMouseListener(this);
			this.removeMouseMotionListener(this.motionListener);

			this.finalize();
		} catch (Throwable ex) {
			// do nothing
		}
	}

    /**
     * Sets image icon.
     *
     * @param pic the pic
     */
    public void setImageIcon(ImageIcon pic) {
		this.thumbIcon = pic;
		this.imageLabel.setIcon(this.thumbIcon);
	}

    /**
     * Add listeners.
     */
    void addListeners() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this.motionListener);
	}

    /**
     * Component mouse dragged.
     *
     * @param point the point
     */
    public void componentMouseDragged(Point point) {
		this.setLocation((int) (this.getX() + point.getX() - this.tx), (int) (this.getY() + point.getY() - this.ty));

		if (this.getX() > this.getParentPane().getWidth()) {
			this.getParentPane().setSize(this.getParentPane().getWidth() + 20, this.getParentPane().getHeight());
			this.getParentPane().repaint();
		}
		if (this.getY() > this.getParentPane().getHeight()) {
			this.getParentPane().setSize(this.getParentPane().getWidth(), this.getParentPane().getHeight() + 20);
			this.getParentPane().repaint();
		}
	}

    /**
     * Component mouse pressed.
     *
     * @param e the e
     */
    public void componentMousePressed(MouseEvent e) {
		this.tx = e.getX();
		this.ty = e.getY();
		this.initPosition = this.getLocation();
	}

    /**
     * Component mouse released.
     *
     * @param e the e
     */
    public void componentMouseReleased(MouseEvent e) {
		if (this.parentPane.getFilePane().isAutoArrange()) {
			this.setLocation(this.initPosition);
		} else {
			return;
		}
	}

    /**
     * Gets item name.
     *
     * @return the item name
     */
    public String getItemName() {
		return this.fileName;
	}

    /**
     * Gets item file size.
     *
     * @return the item file size
     */
    public long getItemFileSize() {
		return this.fileSize;
	}

    /**
     * Gets item file last modified.
     *
     * @return the item file last modified
     */
    public long getItemFileLastModified() {
		return this.lastModification;
	}

    /**
     * Update selection mode.
     *
     * @param isSelected the is selected
     */
    public void updateSelectionMode(boolean isSelected) {
		if (isSelected == this.selectionStatus) {
			return;
		}

		this.selectionStatus = isSelected;

		if (isSelected) {
			if (this.parentPane.filePane.filechooserUI.viewType.equals(FileChooserUI.FILECHOOSER_VIEW_THUMBNAIL)
					|| this.parentPane.filePane.filechooserUI.viewType.equals(FileChooserUI.FILECHOOSER_VIEW_ICON)) {
				this.setBorder(selectedThumbBorder);
				this.nameLabel.setMultiLine(true);
				this.setSize((int) this.bestSize.getWidth(), (int) this.bestSize.getHeight() + this.nameLabel.getLineDiff());
			}

			this.nameLabel.setOpaque(true);
			this.nameLabel.setNeed_update(true);
			this.nameLabel.setForeground(UIManager.getColor("textHighlightText"));
		} else {
			if (this.parentPane.filePane.filechooserUI.viewType.equals(FileChooserUI.FILECHOOSER_VIEW_THUMBNAIL)
					|| this.parentPane.filePane.filechooserUI.viewType.equals(FileChooserUI.FILECHOOSER_VIEW_ICON)) {
				this.setBorder(defaultThumbBorder);
			}

			this.setSize((int) this.bestSize.getWidth(), (int) this.bestSize.getHeight());
			this.nameLabel.setMultiLine(false);

			this.nameLabel.setOpaque(false);
			this.nameLabel.setForeground(UIManager.getColor("textText"));
		}
		repaint();
	}

    /**
     * Gets popup menu.
     *
     * @return the popup menu
     */
    public JPopupMenu getPopupMenu() {
		getAdditionalFileData();
		createPopupMenu();
		return this.popup;
	}

    /**
     * Sets add to bookmarks menu item status.
     *
     * @param val the val
     */
    public void setAddToBookmarksMenuItemStatus(boolean val) {
		if (this.addToBookmarksMenuItem != null) {
			this.addToBookmarksMenuItem.setVisible(val);
		}
	}

    /**
     * Create popup menu.
     */
    public void createPopupMenu() {
		if (this.popup != null) {
			return;
		}
		this.popup = new JPopupMenu();

		JMenuItem menuItem;

		if (this.isDirectory) {

			menuItem = new JMenuItem(OPEN_ACTION);
			// menuItem.setFont(strongMenuFont);
			this.popup.add(menuItem);

			if (getFileChooser().isDirectorySelectionEnabled()) {
				menuItem = new JMenuItem(SELECT_ACTION);
				menuItem.setFont(menuFont);
				this.popup.add(menuItem);
			}

			// no separator for remote files
			if (!(this.fileSystemView instanceof RemoteFileSystemView)) {
				this.popup.addSeparator();
			}

			// no bookmarks for remote files
			if (!(this.fileSystemView instanceof RemoteFileSystemView)) {
				this.addToBookmarksMenuItem = new JMenuItem(ADD_TO_BOOKMARKS_ACTION);
				this.addToBookmarksMenuItem.setFont(menuFont);
				if (!new File(this.file.getAbsolutePath()).exists()) {
					this.addToBookmarksMenuItem.setEnabled(false);
				}
				this.popup.add(this.addToBookmarksMenuItem);
			}

			this.popup.getComponent();

			// for remote files only show rename if renaming is possible
			if (!(this.fileSystemView instanceof RemoteFileSystemView
					&& !((RemoteFileSystemView) this.fileSystemView).isRenamingEnabled())) {
				menuItem = new JMenuItem(RENAME_ACTION);
				this.popup.add(menuItem);
			}

			// for remote files only show delete if deleting is possible
			if (!(this.fileSystemView instanceof RemoteFileSystemView
					&& !((RemoteFileSystemView) this.fileSystemView).isDeletingEnabled())) {
				menuItem = new JMenuItem(DELETE_ACTION);
				this.popup.add(menuItem);
			}
		} else {
			menuItem = new JMenuItem(SELECT_ACTION);
			// menuItem.setFont(strongMenuFont);
			this.popup.add(menuItem);

			// no separator for remote files
			if (!(this.fileSystemView instanceof RemoteFileSystemView)) {
				this.popup.addSeparator();
			}

			// for remote files only show rename if renaming is possible
			if (!(this.fileSystemView instanceof RemoteFileSystemView
					&& !((RemoteFileSystemView) this.fileSystemView).isRenamingEnabled())) {
				menuItem = new JMenuItem(RENAME_ACTION);
				this.popup.add(menuItem);
			}

			// for remote files only show delete if deleting is possible
			if (!(this.fileSystemView instanceof RemoteFileSystemView
					&& !((RemoteFileSystemView) this.fileSystemView).isDeletingEnabled())) {
				menuItem = new JMenuItem(DELETE_ACTION);
				this.popup.add(menuItem);
			}
		}
	}

	private void renameFile() {
		this.response = SwingTools.showInputDialog("file_chooser.rename", this.fileName,
				(isDirectory() ? "directory" : "file") + " " + this.fileName);
		try {
			if (this.response == null || this.response.equals("")) {
				SwingTools.showVerySimpleErrorMessage("file_chooser.rename.invalid");
			} else {
				if (this.file.renameTo(new File(this.file.getParentFile(), this.response))) {
					this.file = new File(this.file.getParentFile(), this.response);
					getFileData();
					this.parentPane.filePane.rescanDirectory();
					this.repaint();
				} else {
					SwingTools.showVerySimpleErrorMessage("file_chooser.rename.error");
				}
			}
		} catch (Exception exp) {
			// do nothing
		}
	}

    /**
     * Sets paste menu item status.
     *
     * @param state the state
     */
    public void setPasteMenuItemStatus(boolean state) {
		if (this.isDirectory && this.pasteMenuItem != null) {
			this.pasteMenuItem.setEnabled(state);
		}
	}

    /**
     * Convert to correct format string.
     *
     * @param f the f
     * @return the string
     */
    public String convertToCorrectFormat(long f) {
		if (this.isDirectory) {
			return "";
		}

		f = f / 1024L;
		if (f < 1024L) {
			return (f == 0L ? 1L : f) + " KB";
		} else {
			f /= 1024L;
			if (f < 1024L) {
				return f + " MB";
			} else {
				f /= 1024L;
				return f + " GB";
			}
		}
	}

	@Override
	public int compareTo(Item other) {
		int res = 0;
		boolean isFirstDir = this.getFile().isDirectory();
		boolean isSecondDir = other.getFile().isDirectory();

		if (isFirstDir && !isSecondDir) {
			res = -1;
		} else if (!isFirstDir && isSecondDir) {
			res = 1;
		} else {
			if (this.compareType.equals(FileList.ORDER_BY_FILE_NAME)) {
				res = this.getFileName().toLowerCase().compareTo(other.getFileName().toLowerCase());
			} else if (this.compareType.equals(FileList.ORDER_BY_FILE_SIZE)) {
				res = Long.valueOf(this.getFileSize()).compareTo(Long.valueOf(other.getFileSize()));
			} else if (this.compareType.equals(FileList.ORDER_BY_FILE_MODIFIED)) {
				res = Long.valueOf(this.getLastModificationTime()).compareTo(Long.valueOf(other.getLastModificationTime()));
			} else if (this.compareType.equals(FileList.ORDER_BY_FILE_TYPE)) {
				res = this.getFileType().compareTo(other.getFileType());
			}
		}

		if (res == 0) {
			res = -1;
		}
		return res;

	}

	private boolean delete() {
		return delete(this.getFile());
	}

	private boolean delete(File file) {
		if (file.isDirectory()) {
			File[] children = fileSystemView.getFiles(file, false);
			boolean result = true;
			for (File child : children) {
				result &= delete(child);
			}
			return result && file.delete();
		} else {
			return file.delete();
		}
	}

    /**
     * Update thumbnail.
     */
    public void updateThumbnail() {
		if (isImage()) {
			this.imageLabel.setIcon(getThumbnailIcon());
		}
	}

    /**
     * Update item icon.
     */
    public void updateItemIcon() {
		if (this.parentPane.filePane.filechooserUI.getView().equals(FileChooserUI.FILECHOOSER_VIEW_THUMBNAIL)
				|| this.parentPane.filePane.filechooserUI.getView().equals(FileChooserUI.FILECHOOSER_VIEW_ICON)) {
			this.imageLabel.setIcon(getBigSystemIcon());
		} else if (this.parentPane.filePane.filechooserUI.getView().equals(FileChooserUI.FILECHOOSER_VIEW_LIST)
				|| this.parentPane.filePane.filechooserUI.getView().equals(FileChooserUI.FILECHOOSER_VIEW_DETAILS)) {
			this.imageLabel.setIcon(getSmallSystemIcon());
		}
	}

	private ImageIcon getThumbnailIcon() {
		if (this.thumbIcon == null) {
			if (this.isDirectory) {
				this.thumbIcon = getBigSystemIcon();
			} else {
				try {
					this.thumbIcon = new ImageIcon(Tools.getScaledInstance(this.file));
					if (this.thumbIcon == null) {
						this.thumbIcon = getBigSystemIcon();
					}
				} catch (Exception ex) {
					this.thumbIcon = getBigSystemIcon();
				}
			}
		}
		return this.thumbIcon;
	}

    /**
     * Gets small system icon.
     *
     * @return the small system icon
     */
    public ImageIcon getSmallSystemIcon() {
		if (this.smallSystemIcon == null) {
			try {
				this.smallSystemIcon = this.parentPane.getFilePane().getSystemIcon(this.file, this.fileName,
						this.isDirectory, false);
			} catch (Exception ex) {
			}
		}
		return this.smallSystemIcon;
	}

	private ImageIcon getBigSystemIcon() {
		if (this.bigSystemIcon == null) {
			try {
				this.bigSystemIcon = this.parentPane.getFilePane().getSystemIcon(this.file, this.fileName, this.isDirectory,
						true);
			} catch (Exception ex) {
				// do nothing
			}
		}
		return this.bigSystemIcon;
	}

    /**
     * Gets file type.
     *
     * @return the file type
     */
    public String getFileType() {
		return this.fileType;
	}

	private boolean isImage() {
		String file_original_name = this.file.getName().toLowerCase();
		if (file_original_name.endsWith("jpeg") || file_original_name.endsWith("jpg") || file_original_name.endsWith("png")
				|| file_original_name.endsWith("gif") || file_original_name.endsWith("bmp")
				|| file_original_name.endsWith("tif") || file_original_name.endsWith("tiff")
				|| file_original_name.endsWith("png")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			if (this.isDirectory) {
				updateChooserPath();
			} else {
				this.parentPane.filePane.fc.setSelectedFile(getFile());
				getParentPane().getFilePane().filechooserUI.getApproveSelectionAction().actionPerformed(null);
			}
		}
	}

    /**
     * Update chooser path.
     */
    protected void updateChooserPath() {
		this.parentPane.filePane.filechooserUI.setCurrentDirectoryOfFileChooser(this.file);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		getAdditionalFileData();
	}

	private void getFloppyData() {
		this.fileSize = 0;
		this.lastModification = 0;
		this.setToolTipText("<html><body>" + "<strong>" + this.fileName + "</strong><br>" + "Last modified : "
				+ java.text.DateFormat.getDateInstance().format(new java.util.Date(this.lastModification)) + "<br>"
				+ (this.isDirectory ? "" : "File size : " + convertToCorrectFormat(this.fileSize) + "<br>")
				+ "File Description : " + this.fileType + "</body></html>");
	}

    /**
     * Gets additional file data.
     */
    protected void getAdditionalFileData() {
		if (this.getToolTipText().equals("")) {
			if (this.isFloppyDrive) {
				this.fileSize = 0;
				this.lastModification = 0;
			} else {
				this.fileSize = this.file.length();
				this.lastModification = this.file.lastModified();
			}

			if (new File(this.file.getAbsolutePath()).exists()) {
				this.setToolTipText("<html><body>" + "<strong>" + this.fileName + "</strong><br>" + "Last modified : "
						+ java.text.DateFormat.getDateInstance().format(new java.util.Date(this.lastModification)) + "<br>"
						+ (this.isDirectory ? "" : "File size : " + convertToCorrectFormat(this.fileSize) + "<br>")
						+ "File Description : " + this.fileType + "<br>" + this.file.getPath() + "</body></html>");
			} else {
				this.setToolTipText("<html><body>" + "<strong>" + this.fileName + "</strong>" + "</body></html>");
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		this.requestFocusInWindow();
		processEvent(e);
		this.getParentPane().selectedComponentMousePressed(e);
		evaluateClick(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.getParentPane().selectedComponentMouseReleased(e);
		evaluateClick(e);
	}

	private void evaluateClick(MouseEvent e) {
		if (e.isPopupTrigger()) {
			try {
				createPopupMenu();
				this.popup.show(e.getComponent(), e.getX(), e.getY());
			} catch (Exception exp) {
				// do nothing
			}
		}
	}

    /**
     * Sets selection mode.
     *
     * @param b the b
     */
    public void setSelectionMode(boolean b) {
		this.selectionStatus = b;
	}

    /**
     * Gets selection mode.
     *
     * @return the selection mode
     */
    public boolean getSelectionMode() {
		return this.selectionStatus;
	}

	private void processEvent(MouseEvent e) {
		this.getParentPane().getFilePane().updateFilechooserSelectedItems(this, SwingTools.isControlOrMetaDown(e));
	}

	private JFileChooser getFileChooser() {
		return this.getParentPane().getFilePane().fc;
	}
}
