package com.system.library.application.service;

import java.util.List;

import javax.validation.Valid;

import com.system.library.application.filter.PatronFilter;
import com.system.library.application.model.request.PatronReqModel;
import com.system.library.application.model.response.PatronResModel;

public interface PatronService {

	List<PatronResModel> getAllPatrons();

	List<PatronResModel> getAllFilteredPatrons(PatronFilter patronFilter, int pageSize, int pageIndex, String sortField,
			String sortOrder);

	PatronResModel createPatron(@Valid PatronReqModel patronReqModel);

	PatronResModel getPatronById(int id);

	PatronResModel updatePatronById(int id, @Valid PatronReqModel patronReqModel);

	Void deletePatronById(int id);

}
