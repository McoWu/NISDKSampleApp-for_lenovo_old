package ai.naturali.sdksample.fan;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import com.lenovo.menu_assistant.base.fc_util.FileOperationUtils;
import com.lenovo.menu_assistant.base.lv_rules.RuleFormater;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 姓名：McoWu
 * 时间:2017/12/27 08:46.
 * 本类作用:
 */

public class Utils {
    private static final String TAG = "==============================";
    private static RuleFormater mRule = null;
    static boolean isStop;
    static OnNluResult mOnNluResult;

    public void setOnNLUResult(OnNluResult mOnNluResult) {
        this.mOnNluResult = mOnNluResult;
    }

    @SuppressLint("LongLogTag")
    public static void query4NLUTest(boolean dumi, boolean sg, boolean wen)  {
       isStop = false;
        String path = Environment.getExternalStorageDirectory().getPath() + "/";

        Log.d(TAG, "===path===" + path);
        String fileName = "testMin.xls";
        String targetName = "result4nlu.txt";
        String excelName = "result4nlu.xls";

        File file = new File(path, targetName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int total = 0;
        int currentNum = 0;

        List<String> queryList = FileOperationUtils.readFileOnSdCard(path, fileName);

        total = queryList.size();

        Log.d(TAG, "===queryList===" + queryList);

        //for (String query : queryList) {
        WritableWorkbook wwb;
        File excfile = new File(path, excelName);
        if (!excfile.exists()) {
            try {
                excfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            excfile.delete();
            try {
                excfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try{
            OutputStream os = new FileOutputStream(excfile);
            wwb = Workbook.createWorkbook(os);
            WritableSheet sheet = wwb.createSheet("sheet1",0);



            //   ffgcWritableSheet sheet1 = wwb.getSheet("sheet1");
            String[] tablehead = new String[]{"query", "du_nlu","du_resource","du_views", "du_speech", "du_answer", "du_org",
                    "wen_states", "wen_displayText", "wen_domain", "wen_intent", "wen_dataSummary", "wen_org"};
            //Label label;
            for (int m = 0; m < tablehead.length; m++) {
                // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
                // 在Label对象的子对象中指明单元格的位置和内容
                //label = new Label(i, 0, tablehead[i]);
                // 将定义好的单元格添加到工作表中
//            Cell[] row = sheet1.getRow(0);
                Label label;
                label = new Label(m, 0, tablehead[m]);
                try{
                    sheet.addCell(label);
                }catch(WriteException e){
                    e.printStackTrace();
                }


            }
            for (int i = 0; i < total; i++) {
                String query = queryList.get(i);
                Map<String, String> resutlMap = mRule.nluQueryTest(query);
                if (isStop) {
                    if (mOnNluResult != null) {
                        mOnNluResult.onStop();
                    }
                    break;
                }
                if (resutlMap != null) {
//                FileOperationUtils.writeFileOnSdCard(path, targetName, query, resutlMap.get("dumi"),
//                        resutlMap.get("sg"), resutlMap.get("wenwen"));
                    if (dumi) {
                        FileOperationUtils.writeFiledumiOnSdCard(path, targetName, query, resutlMap.get("dumi"));
                    }
                    if (sg) {
                        FileOperationUtils.writeFilesgOnSdCard(path, targetName, query, resutlMap.get("sg"));
                    }
                    if (wen) {
                        FileOperationUtils.writeFilewenOnSdCard(path, targetName, query, resutlMap.get("wenwen"));
                    }
                    if (dumi && wen) {
//                    WritableWorkbook wwb;
//                    File excfile = new File(path,excelName);
//                    if(!excfile.exists()) {
//                        try {
//                            excfile.createNewFile();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        excfile.delete();
//                        try {
//                            excfile.createNewFile();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    try {
//                        OutputStream os = new FileOutputStream(excfile);;
//                        wwb = Workbook.createWorkbook(os);
//                        WritableSheet sheet = wwb.createSheet("sheet1", 0);
//                        String[] tablehead = new String[]{"query","du_nlu","du_view","du_speech","du_answer","du_org",
//                                "wen_states","wen_displayText", "wen_domain","wen_intent","wen_dataSummary","wen_org"};
//                        //Label label;
//                        for (int m = 0; m < tablehead.length; m++) {
//                            // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
//                            // 在Label对象的子对象中指明单元格的位置和内容
//                            //label = new Label(i, 0, tablehead[i]);
//                            // 将定义好的单元格添加到工作表中
//                            Label label;
//                            label = new Label(i, 0, tablehead[i]);
//                            sheet.addCell(label);
//                        }
                        try {
                            //sheet.addCell(label);
                            //for (int j = 0; j < total; j++) {

                            Label query1 = new Label(0, i + 1, query);
                            String dumiinfo = resutlMap.get("dumi");
                            //String weninfo = resutlMap.get("wenwen");
                            JSONObject jsonObjdumi = new JSONObject(dumiinfo);
                            String result = null;
                            String jsonObjnlu = null;
                            String jsonresource = null;
                            String jsonObjviews = null;
                            String jsonObjspeech = null;
                            String jsonObjresource = null;
                            String jsonObjdata =null;
                            String jsonObjresanswer = null;
                            if(jsonObjdumi.has("result")){
                                result = jsonObjdumi.optString("result");
                                JSONObject jsonObjresult = new JSONObject(result);

                                if(jsonObjresult.has("nlu")){
                                    jsonObjnlu = jsonObjresult.optString("nlu");
                                }
                                if(jsonObjresult.has("resource")){
                                    jsonresource = jsonObjresult.optString("resource");
                                }
                                if(jsonObjresult.has("views")){
                                    jsonObjviews = jsonObjresult.optString("views");
                                }
                                if(jsonObjresult.has("speech")){
                                    jsonObjspeech = jsonObjresult.optString("speech");
                                }
                                if(jsonObjresult.has("resource")){
                                    jsonObjresource = jsonObjresult.optString("resource");
                                    JSONObject jsonObjruresource = new JSONObject(jsonObjresource);
                                    jsonObjdata = jsonObjruresource.optString("data");
                                    JSONObject jsonObjndata = new JSONObject(jsonObjdata);
                                    if (jsonObjndata.has("answer")) {
                                        jsonObjresanswer = jsonObjndata.optString("answer");
                                    }
                                }
                            }






                            Label du_nlu2 = new Label(1, i + 1, jsonObjnlu);
                            Label du_resource3 = new Label(2, i + 1, jsonresource);
                            Label du_views4 = new Label(3, i + 1, jsonObjviews);
                            Label du_speech5 = new Label(4, i + 1, jsonObjspeech);

                            Label du_answer6 = new Label(5, i + 1, jsonObjresanswer);
                            Label du_org7 = new Label(6, i + 1, dumiinfo);
                            sheet.addCell(query1);
                            sheet.addCell(du_nlu2);
                            sheet.addCell(du_resource3);
                            sheet.addCell(du_views4);
                            sheet.addCell(du_speech5);
                            sheet.addCell(du_answer6);
                            sheet.addCell(du_org7);


                            String weninfo = resutlMap.get("wenwen");
                            String wenstates = null;
                            String languageOutput = null;
                            String displayText = null;
                            String wendomain = null;
                            String wenintent = null;
                            String wenclientData =null;
                            String wen_dataSummary = null;
                            JSONObject jsonObjwen = new JSONObject(weninfo);
                            if(jsonObjwen.has("states")){
                                wenstates = jsonObjwen.optString("states");
                            }
                            Label wen_states8 = new Label(7, i + 1, wenstates);
                            if(jsonObjwen.has("languageOutput")){
                                languageOutput = jsonObjwen.optString("languageOutput");
                                JSONObject jsonlanguageOutput = new JSONObject(languageOutput);
                                if(jsonlanguageOutput.has("displayText")){
                                    displayText = jsonlanguageOutput.optString("displayText");
                                }
                            }
                            Label wen_displayText9 = new Label(8, i + 1, displayText);
                            if(jsonObjwen.has("domain")){
                                wendomain = jsonObjwen.optString("domain");
                            }
                            Label wen_domain10 = new Label(9, i + 1, wendomain);
                            if(jsonObjwen.has("intent")){
                                wenintent = jsonObjwen.optString("intent");
                            }
                            Label wen_intent11 = new Label(10, i + 1, wenintent);
                            if(jsonObjwen.has("clientData")){
                                wenclientData = jsonObjwen.optString("clientData");
                                JSONObject jsonObjwenclientData = new JSONObject(wenclientData);
                                if (jsonObjwenclientData.has("dataSummary")) {
                                    wen_dataSummary = jsonObjwenclientData.optString("dataSummary");
                                }
                            }



                            Label wen_dataSummary12 = new Label(11, i + 1, wen_dataSummary);
                            Label wen_org13 = new Label(12, i + 1, weninfo);
                            sheet.addCell(wen_states8);
                            sheet.addCell(wen_displayText9);
                            sheet.addCell(wen_domain10);
                            sheet.addCell(wen_intent11);
                            sheet.addCell(wen_dataSummary12);
                            sheet.addCell(wen_org13);


//                            Toast.makeText(context, "写入成功", Toast.LENGTH_LONG).show();
//
                            //}
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        currentNum += 1;
                        if (mOnNluResult != null) {
                            mOnNluResult.onNluResult(total, currentNum, query);
                        }

                        try {
                            Thread.sleep(3500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }



                    }

                }

            }
            wwb.write();
            wwb.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public interface OnNluResult {
        abstract void onNluResult(int total, int currentNum, String query);
        abstract void onStop();
    }

}
