package com.system.library.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.system.library.application.entity.Patron;
import com.system.library.application.filter.PatronFilter;
import com.system.library.application.model.mapInterface.PatronMapper;
import com.system.library.application.model.request.PatronReqModel;
import com.system.library.application.model.response.PatronResModel;
import com.system.library.application.repository.BorrowingRecordRepository;
import com.system.library.application.repository.PatronRepository;
import com.system.library.application.service.PatronService;
import com.system.library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.library.utils.exception.impl.BusinessLogicViolationException;

@Service
public class DefaultPatronService implements PatronService {

	@Autowired
	PatronRepository patronRepository;

	@Autowired
	BorrowingRecordRepository borrowingRecordRepository;

	@Autowired
	PatronMapper patronMapper;

	@Override
	public List<PatronResModel> getAllPatrons() {
		return patronMapper.mapToPatronResModel(patronRepository.findAll());
	}

	@Override
	public List<PatronResModel> getAllFilteredPatrons(PatronFilter patronFilter, int pageSize, int pageIndex,
			String sortField, String sortOrder) {
		Pageable pageableRequest = null;

		if (sortField != null && !sortField.isBlank() && sortOrder != null && !sortOrder.isBlank()) {
			pageableRequest = PageRequest.of(pageIndex, pageSize,
					sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending()
							: Sort.by(sortField).descending());
		} else {
			pageableRequest = PageRequest.of(pageIndex, pageSize);
		}

		Page<Patron> allPatrons = patronRepository.findAllFilteredPatrons(patronFilter, pageableRequest);

		if (allPatrons.hasContent()) {
			return patronMapper.mapToPatronResModel(allPatrons.getContent());
		}

		return new ArrayList<>();
	}

	@Override
	public PatronResModel createPatron(PatronReqModel patronReqModel) {

		validatePatronReqModel(patronReqModel, 0);

		Patron patron = patronMapper.mapToPatron(patronReqModel);
		patronRepository.save(patron);

		return patronMapper.mapToPatronResModel(patron);
	}

	@Override
	public PatronResModel getPatronById(int id) {

		Patron patron = patronRepository.findById(id).orElse(null);

		if (patron == null) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_NOT_FOUND.name());
		}

		return patronMapper.mapToPatronResModel(patron);
	}

	@Override
	public PatronResModel updatePatronById(int id, PatronReqModel patronReqModel) {

		Patron patron = patronRepository.findById(id).orElse(null);

		if (patron == null) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_NOT_FOUND.name());
		}

		validatePatronReqModel(patronReqModel, id);

		patronRepository.save(patronMapper.mapPatronReqModelToPatron(patronReqModel, patron));

		return patronMapper.mapToPatronResModel(patron);
	}

	@Override
	@Transactional
	public Void deletePatronById(int id) {
		Patron patron = patronRepository.findById(id).orElse(null);

		if (patron == null) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_NOT_FOUND.name());
		}
		borrowingRecordRepository.deleteAllByPatronId(id);
		patronRepository.deleteById(id);

		return null;
	}

	private void validatePatronReqModel(PatronReqModel patronReqModel, int patronId) {
		validatePatronContactInfo(patronReqModel, patronId);
	}

	private void validatePatronContactInfo(PatronReqModel patronReqModel, int patronId) {
		validatePatronEmailUniqueness(patronReqModel, patronId);
		validatePatronPhoneUniqueness(patronReqModel, patronId);

	}

	private void validatePatronPhoneUniqueness(PatronReqModel patronReqModel, int patronId) {
		Patron existingPatron = patronRepository.findByContactInfoPhone(patronReqModel.getContactInfo().getPhone());

		// Update
		if (patronId != 0) {
			if (existingPatron != null && existingPatron.getId() != patronId) {
				throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_PHONE_MUST_BE_UNIQUE.name());
			}

		} else { // Create
			if (existingPatron != null) {
				throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_PHONE_MUST_BE_UNIQUE.name());
			}
		}

	}

	private void validatePatronEmailUniqueness(PatronReqModel patronReqModel, int patronId) {
		Patron existingPatron = patronRepository.findByContactInfoEmail(patronReqModel.getContactInfo().getEmail());

		// Update
		if (patronId != 0) {
			if (existingPatron != null && existingPatron.getId() != patronId) {
				throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_EMAIL_MUST_BE_UNIQUE.name());
			}

		} else { // Create
			if (existingPatron != null) {
				throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_EMAIL_MUST_BE_UNIQUE.name());
			}
		}

	}

}
