/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.egov.smartqr.vo;


/**
 * @Class Name : SampleVO.java
 * @Description : SampleVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public class SubJihasuVO {

	public String start_date;
	public String end_date;
	public String obsv_date;	//측정일
	public String obsv_code;	//관측망 코드
	public String gwdep;		//지하수수위
	public String gwtemp;		//수온
	public String ec;			//전기전도도
	public String obsv_time;	//측정시간
	public String avg_gwdep; 	//평균 깊이
	public String avg_gwtemp;	//평균 온도
	public String avg_ec;		//평균 전기전도도
	public String max_gwdep;	//최대 깊이
	public String max_gwtemp;	//최대 온도
	public String max_ec;		//최대 전기전도도
	public String min_gwdep;	//최소 깊이
	public String min_gwtemp;	//최소 온도
	public String min_ec;		//최소 전기전도도
	public String standard_gwdep;	//표준편차 깊이
	public String standard_gwtemp;	//표준편차 온도
	public String standard_ec;		//표준편차 전기전도도
	public String range_gwdep;		//범위 깊이
	public String range_gwtemp;	//범위 온도
	public String range_ec;		//범위 전기전도도
	public String center_gwdep;	//중앙값 깊이
	public String center_gwtemp;	//중앙값 온도
	public String center_ec;		//중앙값 전기전도도
	public String sf_team_code;
	public String perm_nt_no;
	public String type;
	public String sido; 
	public String sgg; 
	public String umd; 
	public String ri; 
	public String bunji; 
	public String ho; 
	public String lttd_dg;
	public String lttd_mint;
	public String lttd_sc;
	public String litd_dg;
	public String litd_mint;
	public String litd_sc;
	public String perm_nt_form; 	//관정형태
	public String uwater_srv_nm; 	//용도
	public String uwater_dtl_srv; 	//세부용도
	public String pota_yn; 	//음용여부
	public String dig_diam; 	//굴착직경 (mm)
	public String dvop_ymd; 	//개발일자
	public String dph; 		//설치심도 (m)
	public String frw_pln_qua; //취수계획량 (㎥/일)
	public String rwt_cap;		//양수능력(㎥/일) 
	public String pump_hrp; 	//동력장치마력 (마력)
	public String pipe_diam; 	//토출관 직경 (mm)
	public String pub_pri_gbn;	//공공/사설 여부 
	public String obsv_name;	//관측소명 
	public String mgr_org;		//관리기관 
	public String pyogo;		//표고 
	public String insdate;		//설치일자 
	public String guldep;		//굴착심도 
	public String guldia;		//굴착구경 
	public String dcode;		//징겨코드 
	public String tmx;			//tmx 좌표 
	public String tmy;			//tmy 좌표
	
	public void print() {
		System.out.println(
							"start_date = " + start_date + " | " +
							"end_date = " + end_date + " | " +
							"obsv_date = " + obsv_date + " | " +
							"obsv_code = " + obsv_code + " | " +
							"gwdep = " + gwdep + " | " +
							"gwtemp = " + gwtemp + " | " +
							"ec = " + ec + " | " +
							"obsv_time = " + obsv_time + " | " +
							"avg_gwdep = " + avg_gwdep + " | " +
							"avg_gwtemp = " + avg_gwtemp + " | " +
							"avg_ec = " + avg_ec + " | " +
							"max_gwdep = " + max_gwdep + " | " +
							"max_gwtemp = " + max_gwtemp + " | " +
							"max_ec = " + max_ec + " | " +
							"min_gwdep = " + min_gwdep + " | " +
							"min_gwtemp = " + min_gwtemp + " | " +
							"min_ec = " + min_ec + " | " +
							"standard_gwdep = " + standard_gwdep + " | " +
							"standard_gwtemp = " + standard_gwtemp + " | " +
							"standard_ec = " + standard_ec + " | " +
							"range_gwdep = " + range_gwdep + " | " +
							"range_gwtemp = " + range_gwtemp + " | " +
							"range_ec = " + range_ec + " | " +
							"center_gwdep = " + center_gwdep + " | " +
							"center_gwtemp = " + center_gwtemp + " | " +
							"center_ec = " + center_ec + " | " +
							"sf_team_code = " + sf_team_code + " |  " +
							"perm_nt_no = " + perm_nt_no + " |  " +
							"type = " + type + " |  " +
							"sido = " + sido + " |  " +
							"sgg = " + sgg + " |  " +
							"umd = " + umd + " |  " +
							"ri = " + ri + " |  " +
							"bunji = " + bunji + " |  " +
							"ho = " + ho + " |  " +
							"lttd_dg = " + lttd_dg + " |  " +
							"lttd_mint = " + lttd_mint + " |  " +
							"lttd_sc = " + lttd_sc + " |  " +
							"litd_dg = " + litd_dg + " |  " +
							"litd_mint = " + litd_mint + " |  " +
							"litd_sc = " + litd_sc + " |  " +
							"perm_nt_form = " + perm_nt_form + " |  " +
							"uwater_srv_nm = " + uwater_srv_nm + " |  " +
							"pota_yn = " + pota_yn + " |  " +
							"uwater_dtl_srv = " + uwater_dtl_srv + " |  " +
							"dig_diam = " + dig_diam + " |  " +
							"dvop_ymd = " + dvop_ymd + " |  " +
							"dph = " + dph + " |  " +
							"frw_pln_qua = " + frw_pln_qua + " |  " +
							"rwt_cap = " + rwt_cap + " |  " +
							"pump_hrp = " + pump_hrp + " |  " +
							"pipe_diam = " + pipe_diam + " |  " +
							"pub_pri_gbn = " + pub_pri_gbn + " |  " +
							"obsv_name = " + obsv_name + " |  " +
							"mgr_org = " + mgr_org + " |  " +
							"pyogo = " + pyogo + " |  " +
							"insdate = " + insdate + " |  " +
							"guldep = " + guldep + " |  " +
							"guldia = " + guldia + " |  " +
							"dcode = " + dcode + " |  " +
							"tmx = " + tmx + " |  " +
							"tmy = " + tmy + " |  "
		);
	}

	public String getSf_team_code() {
		return sf_team_code;
	}
	public void setSf_team_code(String sf_team_code) {
		this.sf_team_code = sf_team_code;
	}
	public String getPerm_nt_no() {
		return perm_nt_no;
	}
	public void setPerm_nt_no(String perm_nt_no) {
		this.perm_nt_no = perm_nt_no;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getSgg() {
		return sgg;
	}
	public void setSgg(String sgg) {
		this.sgg = sgg;
	}
	public String getUmd() {
		return umd;
	}
	public void setUmd(String umd) {
		this.umd = umd;
	}
	public String getRi() {
		return ri;
	}
	public void setRi(String ri) {
		this.ri = ri;
	}
	public String getBunji() {
		return bunji;
	}
	public void setBunji(String bunji) {
		this.bunji = bunji;
	}
	public String getHo() {
		return ho;
	}
	public void setHo(String ho) {
		this.ho = ho;
	}
	public String getLttd_dg() {
		return lttd_dg;
	}
	public void setLttd_dg(String lttd_dg) {
		this.lttd_dg = lttd_dg;
	}
	public String getLttd_mint() {
		return lttd_mint;
	}
	public void setLttd_mint(String lttd_mint) {
		this.lttd_mint = lttd_mint;
	}
	public String getLttd_sc() {
		return lttd_sc;
	}
	public void setLttd_sc(String lttd_sc) {
		this.lttd_sc = lttd_sc;
	}
	public String getLitd_dg() {
		return litd_dg;
	}
	public void setLitd_dg(String litd_dg) {
		this.litd_dg = litd_dg;
	}
	public String getLitd_mint() {
		return litd_mint;
	}
	public void setLitd_mint(String litd_mint) {
		this.litd_mint = litd_mint;
	}
	public String getLitd_sc() {
		return litd_sc;
	}
	public void setLitd_sc(String litd_sc) {
		this.litd_sc = litd_sc;
	}
	public String getPerm_nt_form() {
		return perm_nt_form;
	}
	public void setPerm_nt_form(String perm_nt_form) {
		this.perm_nt_form = perm_nt_form;
	}
	public String getUwater_srv_nm() {
		return uwater_srv_nm;
	}
	public void setUwater_srv_nm(String uwater_srv_nm) {
		this.uwater_srv_nm = uwater_srv_nm;
	}
	public String getUwater_dtl_srv() {
		return uwater_dtl_srv;
	}
	public void setUwater_dtl_srv(String uwater_dtl_srv) {
		this.uwater_dtl_srv = uwater_dtl_srv;
	}
	public String getPota_yn() {
		return pota_yn;
	}
	public void setPota_yn(String pota_yn) {
		this.pota_yn = pota_yn;
	}
	public String getDig_diam() {
		return dig_diam;
	}
	public void setDig_diam(String dig_diam) {
		this.dig_diam = dig_diam;
	}
	public String getDvop_ymd() {
		return dvop_ymd;
	}
	public void setDvop_ymd(String dvop_ymd) {
		this.dvop_ymd = dvop_ymd;
	}
	public String getDph() {
		return dph;
	}
	public void setDph(String dph) {
		this.dph = dph;
	}
	public String getFrw_pln_qua() {
		return frw_pln_qua;
	}
	public void setFrw_pln_qua(String frw_pln_qua) {
		this.frw_pln_qua = frw_pln_qua;
	}
	public String getRwt_cap() {
		return rwt_cap;
	}
	public void setRwt_cap(String rwt_cap) {
		this.rwt_cap = rwt_cap;
	}
	public String getPump_hrp() {
		return pump_hrp;
	}
	public void setPump_hrp(String pump_hrp) {
		this.pump_hrp = pump_hrp;
	}
	public String getPipe_diam() {
		return pipe_diam;
	}
	public void setPipe_diam(String pipe_diam) {
		this.pipe_diam = pipe_diam;
	}
	public String getPub_pri_gbn() {
		return pub_pri_gbn;
	}
	public void setPub_pri_gbn(String pub_pri_gbn) {
		this.pub_pri_gbn = pub_pri_gbn;
	}
	public String getObsv_name() {
		return obsv_name;
	}
	public void setObsv_name(String obsv_name) {
		this.obsv_name = obsv_name;
	}
	public String getMgr_org() {
		return mgr_org;
	}
	public void setMgr_org(String mgr_org) {
		this.mgr_org = mgr_org;
	}
	public String getPyogo() {
		return pyogo;
	}
	public void setPyogo(String pyogo) {
		this.pyogo = pyogo;
	}
	public String getInsdate() {
		return insdate;
	}
	public void setInsdate(String insdate) {
		this.insdate = insdate;
	}
	public String getGuldep() {
		return guldep;
	}
	public void setGuldep(String guldep) {
		this.guldep = guldep;
	}
	public String getGuldai() {
		return guldia;
	}
	public void setGuldai(String guldia) {
		this.guldia = guldia;
	}
	public String getDcode() {
		return dcode;
	}
	public void setDcode(String dcode) {
		this.dcode = dcode;
	}
	public String getTmx() {
		return tmx;
	}
	public void setTmx(String tmx) {
		this.tmx = tmx;
	}
	public String getTmy() {
		return tmy;
	}
	public void setTmy(String tmy) {
		this.tmy = tmy;
	}


	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getObsv_date() {
		return obsv_date;
	}

	public void setObsv_date(String obsv_date) {
		this.obsv_date = obsv_date;
	}

	public String getObsv_code() {
		return obsv_code;
	}

	public void setObsv_code(String obsv_code) {
		this.obsv_code = obsv_code;
	}

	public String getGwdep() {
		return gwdep;
	}

	public void setGwdep(String gwdep) {
		this.gwdep = gwdep;
	}

	public String getGwtemp() {
		return gwtemp;
	}

	public void setGwtemp(String gwtemp) {
		this.gwtemp = gwtemp;
	}

	public String getEc() {
		return ec;
	}

	public void setEc(String ec) {
		this.ec = ec;
	}

	public String getObsv_time() {
		return obsv_time;
	}

	public void setObsv_time(String obsv_time) {
		this.obsv_time = obsv_time;
	}

	public String getAvg_gwdep() {
		return avg_gwdep;
	}

	public void setAvg_gwdep(String avg_gwdep) {
		this.avg_gwdep = avg_gwdep;
	}

	public String getAvg_gwtemp() {
		return avg_gwtemp;
	}

	public void setAvg_gwtemp(String avg_gwtemp) {
		this.avg_gwtemp = avg_gwtemp;
	}

	public String getAvg_ec() {
		return avg_ec;
	}

	public void setAvg_ec(String avg_ec) {
		this.avg_ec = avg_ec;
	}

	public String getMax_gwdep() {
		return max_gwdep;
	}

	public void setMax_gwdep(String max_gwdep) {
		this.max_gwdep = max_gwdep;
	}

	public String getMax_gwtemp() {
		return max_gwtemp;
	}

	public void setMax_gwtemp(String max_gwtemp) {
		this.max_gwtemp = max_gwtemp;
	}

	public String getMax_ec() {
		return max_ec;
	}

	public void setMax_ec(String max_ec) {
		this.max_ec = max_ec;
	}

	public String getMin_gwdep() {
		return min_gwdep;
	}

	public void setMin_gwdep(String min_gwdep) {
		this.min_gwdep = min_gwdep;
	}

	public String getMin_gwtemp() {
		return min_gwtemp;
	}

	public void setMin_gwtemp(String min_gwtemp) {
		this.min_gwtemp = min_gwtemp;
	}

	public String getMin_ec() {
		return min_ec;
	}

	public void setMin_ec(String min_ec) {
		this.min_ec = min_ec;
	}

	public String getStandard_gwdep() {
		return standard_gwdep;
	}

	public void setStandard_gwdep(String standard_gwdep) {
		this.standard_gwdep = standard_gwdep;
	}

	public String getStandard_gwtemp() {
		return standard_gwtemp;
	}

	public void setStandard_gwtemp(String standard_gwtemp) {
		this.standard_gwtemp = standard_gwtemp;
	}

	public String getStandard_ec() {
		return standard_ec;
	}

	public void setStandard_ec(String standard_ec) {
		this.standard_ec = standard_ec;
	}

	public String getRange_gwdep() {
		return range_gwdep;
	}

	public void setRange_gwdep(String range_gwdep) {
		this.range_gwdep = range_gwdep;
	}

	public String getRange_gwtemp() {
		return range_gwtemp;
	}

	public void setRange_gwtemp(String range_gwtemp) {
		this.range_gwtemp = range_gwtemp;
	}

	public String getRange_ec() {
		return range_ec;
	}

	public void setRange_ec(String range_ec) {
		this.range_ec = range_ec;
	}

	public String getCenter_gwdep() {
		return center_gwdep;
	}

	public void setCenter_gwdep(String center_gwdep) {
		this.center_gwdep = center_gwdep;
	}

	public String getCenter_gwtemp() {
		return center_gwtemp;
	}

	public void setCenter_gwtemp(String center_gwtemp) {
		this.center_gwtemp = center_gwtemp;
	}

	public String getCenter_ec() {
		return center_ec;
	}

	public void setCenter_ec(String center_ec) {
		this.center_ec = center_ec;
	}

	public String getGuldia() {
		return guldia;
	}

	public void setGuldia(String guldia) {
		this.guldia = guldia;
	}
}
