package com.system.library.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.library.application.entity.ContactInfo;
import com.system.library.application.entity.Patron;
import com.system.library.application.filter.PatronFilter;
import com.system.library.application.model.mapInterface.PatronMapper;
import com.system.library.application.model.request.ContactInfoReqModel;
import com.system.library.application.model.request.PatronReqModel;
import com.system.library.application.model.response.ContactInfoResModel;
import com.system.library.application.model.response.PatronResModel;
import com.system.library.application.repository.BorrowingRecordRepository;
import com.system.library.application.repository.PatronRepository;
import com.system.library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.library.utils.exception.impl.BusinessLogicViolationException;

@ExtendWith(MockitoExtension.class)
public class DefaultPatronServiceTest {

	@Mock
	private PatronRepository patronRepository;

	@Mock
	private BorrowingRecordRepository borrowingRecordRepository;

	@Mock
	private PatronMapper patronMapper;

	@InjectMocks
	private DefaultPatronService patronService;

	private Patron patron;
	private ContactInfo contactInfo;
	private PatronReqModel patronReqModel;
	private ContactInfoReqModel contactInfoReqModel;
	private PatronResModel patronResModel;
	private ContactInfoResModel contactInfoResModel;

	@BeforeEach
	public void setup() {

		contactInfo = new ContactInfo();
		contactInfo.setEmail("john.doe@example.com");
		contactInfo.setPhone("1234567890");

		patron = new Patron();
		patron.setId(1);
		patron.setName("John Doe");
		patron.setContactInfo(contactInfo);

		contactInfoReqModel = new ContactInfoReqModel();
		contactInfoReqModel.setEmail("john.doe@example.com");
		contactInfoReqModel.setPhone("1234567890");

		patronReqModel = new PatronReqModel();
		patronReqModel.setName("John Doe");
		patronReqModel.setContactInfo(contactInfoReqModel);

		contactInfoResModel = new ContactInfoResModel();
		contactInfoResModel.setEmail("john.doe@example.com");
		contactInfoResModel.setPhone("1234567890");

		patronResModel = new PatronResModel();
		patronResModel.setId(1);
		patronResModel.setName("John Doe");
		patronResModel.setContactInfo(contactInfoResModel);

	}

	@Test
	public void testGetAllPatrons() {
		List<Patron> patrons = Arrays.asList(patron);
		List<PatronResModel> patronResModels = Arrays.asList(patronResModel);

		when(patronRepository.findAll()).thenReturn(patrons);
		when(patronMapper.mapToPatronResModel(patrons)).thenReturn(patronResModels);

		List<PatronResModel> result = patronService.getAllPatrons();
		assertEquals(1, result.size());
		assertEquals("John Doe", result.get(0).getName());
	}

	@Test
	public void testGetAllFilteredPatrons() {
		List<Patron> patrons = Arrays.asList(patron);
		List<PatronResModel> patronResModels = Arrays.asList(patronResModel);
		Page<Patron> page = new PageImpl<>(patrons);
		Pageable pageable = PageRequest.of(0, 5);

		when(patronRepository.findAllFilteredPatrons(any(PatronFilter.class), eq(pageable))).thenReturn(page);
		when(patronMapper.mapToPatronResModel(patrons)).thenReturn(patronResModels);

		List<PatronResModel> result = patronService.getAllFilteredPatrons(new PatronFilter(), 5, 0, "name", "asc");
		assertEquals(1, result.size());
		assertEquals("John Doe", result.get(0).getName());
	}

	@Test
	public void testCreatePatron() {
		when(patronRepository.save(any(Patron.class))).thenReturn(patron);
		when(patronMapper.mapToPatron(any(PatronReqModel.class))).thenReturn(patron);
		when(patronMapper.mapToPatronResModel(patron)).thenReturn(patronResModel);

		PatronResModel result = patronService.createPatron(patronReqModel);
		assertNotNull(result);
		assertEquals("John Doe", result.getName());
	}

	@Test
	public void testGetPatronById_PatronExists() {
		when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
		when(patronMapper.mapToPatronResModel(patron)).thenReturn(patronResModel);

		PatronResModel result = patronService.getPatronById(1);
		assertNotNull(result);
		assertEquals("John Doe", result.getName());
	}

	@Test
	public void testGetPatronById_PatronNotExists() {
		when(patronRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> patronService.getPatronById(1));
		assertEquals(ApiErrorMessageEnum.BCV_PATRON_NOT_FOUND.name(), exception.getMessage());
	}

	@Test
	public void testUpdatePatronById_PatronExists() {
		when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
		when(patronRepository.save(any(Patron.class))).thenReturn(patron);
		when(patronMapper.mapPatronReqModelToPatron(any(PatronReqModel.class), eq(patron))).thenReturn(patron);
		when(patronMapper.mapToPatronResModel(patron)).thenReturn(patronResModel);

		PatronResModel result = patronService.updatePatronById(1, patronReqModel);
		assertNotNull(result);
		assertEquals("John Doe", result.getName());
	}

	@Test
	public void testUpdatePatronById_PatronNotExists() {
		when(patronRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> patronService.updatePatronById(1, patronReqModel));
		assertEquals(ApiErrorMessageEnum.BCV_PATRON_NOT_FOUND.name(), exception.getMessage());
	}

	@Test
	public void testDeletePatronById_PatronExists() {
		when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
		doNothing().when(borrowingRecordRepository).deleteAllByPatronId(1);
		doNothing().when(patronRepository).deleteById(1);

		assertDoesNotThrow(() -> patronService.deletePatronById(1));
		verify(patronRepository, times(1)).deleteById(1);
	}

	@Test
	public void testDeletePatronById_PatronNotExists() {
		when(patronRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> patronService.deletePatronById(1));
		assertEquals(ApiErrorMessageEnum.BCV_PATRON_NOT_FOUND.name(), exception.getMessage());
	}
}
