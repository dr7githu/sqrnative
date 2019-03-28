package com.egov.smartqr.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLogTags;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.egov.smartqr.MainActivity;
import com.egov.smartqr.R;
import com.egov.smartqr.common.Common;
import com.egov.smartqr.util.GeoPoint;
import com.egov.smartqr.util.GeoTrans;
import com.egov.smartqr.util.KeyboardUtil;
import com.egov.smartqr.util.MyLocation;
import com.egov.smartqr.util.NaviUtil;
import com.egov.smartqr.util.StrUtil;
import com.egov.smartqr.util.UIUtil;
import com.egov.smartqr.util.UnitUtil;
import com.egov.smartqr.vo.AddrVO;
import com.egov.smartqr.vo.QrVO;
import com.egov.smartqr.vo.SearchVO;
import com.egov.smartqr.vo.SubJihasuVO;
import com.egov.smartqr.vo.ThemeVO;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sds.mobile.servicebrokerLib.ServiceBrokerLib;
import com.sds.mobile.servicebrokerLib.event.ResponseEvent;
import com.sds.mobile.servicebrokerLib.event.ResponseListener;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

//import static com.sds.mobiledesk.mdhybrid.common.PictureSerializer.DrawType.v;

/**
 * Created by lwj on 2018. 1. 10..
 */

public class SubJihasuFragment extends Fragment implements View.OnClickListener, MapView.POIItemEventListener {

    @BindView(R.id.frame_map_sub) FrameLayout frame_map_sub;


    @BindView(R.id.frame_map_plus) FrameLayout frame_map_plus;
    @BindView(R.id.frame_map_minus) FrameLayout frame_map_minus;

    //시군구 검색
    @BindView(R.id.btn_search_sgg) Button btn_search_sgg;
    @BindView(R.id.frame_sgg) FrameLayout frame_sgg;
    @BindView(R.id.textview_sgg) TextView textview_sgg;

    //검색조건
    @BindView(R.id.radioGroup_sel_search) RadioGroup radioGroup_sel_search;
    @BindView(R.id.radio_sel_day) RadioButton radio_sel_day;
    @BindView(R.id.radio_sel_month) RadioButton radio_sel_month;
    @BindView(R.id.radio_sel_year) RadioButton radio_sel_year;
    @BindView(R.id.btn_start_date) Button btn_start_date;
    @BindView(R.id.btn_end_date) Button btn_end_date;
    @BindView(R.id.btn_search_graph) Button btn_search_graph;

    //테이블
    @BindView(R.id.waterLevel_avg) TextView waterLevel_avg;
    @BindView(R.id.waterLevel_high) TextView waterLevel_high;
    @BindView(R.id.waterLevel_low) TextView waterLevel_low;
    @BindView(R.id.waterLevel_standard) TextView waterLevel_standard;
    @BindView(R.id.waterLevel_range) TextView waterLevel_range;
    @BindView(R.id.waterLevel_center) TextView waterLevel_center;
    @BindView(R.id.waterTem_avg) TextView waterTem_avg;
    @BindView(R.id.waterTem_high) TextView waterTem_high;
    @BindView(R.id.waterTem_low) TextView waterTem_low;
    @BindView(R.id.waterTem_standard) TextView waterTem_standard;
    @BindView(R.id.waterTem_range) TextView waterTem_range;
    @BindView(R.id.waterTem_center) TextView waterTem_center;
    @BindView(R.id.elecCon_avg) TextView elecCon_avg;
    @BindView(R.id.elecCon_high) TextView elecCon_high;
    @BindView(R.id.elecCon_low) TextView elecCon_low;
    @BindView(R.id.elecCon_standard) TextView elecCon_standard;
    @BindView(R.id.elecCon_range) TextView elecCon_range;
    @BindView(R.id.elecCon_center) TextView elecCon_center;

    //그래프
    @BindView(R.id.radioGroup_sel_graph) RadioGroup radioGroup_sel_graph;
    @BindView(R.id.radio_graph_1) RadioButton radio_graph_1;
    @BindView(R.id.radio_graph_2) RadioButton radio_graph_2;
    @BindView(R.id.radio_graph_3) RadioButton radio_graph_3;
    @BindView(R.id.chart) LineChart chart;


    @BindView(R.id.recycler) RecyclerView recycler;

    ArrayList<SearchVO> arySearch;//검색결과
    SearchRecyclerAdapter searchRecyclerAdapter = null;

    //테마검색
    ArrayList<ThemeVO> aryThemeJihasu;
    ArrayList<ThemeVO> aryThemeGonggong;
    ArrayList<ThemeVO> aryThemePerm;
    ArrayList<ThemeVO> aryThemeCivil;
    ArrayList<SubJihasuVO> aryJihasu;
    ArrayList<SubJihasuVO> aryJihasuDetail;
    ArrayList<SubJihasuVO> saveJihasuDetail = null;
    ArrayList<SubJihasuVO> saveJihasuChart = null;

    //주소검색
    ArrayList<AddrVO> aryUmd = null;//읍면동
    ArrayList<AddrVO> aryRi = null;//리

    //다음지도
    MapView mapViewSub;

    Bitmap bmpIcon, bmpIconSel;
    Bitmap bmpTheme1, bmpTheme1Sel;
    Bitmap bmpTheme2, bmpTheme2Sel;
    Bitmap bmpTheme3, bmpTheme3Sel;
    Bitmap bmpTheme4, bmpTheme4Sel;

    //금일 날짜 구하기
    int year, month, day;
    String stryear, strmonth, strday;
    String enddate;
    String startdate;
    int syear, smonth, sday, eyear, emonth, eday, myear, mmonth, mday;

    //보조지하수 아이디 저장
    String subJihasuCode = null;

    public static SubJihasuFragment newInstance() {
        return new SubJihasuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_jihasu, container, false);
        ButterKnife.bind(this, view);

        removeTable();
        chart.invalidate();

        //현재 date 가져오기
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        //day= calendar.get(Calendar.DAY_OF_MONTH);
        day= calendar.getMaximum(Calendar.DAY_OF_MONTH);

        //현재 월 1달 선택
        if(month<10){
            strmonth = "0"+ String.valueOf(month);
        }else{
            strmonth = String.valueOf(month);
        }
        if(day < 10){
            strday = "0"+ String.valueOf(day);
        }else{
            strday = String.valueOf(day);
        }
        startdate = String.valueOf(year)+strmonth+"01";
        enddate = String.valueOf(year)+strmonth+strday;
/*

        //startdate 설정
        if(month < 10){
            strmonth = "0"+ String.valueOf(month);
        }else{
            strmonth = String.valueOf(month);
        }

        if(day < 10){
            strday = "0"+ String.valueOf(day);
        }else{
            strday = String.valueOf(day);
        }
        //startdate = String.valueOf(year)+strmonth+strday;
        startdate = String.valueOf(year)+strmonth+"01";

        //enddate 1개월 추가
        if(month == 12){
            enddate = String.valueOf(year+1)+"01"+strday;
        }else{
            if(month < 9){
                strmonth = "0"+ String.valueOf(month+1);
            }else{
                strmonth = String.valueOf(month+1);
            }
            enddate = String.valueOf(year)+strmonth+strday;
        }
*/

        frame_map_plus.setOnClickListener(this);
        frame_map_minus.setOnClickListener(this);
        frame_sgg.setOnClickListener(this);
        btn_search_graph.setOnClickListener(this);
        btn_search_sgg.setOnClickListener(this);
        radioGroup_sel_graph.setOnClickListener(this);
        radioGroup_sel_search.setOnClickListener(this);
        radio_graph_1.setOnClickListener(this);
        radio_graph_2.setOnClickListener(this);
        radio_graph_3.setOnClickListener(this);
        btn_start_date.setOnClickListener(this);
        btn_end_date.setOnClickListener(this);

        textview_sgg.setText(Common.nameOfSfteamcode(Common.memberVO.getIns_org()));
        btn_start_date.setText(startdate);
        btn_end_date.setText(enddate);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRecyclerAdapter = new SearchRecyclerAdapter();
        recycler.setAdapter(searchRecyclerAdapter);

        //Map
        mapViewSub = new MapView(getContext());

        ViewGroup mapViewContainer = frame_map_sub;
        mapViewContainer.addView(mapViewSub);

