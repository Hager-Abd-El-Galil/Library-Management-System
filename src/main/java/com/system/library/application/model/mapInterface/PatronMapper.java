package com.system.library.application.model.mapInterface;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.system.library.application.entity.Patron;
import com.system.library.application.model.request.PatronReqModel;
import com.system.library.application.model.response.PatronResModel;

@Mapper(componentModel = "spring")
public interface PatronMapper {

	PatronResModel mapToPatronResModel(Patron patron);

	List<PatronResModel> mapToPatronResModel(List<Patron> patrons);

	Patron mapToPatron(PatronReqModel patronReqModel);

	Patron mapPatronReqModelToPatron(PatronReqModel patronReqModel, @MappingTarget Patron patron);

}
