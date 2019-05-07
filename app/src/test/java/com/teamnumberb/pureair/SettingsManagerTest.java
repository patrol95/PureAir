package com.teamnumberb.pureair;

import android.content.Context;

import com.teamnumberb.pureair.mocks.ContextMock;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class SettingsManagerTest {
    private static SettingsData dummyData = new SettingsData(100, 500, 2);
    private static final String TEST_STREAM_NAME = "testStream";
    private static String filename = "settings";
    private FileOutputStream outputStream;
    private FileInputStream inputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ContextMock contextMock;
    private SettingsManager uut;

    private void setupTest() {
        contextMock = new ContextMock();
        contextMock.expectedOutputFilename = filename;
        contextMock.expectedInputFilename = filename;
        contextMock.expectedOutputFileMode = Context.MODE_PRIVATE;
        try {
            outputStream = new FileOutputStream(TEST_STREAM_NAME, false);
            inputStream = new FileInputStream(TEST_STREAM_NAME);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (Exception e) {
            fail();
        }
        contextMock.inputStreamToReturn = inputStream;
        contextMock.outputStreamToReturn = outputStream;
        uut = new SettingsManager(contextMock);
    }
}