        // 중심점 변경 : 충남도청
        //mapViewSub.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(36.658871, 126.672844), true);
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(final Location location){
                UIUtil.hideProgress();
                //Got the location!

                try {
                    mapViewSub.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.getLatitude(),location.getLongitude()), true);
                    mapViewSub.setZoomLevel(6, true);
                    //Toast.makeText(getContext(), String.format("lon : %.7f, lat : %.7f", location.getLongitude(), location.getLatitude()), Toast.LENGTH_SHORT).show();
                }
                catch(Exception x){
                    x.getMessage();
                }
            }
        };
        if(!new MyLocation().getLocation(getContext(), locationResult)) {
            Toast.makeText(getContext(), "GPS 정보를 불러올수 없습니다.", Toast.LENGTH_SHORT).show();
        }

        // 줌 레벨 변경
        //mapViewSub.setZoomLevel(16, true);
        mapViewSub.setPOIItemEventListener(this);
        mapViewSub.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        makeMarkerBitmap();

        String initSgg = textview_sgg.getText().toString();
        initSgg = initSgg.replaceAll(" ","");
        reqJihasuMap(initSgg);
        setGraph();
        chart.clearValues();
        chart.invalidate();


        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == frame_map_plus) {
            mapViewSub.zoomIn(true);
        }
        else if(view == frame_map_minus) {
            mapViewSub.zoomOut(true);
        }else if(view == frame_sgg) {//시군구 선택
            DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(getActivity(), frame_sgg);
            for(int i=0; i< Common.getArySfTeamCode().size(); i++) {
                droppyBuilder.addMenuItem(new DroppyMenuItem(Common.getArySfTeamCode().get(i).sggNm)).addSeparator();
            }
            droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
                @Override
                public void call(View v, int id) {
                    textview_sgg.setText(Common.getArySfTeamCode().get(id).sggNm);
               }
            });
            DroppyMenuPopup droppyMenu = droppyBuilder.build();
            droppyMenu.show();
        }else if (view == btn_search_sgg){
            String search_sgg = textview_sgg.getText().toString();
            search_sgg = search_sgg.replace(" ","");
            //reqJihasuMapTest(search_sgg);
            reqJihasuMap(search_sgg);
        }else if(view == btn_start_date){
            DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    StringBuffer strBuf = new StringBuffer();
                    //strBuf.append("You select date is ");
                    if(month < 9){
                        strmonth = "0"+String.valueOf(month+1);
                    }else{
                        strmonth = String.valueOf(month+1);
                    }
                    if(dayOfMonth<10){
                        strday = "0"+String.valueOf(dayOfMonth);
                    }else{
                        strday = String.valueOf(dayOfMonth);
                    }
                    strBuf.append(year);
                    strBuf.append(strmonth);
                    strBuf.append(strday);

                    btn_start_date.setText(strBuf.toString());
                }
            };

            // Get current year, month and day.
            Calendar now = Calendar.getInstance();
            int myear = now.get(java.util.Calendar.YEAR);
            int mmonth = now.get(java.util.Calendar.MONTH);
            int mday = now.get(java.util.Calendar.DAY_OF_MONTH);

            myear = Integer.parseInt(btn_start_date.getText().toString().substring(0,4));
            mmonth = Integer.parseInt(btn_start_date.getText().toString().substring(4,6))-1;
            mday = Integer.parseInt(btn_start_date.getText().toString().substring(6,8));

            // Create the new DatePickerDialog instance.
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), onDateSetListener, myear, mmonth, mday);

            // Set dialog icon and title.
            datePickerDialog.setTitle("Please select date.");

            // Popup the dialog.
            datePickerDialog.show();
        }else if(view == btn_end_date){
            DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    StringBuffer strBuf = new StringBuffer();
                    //strBuf.append("You select date is ");
                    if(month < 9){
                        strmonth = "0"+String.valueOf(month+1);
                    }else{
                        strmonth = String.valueOf(month+1);
                    }
                    if(dayOfMonth<10){
                        strday = "0"+String.valueOf(dayOfMonth);
                    }else{
                        strday = String.valueOf(dayOfMonth);
                    }
                    strBuf.append(year);
                    strBuf.append(strmonth);
                    strBuf.append(strday);

                    int comsdate = Integer.parseInt(btn_start_date.getText().toString());
                    int comedate = Integer.parseInt(strBuf.toString());
                    if(comsdate > comedate){
                        Toast.makeText(getContext(),"시작일 이후 날짜를 선택해 주세요.",Toast.LENGTH_LONG);
                    }else{
                        btn_end_date.setText(strBuf.toString());
                    }



                }
            };

            // Get current year, month and day.
            Calendar now = Calendar.getInstance();
            int myear = now.get(java.util.Calendar.YEAR);
            int mmonth = now.get(java.util.Calendar.MONTH);
            int mday = now.get(java.util.Calendar.DAY_OF_MONTH);

            myear = Integer.parseInt(btn_end_date.getText().toString().substring(0,4));
            mmonth = Integer.parseInt(btn_end_date.getText().toString().substring(4,6))-1;
            mday = Integer.parseInt(btn_end_date.getText().toString().substring(6,8));

            // Create the new DatePickerDialog instance.
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), onDateSetListener, myear, mmonth, mday);

            // Set dialog icon and title.
            datePickerDialog.setTitle("Please select date.");

            // Popup the dialog.
            datePickerDialog.show();
        }else if(view == btn_search_graph){
            if(subJihasuCode!=null){
                String type = getRadioType();
                String code = subJihasuCode;
                String sdate = btn_start_date.getText().toString();
                String edate = btn_end_date.getText().toString();

                reqJihasuMapDetail(code,sdate,edate);
                reqJihasuMapChart(type, code, sdate, edate);
            }else{
                UIUtil.alert(getContext(), "지도에서 관정을 선택해 주세요.");
            }

        }else if(view == radio_graph_1){
            if(subJihasuCode!=null) {
                setGraph();
            }
        }else if(view == radio_graph_2){
            if(subJihasuCode!=null) {
                setGraph();
            }
        }else if(view == radio_graph_3){
            if(subJihasuCode!=null){
                setGraph();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // QR코드/ 바코드를 스캔한 결과
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String text = result.getContents();
        int find = text.indexOf("gid=");
        String gid = text.substring(find+4, text.length());
        //reqGetPermAndSf(gid);
    }

    //--- Daum지도 마커 클릭 이벤트 리스너 ------------------------------
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        if(mapPOIItem.getTag() == 1) {
            SearchVO search = (SearchVO)mapPOIItem.getUserObject();

            String stc = "", pnn = "";
            if(search != null) {
                stc = search.sf_team_code;
                pnn = search.perm_nt_no;
            }
            ((MainActivity)getActivity()).screenMove(DetailInfoFragment.class, stc, pnn);
        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    //------------------------------------------------------------



    void makeMarkerBitmap() {
        bmpIcon = BitmapFactory.decodeResource(getResources(), R.drawable.gw_map_icon);
        bmpIconSel = BitmapFactory.decodeResource(getResources(), R.drawable.gw_map_icon_sel);
        bmpTheme1 = BitmapFactory.decodeResource(getResources(), R.drawable.gw_country_icon);
        bmpTheme1Sel = BitmapFactory.decodeResource(getResources(), R.drawable.gw_country_icon_sel);
        bmpTheme2 = BitmapFactory.decodeResource(getResources(), R.drawable.gw_perm_icon);
        bmpTheme2Sel = BitmapFactory.decodeResource(getResources(), R.drawable.gw_perm_icon_sel);
        bmpTheme3 = BitmapFactory.decodeResource(getResources(), R.drawable.gw_farm_icon);
        bmpTheme3Sel = BitmapFactory.decodeResource(getResources(), R.drawable.gw_farm_icon_sel);
        bmpTheme4 = BitmapFactory.decodeResource(getResources(), R.drawable.gw_def_icon);
        bmpTheme4Sel = BitmapFactory.decodeResource(getResources(), R.drawable.gw_def_icon_sel);

        int size = UnitUtil.dp2pxl(getContext(), 25);
        //resize
        bmpIcon = Bitmap.createScaledBitmap(bmpIcon, size, size, true);
        bmpIconSel = Bitmap.createScaledBitmap(bmpIconSel, size, size, true);
        bmpTheme1 = Bitmap.createScaledBitmap(bmpTheme1, size, size, true);
        bmpTheme1Sel = Bitmap.createScaledBitmap(bmpTheme1Sel, size, size, true);
        bmpTheme2 = Bitmap.createScaledBitmap(bmpTheme2, size, size, true);
        bmpTheme2Sel = Bitmap.createScaledBitmap(bmpTheme2Sel, size, size, true);
        bmpTheme3 = Bitmap.createScaledBitmap(bmpTheme3, size, size, true);
        bmpTheme3Sel = Bitmap.createScaledBitmap(bmpTheme3Sel, size, size, true);
        bmpTheme4 = Bitmap.createScaledBitmap(bmpTheme4, size, size, true);
        bmpTheme4Sel = Bitmap.createScaledBitmap(bmpTheme4Sel, size, size, true);
    }



    void setTable(ArrayList<SubJihasuVO> arygraph){
        if(arygraph != null) {
            for (int i = 0; i < arygraph.size(); i++) {
                SubJihasuVO graph = arygraph.get(i);
                waterLevel_avg.setText(graph.avg_gwdep);
                waterLevel_center.setText(graph.center_gwdep);
                waterLevel_high.setText(graph.max_gwdep);
                waterLevel_low.setText(graph.min_gwdep);
                waterLevel_range.setText(graph.range_gwdep);
                waterLevel_standard.setText(graph.standard_gwdep);
                waterTem_avg.setText(graph.avg_gwtemp);
                waterTem_center.setText(graph.center_gwtemp);
                waterTem_high.setText(graph.max_gwtemp);
                waterTem_low.setText(graph.min_gwtemp);
                waterTem_range.setText(graph.range_gwtemp);
                waterTem_standard.setText(graph.standard_gwtemp);
                elecCon_avg.setText(graph.avg_ec);
                elecCon_center.setText(graph.center_ec);
                elecCon_high.setText(graph.max_ec);
                elecCon_low.setText(graph.min_ec);
                elecCon_range.setText(graph.range_ec);
                elecCon_standard.setText(graph.standard_ec);
            }
        }

    }
    void removeTable(){
        waterLevel_avg.setText("-");
        waterLevel_center.setText("-");
        waterLevel_high.setText("-");
        waterLevel_low.setText("-");
        waterLevel_range.setText("-");
        waterLevel_standard.setText("-");
        waterTem_avg.setText("-");
        waterTem_center.setText("-");
        waterTem_high.setText("-");
        waterTem_low.setText("-");
        waterTem_range.setText("-");
        waterTem_standard.setText("-");
        elecCon_avg.setText("-");
        elecCon_center.setText("-");
        elecCon_high.setText("-");
        elecCon_low.setText("-");
        elecCon_range.setText("-");
        elecCon_standard.setText("-");
    }

    void setGraph(){
        //차트 연습

        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        Float f;
        String sel = getRadioGraph();
        String legendlabel = "";

            if(saveJihasuChart != null) {
                for (int i = 0; i < saveJihasuChart.size(); i++) {
                    SubJihasuVO graph = saveJihasuChart.get(i);
                    if(sel == "gwdep") {
                        f = Float.parseFloat(graph.gwdep);

                        valsComp1.add(new Entry(f,i));

                        xVals.add(graph.obsv_date);
                        legendlabel = "지하수수위(EL.m)";

                    }else if(sel == "gwtemp") {
                        f = Float.parseFloat(graph.gwtemp);

                        valsComp1.add(new Entry(f,i));

                        xVals.add(graph.obsv_date);
                        legendlabel = "수온(℃)";
                    }else if(sel == "ec") {
                        f = Float.parseFloat(graph.ec);

                        valsComp1.add(new Entry(f,i));

                        xVals.add(graph.obsv_date);
                        legendlabel = "전기전도도(㎲/cm)";
                    }
                }
            }else {
                valsComp1.add(new Entry(10,0));
                valsComp1.add(new Entry(0,0));

                xVals.add("");
                xVals.add("");
                legendlabel = "";
            }

        LineDataSet setComp1 = new LineDataSet(valsComp1,legendlabel);
        if(sel == "gwdep") {
            //setComp1.setColors(new int[] {R.color.colorOrange1}, getContext());
            setComp1.setColor(Color.parseColor("#2E64FE"));
            setComp1.setCircleColor(Color.parseColor("#2E64FE"));
        }else if(sel == "gwtemp") {
            //setComp1.setColors(new int[] {R.color.colorGreen1}, getContext());
            setComp1.setColor(Color.parseColor("#FE9A2E"));
            setComp1.setCircleColor(Color.parseColor("#FE9A2E"));
        }else if(sel == "ec") {
            //setComp1.setColors(new int[] {R.color.colorRed1}, getContext());
            setComp1.setColor(Color.parseColor("#3ADF00"));
            setComp1.setCircleColor(Color.parseColor("#3ADF00"));
        }

        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

        //x축 설정
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //레전드 설정
        Legend leg = chart.getLegend();
        leg.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);

        //description 삭제




        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setComp1);
        LineData data = new LineData(xVals, dataSets);


        chart.setBackgroundColor(Color.parseColor("#eeeeee"));
        chart.setDescription("");
        chart.setBorderWidth(3);
        chart.setData(data);
        chart.invalidate();
        chart.animateXY(500,500);


    }


    //시설물조회
    void reqThemeMap(final String sgg, final String type) {
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        //KeyboardUtil.hide(getContext(), edittext_num);

                        if(re.getResultCode() == 0) {
                            ArrayList<ThemeVO> aryTheme = null;
                            if(type.equals("jihasu")) {
                                aryThemeJihasu = null;
                                try {
                                    Type listType = new TypeToken<ArrayList<ThemeVO>>() {}.getType();
                                    aryThemeJihasu = new Gson().fromJson(re.getResultData(), listType);
                                    aryTheme = aryThemeJihasu;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if(type.equals("gonggong")) {
                                aryThemeGonggong = null;
                                try {
                                    Type listType = new TypeToken<ArrayList<ThemeVO>>() {}.getType();
                                    aryThemeGonggong = new Gson().fromJson(re.getResultData(), listType);
                                    aryTheme = aryThemeGonggong;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if(type.equals("perm")) {
                                aryThemePerm = null;
                                try {
                                    Type listType = new TypeToken<ArrayList<ThemeVO>>() {}.getType();
                                    aryThemePerm = new Gson().fromJson(re.getResultData(), listType);
                                    aryTheme = aryThemePerm;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if(type.equals("civil")) {
                                aryThemeCivil = null;
                                try {
                                    Type listType = new TypeToken<ArrayList<ThemeVO>>() {}.getType();
                                    aryThemeCivil = new Gson().fromJson(re.getResultData(), listType);
                                    aryTheme = aryThemeCivil;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            //Process...
                            addTheme(type, aryTheme);
                        } else {
                            UIUtil.alert(getContext(), re.getResultData());
                        }
                    }
                });
        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", String.format("method=themeMap;sgg=%s;type=%s", sgg, type));
        lib.request(intent);
    }
/*

    void reqJihasuMapTest(final String sgg){
        String test = "[{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"성환읍\",\"ri\":\"양령리\",\"bunji\":\"685번지\",\"obsv_name\":\"성환 보조지하수관측정 0001\",\"mgr_org\":\"천안시 수도사업소 하수과\",\"pyogo\":\"10.98\",\"insdate\":\"20040101\",\"guldep\":\"26.5\",\"guldia\":\"300\",\"dcode\":\"44133250\",\"tmx\":\"213524.78650000\",\"tmy\":\"385278.19950000\",\"obsv_code\":\"CN-CAN-G1-0001\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"성환읍\",\"ri\":\"율금리\",\"bunji\":\"43-2번지\",\"obsv_name\":\"성환 보조지하수관측정 0003\",\"mgr_org\":\"천안시 수도사업소 하수과\",\"pyogo\":\"23.32\",\"insdate\":\"20040101\",\"guldep\":\"16\",\"guldia\":\"300\",\"dcode\":\"44133250\",\"tmx\":\"209866.88454200\",\"tmy\":\"377584.60788100\",\"obsv_code\":\"CN-CAN-G1-0003\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"직산읍\",\"ri\":\"자은가리\",\"bunji\":\"8번지\",\"obsv_name\":\"직산 보조지하수관측정 0004\",\"mgr_org\":\"천안시 수도사업소 하수과\",\"pyogo\":\"32.670000000000002\",\"insdate\":\"20040101\",\"guldep\":\"70\",\"guldia\":\"300\",\"dcode\":\"44133256\",\"tmx\":\"211169.94575000\",\"tmy\":\"375682.06545800\",\"obsv_code\":\"CN-CAN-G1-0004\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"유리\",\"bunji\":\"368-3번지\",\"obsv_name\":\"입장 보조지하수관측정 0006\",\"mgr_org\":\"천안시 수도사업소 하수과\",\"pyogo\":\"39.340000000000003\",\"insdate\":\"20040101\",\"guldep\":\"70\",\"guldia\":\"300\",\"dcode\":\"44133310\",\"tmx\":\"218771.60125000\",\"tmy\":\"380408.46084100\",\"obsv_code\":\"CN-CAN-G1-0006\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"호당리\",\"bunji\":\"235-3번지\",\"obsv_name\":\"입장 보조지하수관측정 0007\",\"mgr_org\":\"천안시 수도사업소 하수과\",\"pyogo\":\"68.920000000000002\",\"insdate\":\"20040101\",\"guldep\":\"70\",\"guldia\":\"300\",\"dcode\":\"44133310\",\"tmx\":\"221128.02867000\",\"tmy\":\"377500.61457300\",\"obsv_code\":\"CN-CAN-G1-0007\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"성거읍\",\"ri\":\"삼곡리\",\"bunji\":\"98-2번지\",\"obsv_name\":\"성거읍보조지하수관측정0032\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"51\",\"insdate\":\"20050101\",\"guldep\":\"100\",\"guldia\":\"300\",\"dcode\":\"44133253\",\"tmx\":\"216588.88675000\",\"tmy\":\"377991.71499900\",\"obsv_code\":\"CN-CAN-G1-0032\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"성거읍\",\"ri\":\"소우리\",\"bunji\":\"76-3번지\",\"obsv_name\":\"성거읍보조지하수관측정0033\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"83\",\"insdate\":\"20050101\",\"guldep\":\"180\",\"guldia\":\"350\",\"dcode\":\"44133253\",\"tmx\":\"215435.91725000\",\"tmy\":\"374462.69501700\",\"obsv_code\":\"CN-CAN-G1-0033\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"성환읍\",\"ri\":\"수향리\",\"bunji\":\"55-18번지\",\"obsv_name\":\"성환읍보조지하수관측정0039\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"38\",\"insdate\":\"20050101\",\"guldep\":\"100\",\"guldia\":\"300\",\"dcode\":\"44133250\",\"tmx\":\"212517.28125000\",\"tmy\":\"383529.75347400\",\"obsv_code\":\"CN-CAN-G1-0039\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"성환읍\",\"ri\":\"신방리\",\"bunji\":\"205-4번지\",\"obsv_name\":\"성환읍보조지하수관측정0040\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"34\",\"insdate\":\"20040101\",\"guldep\":\"100\",\"guldia\":\"300\",\"dcode\":\"44133250\",\"tmx\":\"207350.96350000\",\"tmy\":\"382342.72432700\",\"obsv_code\":\"CN-CAN-G1-0040\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"도림리\",\"bunji\":\"247-1번지\",\"obsv_name\":\"입장면보조지하수관측정0042\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"65\",\"insdate\":\"20050101\",\"guldep\":\"180\",\"guldia\":\"350\",\"dcode\":\"44133310\",\"tmx\":\"222518.79726000\",\"tmy\":\"380183.03201000\",\"obsv_code\":\"CN-CAN-G1-0042\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"직산읍\",\"ri\":\"양당리\",\"bunji\":\"126-2번지\",\"obsv_name\":\"직산읍보조지하수관측정0047\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"51\",\"insdate\":\"20040101\",\"guldep\":\"100\",\"guldia\":\"300\",\"dcode\":\"44133256\",\"tmx\":\"210150.10424100\",\"tmy\":\"375819.29630000\",\"obsv_code\":\"CN-CAN-G1-0047\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"성환읍\",\"ri\":\"성환리\",\"bunji\":\"472번지\",\"obsv_name\":\"성환(성환)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"15\",\"insdate\":\"20060101\",\"guldia\":\"150\",\"dcode\":\"44133250\",\"tmx\":\"210942.20750000\",\"tmy\":\"380674.11451000\",\"obsv_code\":\"CN-CAN-G1-0073\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"성환읍\",\"ri\":\"안궁리\",\"bunji\":\"122-17번지\",\"obsv_name\":\"성환(안궁)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"12\",\"insdate\":\"20100101\",\"guldep\":\"80\",\"guldia\":\"300\",\"dcode\":\"44133250\",\"tmx\":\"213001.41100000\",\"tmy\":\"384285.34241200\",\"obsv_code\":\"CN-CAN-G1-0074\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"성환읍\",\"ri\":\"와룡리\",\"bunji\":\"201-1번지\",\"obsv_name\":\"성환(와룡)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"8\",\"insdate\":\"20060101\",\"guldep\":\"200\",\"guldia\":\"300\",\"dcode\":\"44133250\",\"tmx\":\"208899.40525000\",\"tmy\":\"382849.34500800\",\"obsv_code\":\"CN-CAN-G1-0075\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"호당리\",\"bunji\":\"311번지\",\"obsv_name\":\"입장(호당)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"176\",\"insdate\":\"20060101\",\"guldep\":\"110\",\"guldia\":\"300\",\"dcode\":\"44133310\",\"tmx\":\"221094.76900300\",\"tmy\":\"377677.24141300\",\"obsv_code\":\"CN-CAN-G1-0076\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"가산리\",\"bunji\":\"168-12번지\",\"obsv_name\":\"입장(가산)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"40\",\"insdate\":\"20060101\",\"guldia\":\"300\",\"dcode\":\"44133310\",\"tmx\":\"217599.10050000\",\"tmy\":\"381547.91316800\",\"obsv_code\":\"CN-CAN-G1-0077\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"직산읍\",\"ri\":\"수헐리\",\"bunji\":\"5-4번지\",\"obsv_name\":\"직산(수헐)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"32\",\"insdate\":\"20060101\",\"guldep\":\"110\",\"guldia\":\"300\",\"dcode\":\"44133256\",\"tmx\":\"212428.85804400\",\"tmy\":\"377394.64474100\",\"obsv_code\":\"CN-CAN-G1-0078\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"호당리\",\"bunji\":\"59번지\",\"obsv_name\":\"입장(호당)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"113\",\"insdate\":\"20070101\",\"guldep\":\"182\",\"guldia\":\"350\",\"dcode\":\"44133310\",\"tmx\":\"221262.73388200\",\"tmy\":\"375983.25504000\",\"obsv_code\":\"CN-CAN-G1-0103\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"시장리\",\"bunji\":\"280-1번지\",\"obsv_name\":\"입장(시장)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"56\",\"insdate\":\"20070101\",\"guldep\":\"200\",\"guldia\":\"350\",\"dcode\":\"44133310\",\"tmx\":\"219583.77800000\",\"tmy\":\"378395.84771500\",\"obsv_code\":\"CN-CAN-G1-0104\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"가산리\",\"bunji\":\"산101번지\",\"obsv_name\":\"입장(가산)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"11\",\"insdate\":\"20070101\",\"guldep\":\"180\",\"guldia\":\"350\",\"dcode\":\"44133310\",\"tmx\":\"217046.87900000\",\"tmy\":\"382027.52523500\",\"obsv_code\":\"CN-CAN-G1-0105\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"연곡리\",\"bunji\":\"246-2번지\",\"obsv_name\":\"입장(연곡)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"15\",\"insdate\":\"20070101\",\"guldep\":\"110\",\"guldia\":\"300\",\"dcode\":\"44133310\",\"tmx\":\"216334.32097300\",\"tmy\":\"384097.88879400\",\"obsv_code\":\"CN-CAN-G1-0106\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"용정리\",\"bunji\":\"294-5번지\",\"obsv_name\":\"입장 보조지하수관측정0107\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"38\",\"insdate\":\"20070101\",\"guldep\":\"182\",\"guldia\":\"350\",\"dcode\":\"44133310\",\"tmx\":\"216512.97544200\",\"tmy\":\"380816.39438600\",\"obsv_code\":\"CN-CAN-G1-0107\"},{\"sido\":\"충청남도\",\"sgg\":\"천안시서북구\",\"umd\":\"입장면\",\"ri\":\"용정리\",\"bunji\":\"182-7번지\",\"obsv_name\":\"입장(용정)보조지하수관측정\",\"mgr_org\":\"천안시 수도사업소 급수과\",\"pyogo\":\"42\",\"insdate\":\"20070101\",\"guldep\":\"180\",\"guldia\":\"350\",\"dcode\":\"44133310\",\"tmx\":\"216661.37968600\",\"tmy\":\"380477.51285300\",\"obsv_code\":\"CN-CAN-G1-0108\"}]";
        */
/*JSONObject json = null;
        try{
            json = new JSONObject(test);

        }catch (Exception e){

        }*//*


        ArrayList<SubJihasuVO> aryTheme = null;
        Type listType = new TypeToken<ArrayList<SubJihasuVO>>() {}.getType();
        aryJihasu = new Gson().fromJson(test, listType);
        aryTheme = aryJihasu;
        addSubJihasu(aryTheme);
    }
*/

    //보조지하수 관측망 시군구 조회
    void reqJihasuMap(final String sgg) {
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        //KeyboardUtil.hide(getContext(), edittext_num);

                        if(re.getResultCode() == 0) {

                            if(re.getResultData().equals("[]")){
                                Toast.makeText(getActivity() ,"데이터가 없습니다.",Toast.LENGTH_LONG);
                                UIUtil.alert(getContext(), "선택된 시군구내 조회된 보조지하수 관측망 정보가 없습니다.");
                                MapPOIItem[] items = mapViewSub.getPOIItems();
                                if(items != null && items.length > 0) {
                                    for(int i=0; i<items.length; i++) {
                                        if(items[i].getTag() == 14) {
                                            mapViewSub.removePOIItem(items[i]);
                                        }
                                    }
                                }
                                subJihasuCode = null;
                                removeTable();
                                chart.clearValues();
                                chart.invalidate();
                            }else{
                                ArrayList<SubJihasuVO> aryTheme = null;

                                aryJihasu = null;
                                try{
                                    Type listType = new TypeToken<ArrayList<SubJihasuVO>>() {}.getType();
                                    aryJihasu = new Gson().fromJson(re.getResultData(), listType);
                                    aryTheme = aryJihasu;
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                                //Process...

                                //Toast.makeText(getContext(),"영현이 바보",Toast.LENGTH_LONG).show();
                                addSubJihasu(aryTheme);
                            }
                        } else {
                            UIUtil.alert(getContext(), re.getResultData());
                        }
                    }
                });
        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", String.format("method=jihasuMap;sgg=%s", sgg));
        lib.request(intent);
    }

/*
    void reqJihasuMapChartTest(final String type, final String code, final String sdate, final String edate) {
        String test = "[{\"obsv_date\":\"20100222\",\"gwdep\":\"4.20\",\"gwtemp\":\"15.40\",\"ec\":\"53.00\"},{\"obsv_date\":\"20100301\",\"gwdep\":\"4.30\",\"gwtemp\":\"14.50\",\"ec\":\"55.00\"},{\"obsv_date\":\"20100315\",\"gwdep\":\"4.10\",\"gwtemp\":\"15.20\",\"ec\":\"54.00\"},{\"obsv_date\":\"20100405\",\"gwdep\":\"4.20\",\"gwtemp\":\"15.50\",\"ec\":\"55.00\"},{\"obsv_date\":\"20100419\",\"gwdep\":\"4.30\",\"gwtemp\":\"15.40\",\"ec\":\"55.00\"},{\"obsv_date\":\"20100503\",\"gwdep\":\"4.60\",\"gwtemp\":\"16.80\",\"ec\":\"56.00\"},{\"obsv_date\":\"20100517\",\"gwdep\":\"4.80\",\"gwtemp\":\"16.60\",\"ec\":\"56.00\"},{\"obsv_date\":\"20100607\",\"gwdep\":\"4.60\",\"gwtemp\":\"17.90\",\"ec\":\"56.00\"},{\"obsv_date\":\"20100621\",\"gwdep\":\"4.70\",\"gwtemp\":\"17.80\",\"ec\":\"57.00\"},{\"obsv_date\":\"20100705\",\"gwdep\":\"4.50\",\"gwtemp\":\"18.40\",\"ec\":\"56.00\"},{\"obsv_date\":\"20100712\",\"gwdep\":\"4.50\",\"gwtemp\":\"20.00\",\"ec\":\"56.00\"},{\"obsv_date\":\"20100719\",\"gwdep\":\"4.60\",\"gwtemp\":\"19.80\",\"ec\":\"56.00\"},{\"obsv_date\":\"20100802\",\"gwdep\":\"4.20\",\"gwtemp\":\"20.70\",\"ec\":\"57.00\"},{\"obsv_date\":\"20100809\",\"gwdep\":\"4.20\",\"gwtemp\":\"20.30\",\"ec\":\"57.00\"},{\"obsv_date\":\"20100816\",\"gwdep\":\"4.30\",\"gwtemp\":\"19.70\",\"ec\":\"57.00\"},{\"obsv_date\":\"20100906\",\"gwdep\":\"4.30\",\"gwtemp\":\"18.10\",\"ec\":\"60.00\"},{\"obsv_date\":\"20100913\",\"gwdep\":\"4.30\",\"gwtemp\":\"18.40\",\"ec\":\"62.00\"},{\"obsv_date\":\"20100920\",\"gwdep\":\"4.40\",\"gwtemp\":\"17.40\",\"ec\":\"61.00\"},{\"obsv_date\":\"20101004\",\"gwdep\":\"4.30\",\"gwtemp\":\"17.00\",\"ec\":\"60.00\"},{\"obsv_date\":\"20101018\",\"gwdep\":\"4.30\",\"gwtemp\":\"17.00\",\"ec\":\"59.00\"},{\"obsv_date\":\"20101101\",\"gwdep\":\"4.20\",\"gwtemp\":\"15.50\",\"ec\":\"57.00\"},{\"obsv_date\":\"20101115\",\"gwdep\":\"4.10\",\"gwtemp\":\"15.00\",\"ec\":\"59.00\"},{\"obsv_date\":\"20101206\",\"gwdep\":\"4.20\",\"gwtemp\":\"15.50\",\"ec\":\"59.00\"},{\"obsv_date\":\"20101220\",\"gwdep\":\"4.20\",\"gwtemp\":\"14.90\",\"ec\":\"57.00\"},{\"obsv_date\":\"20110221\",\"gwdep\":\"3.80\",\"gwtemp\":\"16.30\",\"ec\":\"61.00\"},{\"obsv_date\":\"20110307\",\"gwdep\":\"3.90\",\"gwtemp\":\"15.40\",\"ec\":\"63.00\"},{\"obsv_date\":\"20110321\",\"gwdep\":\"3.70\",\"gwtemp\":\"16.10\",\"ec\":\"62.00\"},{\"obsv_date\":\"20110404\",\"gwdep\":\"3.80\",\"gwtemp\":\"16.40\",\"ec\":\"62.00\"},{\"obsv_date\":\"20110418\",\"gwdep\":\"3.90\",\"gwtemp\":\"16.30\",\"ec\":\"62.00\"},{\"obsv_date\":\"20110502\",\"gwdep\":\"4.20\",\"gwtemp\":\"17.70\",\"ec\":\"63.00\"},{\"obsv_date\":\"20110523\",\"gwdep\":\"4.40\",\"gwtemp\":\"17.50\",\"ec\":\"63.00\"},{\"obsv_date\":\"20110606\",\"gwdep\":\"4.20\",\"gwtemp\":\"18.80\",\"ec\":\"64.00\"},{\"obsv_date\":\"20110620\",\"gwdep\":\"4.30\",\"gwtemp\":\"18.70\",\"ec\":\"64.00\"},{\"obsv_date\":\"20110704\",\"gwdep\":\"4.10\",\"gwtemp\":\"19.30\",\"ec\":\"64.00\"},{\"obsv_date\":\"20110711\",\"gwdep\":\"4.10\",\"gwtemp\":\"20.90\",\"ec\":\"64.00\"},{\"obsv_date\":\"20110718\",\"gwdep\":\"4.20\",\"gwtemp\":\"20.70\",\"ec\":\"63.00\"},{\"obsv_date\":\"20110808\",\"gwdep\":\"3.80\",\"gwtemp\":\"21.60\",\"ec\":\"64.00\"},{\"obsv_date\":\"20110815\",\"gwdep\":\"3.80\",\"gwtemp\":\"21.20\",\"ec\":\"65.00\"},{\"obsv_date\":\"20110822\",\"gwdep\":\"3.90\",\"gwtemp\":\"20.60\",\"ec\":\"65.00\"},{\"obsv_date\":\"20110905\",\"gwdep\":\"3.90\",\"gwtemp\":\"19.00\",\"ec\":\"67.00\"},{\"obsv_date\":\"20110912\",\"gwdep\":\"3.90\",\"gwtemp\":\"19.30\",\"ec\":\"70.00\"},{\"obsv_date\":\"20110919\",\"gwdep\":\"4.00\",\"gwtemp\":\"18.30\",\"ec\":\"68.00\"},{\"obsv_date\":\"20111003\",\"gwdep\":\"3.90\",\"gwtemp\":\"17.90\",\"ec\":\"68.00\"},{\"obsv_date\":\"20111017\",\"gwdep\":\"3.90\",\"gwtemp\":\"17.90\",\"ec\":\"67.00\"},{\"obsv_date\":\"20111107\",\"gwdep\":\"3.80\",\"gwtemp\":\"16.40\",\"ec\":\"65.00\"},{\"obsv_date\":\"20111121\",\"gwdep\":\"3.70\",\"gwtemp\":\"15.90\",\"ec\":\"66.00\"},{\"obsv_date\":\"20111205\",\"gwdep\":\"3.80\",\"gwtemp\":\"16.40\",\"ec\":\"67.00\"},{\"obsv_date\":\"20111219\",\"gwdep\":\"3.80\",\"gwtemp\":\"15.80\",\"ec\":\"64.00\"}]";

        if(saveJihasuChart!=null){
            saveJihasuChart = null;
        }else{

        }
        Type listType = new TypeToken<ArrayList<SubJihasuVO>>() {}.getType();
        aryJihasu = new Gson().fromJson(test, listType);
        saveJihasuChart = aryJihasu;
        setGraph();
    }*/

    //보조지하수 관측망 시군구 조회
    void reqJihasuMapChart(final String type, final String code, final String sdate, final String edate) {
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        //KeyboardUtil.hide(getContext(), edittext_num);

                        if(re.getResultCode() == 0) {

                            if(re.getResultData().equals("[]")){
                                Toast.makeText(getActivity(),"데이터가 없습니다.",Toast.LENGTH_LONG);
                                UIUtil.alert(getContext(), "선택된 기간에 대한 조회된 정보가 없습니다.");
                                removeTable();
                                chart.clearValues();
                                chart.invalidate();
                            }else{
                                ArrayList<SubJihasuVO> aryTheme = null;
                                if(saveJihasuChart!=null){
                                    saveJihasuChart = null;
                                }

                                aryJihasu = null;
                                try{
                                /*Type listType = new TypeToken<ArrayList<SubJihasuVO>>() {}.getType();
                                aryJihasu = new Gson().fromJson(re.getResultData(), listType);
                                aryTheme = aryJihasu;*/
                                    Type listType = new TypeToken<ArrayList<SubJihasuVO>>() {}.getType();
                                    aryJihasu = new Gson().fromJson(re.getResultData(), listType);
                                    saveJihasuChart = aryJihasu;
                                } catch (Exception e){
                                    e.printStackTrace();
                                }


                                //Process
                                //Toast.makeText(getContext(),"Chart 데이터 조회 성공",Toast.LENGTH_LONG).show();

                                setGraph();
                            }

                        } else {
                            UIUtil.alert(getContext(), re.getResultData());
                        }
                    }
                });
        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", String.format("method=jihasuMapChart;type=%s;code=%s;start_date=%s;end_date=%s", type, code, sdate, edate));
        lib.request(intent);
    }
