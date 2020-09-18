package com.training.functions;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.training.constants.Constants;
import com.training.pointentity.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExportPointsAsyncTask extends AsyncTask<String, String, String> {
    Context context;
    private ArrayList<Point> pointList;
    ProgressDialog progressDialog;

    ExportPointsAsyncTask(Context context, ArrayList<Point> pointList) {
        this.context = context;
        this.pointList = pointList;
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setTitle("Progress Dialog");
    }

    @Override
    protected String doInBackground(String... strings) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + Constants.DIR_NAME);
            if (!Dir.exists()) {
                Dir.mkdir();
            }
            File file = new File(Dir, Constants.CSV_FILE_NAME);

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file, false);

                String content = Constants.NAME + Constants.COMMA + Constants.X + Constants.COMMA + Constants.Y + Constants.LINE_SEPERATOR;
                fileOutputStream.write(content.getBytes());

                for (int i = 0; i < this.pointList.size(); i++) {
                    Point pointObj = this.pointList.get(i);
                    content = pointObj.getName() + Constants.COMMA + Double.toString(pointObj.getX())
                            + Constants.COMMA + Double.toString(pointObj.getY()) + Constants.LINE_SEPERATOR;
                    fileOutputStream.write(content.getBytes());
                }
                
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this.context, "SD card not found.", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setMessage("Exporting all points to " + Constants.CSV_FILE_NAME);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
