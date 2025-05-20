package in.ashokitp.service;

import in.ashokitp.binding.DashboardResponse;
import in.ashokitp.entity.Counsellor;

public interface CounsellorService {
	
	public String saveCounsellor(Counsellor c);
	 
	public Counsellor loginCheck(String email, String pwd);

	public boolean recoverPwd(String email);

	public DashboardResponse getDashboardInfo(Integer cid);

}

