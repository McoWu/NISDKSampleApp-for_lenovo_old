package ai.naturali.sdksample.fan;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 姓名：McoWu
 * 时间:2017/12/27 09:38.
 * 本类作用:
 */

public class Utils2 {

    private static ArrayList<Info> countryModels1;
     static Context context;
    private static ProgressDialog progressDialog;
    public Utils2(Context context) {
        this.context = context;
    }

    public static ArrayList<Info> readExcel() {
        ArrayList<Info> countryList = new ArrayList<Info>();
        try {
            String s = Environment.getExternalStorageDirectory() + "/testMin.xls";
            Log.i("--------------", "readExcel: " + s);
            InputStream is = new FileInputStream(s);
            //Workbook book = Workbook.getWorkbook(new File("mnt/sdcard/test.xls"));
            Workbook book = Workbook.getWorkbook(is);
            int num = book.getNumberOfSheets();
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            int sheetNum = book.getNumberOfSheets();
            int Rows = sheet.getRows();
            int Cols = sheet.getColumns();
            Log.i("--------------", "readExcel: " + Rows);
            Log.i("--------------", "readExcel: " + Cols);
            for (int i = 1; i < Rows; i++) {
                Info info = new Info();
                info.setId(sheet.getCell(0, i).getContents());
                info.setName(sheet.getCell(1, i).getContents());
                countryList.add(info);
            }
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return countryList;
    }

    //在异步方法中 调用
    public static class ExcelDataLoader extends AsyncTask<String, Void, ArrayList<Info>> {



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected ArrayList<Info> doInBackground(String... params) {
            return readExcel();
        }

        @Override
        protected void onPostExecute(ArrayList<Info> countryModels) {

            if (countryModels != null && countryModels.size() > 0) {
                //存在数据
                //sortByName(countryModels);
                setupData(countryModels);
            } else {
                //加载失败
            }
        }


    }

    public static void setupData(ArrayList<Info> countryModels) {
        countryModels1 = countryModels;
        Log.i("=========", "setupData: " + countryModels.toString());
    }

    public static ArrayList<Info> sortByName() {
        return countryModels1;
    }


}
