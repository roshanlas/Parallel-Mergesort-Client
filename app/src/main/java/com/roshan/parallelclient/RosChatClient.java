package com.roshan.parallelclient;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Roshan Lasrado on 3/22/2016.
 */
public class RosChatClient implements Runnable {
    static Socket clientSocket = null;
    static PrintStream os = null;
    static DataInputStream is = null;
    //static BufferedReader inputLine = null;
    static boolean closed = false;
    int port_number = 8888;
    String host = "192.168.1.1";
    Context context;

    public RosChatClient(String serverAddr, int portNum, Context context) {
        host = serverAddr;
        port_number = portNum;
        this.context = context;
    }

    public void runCommunicator() {
        HomeActivity.setPromptText("Looking for Parallel Server at " + host + ":" + port_number);
        try {
            clientSocket = new Socket(host, port_number);
            //inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    HomeActivity.setPromptText("Cannot find host at " + host + ":" + port_number);
                }
            });
        } catch (final IOException e) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    HomeActivity.setPromptText(e.toString());
                }
            });
        }
        if (clientSocket != null && os != null && is != null) {
            try {
                new Thread(new RosChatClient(host, port_number, context)).start();
                os.println(clientSocket.getLocalAddress().toString());
                while (!closed) {
                    //os.println(inputLine.readLine());
                }
                os.close();
                is.close();
                clientSocket.close();
            } catch (final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        HomeActivity.setPromptText(e.toString());
                    }
                });
            }
        }
    }

    public void run() {
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                final String finalResponseLine = responseLine;
                final String temp;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        HomeActivity.setPromptText(finalResponseLine);
                    }
                });
                if (responseLine.contains("Bye")) break;
                if (responseLine.contains(",")) {
                    temp = MergeSort(responseLine);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            HomeActivity.setPromptText(temp);
                        }
                    });
                    os.println(temp);
                }
            }
            closed = true;
        } catch (final IOException e) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    HomeActivity.setPromptText(e.toString());
                }
            });
        }
    }

    private String MergeSort(String data) {
        String[] strArray = data.split(",");
        int[] intArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        //Log.d("TEST", Arrays.asList(intArray).toString());
        doMergeSort(intArray, 0, intArray.length - 1);
        List<Integer> list = new ArrayList<Integer>();
        for (int index = 0; index < intArray.length; index++)
        {
            list.add(intArray[index]);
        }
        return TextUtils.join(",", list);
    }

    private void doMergeSort(int a[], int lowerIndex, int higherIndex) {

        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doMergeSort(a, lowerIndex, middle);
            // Below step sorts the right side of the array
            doMergeSort(a, middle + 1, higherIndex);
            // Now merge both sides
            mergeParts(a, lowerIndex, middle, higherIndex);
        }
    }

    private void mergeParts(int a[], int lowerIndex, int middle, int higherIndex) {
        int tempMergArr[] = new int[a.length];
        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = a[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i] <= tempMergArr[j]) {
                a[k] = tempMergArr[i];
                i++;
            } else {
                a[k] = tempMergArr[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            a[k] = tempMergArr[i];
            k++;
            i++;
        }
    }
}