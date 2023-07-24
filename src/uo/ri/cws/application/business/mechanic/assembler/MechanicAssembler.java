package uo.ri.cws.application.business.mechanic.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import uo.ri.cws.application.business.mechanic.MechanicService.*;
import java.util.List;

public class MechanicAssembler {

	public static MechanicBLDto toMechanicDto(ResultSet m) throws SQLException {
		MechanicBLDto dto = new MechanicBLDto();
		dto.id = m.getString("id");
		dto.version = m.getLong("version");

		dto.dni = m.getString("dni");
		dto.name = m.getString("name");
		dto.surname = m.getString("surname");
		return dto;
	}

	public static List<MechanicBLDto> toMechanicDtoList(ResultSet rs) throws SQLException {
		List<MechanicBLDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( toMechanicDto( rs ) );
		}

		return res;
	}
	
	/*
	 * Sesi�n 4
	 */
	/*
	public static Optional<MechanicBLDto> toBLDto(Optional<MechanicDALDto> arg) {
		Optional<MechanicBLDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toMechanicDto(arg.get()));
		return result;
	}

	public static List<MechanicBLDto> toDtoList(List<MechanicDALDto> arg) {
		List<MechanicBLDto> result = new ArrayList<MechanicBLDto>();
		for (MechanicDALDto mr : arg)
			result.add(toMechanicDto(mr));
		return result;
	}

	public static MechanicDALDto toDALDto(MechanicBLDto arg) {
		MechanicDALDto result = new MechanicDALDto();
		result.id = arg.id;
		result.version = arg.version;
		result.dni = arg.dni;
		result.name = arg.name;
		result.surname = arg.surname;
		return result;
	}

	private static MechanicBLDto toMechanicDto(MechanicDALDto arg) {

		MechanicBLDto result = new MechanicBLDto();
		result.id = arg.id;
		result.version = arg.version;
		result.name = arg.name;
		result.surname = arg.surname;
		result.dni = arg.dni;
		return result;
	}

	*/
	
}