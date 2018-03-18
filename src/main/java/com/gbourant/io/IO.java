package com.gbourant.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Use path without trailing "/"
 * <pre>
 * {@code
 *  Path root = Paths.get("./testing");
 *  IO io = new IO(root.toString());
 * }
 * </pre>
 *
 * @author gbourant
 */
public class IO {

    public String path = null;

    public IO(String path) {
        this.path = path;
    }

    public IO(Path path) {
        this(path.toString());
    }

    private Path getPath(Path path) {
        if (this.path == null) {
            throw new NullPointerException("[this.path] is null , please initialize");
        }
        String tmpPath = this.path + path.toFile().toString();
        return Paths.get(tmpPath);
    }

    public byte[] readFileAsByte(Path path) throws IOException {
        return Files.readAllBytes(this.getPath(path));
    }

    public byte[] readFileAsByte(String path) throws IOException {
        return this.readFileAsByte(Paths.get(path));
    }

    public String readFileAsString(Path path) throws IOException {
        return new String(this.readFileAsByte(path));
    }

    public String readFileAsString(String path) throws IOException {
        return this.readFileAsString(Paths.get(path));
    }

    /*
        If the file does not exist, it gets created.
        If the file does exist, it gets overwritten.
        If the folders/sub folders does not exist, they get created.
     */
    public IO writeFile(Path path, byte[] text) throws IOException {
        this.createFolders(path.getParent());
        Files.write(this.getPath(path), text);
        return this;
    }

    public IO writeFile(String path, byte[] text) throws IOException {
        return this.writeFile(Paths.get(path), text);
    }

    public IO writeFile(Path path, String text) throws IOException {
        return this.writeFile(path, text.getBytes());
    }

    public IO writeFile(String path, String text) throws IOException {
        return this.writeFile(Paths.get(path), text);
    }

    /*
        If the file does not exist, it gets created and it appends to it.
        If the file does exist, appends operation get executed.
        If the folders/sub folders does not exist, they get created.
     */
    public IO appendFile(Path path, byte[] text) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            this.createFolders(parent);
        }
        Files.write(this.getPath(path), text, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        return this;
    }

    public IO appendFile(String path, byte[] text) throws IOException {
        return this.appendFile(Paths.get(path), text);
    }

    public IO appendFile(Path path, String text) throws IOException {
        return this.appendFile(path, text.getBytes());
    }

    public IO appendFile(String path, String text) throws IOException {
        return this.appendFile(Paths.get(path), text);
    }

    /*
        If the file does not exist, it gets created.
        If the file does exist, it throws an exception.
        If the folders/sub folders does not exist, they get created.
     */
    public IO createFile(Path fileName) throws IOException {
        this.createFolders(fileName.getParent());
        Files.createFile(this.getPath(fileName));
        return this;
    }

    public IO createFile(String fileName) throws IOException {
        return this.createFile(Paths.get(fileName));
    }

    public IO deleteFile(Path directory) throws IOException {
        Files.deleteIfExists(this.getPath(directory));
        return this;
    }

    public IO deleteFile(String directory) throws IOException {
        return this.deleteFile(Paths.get(directory));
    }

    public IO deleteFolderRecursively(Path directory) throws IOException {
        Path toDelete = this.getPath(directory);
        if (!Files.exists(toDelete)) {
            return this;
        }
        Files.walkFileTree(toDelete, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
        return this;
    }

    public IO deleteFolderRecursively(String directory) throws IOException {
        return this.deleteFolderRecursively(Paths.get(directory));
    }

    public IO deleteRootRecursively() throws IOException {
        return this.deleteFolderRecursively(Paths.get(""));
    }
    
    /*
        If the folders/sub folders does not exist, they get created.
     */
    public IO createFolders(Path directory) throws IOException {
        Files.createDirectories(this.getPath(directory));
        return this;
    }

    public IO createFolders(String directory) throws IOException {
        return this.createFolders(Paths.get(directory));
    }

    public IO createRootFolder() throws IOException {
        return this.createFolders(Paths.get(""));
    }

    public IO copyFromTo(final Path from, final Path to) throws IOException {
        Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Files.createDirectories(to.resolve(from.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, to.resolve(from.relativize(file)));
                return FileVisitResult.CONTINUE;
            }
        });
        return this;
    }

    public IO copyFromTo(String from, Path to) throws IOException {
        return this.copyFromTo(Paths.get(from), to);
    }

    public IO copyFromTo(Path from, String to) throws IOException {
        return this.copyFromTo(from, Paths.get(to));
    }

    public IO copyFromTo(String from, String to) throws IOException {
        return this.copyFromTo(Paths.get(from), Paths.get(to));
    }

}
