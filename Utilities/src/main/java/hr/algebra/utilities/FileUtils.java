package hr.algebra.utilities;

import hr.algebra.utilities.web.UrlConnectionFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


public class FileUtils {
    private FileUtils() {
    }
    
    private static final String SAVE = "Save";
    private static final String TXT = "txt";
    private static final String TEXT_DOCUMENTS_TXT = "Text documents (*txt)";
    
    public static Optional<File> saveFileDialog() throws IOException {
        Optional<File> optFile = Optional.empty();
        
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileNameExtensionFilter(TEXT_DOCUMENTS_TXT, TXT));
        chooser.setDialogTitle(SAVE);
        chooser.setApproveButtonText(SAVE);
        chooser.setApproveButtonToolTipText(SAVE);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            if (!selectedFile.toString().endsWith(TXT)) {
                selectedFile = new File(selectedFile
                        .toString()
                        .concat(".")
                        .concat(TXT));
            }
            optFile = Optional.of(selectedFile);
        }

        return optFile;
    }
    
    private static void createDirHierarchy(String destination) throws IOException {
        String dir = destination.substring(0, destination.lastIndexOf(File.separator));
        if (!Files.exists(Paths.get(dir))) {
            Files.createDirectories(Paths.get(dir));
        }
    }
      
    public static void copyFromUrl(String source, String destination) throws MalformedURLException, IOException {
        createDirHierarchy(destination);
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(source);
        try (InputStream is = con.getInputStream()) {
            Files.copy(is, Paths.get(destination));
        }
    }
}
