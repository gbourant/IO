package com.gbourant.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gbourant
 */
public class IOTest {

    public IOTest() {
    }

    Path root = Paths.get("./testing");
    Path fileToDelete = Paths.get(root.toString() + "/tmp/fileUnderTest.txt");
    Path fileUnderTest = Paths.get("/tmp/fileUnderTest.txt");
    Path folderToCopy = Paths.get("./testing2");
    String expResult = "hello";
    IO io = new IO(root.toString());
    IO ioFolderToDelete = new IO("./testing2");

    @Before
    public void setUp() {
        try {
            ioFolderToDelete.deleteRootRecursively();
            io.deleteRootRecursively();
            Files.createDirectory(root);
        } catch (IOException ex) {
            Logger.getLogger(IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
        try {
            ioFolderToDelete.deleteRootRecursively();
            io.deleteRootRecursively();
        } catch (IOException ex) {
            Logger.getLogger(IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeFile() {
        try {
            io.writeFile(fileUnderTest, expResult.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testReadFileAsByte_Path() throws Exception {
        this.writeFile();
        byte[] readFileAsByte = io.readFileAsByte(fileUnderTest);
        String result = new String(readFileAsByte);
        assertEquals(expResult, result);
    }

    @Test
    public void testReadFileAsByte_String() throws Exception {
        this.writeFile();
        byte[] readFileAsByte = io.readFileAsByte(fileUnderTest.toString());
        String result = new String(readFileAsByte);
        assertEquals(expResult, result);
    }

    @Test
    public void testReadFileAsString_Path() throws Exception {
        this.writeFile();
        String readFileAsString = io.readFileAsString(fileUnderTest);
        assertEquals(expResult, readFileAsString);
    }

    @Test
    public void testReadFileAsString_String() throws Exception {
        this.writeFile();
        String readFileAsString = io.readFileAsString(fileUnderTest.toString());
        assertEquals(expResult, readFileAsString);
    }

    @Test
    public void testWriteFile_Path_byteArr() throws Exception {
        io.writeFile(fileUnderTest, expResult.getBytes());
        String readAllBytes = io.readFileAsString(fileUnderTest);
        assertEquals(expResult, readAllBytes);

    }

    @Test
    public void testWriteFile_String_byteArr() throws Exception {
        io.writeFile(fileUnderTest.toString(), expResult.getBytes());
        String readAllBytes = io.readFileAsString(fileUnderTest);
        assertEquals(expResult, readAllBytes);
    }

    @Test
    public void testWriteFile_Path_String() throws Exception {
        io.writeFile(fileUnderTest, expResult);
        String readAllBytes = io.readFileAsString(fileUnderTest);
        assertEquals(expResult, readAllBytes);
    }

    @Test
    public void testWriteFile_String_String() throws Exception {
        io.writeFile(fileUnderTest.toString(), expResult);
        String readAllBytes = io.readFileAsString(fileUnderTest);
        assertEquals(expResult, readAllBytes);
    }

    @Test
    public void testAppendFile_Path_byteArr() throws Exception {
        io.appendFile(fileUnderTest, expResult.getBytes());
        String readAllBytes = io.readFileAsString(fileUnderTest);
        assertEquals(expResult, readAllBytes);
    }

    @Test
    public void testAppendFile_String_byteArr() throws Exception {
        io.appendFile(fileUnderTest.toString(), expResult.getBytes());
        String readAllBytes = io.readFileAsString(fileUnderTest);
        assertEquals(expResult, readAllBytes);
    }

    @Test
    public void testAppendFile_Path_String() throws Exception {
        io.appendFile(fileUnderTest, expResult);
        String readAllBytes = io.readFileAsString(fileUnderTest);
        assertEquals(expResult, readAllBytes);
    }

    @Test
    public void testAppendFile_String_String() throws Exception {
        io.appendFile(fileUnderTest.toString(), expResult);
        String readAllBytes = io.readFileAsString(fileUnderTest);
        assertEquals(expResult, readAllBytes);
    }

    @Test
    public void testCreateFile_Path() throws Exception {
        io.createFile(fileUnderTest);
        boolean exists = Files.exists(Paths.get(root.toString() + fileUnderTest.toString()));
        assertTrue(exists);
    }

    @Test
    public void testCreateFile_String() throws Exception {
        io.createFile(fileUnderTest.toString());
        boolean exists = Files.exists(Paths.get(root.toString() + fileUnderTest.toString()));
        assertTrue(exists);
    }

    @Test
    public void testDeleteFile_Path() throws Exception {
        this.writeFile();
        io.deleteFile(fileUnderTest);
        boolean exists = Files.exists(Paths.get(root.toString() + fileUnderTest.toString()));
        assertFalse(exists);
    }

    @Test
    public void testDeleteFile_String() throws Exception {
        this.writeFile();
        io.deleteFile(fileUnderTest.toString());
        boolean exists = Files.exists(Paths.get(root.toString() + fileUnderTest.toString()));
        assertFalse(exists);
    }

    @Test
    public void testDeleteFolderRecursively_Path() throws Exception {
        this.writeFile();
        io.deleteFolderRecursively(fileUnderTest);
        boolean exists = Files.exists(Paths.get(root.toString() + fileUnderTest.toString()));
        assertFalse(exists);
    }

    @Test
    public void testDeleteFolderRecursively_String() throws Exception {
        this.writeFile();
        io.deleteFolderRecursively(fileUnderTest.toString());
        boolean exists = Files.exists(Paths.get(root.toString() + fileUnderTest.toString()));
        assertFalse(exists);
    }

    @Test
    public void testDeleteRootRecursively() throws Exception {
        this.writeFile();
        io.deleteRootRecursively();
        boolean exists = Files.exists(root);
        assertFalse(exists);
    }

    @Test
    public void testCreateFolders_Path() throws Exception {
        Path folders = Paths.get("/tmp1/tmp2/tmp3");
        io.createFolders(folders);
        boolean exists = Files.exists(Paths.get(root.toString() + folders.toString()));
        assertTrue(exists);
    }

    @Test
    public void testCreateFolders_String() throws Exception {
        Path folders = Paths.get("/tmp1/tmp2/tmp3");
        io.createFolders(folders.toString());
        boolean exists = Files.exists(Paths.get(root.toString() + folders.toString()));
        assertTrue(exists);
    }

    @Test
    public void testCreateRootFolder() throws Exception {
        io.deleteRootRecursively();
        io.createRootFolder();
        boolean exists = Files.exists(root);
        assertTrue(exists);
    }

    @Test
    public void testCopyFromTo_Path_Path() throws Exception {
        io.writeFile("/tmp.txt", "hey!");
        io.copyFromTo(root, folderToCopy);
        boolean exists = Files.exists(folderToCopy);
        assertTrue(exists);
    }

    @Test
    public void testCopyFromTo_String_Path() throws Exception {
        io.writeFile("/tmp.txt", "hey!");
        io.copyFromTo(root.toString(), folderToCopy);
        boolean exists = Files.exists(folderToCopy);
        assertTrue(exists);
    }

    @Test
    public void testCopyFromTo_Path_String() throws Exception {
        io.writeFile("/tmp.txt", "hey!");
        io.copyFromTo(root, folderToCopy.toString());
        boolean exists = Files.exists(folderToCopy);
        assertTrue(exists);
    }

    @Test
    public void testCopyFromTo_String_String() throws Exception {
        io.writeFile("/tmp.txt", "hey!");
        io.copyFromTo(root.toString(), folderToCopy.toString());
        boolean exists = Files.exists(folderToCopy);
        assertTrue(exists);
    }
}