/*
    void reqJihasuMapDetailTest(final String subJihasuCode, final String sdate, final String edate){
        String test = "[{\"avg_gwdep\":\"16.34\",\"avg_gwtemp\":\"14.79\",\"avg_ec\":\"266.73\",\"max_gwdep\":\"16.50\",\"max_gwtemp\":\"14.82\",\"max_ec\":\"275\",\"min_gwdep\":\"16.15\",\"min_gwtemp\":\"14.77\",\"min_ec\":\"258\",\"standard_gwdep\":\"0.06\",\"standard_gwtemp\":\"0.01\",\"standard_ec\":\"2.47\",\"range_gwdep\":\"0.35\",\"range_gwtemp\":\"0.05\",\"range_ec\":\"17\",\"center_gwdep\":\"16.34\",\"center_gwtemp\":\"14.79\",\"center_ec\":\"267\"}]";
        *//*JSONObject json = null;
        try{
            json = new JSONObject(test);

        }catch (Exception e){

        }*//*

        ArrayList<SubJihasuVO> aryTheme = null;
        Type listType = new TypeToken<ArrayList<SubJihasuVO>>() {}.getType();
        aryJihasu = new Gson().fromJson(test, listType);
        aryTheme = aryJihasu;
        setTable(aryTheme);
    }*/

    //보조지하수 관측망 시군구 조회
    void reqJihasuMapDetail(final String subJihasuCode, final String startdate, final String enddate) {
        UIUtil.showProgress(getContext());
        ServiceBrokerLib lib = new ServiceBrokerLib(getContext(),
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();
                        //KeyboardUtil.hide(getContext(), edittext_num);

                        if(re.getResultCode() == 0) {

                            if(re.getResultData().equals("[]")){
                                Toast.makeText(getActivity(),"데이터가 없습니다.",Toast.LENGTH_LONG);
                                //UIUtil.alert(getContext(), "데이터가 없습니다.");
                                removeTable();
                                chart.clearValues();
                                chart.invalidate();
                            }else{
                                ArrayList<SubJihasuVO> aryTheme = null;
                                //ArrayList<ThemeVO> aryTheme = null;

                                aryJihasu = null;
                                try{
                                    Type listType = new TypeToken<ArrayList<SubJihasuVO>>() {}.getType();
                                    aryJihasu = new Gson().fromJson(re.getResultData(), listType);
                                    aryTheme = aryJihasu;
                                } catch (Exception e){
                                    e.printStackTrace();
                                }


                                //Toast.makeText(getContext(),"테이블 데이터 조회 성공",Toast.LENGTH_LONG).show();
                                //Process...
                                setTable(aryTheme);
                            }

                        } else {
                            UIUtil.alert(getContext(), re.getResultData());
                        }
                    }
                });
        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", String.format("method=jihasuMapDetail;code=%s;start_date=%s;end_date=%s", subJihasuCode, startdate, enddate));
        lib.request(intent);
    }

    String getRadioGraph(){
        String result = "";
        if(radio_graph_1.isChecked()){
            result = "gwdep";
        }else if(radio_graph_2.isChecked()){
            result = "gwtemp";
        }else if(radio_graph_3.isChecked()){
            result = "ec";
        }

        return result;
    }

    String getRadioType(){
        String result = "";
        if(radio_sel_day.isChecked()){
            result = "day";
        }else if(radio_sel_month.isChecked()){
            result = "month";
        }else if(radio_sel_year.isChecked()){
            result = "year";
        }

        return result;
    }


    View.OnClickListener onClickList = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SearchVO search = (SearchVO) view.getTag();

            //선택처리
            for(int i=0; i<arySearch.size(); i++) {
                if(arySearch.get(i).is_selected) {
                    arySearch.get(i).is_selected = false;
                    searchRecyclerAdapter.notifyItemChanged(i);
                }
            }
            search.is_selected = true;
            searchRecyclerAdapter.notifyItemChanged(arySearch.indexOf(search));

            //지도이동
            //double lat = Double.parseDouble(search.lttd_dg) + Double.parseDouble(search.lttd_mint) / 60. + Double.parseDouble(search.lttd_sc) / 3600.;
            //double lon = Double.parseDouble(search.litd_dg) + Double.parseDouble(search.litd_mint) / 60. + Double.parseDouble(search.litd_sc) / 3600.;

            // 중심점 변경
            //mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat, lon), true);
            // 줌 레벨 변경
            mapViewSub.setZoomLevel(4, true);

            MapPOIItem[] items = mapViewSub.getPOIItems();
            if(items != null && items.length > 0) {
                for(int i=0; i<items.length; i++) {
                    if(items[i].getTag() == 1 && items[i].getUserObject().equals(search)) {
                        mapViewSub.selectPOIItem(items[i], true);
                        break;
                    }
                }
            }

