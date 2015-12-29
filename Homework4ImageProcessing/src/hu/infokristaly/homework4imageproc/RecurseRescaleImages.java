package hu.infokristaly.homework4imageproc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class RecurseRescaleImages {

    private String startDir;
    private String pattern;
    private String targetDir;

    public void run() {
        Path startDirPath = Paths.get(startDir);
        SimpleFileVisitor<? super Path> finder = new Finder(startDir, targetDir, pattern);

        try {
            Date start = new Date();
            Files.walkFileTree(startDirPath, finder);
            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public RecurseRescaleImages(String startDir, String targetDir, String pattern) {
        this.startDir = startDir;
        this.targetDir = targetDir;
        this.pattern = pattern;
    }

    public static class Finder extends SimpleFileVisitor<Path> {

        private String targetRoot;
        private String sourceRoot;
        private Path sourcePath;
        private Path targetPath;
        private final PathMatcher matcher;
        private int numMatches = 0;
        private int optw = 543 + 292;
        private int minw = 543;
        private int maxh = 615;
        private int maxw = 1086;

        public Finder(String source, String target, String pattern) {
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
            targetRoot = target;
            sourceRoot = source;
            sourcePath = (new File(sourceRoot)).toPath();
            targetPath = sourcePath.resolve(targetRoot);
        }

        public void find(Path file, BasicFileAttributes attrs) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                numMatches++;
                System.out.println(file);
                if (attrs.isRegularFile()) {
                    try {
                        BufferedImage image = (BufferedImage) ImageProcessorUtils.getImage(file);
                        int width = image.getWidth();

                        if (image.getWidth() > maxw) {
                            width = 1086;
                            image = ImageProcessorUtils.getScaledImageWithImgscalr(image, width, maxh);
                        }
                        
                        if ((width < maxw) && (width > minw)) {
                            if (image.getWidth() < optw) {
                                width = minw;
                            }
                            image = ImageProcessorUtils.getScaledImageWithImgscalr(image, width, maxh);
                        }
                        
                        if (image.getHeight() > maxh) {
                            image = ImageProcessorUtils.getScaledImageWithImgscalr(image, width, maxh);
                        }

                        Path relativePath = sourcePath.relativize(file);
                        if (!("".equals(relativePath.toString()))) {
                            File fTarget = new File(targetPath.toString() + File.separator + relativePath.toString());
                            ImageProcessorUtils.saveBufferedImage(image, fTarget);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void done() {
            System.out.println("Matched: " + numMatches);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            find(file, attrs);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            if (attrs.isDirectory()) {
                Path relativePath = sourcePath.relativize(dir);
                if (!("".equals(relativePath.toString()))) {
                    File fDir = new File(targetPath.toString() + File.separator + relativePath.toString());
                    if (!(fDir).exists()) {
                        (fDir).mkdirs();
                    }
                }
            }
            find(dir, attrs);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
            if (e == null) {
                return FileVisitResult.CONTINUE;
            } else {
                // directory iteration failed
                throw e;
            }
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            System.err.println(exc);
            return FileVisitResult.CONTINUE;
        }
    }
}
