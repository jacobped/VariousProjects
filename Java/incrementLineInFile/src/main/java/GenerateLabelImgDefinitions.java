import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jacob on 2018-03-18 (YYYY-MM-DD).
 */
public class GenerateLabelImgDefinitions {
//    private static long uniqueValue = 1521329230;

    public static void main(String[] args) throws IOException {
        File imageDir = new File("/home/jacob/andet/training/docker-training-shared/subTraining/train/nosub");
        File trainingOutputDit = new File("/home/jacob/andet/training/docker-training-shared/subTraining/train/trainingNoSub");

        generate(imageDir, trainingOutputDit);
    }

    private static void generate(File imageDir, File trainingOutputDir) throws IOException {
        if( ! Files.isDirectory(imageDir.toPath()) && ! Files.isDirectory(trainingOutputDir.toPath())) {
            throw new RuntimeException("One of the inputs is not a directory");
        }

        Collection<File> imagefiles = FileUtils.listFiles(imageDir, new String[]{"jpg"}, false);

        for (File imageFile : imagefiles) {
            List<String> result = new ArrayList<>(1);
            String fileNumber = getFileNumber(imageFile);
            String fileName = fileNumber + ".xml";
            String content = generateTrainingFile(fileNumber);
            result.add(content);

            // Save content to proper file.
            Path trainingFilePath = Paths.get(trainingOutputDir.getAbsolutePath(), fileName);
            File file = trainingFilePath.toFile();
            FileUtils.writeLines(file, result);
        }
    }

    private static String generateTrainingFile(String fileNumber) {

        String s = "<annotation>\n" +
                "\t<folder>nosub</folder>\n" +
                "\t<filename>" + fileNumber + ".jpg</filename>\n" +
                "\t<path>/home/jacob/andet/training/docker-training-shared/subTraining/train/nosub/" + fileNumber + ".jpg</path>\n" +
                "\t<source>\n" +
                "\t\t<database>Unknown</database>\n" +
                "\t</source>\n" +
                "\t<size>\n" +
                "\t\t<width>400</width>\n" +
                "\t\t<height>225</height>\n" +
                "\t\t<depth>3</depth>\n" +
                "\t</size>\n" +
                "\t<segmented>0</segmented>\n" +
                "\t<object>\n" +
                "\t\t<name>nosub</name>\n" +
                "\t\t<pose>Unspecified</pose>\n" +
                "\t\t<truncated>1</truncated>\n" +
                "\t\t<difficult>0</difficult>\n" +
                "\t\t<bndbox>\n" +
                "\t\t\t<xmin>17</xmin>\n" +
                "\t\t\t<ymin>1</ymin>\n" +
                "\t\t\t<xmax>382</xmax>\n" +
                "\t\t\t<ymax>225</ymax>\n" +
                "\t\t</bndbox>\n" +
                "\t</object>\n" +
                "</annotation>";

        return s;
    }

    private static String getFileNumber(File imageFile) {
        String name = imageFile.getName().substring(0, 5);
        return name;
    }
}