//            String stc = "", pnn = "";
//            if(search != null) {
//                stc = search.sf_team_code;
//                pnn = search.perm_nt_no;
//            }
//            ((MainActivity)getActivity()).screenMove(DetailInfoFragment.class, stc, pnn);
        }
    };

    void addMarker() {
        MapPOIItem[] items = mapViewSub.getPOIItems();
        if(items != null && items.length > 0) {
            for(int i=0; i<items.length; i++) {
                if(items[i].getTag() == 1) {
                    mapViewSub.removePOIItem(items[i]);
                }
            }
        }

        double minX=999999999, minY=999999999, maxX=0, maxY=0;
        for(int i=0; i<arySearch.size(); i++) {
            SearchVO search = arySearch.get(i);

            try {
                //얘가 null인 경우가 있음..
                //if(search.lttd_dg == null) search.lttd_dg = "37";
                double lat = Double.parseDouble(search.lttd_dg) + Double.parseDouble(search.lttd_mint) / 60. + Double.parseDouble(search.lttd_sc) / 3600.;
                double lon = Double.parseDouble(search.litd_dg) + Double.parseDouble(search.litd_mint) / 60. + Double.parseDouble(search.litd_sc) / 3600.;

                if(lon < minX) minX = lon;
                if(lon > maxX) maxX = lon;
                if(lat < minY) minY = lat;
                if(lat > maxY) maxY = lat;

                MapPOIItem customMarker = new MapPOIItem();
                customMarker.setItemName("Custom Marker");
                customMarker.setTag(1);
                customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
                customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
                customMarker.setCustomImageBitmap(bmpIcon);
                customMarker.setCustomSelectedImageBitmap(bmpIconSel);
                customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                customMarker.setCustomImageAnchor(0.5f, 0.5f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
                customMarker.setUserObject(search);

                mapViewSub.addPOIItem(customMarker);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //전체 포인트가 다 보이게 바운드 설정
        MapPointBounds bounds = new MapPointBounds(MapPoint.mapPointWithGeoCoord(minY, minX), MapPoint.mapPointWithGeoCoord(maxY, maxX));
        mapViewSub.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, 100));
    }

    void addTheme(String type, ArrayList<ThemeVO> aryTheme) {
        if(type.equals("jihasu")) {
            MapPOIItem[] items = mapViewSub.getPOIItems();
            if(items != null && items.length > 0) {
                for(int i=0; i<items.length; i++) {
                    if(items[i].getTag() == 10) {
                        mapViewSub.removePOIItem(items[i]);
                    }
                }
            }
        } else if(type.equals("gonggong")) {
            MapPOIItem[] items = mapViewSub.getPOIItems();
            if(items != null && items.length > 0) {
                for(int i=0; i<items.length; i++) {
                    if(items[i].getTag() == 11) {
                        mapViewSub.removePOIItem(items[i]);
                    }
                }
            }
        } else if(type.equals("perm")) {
            MapPOIItem[] items = mapViewSub.getPOIItems();
            if(items != null && items.length > 0) {
                for(int i=0; i<items.length; i++) {
                    if(items[i].getTag() == 12) {
                        mapViewSub.removePOIItem(items[i]);
                    }
                }
            }
        } else if(type.equals("civil")) {
            MapPOIItem[] items = mapViewSub.getPOIItems();
            if(items != null && items.length > 0) {
                for(int i=0; i<items.length; i++) {
                    if(items[i].getTag() == 13) {
                        mapViewSub.removePOIItem(items[i]);
                    }
                }
            }
        }

        if(aryTheme != null) {
            for (int i = 0; i < aryTheme.size(); i++) {
                ThemeVO theme = aryTheme.get(i);
                try {
                    double lat = 0.0;
                    double lon = 0.0;

                    if (type.equals("jihasu")) {
                        GeoPoint tm_pt = new GeoPoint(Double.parseDouble(theme.getTmx()), Double.parseDouble(theme.getTmy()));
                        GeoPoint katec_pt = GeoTrans.convert(GeoTrans.GRS80, GeoTrans.GEO, tm_pt);
                        lat = katec_pt.getY();
                        lon = katec_pt.getX();
                    } else {
                        lat = Double.parseDouble(theme.lttd_dg) + Double.parseDouble(theme.lttd_mint) / 60. + Double.parseDouble(theme.lttd_sc) / 3600.;
                        lon = Double.parseDouble(theme.litd_dg) + Double.parseDouble(theme.litd_mint) / 60. + Double.parseDouble(theme.litd_sc) / 3600.;
                    }

                    MapPOIItem customMarker = new MapPOIItem();
                    customMarker.setItemName("Custom Marker");
                    customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
                    customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.

                    if (type.equals("jihasu")) {
                        customMarker.setTag(10);
                        customMarker.setCustomImageBitmap(bmpTheme1);
                        customMarker.setCustomSelectedImageBitmap(bmpTheme1Sel);
                    } else if (type.equals("gonggong")) {
                        customMarker.setTag(11);
                        customMarker.setCustomImageBitmap(bmpTheme2);
                        customMarker.setCustomSelectedImageBitmap(bmpTheme2Sel);
                    } else if (type.equals("perm")) {
                        customMarker.setTag(12);
                        customMarker.setCustomImageBitmap(bmpTheme3);
                        customMarker.setCustomSelectedImageBitmap(bmpTheme3Sel);
                    } else if (type.equals("civil")) {
                        customMarker.setTag(13);
                        customMarker.setCustomImageBitmap(bmpTheme4);
                        customMarker.setCustomSelectedImageBitmap(bmpTheme4Sel);
                    }
                    customMarker.setUserObject(theme);
                    customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                    customMarker.setCustomImageAnchor(0.5f, 0.5f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

                    mapViewSub.addPOIItem(customMarker);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void addSubJihasu(ArrayList<SubJihasuVO> aryTheme) {

        MapPOIItem[] items = mapViewSub.getPOIItems();
        if(items != null && items.length > 0) {
            for(int i=0; i<items.length; i++) {
                if(items[i].getTag() == 14) {
                    mapViewSub.removePOIItem(items[i]);
                }
            }
        }

        if(aryTheme != null) {
            for (int i = 0; i < aryTheme.size(); i++) {
                SubJihasuVO theme = aryTheme.get(i);
                try {
                    double lat = 0.0;
                    double lon = 0.0;

                    GeoPoint tm_pt = new GeoPoint(Double.parseDouble(theme.getTmx()), Double.parseDouble(theme.getTmy()));
                    GeoPoint katec_pt = GeoTrans.convert(GeoTrans.GRS80, GeoTrans.GEO, tm_pt);
                    lat = katec_pt.getY();
                    lon = katec_pt.getX();

                    MapPOIItem customMarker = new MapPOIItem();
                    customMarker.setItemName("Custom Marker");
                    customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
                    customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.

                    customMarker.setTag(14);
                    customMarker.setCustomImageBitmap(bmpTheme1);
                    customMarker.setCustomSelectedImageBitmap(bmpTheme1Sel);

                    customMarker.setUserObject(theme);
                    customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                    customMarker.setCustomImageAnchor(0.5f, 0.5f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

                    mapViewSub.addPOIItem(customMarker);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

        public SearchRecyclerAdapter(){
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_info, viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            SearchVO search = arySearch.get(position);
            viewHolder.imagebtn_click.setTag(search);
            viewHolder.imagebtn_click.setOnClickListener(onClickList);

            viewHolder.linear_root.setSelected(search.is_selected);

            viewHolder.textview_num.setText(StrUtil.hipen(search.perm_nt_no));
            viewHolder.textview_gubun.setText(StrUtil.hipen(search.pub_gbn));
            viewHolder.textview_useage.setText(StrUtil.hipen(search.uwater_srv));
            viewHolder.textview_useage_detail.setText(StrUtil.hipen(search.uwater_dul_srv));
        }

        @Override
        public int getItemCount() {
            if(arySearch == null) return 0;
            else return arySearch.size();
        }

        /**
         * 뷰 재활용을 위한 viewHolder
         */
        public class ViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.linear_root) LinearLayout linear_root;
            @BindView(R.id.imagebtn_click) ImageButton imagebtn_click;
            @BindView(R.id.textview_num) TextView textview_num;
            @BindView(R.id.textview_gubun) TextView textview_gubun;
            @BindView(R.id.textview_useage) TextView textview_useage;
            @BindView(R.id.textview_useage_detail) TextView textview_useage_detail;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getActivity().getLayoutInflater().inflate(R.layout.item_marker_info, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            LinearLayout linear_search = (LinearLayout) mCalloutBalloon.findViewById(R.id.linear_search);
            LinearLayout linear_theme = (LinearLayout) mCalloutBalloon.findViewById(R.id.linear_theme);
            LinearLayout linear_theme_jihasu = (LinearLayout) mCalloutBalloon.findViewById(R.id.linear_theme_jihasu);
            if(poiItem.getTag() == 1) {
                linear_search.setVisibility(View.VISIBLE);
                linear_theme.setVisibility(View.GONE);
                linear_theme_jihasu.setVisibility(View.GONE);
            } else {
                linear_search.setVisibility(View.GONE);

                /*ThemeVO theme = (ThemeVO)poiItem.getUserObject();
                theme.print();*/


                SubJihasuVO subJihasu = (SubJihasuVO)poiItem.getUserObject();
                subJihasu.print();

                if (poiItem.getTag() == 14) {
                    linear_theme_jihasu.setVisibility(View.VISIBLE);
                    linear_theme.setVisibility(View.GONE);

                    TextView textview_obsv_name = (TextView) mCalloutBalloon.findViewById(R.id.textview_obsv_name);
                    TextView textview_addr_jihash = (TextView) mCalloutBalloon.findViewById(R.id.textview_addr_jihash);
                    TextView textview_mgr_org = (TextView) mCalloutBalloon.findViewById(R.id.textview_mgr_org);
                    TextView textview_pyogo = (TextView) mCalloutBalloon.findViewById(R.id.textview_pyogo);
                    TextView textview_insdate = (TextView) mCalloutBalloon.findViewById(R.id.textview_insdate);
                    TextView textview_guldep = (TextView) mCalloutBalloon.findViewById(R.id.textview_guldep);
                    TextView textview_guldia = (TextView) mCalloutBalloon.findViewById(R.id.textview_guldia);

                    String addr = "";
                    if(StrUtil.notEmpty(subJihasu.sido)){
                        addr += subJihasu.sido + " ";
                    }
                    if(StrUtil.notEmpty(subJihasu.sgg)){
                        addr += subJihasu.sgg + " ";
                    }
                    if(StrUtil.notEmpty(subJihasu.umd)){
                        addr += subJihasu.umd + " ";
                    }
                    if(StrUtil.notEmpty(subJihasu.ri)){
                        addr += subJihasu.ri + " ";
                    }
                    if(StrUtil.notEmpty(subJihasu.bunji)){
                        addr += subJihasu.bunji + " ";
                    }

                    textview_obsv_name.setText(StrUtil.hipen(subJihasu.obsv_name));
                    textview_addr_jihash.setText(addr);
                    textview_mgr_org.setText(StrUtil.hipen(subJihasu.mgr_org));
                    textview_pyogo.setText(StrUtil.hipen(subJihasu.pyogo) + " (m)");
                    textview_insdate.setText(StrUtil.hipen(subJihasu.insdate));
                    textview_guldep.setText(StrUtil.hipen(subJihasu.guldep)  + " (mm)");
                    textview_guldia.setText(StrUtil.hipen(subJihasu.guldia)  + " (mm)");
                    subJihasuCode = StrUtil.hipen(subJihasu.obsv_code);

/*
                    //차트 연습
                    LineChart chart = (LineChart) mCalloutBalloon.findViewById(R.id.chart);

                    ArrayList<Entry> valsComp1 = new ArrayList<Entry>();

                    valsComp1.add(new Entry(100.0f,0));
                    valsComp1.add(new Entry(50.0f,1));
                    valsComp1.add(new Entry(750.0f,2));
                    valsComp1.add(new Entry(50.0f,3));

                    LineDataSet setComp1 = new LineDataSet(valsComp1,"test1");
                    setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

                    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                    dataSets.add(setComp1);

                    ArrayList<String> xVals = new ArrayList<String>();
                    xVals.add("1.Q");
                    xVals.add("2.Q");
                    xVals.add("3.Q");
                    xVals.add("4.Q");

                    LineData data = new LineData(xVals, dataSets);

                    chart.setData(data);
                    chart.invalidate();
*/


                } /*else if (poiItem.getTag() == 10) {
                    linear_theme_jihasu.setVisibility(View.VISIBLE);
                    linear_theme.setVisibility(View.GONE);

                    TextView textview_obsv_name = (TextView) mCalloutBalloon.findViewById(R.id.textview_obsv_name);
                    TextView textview_addr_jihash = (TextView) mCalloutBalloon.findViewById(R.id.textview_addr_jihash);
                    TextView textview_mgr_org = (TextView) mCalloutBalloon.findViewById(R.id.textview_mgr_org);
                    TextView textview_pyogo = (TextView) mCalloutBalloon.findViewById(R.id.textview_pyogo);
                    TextView textview_insdate = (TextView) mCalloutBalloon.findViewById(R.id.textview_insdate);
                    TextView textview_guldep = (TextView) mCalloutBalloon.findViewById(R.id.textview_guldep);
                    TextView textview_guldia = (TextView) mCalloutBalloon.findViewById(R.id.textview_guldia);

                    String addr = "";
                    if(StrUtil.notEmpty(theme.sido)){
                        addr += theme.sido + " ";
                    }
                    if(StrUtil.notEmpty(theme.sgg)){
                        addr += theme.sgg + " ";
                    }
                    if(StrUtil.notEmpty(theme.umd)){
                        addr += theme.umd + " ";
                    }
                    if(StrUtil.notEmpty(theme.ri)){
                        addr += theme.ri + " ";
                    }
                    if(StrUtil.notEmpty(theme.bunji)){
                        addr += theme.bunji + " ";
                    }

                    textview_obsv_name.setText(StrUtil.hipen(theme.obsv_name));
                    textview_addr_jihash.setText(addr);
                    textview_mgr_org.setText(StrUtil.hipen(theme.mgr_org));
                    textview_pyogo.setText(StrUtil.hipen(theme.pyogo) + " m");
                    textview_insdate.setText(StrUtil.hipen(theme.insdate));
                    textview_guldep.setText(StrUtil.hipen(theme.guldep)  + " mm");
                    textview_guldia.setText(StrUtil.hipen(theme.guldia)  + " mm");

                } else {
                    linear_theme.setVisibility(View.VISIBLE);
                    linear_theme_jihasu.setVisibility(View.GONE);

                    TextView textview_addr = (TextView) mCalloutBalloon.findViewById(R.id.textview_addr);
                    TextView textview_guanjung_hyungtae = (TextView) mCalloutBalloon.findViewById(R.id.textview_guanjung_hyungtae);
                    TextView textview_yongdo = (TextView) mCalloutBalloon.findViewById(R.id.textview_yongdo);
                    TextView textview_saebu_yongdo = (TextView) mCalloutBalloon.findViewById(R.id.textview_saebu_yongdo);
                    TextView textview_umyong_yebu = (TextView) mCalloutBalloon.findViewById(R.id.textview_umyong_yebu);
                    TextView textview_gulchak_jikgyung = (TextView) mCalloutBalloon.findViewById(R.id.textview_gulchak_jikgyung);
                    TextView textview_gaebal_day = (TextView) mCalloutBalloon.findViewById(R.id.textview_gaebal_day);
                    TextView textview_sulchi_simdo = (TextView) mCalloutBalloon.findViewById(R.id.textview_sulchi_simdo);
                    TextView textview_chisu_plan_amount = (TextView) mCalloutBalloon.findViewById(R.id.textview_chisu_plan_amount);
                    TextView textview_yangsu_nungryuk = (TextView) mCalloutBalloon.findViewById(R.id.textview_yangsu_nungryuk);
                    TextView textview_donryuk_jangchi_maryuk = (TextView) mCalloutBalloon.findViewById(R.id.textview_donryuk_jangchi_maryuk);
                    TextView textview_tochulguan_jikgyung = (TextView) mCalloutBalloon.findViewById(R.id.textview_tochulguan_jikgyung);
                    TextView textview_gonggong_sasul = (TextView) mCalloutBalloon.findViewById(R.id.textview_gonggong_sasul);

                    String addr = "";
                    if(StrUtil.notEmpty(theme.sido)){
                        addr += theme.sido + " ";
                    }
                    if(StrUtil.notEmpty(theme.sgg)){
                        addr += theme.sgg + " ";
                    }
                    if(StrUtil.notEmpty(theme.umd)){
                        addr += theme.umd + " ";
                    }
                    if(StrUtil.notEmpty(theme.ri)){
                        addr += theme.ri + " ";
                    }
                    if(StrUtil.notEmpty(theme.bunji)){
                        addr += theme.bunji + " ";
                    }

                    textview_addr.setText(addr);
                    textview_guanjung_hyungtae.setText(StrUtil.hipen(theme.perm_nt_form));
                    textview_yongdo.setText(StrUtil.hipen(theme.uwater_srv_nm));
                    textview_saebu_yongdo.setText(StrUtil.hipen(theme.uwater_dtl_srv));
                    textview_umyong_yebu.setText(StrUtil.hipen(theme.pota_yn));
                    textview_gulchak_jikgyung.setText(StrUtil.hipen(theme.dig_diam) + " mm");
                    textview_gaebal_day.setText(StrUtil.hipen(theme.dvop_ymd));
                    textview_sulchi_simdo.setText(StrUtil.hipen(theme.dph) + " m");
                    textview_chisu_plan_amount.setText(StrUtil.hipen(theme.frw_pln_qua) + " ㎥/일");
                    textview_yangsu_nungryuk.setText(StrUtil.hipen(theme.rwt_cap) + " ㎥/일");
                    textview_donryuk_jangchi_maryuk.setText(StrUtil.hipen(theme.pump_hrp) + " 마력");
                    textview_tochulguan_jikgyung.setText(StrUtil.hipen(theme.pipe_diam) + " mm");
                    textview_gonggong_sasul.setText(StrUtil.hipen(theme.pub_pri_gbn));
                }*/

            }
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }



    // 앱을 내리면 마크가 많아서 메모리 문제가 발생함, 앱을 내릴때 마크 전부 다지움
    @Override
    public void onPause() {
        super.onPause();
        mapViewSub.removeAllPOIItems();
        subJihasuCode = null;
        chart.clearValues();
        chart.invalidate();
        removeTable();


    }

    // 앱을 다시 실행하면 마크 전부 다시 설정함
    @Override
    public void onResume() {
        super.onResume();
    }

}
