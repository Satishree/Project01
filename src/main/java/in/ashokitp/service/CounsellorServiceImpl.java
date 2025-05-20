package in.ashokitp.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokitp.binding.DashboardResponse;
import in.ashokitp.entity.Counsellor;
import in.ashokitp.entity.StudentEnq;
import in.ashokitp.repo.CounsellorRepo;
import in.ashokitp.repo.StudentEnqRepo;
import in.ashokitp.util.EmailUtils;


@Service
public class CounsellorServiceImpl implements CounsellorService {

	@Autowired
	private CounsellorRepo crepo;
	
	@Autowired
	private StudentEnqRepo srepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String saveCounsellor(Counsellor c) {

		// verify duplicate email
		Counsellor obj = crepo.findByEmail(c.getEmail());

		if (obj != null) {
			return "Duplicate Email";
		}

		Counsellor savedObj = crepo.save(c);
		if (savedObj.getCid() != null) {
			return "Registration Success";
		}
		return "Registration Failed";
	}

	@Override
	public Counsellor loginCheck(String email, String pwd) {
		return crepo.findByEmailAndPwd(email, pwd);
	}

	@Override
	public boolean recoverPwd(String email) {
		Counsellor c = crepo.findByEmail(email);
		if (c == null) {
			return false;
		}
		String subject = "Recover Password - Ashok IT";
		String body = "<h1>Your Password :  " + c.getPwd() + "</h1>";
		return emailUtils.sendEmail(subject, body, email);
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer cid) {
		
		List<StudentEnq> allEnqs = srepo.findByCid(cid);
		
		int enrolledEnqs = allEnqs.stream()
				                  .filter(e -> e.getEnqStatus().equals("Enrolled"))
				                  .collect(Collectors.toList())
				                  .size();
		
		
		DashboardResponse resp = new DashboardResponse();
		
		resp.setTotalEnq(allEnqs.size());
		resp.setEnrolledEnq(enrolledEnqs);
		resp.setLostEnq(allEnqs.size() - enrolledEnqs);
		
		return resp;
	}
}