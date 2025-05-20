package in.ashokitp.service;

import java.util.List;

import in.ashokitp.binding.SearchCriteria;
import in.ashokitp.entity.StudentEnq;
public interface EnquiryService {
	
	public boolean addEnq(StudentEnq se);

	public List<StudentEnq> getEnquiries(Integer cid, SearchCriteria s);

}